package com.lothrazar.pickybags.item.bag;

import com.lothrazar.pickybags.item.BagsMenuRegistry;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BagScreen extends AbstractContainerScreen<BagContainer> {

  public BagScreen(BagContainer screenContainer, Inventory inv, Component titleIn) {
    super(screenContainer, inv, titleIn);
    this.imageHeight = 221;
    this.inventoryLabelY = 128;
  }

  @Override
  public void render(GuiGraphics ms, int mouseX, int mouseY, float partialTicks) {
    this.renderBackground(ms);
    super.render(ms, mouseX, mouseY, partialTicks);
    this.renderTooltip(ms, mouseX, mouseY);
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
  protected void renderBg(GuiGraphics ms, float partialTicks, int x, int y) {
    this.drawBackground(ms, BagsMenuRegistry.GENERIC_54);
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
