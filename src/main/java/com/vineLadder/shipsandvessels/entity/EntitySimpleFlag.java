package com.vineLadder.shipsandvessels.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntitySimpleFlag extends Entity {

	private float width= 2.0f;
	private float height= 2.0f;

	public EntitySimpleFlag(World world) {

		super(world);
		this.preventEntitySpawning = true;
		this.setSize(width, height);
		this.yOffset = this.height / 2.0F;
		this.isImmuneToFire=true;

	}

	@Override
	public boolean canBeCollidedWith() {

		//do not collide with another entity
		return false;
	}

	@Override
	public boolean canBePushed() {

		// dont be pushed by another entity
		return false;
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
