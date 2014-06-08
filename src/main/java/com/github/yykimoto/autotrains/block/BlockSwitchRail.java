package com.github.yykimoto.autotrains.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRail;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockSwitchRail extends BlockRail{
	@SideOnly(Side.CLIENT)
	private IIcon turnIcon;

	public BlockSwitchRail() {
		super();
		this.setCreativeTab(CreativeTabs.tabRedstone);
		setBlockName("blockSwitch");/* システム名の設定 */
		setBlockTextureName("autotrains:rail");/* ブロックのテクスチャの指定 */

	}

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        return p_149691_2_ >= 6 ? this.turnIcon : this.blockIcon;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        super.registerBlockIcons(register);
        this.turnIcon = register.registerIcon(this.getTextureName() + "_turn");
    }


}
