package com.github.almostreliable.lazierae2.component;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.ItemStackHandler;

public class InventoryHandler extends ItemStackHandler {

    public static final int NON_INPUT_SLOTS = 2;
    private final TileEntity tile;

    public InventoryHandler(TileEntity tile, int inputSlots) {
        super(inputSlots + NON_INPUT_SLOTS);
        this.tile = tile;
    }

    @Override
    protected void onContentsChanged(int slot) {
        tile.setChanged();
    }
}