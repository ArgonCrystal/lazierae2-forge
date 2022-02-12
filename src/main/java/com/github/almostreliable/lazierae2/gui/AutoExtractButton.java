package com.github.almostreliable.lazierae2.gui;

import com.github.almostreliable.lazierae2.network.AutoExtractPacket;
import com.github.almostreliable.lazierae2.network.PacketHandler;

import java.util.function.BooleanSupplier;

public class AutoExtractButton extends ToggleButton {

    private static final String TEXTURE_ID = "auto_extract";
    private static final int POS_X = 7;
    private static final int POS_Y = 28;
    private static final int BUTTON_SIZE = 18;

    AutoExtractButton(
        MachineScreen screen, BooleanSupplier pressed
    ) {
        super(screen, POS_X, POS_Y, BUTTON_SIZE, BUTTON_SIZE, TEXTURE_ID, pressed);
    }

    @Override
    protected void clickHandler() {
        PacketHandler.CHANNEL.sendToServer(new AutoExtractPacket(!pressed.getAsBoolean()));
    }
}