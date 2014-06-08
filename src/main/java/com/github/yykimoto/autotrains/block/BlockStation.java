package com.github.yykimoto.autotrains.block;

import com.github.yykimoto.autotrains.tileentity.TileEntityStation;
import com.github.yykimoto.autotrains.tileentity.TileEntityWayLoader;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockRail;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRailPowered;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockStation extends BlockContainer {
	private IIcon topIcon;
	private IIcon bottomIcon;
	private IIcon sideIcon;

	public BlockStation() {
		super(Material.rock);
		this.setCreativeTab(CreativeTabs.tabRedstone);
		setBlockName("blockStation");
		setBlockTextureName("autotrains:station");
	}

	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int side, float dx, float dy, float dz) {
		if (!world.isRemote) {
			
			player.addChatMessage(new ChatComponentText("Added"));
		}

		return true;
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if (side == ForgeDirection.UP.ordinal()) {
			return this.topIcon;
		} else if (side == ForgeDirection.DOWN.ordinal()) {
			return this.bottomIcon;
		} else {
			return sideIcon;
		}
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		this.topIcon = iconRegister.registerIcon("autotrains:station_top");
		this.sideIcon = iconRegister.registerIcon("furnace_side");
		this.bottomIcon = iconRegister.registerIcon("furnace_top");

	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		// TODO 自動生成されたメソッド・スタブ
		return new TileEntityStation();
	}

}
