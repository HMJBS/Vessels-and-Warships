package com.vineLadder.vesselandwarship;

import com.vineLadder.vesselandwarship.entity.Entities;
import com.vineLadder.vesselandwarship.item.Items;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	public void preInit(FMLPreInitializationEvent e){

		Items.preInit();

	}
	public void init(FMLInitializationEvent e){

		Entities.init();


	}
	public void postInit(FMLPostInitializationEvent e){

	}

}
