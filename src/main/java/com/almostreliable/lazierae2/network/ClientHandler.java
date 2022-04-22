package com.almostreliable.lazierae2.network;

import com.almostreliable.lazierae2.content.maintainer.MaintainerEntity;
import com.almostreliable.lazierae2.gui.MaintainerScreen;
import com.almostreliable.lazierae2.network.packets.MaintainerSyncPacket;
import com.almostreliable.lazierae2.network.packets.MaintainerSyncPacket.SYNC_FLAGS;
import net.minecraft.client.Minecraft;

final class ClientHandler {

    private ClientHandler() {}

    static <T> void handlePacket(T packet) {
        if (packet instanceof MaintainerSyncPacket maintainerPacket) handleMaintainerSyncPacket(maintainerPacket);
    }

    private static void handleMaintainerSyncPacket(MaintainerSyncPacket packet) {
        var level = Minecraft.getInstance().level;
        if (level == null) return;
        var entity = level.getBlockEntity(packet.getPos());
        if (!(entity instanceof MaintainerEntity maintainer)) return;
        // sync data to the entity in the world
        if ((packet.getFlags() & SYNC_FLAGS.STATE) != 0) {
            maintainer.getCraftRequests().updateState(packet.getSlot(), packet.getState());
        }
        if ((packet.getFlags() & SYNC_FLAGS.STACK) != 0) {
            maintainer.getCraftRequests().updateStackClient(packet.getSlot(), packet.getStack());
        }
        if ((packet.getFlags() & SYNC_FLAGS.COUNT) != 0) {
            maintainer.getCraftRequests().updateCount(packet.getSlot(), packet.getCount());
        }
        if ((packet.getFlags() & SYNC_FLAGS.BATCH) != 0) {
            maintainer.getCraftRequests().updateBatch(packet.getSlot(), packet.getBatch());
        }
        // refresh data on the screen the player is looking at
        var screen = Minecraft.getInstance().screen;
        if (!(screen instanceof MaintainerScreen maintainerScreen)) return;
        if ((packet.getFlags() & SYNC_FLAGS.COUNT) != 0) {
            maintainerScreen.maintainerControl.updateCountBox(packet.getSlot(), packet.getCount());
        }
        if ((packet.getFlags() & SYNC_FLAGS.BATCH) != 0) {
            maintainerScreen.maintainerControl.updateBatchBox(packet.getSlot(), packet.getBatch());
        }
    }
}
