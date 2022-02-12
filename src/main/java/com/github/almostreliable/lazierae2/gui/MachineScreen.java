package com.github.almostreliable.lazierae2.gui;

import com.github.almostreliable.lazierae2.machine.MachineContainer;
import com.github.almostreliable.lazierae2.util.TextUtil;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.Collection;

public class MachineScreen extends ContainerScreen<MachineContainer> {

    private static final int TEXTURE_WIDTH = 178;
    private static final int TEXTURE_HEIGHT = 154;
    private static final int PROGRESS_WIDTH = 40;
    private static final int PROGRESS_HEIGHT = 27;
    private static final int ENERGY_WIDTH = 2;
    private static final int ENERGY_HEIGHT = 58;
    private static final int SLOT_SIZE = 18;
    private static final ResourceLocation TEXTURE = TextUtil.getRL("textures/gui/machine.png");
    private final ResourceLocation progressTexture;
    private final Collection<Widget> renderables = new ArrayList<>();

    public MachineScreen(
        MachineContainer container, PlayerInventory inventory, ITextComponent ignoredTitle
    ) {
        super(container, inventory, StringTextComponent.EMPTY);
        progressTexture = TextUtil.getRL("textures/gui/progress/" + container.getTile().getId() + ".png");
    }

    @Override
    protected void init() {
        super.init();

        addRenderable(new AutoExtractButton(this, () -> menu.getTile().isAutoExtract()));
    }

    @Override
    public void render(MatrixStack matrix, int mX, int mY, float partial) {
        renderBackground(matrix);
        super.render(matrix, mX, mY, partial);
        renderTooltip(matrix, mX, mY);
    }

    @Override
    protected void renderTooltip(MatrixStack matrix, int pX, int pY) {
        // widget tooltips
        for (Widget widget : renderables) {
            if (widget.isHovered() && widget.visible) {
                widget.renderToolTip(matrix, pX, pY);
            }
        }
        super.renderTooltip(matrix, pX, pY);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void renderBg(MatrixStack matrix, float partial, int pX, int pY) {
        // background texture
        if (minecraft == null) return;
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        minecraft.getTextureManager().bind(TEXTURE);
        blit(matrix, leftPos, topPos, 0, 0, 176, 154, TEXTURE_WIDTH, TEXTURE_HEIGHT);

        // upper and lower input slots for triple input machines
        if (menu.getInventory().getInputSlots() == 3) {
            blit(matrix, leftPos + 43, topPos + 7, 43, 28, SLOT_SIZE, SLOT_SIZE, TEXTURE_WIDTH, TEXTURE_HEIGHT);
            blit(matrix, leftPos + 43, topPos + 49, 43, 28, SLOT_SIZE, SLOT_SIZE, TEXTURE_WIDTH, TEXTURE_HEIGHT);
        }

        // energy bar
        int energy = menu.getEnergyStored();
        int capacity = menu.getEnergyCapacity();
        int barHeight = energy > 0 ? energy * ENERGY_HEIGHT / capacity : 0;
        blit(
            matrix,
            leftPos + 166,
            topPos + 8 - barHeight,
            176,
            0,
            ENERGY_WIDTH,
            barHeight,
            TEXTURE_WIDTH,
            TEXTURE_HEIGHT
        );

        // progress bar
        minecraft.getTextureManager().bind(progressTexture);
        int progress = menu.getTile().getProgress();
        int processTime = menu.getTile().getProcessTime();
        int barWidth = progress > 0 ? progress * (PROGRESS_WIDTH / 2) / processTime : 0;
        blit(
            matrix,
            leftPos + 78,
            topPos + 24,
            0,
            0,
            PROGRESS_WIDTH / 2,
            PROGRESS_HEIGHT,
            PROGRESS_WIDTH,
            PROGRESS_HEIGHT
        );
        blit(
            matrix,
            leftPos + 78,
            topPos + 24,
            PROGRESS_WIDTH / 2f,
            0,
            barWidth,
            PROGRESS_HEIGHT,
            PROGRESS_WIDTH,
            PROGRESS_HEIGHT
        );
    }

    private void addRenderable(Widget widget) {
        addButton(widget);
        renderables.add(widget);
    }
}
