package com.github.yykimoto.autotrains.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityWayLoader extends TileEntity {

	public String[] stations;
	public String stationsString;

	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound);
		stationsString = nbtTagCompound.getString("stations");
		stations = stationsString.split("\n");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound) {
		super.writeToNBT(nbtTagCompound);
		StringBuilder sb = new StringBuilder();
		for (String station : stations) {
			sb.append("/n");
			sb.append(station);
		}
		stationsString = sb.toString().replaceFirst("\n", "");
		nbtTagCompound.setString("stations", stationsString);
	}
}
