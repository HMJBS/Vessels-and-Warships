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
	int direction=0;					//コンストラクタに渡す、旗の軸方向
	float shift=0.0f;					//コンストラクタに渡す、シフト量
	Block block;

	public ItemSimpleFlagPlacer(){

		this.setCreativeTab(CreativeTabs.tabMisc);
		this.setUnlocalizedName(this.UNLOCALIZED_NAME);
		this.setTextureName("vineladder.shipsandvessels:" + this.UNLOCALIZED_NAME);


	}

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x,int y, int z, int side, float posX, float posY, float posZ) {
	//右クリックしたときに何かをするならばtrueを返す。　ここではエンティティ召喚処理まで行う
	//引数x,y,zはアイテムで右クリックしたブロックの座標
	//ブロックに対してアイテムを使用したときに呼び出されるらしい
	//何故か二回呼び出されている？ -> logical clientとlogical serverがそれぞれ呼び出しを行う、　client側で取得したdirection,shiftをserverに伝える必要があり

		System.out.println("onItemUse called");

		if(world.isRemote){

			//サーバー側ならスキップ
			System.out.println("skipped");
			return true;


		}else{


			block=world.getBlock(x, y, z);

			x += Facing.offsetsXForSide[side];	//指定のsideにあるブロックの座標を指定するためのオフセット量
			y += Facing.offsetsYForSide[side];	//
			z += Facing.offsetsZForSide[side];	//取得したオフセット量を座標に足すことで、sideに隣接したブロックの座標を取得

			if(side == 0 || side ==1){

				//ブロックの上下側からクリックしても旗を設置しない
				return false;
			}

			switch(side){
			case 2:	//北からクリック
				direction=0;	//南に軸を設定
				break;
			case 3:	//南からクリック
				direction=2;
				break;
			case 4: //西からクリック
				direction=3;
				break;
			case 5: //東からクリック
				direction=1;
				break;
			}

			if(block.getRenderType() == 11){

				//クリック対象のブロックがフェンスならば、フェンス用のシフト量を設定
				shift=EntitySimpleFlag.SHIFT_FOR_FENCE;
			}else{
				shift=0.0f;
			}


			Entity entity = spawnEntity(world,(double)x+0.5d,(double)y,(double)z+0.5d,direction,shift);

			if(entity !=null){
				if(!player.capabilities.isCreativeMode){

					--itemStack.stackSize;
				}
			}

			return true;
		}

	}


	private Entity spawnEntity(World world, double x, double y, double z,int direction,float shift) {

		System.out.println("spawning : direction=" + direction + " shift=" + shift);
		EntitySimpleFlag simpleFlag = new EntitySimpleFlag(world,direction,shift);
		System.out.println("fin declaration of simpleflag");
		simpleFlag.setLocationAndAngles(x, y, z, MathHelper.wrapAngleTo180_float(world.rand.nextFloat()*360.0f), 0.0f);
		world.spawnEntityInWorld(simpleFlag);
		return simpleFlag;
	}



	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		//なにもないところを右クリックすると呼ばれる
		//右クリックして,onItemUseがfalseを返したときに呼ばれるらしい

		System.out.println("onItemRightClick called");

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

						Entity entity = spawnEntity(world, x, y, z,direction,shift);

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
