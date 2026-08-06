package com.lothrazar.pickybags.item.foodbox;

import com.lothrazar.pickybags.registry.BagsMenuRegistry;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
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
  public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
    super.extractBackground(graphics, mouseX, mouseY, a);
    this.drawBackground(graphics, BagsMenuRegistry.SQUARE);
  }

  /**
   * TODO: good @flib candidate
   */
  protected void drawBackground(GuiGraphicsExtractor graphics, Identifier gui) {
    int relX = (this.width - this.imageWidth) / 2;
    int relY = (this.height - this.imageHeight) / 2;
    graphics.blit(RenderPipelines.GUI_TEXTURED, gui, relX, relY, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
  }
}
