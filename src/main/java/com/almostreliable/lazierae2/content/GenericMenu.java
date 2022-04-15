package com.almostreliable.lazierae2.content;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public abstract class GenericMenu<E extends GenericEntity> extends AbstractContainerMenu {

    protected static final int SLOT_SIZE = 18;
    protected static final int PLAYER_INV_SIZE = 36;
    public final E entity;
    private final IItemHandler menuInventory;

    protected GenericMenu(
        MenuType<?> type, int windowId, E entity, Inventory menuInventory
    ) {
        super(type, windowId);
        this.entity = entity;
        this.menuInventory = new InvWrapper(menuInventory);
    }

    @Override
    public boolean stillValid(Player player) {
        return entity.getLevel() != null &&
            AbstractContainerMenu.stillValid(ContainerLevelAccess.create(entity.getLevel(), entity.getBlockPos()),
                player,
                entity.getBlockState().getBlock()
            );
    }

    protected void setupPlayerInventory() {
        // main inventory
        for (var i = 0; i < 3; i++) {
            for (var j = 0; j < 9; j++) {
                addSlot(new SlotItemHandler(menuInventory,
                    j + i * 9 + 9,
                    8 + j * SLOT_SIZE,
                    getSlotY() + i * SLOT_SIZE
                ));
            }
        }
        // hot bar
        for (var i = 0; i < 9; i++) {
            addSlot(new SlotItemHandler(menuInventory, i, 8 + i * SLOT_SIZE, getSlotY() + 58));
        }
    }

    protected abstract void setupContainerInventory();

    protected abstract int getSlotY();
}
