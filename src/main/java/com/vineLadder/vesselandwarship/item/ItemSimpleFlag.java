package com.vineLadder.vesselandwarship.item;

import com.vineLadder.vesselandwarship.entity.EntitySimpleFlag;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemSimpleFlag extends Item {

	private String unlocalizedName="simpleFlag";
	private int range=10;

	public ItemSimpleFlag(){

		this.maxStackSize=16;
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.setUnlocalizedName(this.unlocalizedName);
		this.setTextureName("vineladder.shipsandvessels:" + this.unlocalizedName);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

        if (world.isRemote)
        {
        	return stack;

        }else{

        	MovingObjectPosition mop = Minecraft.getMinecraft().renderViewEntity.rayTrace(range, 1.0F);
        	if(mop != null)
        	{
        		//Bottom = 0, Top = 1, East = 2, West* = 3, North = 4, South = 5
        	    int side = mop.sideHit;		//ブロックの叩いたside
        	    int x=mop.blockX;
        	    int y=mop.blockY;
        	    int z=mop.blockZ;
        	    Block blockLookingAt = world.getBlock(x,y,z);	//見ているブロック

        	    float shift=0.0f;
        	    int direction=0;

    			x += Facing.offsetsXForSide[side];	//指定のsideにあるブロックの座標を指定するためのオフセット量
    			y += Facing.offsetsYForSide[side];	//
    			z += Facing.offsetsZForSide[side];	//取得したオフセット量を座標に足すことで、sideに隣接したブロックの座標を取得

    			switch(side){
    			default:

    				//上下方向から叩いても何もスポーンさせない
    				return stack;

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

    			if(blockLookingAt.getRenderType() == 11){

    				//クリック対象のブロックがフェンスならば、フェンス用のシフト量を設定
    				shift=EntitySimpleFlag.SHIFT_FOR_FENCE;
    			}else{
    				shift=0.0f;
    			}

    			EntitySimpleFlag entity = new EntitySimpleFlag(world, direction, shift);
    			entity.setLocationAndAngles((double)x+0.5d,(double)y,(double)z+0.5d, 0.0f, 0.0f);
    			world.spawnEntityInWorld(entity);

    			System.out.println("direction=" + direction +"shift=" + shift);

    			if(entity !=null){
    				if(!player.capabilities.isCreativeMode){

    					--stack.stackSize;
    				}
    			}
        	}
        }
        return stack;
    }
}
