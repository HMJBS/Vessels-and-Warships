package com.vineLadder.vesselandwarship.entity;

import com.vineLadder.vesselandwarship.Main;
import com.vineLadder.vesselandwarship.client.renderer.entity.RenderSimpleFlag;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;

public final class Entities {

	public  final static void init(){

		//trackingRange,updateFrequencyを0にしてはいけない(戒め)
		//あとこれらの値を小さくするとなぜがレンダリングがおかしくなる　
		EntityRegistry.registerModEntity(EntitySimpleFlag.class, "entitysimpleflag", 0, Main.instance, 128, 5, false);
		if(FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			render();
		}
	}

	private static void render() {

		RenderingRegistry.registerEntityRenderingHandler(EntitySimpleFlag.class, new RenderSimpleFlag());

	}

}
