package com.lothrazar.pickybags.item.foodbox;

import com.lothrazar.pickybags.registry.BagsMenuRegistry;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ScreenLunchbox extends AbstractContainerScreen<ContainerLunchbox> {

  public ScreenLunchbox(ContainerLunchbox screenContainer, Inventory inv, Component titleIn) {
    super(screenContainer, inv, titleIn);
  }

  @Override
  protected void init() {
    super.init();
    //    CompoundTag nbt = this.menu.bag.getOrCreateTag();
  }

  @Override
  protected void renderBg(GuiGraphics ms, float partialTicks, int x, int y) {
    this.drawBackground(ms, BagsMenuRegistry.SQUARE);
  }

  /**
   * TODO: good @flib candidate
   */
  protected void drawBackground(GuiGraphics ms, ResourceLocation gui) {
    int relX = (this.width - this.imageWidth) / 2;
    int relY = (this.height - this.imageHeight) / 2;
    ms.blit(gui, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
  }
}
