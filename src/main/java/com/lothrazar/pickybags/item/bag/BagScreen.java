package com.lothrazar.pickybags.item.bag;

import com.lothrazar.pickybags.registry.BagsMenuRegistry;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class BagScreen extends AbstractContainerScreen<BagContainer> {

  public BagScreen(BagContainer screenContainer, Inventory inv, Component titleIn) {
    super(screenContainer, inv, titleIn, 176, 221);
    this.inventoryLabelY = 128;
  }

  @Override
  public void init() {
    super.init();
    //    int x = leftPos + 108;
    //    int y = topPos + 62;
    //    int size = 14;
    // buttons can go here
    //    this.addRenderableWidget(new ButtonTextured(x, y, size, size, TextureEnum.CRAFT_EMPTY, "pickybags.gui.empty", b -> {
    //      //pressed
    //      PacketRegistry.INSTANCE.sendToServer(new PacketCraftAction(CraftingActionEnum.EMPTY));
    //    }));
  }

  @Override
  public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
    super.extractBackground(graphics, mouseX, mouseY, a);
    this.drawBackground(graphics, BagsMenuRegistry.GENERIC_54);
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
