package com.vineLadder.vesselandwarship.entity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntitySimpleFlag extends Entity {


	//単位はブロック単位
	private float width;
	private float height;
	private int health;
	private static float SHIFT_FOR_FENCE = 0.37f;
	private BufferedImage texture;
	private float textureScale;		//テキスチャの拡大倍率　1.0なら360pixelを1ブロック分で描画
	private int direction;			//旗の軸の方向　0 south z+, 1 west x- ,2 north z-, 3 east x+
	private float shiftAmout;		//旗の軸がある方向へのシフト量　fenceときれいにくっつけるために利用

	//旗のテキスチャファイル名
	private String textureName;
	private ResourceLocation resourceLoc;


	public EntitySimpleFlag(World world) {

		//スーパークラスのコンストラクタを忘れずに
		super(world);

		//テキスチャにしたいpngファイル名
		this.textureName="uk_nav.png";
		this.resourceLoc= new ResourceLocation("vesselandwarship","textures/entity/" + this.textureName);
		this.checkTextureSize();
		this.textureScale=1.0f;
		this.setSize(0.5f, 1.0f);
		this.ignoreFrustumCheck=true;	//エンティティが画面内になくても描画する
		this.direction=0;
		this.shiftAmout=0.0f;
	}

	private void checkTextureSize(){

		/*
		//.minecraftフォルダのFileオブジェクトを取得する方法　assetsの内部ではないのでjar外部のリソースファイルを取得する方法?
		String path=((File)FMLInjectionData.data()[6]).getAbsolutePath();	//return C:\ShipVessels\eclipse\.
		System.out.println(path);



		try {
			texture = ImageIO.read(new File(path));
		} catch (IOException e) {

			System.out.println(path);
			e.printStackTrace();

		}
		*/

		//Minecraft.getMinecraft().getResouseManager()
		//.getResouse(new ResouseLocation(modid, "texture/entity....")).getInputStream()を利用しFileStreamを取得する方法


		try {

			InputStream path;
			path = Minecraft.getMinecraft().getResourceManager().getResource(resourceLoc).getInputStream();
			texture = ImageIO.read(path);
			path.close();

		} catch (IOException e) {

			System.out.println("failed to load texture for SimpleFlag.");
			e.printStackTrace();
			texture=null;
		}

		this.width=texture.getWidth();
		this.height=texture.getHeight();
		System.out.println("checkTextureSize Fin");


	}


	@Override
	public void onUpdate() {

		//必ずスーパークラスを呼ぶ
		super.onUpdate();
	}

	@Override
	public boolean canBeCollidedWith() {

		//ollide with another entity
		//これをfalseにするとプレイヤーはクリックでの干渉ができなくなる？
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

		if(this.isEntityInvulnerable()){
			return false;
		}

		this.setDead();
		return true;
	}



	@Override
	public boolean interactFirst(EntityPlayer entity) {

		/*
		//右クリックで回転　デバッグ用
		this.direction = (this.direction+1)%4;
		System.out.println("direction=" + this.direction);
		*/
		return true;
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

	/* このエンティティのResourceLocationを返す　*/
	public ResourceLocation getResourceLocation(){

		return this.resourceLoc;
	}


	public float getTextureHeight(){
		return this.height;

	}

	public float getTextureWidth(){
		return this.width;

	}

	public float getTextureScale(){
		return this.textureScale;
	}

	public int getDirection() {

		return this.direction;
	}

	public float getShiftAmout(){

		return this.shiftAmout;
	}
}
