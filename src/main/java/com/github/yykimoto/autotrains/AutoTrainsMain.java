package com.github.yykimoto.autotrains;

import com.github.yykimoto.autotrains.block.BlockSwitchRail;
import com.github.yykimoto.autotrains.block.BlockWayLoader;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = AutoTrainsMain.MOD_ID, name = AutoTrainsMain.MOD_NAME, version = AutoTrainsMain.VERSION)
public class AutoTrainsMain {
	public static final String MOD_ID = "autotrains";
	public static final String MOD_NAME = "AutoTrains for Minecraft 1.7.2 a";
	public static final String VERSION = "for 1.7.2 a";

	public static Block blockSwitch;
	public static Block blockWayLoader;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		blockSwitch = new BlockSwitchRail();
		GameRegistry.registerBlock(blockSwitch, "rail");

		blockWayLoader = new BlockWayLoader();
		GameRegistry.registerBlock(blockWayLoader, "wayloader");
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
	}
}
