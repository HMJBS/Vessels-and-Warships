package com.vineLadder.vesselandwarship.client.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

@SideOnly(Side.CLIENT)
public class ModelSimpleFlag extends ModelBase {

	public ModelRenderer canvas;	//旗本体
	private int width=16;		//旗キャンバス横長さ
	private int height=16;		//旗キャンバス縦長さ
	private int thickness=16;
	private static int TEXTURE_SIZE_X = 1440;	//1440
	private static int TEXTURE_SIZE_Y = 360;	//360



	public ModelSimpleFlag(){

		this.canvas = new ModelRenderer(this,0,0);
		this.canvas.addBox(-width/2.0f,0.0f,-thickness/2.0f, width, height, thickness);
		this.canvas.setRotationPoint(0.0f,0.0f,0.0f);
		//this.canvas.setTextureSize(this.TEXTURE_SIZE_X, this.TEXTURE_SIZE_Y);
		this.canvas.mirror=true;

	}

	//argument is explained in
	@Override
	public void render(Entity entity, float time, float walkSpeed, float angles, float rotationYaw,
			float rotationPitch, float scale) {

		super.render(entity, time, walkSpeed, angles, angles, rotationYaw, rotationPitch);
		this.setRotationAngles(time, walkSpeed, angles, rotationYaw, rotationPitch, scale, entity);

		//モデルのレンダリングを行う
		this.canvas.render(scale);
	}

	@Override
	public void setRotationAngles(float time, float walkSpeed, float angles, float rotationYaw,
			float rotationPitch, float scale, Entity entity) {

	}

}
