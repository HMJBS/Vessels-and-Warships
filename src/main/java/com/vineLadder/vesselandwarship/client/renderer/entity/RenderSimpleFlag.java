package com.vineLadder.vesselandwarship.client.renderer.entity;

import org.lwjgl.opengl.GL11;

import com.vineLadder.vesselandwarship.client.model.ModelSimpleFlag;
import com.vineLadder.vesselandwarship.entity.EntitySimpleFlag;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderSimpleFlag extends Render{

	//参考　https://github.com/defeatedcrow/AppleMilkTea2_1.7.10/blob/master/java/mods/defeatedcrow/client/entity/RenderSandwichEntity.java
	//private static  ResourceLocation texture = new ResourceLocation("vesselandwarship","textures/entity/uk_nav.png");
	protected ModelSimpleFlag model;

	public RenderSimpleFlag(){

		//コンストラクタ
		super();
		//this.model = new ModelSimpleFlag();
		this.shadowSize = 0.5F;


	}

	//実際のレンダリング処理　doRenderの引数のEntityをEntitySimpleFlagでキャストして呼び出される
	public void render(EntitySimpleFlag entity,double posX, double posY, double posZ, float round, float yaw){

		//GL11の機能GL11.glScalef()を使い、モデルの拡大縮小が可能
		//http://jabelarminecraft.blogspot.jp/p/minecraft-forge-172-quick-tips-gl11-and.html

		GL11.glPushMatrix();
		//bindTextureしないと変なＴＥＸＴＵＲＥが貼り付けられる
		this.bindTexture(entity.getResourceLocation());
		//エンティティの描画は目線の先が基準らしいので
		//移動命令glTranslatedで適切な場所に移動させる
		GL11.glTranslated(posX, posY, posZ);
		//this.model.render(entity, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
		this.tesselatorRander(entity);
		GL11.glPopMatrix();

	}

	//tesselatorを使用して旗を直接描画
	private void tesselatorRander(EntitySimpleFlag entity) {

		//http://greyminecraftcoder.blogspot.jp/2013/08/the-tessellator.html
	   Tessellator tessellator = Tessellator.instance;
	   tessellator.startDrawingQuads();
	   //tessellator.setBrightness(lightValue); //多分必要ない
	   tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);

	   float widthByBlock=entity.getTextureWidth()/360.0f*entity.getTextureScale();
	   float heightByBlock=entity.getTextureHeight()/360.0f*entity.getTextureScale();

	   //左3パラメータxyzの単位はブロック単位、uvはテクセル(Texel)単位 テキスチャの辺の長さを1とした時の相対的な座標指定

	   switch(entity.getDirection()){
	   case 0:
	   default:
		   //direction=0 z+軸方向に旗の軸

		   //x+を向く面
		   tessellator.addVertexWithUV(0, 0, 0, 1.0, 1.0);							//右下
		   tessellator.addVertexWithUV(0, heightByBlock, 0, 1.0, 0.0);				//右上
		   tessellator.addVertexWithUV(0, heightByBlock, widthByBlock, 0.0, 0.0);	//左上
		   tessellator.addVertexWithUV(0, 0, widthByBlock, 0.0, 1.0);				//左下

		   //x-を向く面
		   tessellator.addVertexWithUV(0, 0, widthByBlock, 0.0, 1.0);				//左下
		   tessellator.addVertexWithUV(0, heightByBlock, widthByBlock, 0.0, 0.0);	//左上
		   tessellator.addVertexWithUV(0, heightByBlock, 0, 1.0, 0.0);				//右上
		   tessellator.addVertexWithUV(0, 0, 0, 1.0, 1.0);							//右下

		   break;

	   case 1:

		   //direction=1 x-軸方向に旗の軸

		   //z+を向く面
		   tessellator.addVertexWithUV(widthByBlock, 0, 0, 1.0, 1.0);					//右下
		   tessellator.addVertexWithUV(widthByBlock, heightByBlock, 0, 1.0, 0.0);		//右上
		   tessellator.addVertexWithUV(0, heightByBlock, 0, 0.0, 0.0);					//左上
		   tessellator.addVertexWithUV(0, 0, 0, 0.0, 1.0);								//左下

		   //z-を向く面
		   tessellator.addVertexWithUV(0, 0, 0, 0.0, 1.0);								//左下
		   tessellator.addVertexWithUV(0, heightByBlock, 0, 0.0, 0.0);					//左上
		   tessellator.addVertexWithUV(widthByBlock, heightByBlock, 0, 1.0, 0.0);		//右上
		   tessellator.addVertexWithUV(widthByBlock, 0, 0, 1.0, 1.0);					//右下
		   break;

	   case 2:
		   //direction=2 z-軸方向に旗の軸

		   //x+を向く面
		   tessellator.addVertexWithUV(0, 0, 0, 0.0, 1.0);							//右下
		   tessellator.addVertexWithUV(0, heightByBlock, 0, 0.0, 0.0);				//右上
		   tessellator.addVertexWithUV(0, heightByBlock, widthByBlock, 1.0, 0.0);	//左上
		   tessellator.addVertexWithUV(0, 0, widthByBlock, 1.0, 1.0);				//左下

		   //x-を向く面
		   tessellator.addVertexWithUV(0, 0, widthByBlock, 1.0, 1.0);				//左下
		   tessellator.addVertexWithUV(0, heightByBlock, widthByBlock, 1.0, 0.0);	//左上
		   tessellator.addVertexWithUV(0, heightByBlock, 0, 0.0, 0.0);				//右上
		   tessellator.addVertexWithUV(0, 0, 0, 0.0, 1.0);							//右下

		   break;
	   case 3:
		   //direction=3 x+軸方向に旗の軸

		   //z+を向く面
		   tessellator.addVertexWithUV(widthByBlock, 0, 0, 0.0, 1.0);					//右下
		   tessellator.addVertexWithUV(widthByBlock, heightByBlock, 0, 0.0, 0.0);		//右上
		   tessellator.addVertexWithUV(0, heightByBlock, 0, 1.0, 0.0);					//左上
		   tessellator.addVertexWithUV(0, 0, 0, 1.0, 1.0);								//左下

		   //z-を向く面
		   tessellator.addVertexWithUV(0, 0, 0, 1.0, 1.0);								//左下
		   tessellator.addVertexWithUV(0, heightByBlock, 0, 1.0, 0.0);					//左上
		   tessellator.addVertexWithUV(widthByBlock, heightByBlock, 0, 0.0, 0.0);		//右上
		   tessellator.addVertexWithUV(widthByBlock, 0, 0, 0.0, 1.0);					//右下
		   break;

	   }
	   tessellator.draw();

	}

	@Override
	public void doRender(Entity entity, double par1, double par2, double par3, float par4,
			float par5) {

		//レンダリング処理で呼び出され、実際の処理を行う
		//javadocによると、このメソッドを他のメソッドの橋渡しにすべし

		//entityをキャストして渡さなければならないらしい？
		this.render((EntitySimpleFlag)entity,par1,par2,par3,par4,par5);

	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {

		//entitySimpleFlagのメンバであるResourceLocationをキャストを使って無理やり取得(していいのか？)
		return ((EntitySimpleFlag)entity).getResourceLocation();
	}


}
