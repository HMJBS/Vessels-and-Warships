package com.vineLadder.vesselandwarship.item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public final class Items {

	public static Item itemSimpleFlag;

	public  final static void preInit(){

		GameRegistry.registerItem(itemSimpleFlag = new ItemSimpleFlag(), "imtesimpleflag");

	}

}
