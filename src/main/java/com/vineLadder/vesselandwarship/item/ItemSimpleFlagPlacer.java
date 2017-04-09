package com.vineLadder.vesselandwarship.item;

import com.vineLadder.vesselandwarship.entity.EntitySimpleFlag;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemSimpleFlagPlacer extends Item {

	static private String UNLOCALIZED_NAME = "itemSimpleFlagPlacer";

	public ItemSimpleFlagPlacer(){

		this.setCreativeTab(CreativeTabs.tabMisc);
		this.setUnlocalizedName(this.UNLOCALIZED_NAME);
		this.setTextureName("vineladder.shipsandvessels:" + this.UNLOCALIZED_NAME);


	}

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x,int y, int z, int side, float posX, float posY, float posZ) {
	//右クリックしたときに何かをするならばtrueを返す。　ここではエンティティ召喚処理まで行う
	//ブロックに対してアイテムを使用したときに呼び出されるらしい

		if(world.isRemote){

			//サーバー側ならスキップ
			return true;

		}else{

			Block block=world.getBlock(x, y, z);
			x += Facing.offsetsXForSide[side];	//指定のsideにあるブロックの座標を指定するためのオフセット量
			y += Facing.offsetsYForSide[side];	//
			z += Facing.offsetsZForSide[side];	//取得したオフセット量を座標に足すことで、sideに隣接したブロックの座標を取得
			double height = 0.0d;

			if(side == 1 && block.getRenderType() == 11){
				//side==1つまブロック上面を見下ろした目線の状態でアイテムを使った　AND
				//その真上にあるブロックのRenderType==11　つまり　フェンスならば

				height=0.5D;

			}

			Entity entity = spawnEntity(world,(double)x+0.5d,(double)y+height,(double)z+0.5d);

			if(entity !=null){

				if(!player.capabilities.isCreativeMode){

					--itemStack.stackSize;
				}
			}

			return true;
		}

	}


	private Entity spawnEntity(World world, double x, double y, double z) {

		EntitySimpleFlag simpleFlag = new EntitySimpleFlag(world);
		simpleFlag.setLocationAndAngles(x, y, z, MathHelper.wrapAngleTo180_float(world.rand.nextFloat()*360.0f), 0.0f);
		world.spawnEntityInWorld(simpleFlag);
		return simpleFlag;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
	//単純にアイテムを使用したときに呼び出されるらしい

		if(world.isRemote){

			//サーバーならスキップ
			return itemStack;

		}else{

			//視点の先の情報を取得できるらしい？
			MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);

			if(movingobjectposition == null){

				return itemStack;

			}else{

				if(movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK){
				//視点の先にあるのがブロックだったら？

					int x = movingobjectposition.blockX;
					int y = movingobjectposition.blockY;
					int z = movingobjectposition.blockZ;

					if(!world.canMineBlock(player, x, y, z)){

						//視点先のブロックが採掘不能なら？
						return itemStack;
					}

					if(!player.canPlayerEdit(x, y, z, movingobjectposition.sideHit, itemStack)){

						//そのブロックを操作することができないなら？
						return itemStack;
					}

					if(world.getBlock(x, y, z) instanceof BlockLiquid){

						Entity entity = spawnEntity(world, x, y, z);

						if(entity != null){
							if(!player.capabilities.isCreativeMode){
								--itemStack.stackSize;
							}
						}
					}
				}

				return itemStack;
			}
		}
	}
}
