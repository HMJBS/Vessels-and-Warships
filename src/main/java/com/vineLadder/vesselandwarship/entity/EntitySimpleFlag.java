package com.vineLadder.vesselandwarship.entity;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntitySimpleFlag extends Entity {

	//単位はブロック単位
	private float width;
	private float height;
	private int health;
	private static int MAX_HEALTH=5;
	private BufferedImage texture;

	//旗のテキスチャファイル名
	private String textureName;


	public EntitySimpleFlag(World world) {

		//スーパークラスのコンストラクタを忘れずに
		super(world);
		this.textureName="uk_nav.png";
		this.checkTextureSize();
		this.setSize(width, height);
		this.health=this.MAX_HEALTH;
	}

	private void checkTextureSize(){
		String path =   "..\\..\\..\\..\\..\\resources\\assets\\vesselandwarship\\textures\\entity\\uk_nav.png";
		try {
			texture = ImageIO.read(new File(path));
		} catch (IOException e) {
			
			System.out.println(path);
			e.printStackTrace();
			
		}

		this.width=texture.getWidth();
		this.height=texture.getHeight();
	}


	@Override
	public void onUpdate() {

		//必ずスーパークラスを呼ぶ
		super.onUpdate();
	}

	@Override
	public boolean canBeCollidedWith() {

		//ollide with another entity
		return true;
	}

	@Override
	public boolean canBePushed() {

		// dont be pushed by another entity
		return false;
	}



	@Override
	public boolean canAttackWithItem() {

		return true;
	}

	@Override
	public boolean isPushedByWater() {

		// dont be pushed by water
		return false;
	}

	@Override
	protected boolean canTriggerWalking() {

		//is movable in crops? maybe
		return false;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float damage) {

       if (this.isEntityInvulnerable())
        {
            return false;
        }
        else
        {
            if (!this.isDead && !this.worldObj.isRemote)
            {
                this.health -= damage;

                if (this.health <= 0)
                {
                    this.setDead();
                }
            }

            return true;
        }
	}



	@Override
	protected void entityInit() {

		//called in Entity();

	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
