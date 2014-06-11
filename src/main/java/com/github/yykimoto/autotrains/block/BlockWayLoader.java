package com.github.yykimoto.autotrains.block;

import java.util.ArrayList;
import java.util.List;

import com.github.yykimoto.autotrains.AutoTrainsMain;
import com.github.yykimoto.autotrains.tileentity.TileEntityWayLoader;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.command.ICommandSender;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockWayLoader extends BlockContainer {
	@SideOnly(Side.CLIENT)
	IIcon frontIcon;

	@SideOnly(Side.CLIENT)
	IIcon sideIcon;

	@SideOnly(Side.CLIENT)
	IIcon topIcon;

	private List<int[]> switches = new ArrayList<int[]>();
	private List<int[]> rails = new ArrayList<int[]>();

	public BlockWayLoader() {
		super(Material.rock);
		this.setCreativeTab(CreativeTabs.tabRedstone);
		setBlockName("blockWayLoader");/* システム名の設定 */
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.frontIcon = iconRegister
				.registerIcon("autotrains:wayloader_front");
		this.sideIcon = iconRegister.registerIcon("furnace_side");
		this.topIcon = iconRegister.registerIcon("furnace_top");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess iBlockAccess, int x, int y, int z,
			int side) {
		if (side == ForgeDirection.UP.ordinal()
				|| side == ForgeDirection.DOWN.ordinal()) {
			return this.topIcon;
		}
		int meta = iBlockAccess.getBlockMetadata(x, y, z);
		return side == meta ? this.frontIcon : this.sideIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if (side == ForgeDirection.UP.ordinal()
				|| side == ForgeDirection.DOWN.ordinal()) {
			return this.topIcon;
		}
		return side == ForgeDirection.SOUTH.ordinal() ? this.frontIcon
				: this.sideIcon;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z,
			EntityLivingBase entityLivingBase, ItemStack itemStack) {
		int playerDir = MathHelper
				.floor_double((double) (entityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		ForgeDirection[] blockDir = { ForgeDirection.NORTH,
				ForgeDirection.EAST, ForgeDirection.SOUTH, ForgeDirection.WEST };

		world.setBlockMetadataWithNotify(x, y, z,
				blockDir[playerDir].ordinal(), 2);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int side, float dx, float dy, float dz) {
		if (!world.isRemote) {
			Block stationRail = world.getBlock(x, y + 1, z);

			if (!(stationRail == AutoTrainsMain.blockSwitch
					|| stationRail == Block.getBlockFromName("golden_rail")
					|| stationRail == Block.getBlockFromName("detector_rail")
					|| stationRail == Block.getBlockFromName("rail") || stationRail == Block
						.getBlockFromName("activator_rail"))) {
				return true;
			}

			searchStations(world, x, y + 1, z);

			// debug
			// for (int[] rail : rails) {
			// world.setBlock(rail[0], rail[1] - 1, rail[2],
			// Block.getBlockFromName("stone"));
			// }

			List<String> stations = new ArrayList<String>();
			// player.addChatMessage(new ChatComponentText(
			// ((TileEntityWayLoader) world.getTileEntity(x, y,
			// z)).stationsString
			// + "\n"));
		}

		return true;
	}

	public void searchStations(World world, int x, int y, int z) {
		int[] railInt = { x, y, z };
		while (true) {
			if (world.getBlock(x, y, z).equals(
					Block.getBlockFromName("golden_rail"))
					|| world.getBlock(x, y, z).equals(
							Block.getBlockFromName("detector_rail"))
					|| world.getBlock(x, y, z).equals(
							Block.getBlockFromName("rail"))
					|| world.getBlock(x, y, z).equals(
							Block.getBlockFromName("activator_rail"))) {
				rails.add(railInt);
				// debug
				world.setBlock(x, y, z, Block.getBlockFromName("stone"));
				// meta, 0);
			}
			if (world.getBlock(x, y, z).equals(AutoTrainsMain.blockSwitch)) {
				rails.add(railInt);
				switches.add(railInt);
			}
			if (!(world.getBlock(x, y, z).equals(
					Block.getBlockFromName("golden_rail"))
					|| world.getBlock(x, y, z).equals(
							Block.getBlockFromName("detector_rail"))
					|| world.getBlock(x, y, z).equals(
							Block.getBlockFromName("rail"))
					|| world.getBlock(x, y, z).equals(
							Block.getBlockFromName("activator_rail")) || world
					.getBlock(x, y, z).equals(AutoTrainsMain.blockSwitch))) {
				break;
			}
			if (rails.indexOf(railInt) > 0) {
				break;
			}
			
			// 同じ高さ
			searchStations(world, x + 1, y, z);
			searchStations(world, x - 1, y, z);
			searchStations(world, x, y, z + 1);
			searchStations(world, x, y, z - 1);

			// 一個上
			searchStations(world, x + 1, y + 1, z);
			searchStations(world, x - 1, y + 1, z);
			searchStations(world, x, y + 1, z + 1);
			searchStations(world, x, y + 1, z - 1);

			// 一個下
			searchStations(world, x + 1, y - 1, z);
			searchStations(world, x - 1, y - 1, z);
			searchStations(world, x, y - 1, z + 1);
			searchStations(world, x, y - 1, z - 1);
			break;
		}
		return;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityWayLoader();
	}

}
