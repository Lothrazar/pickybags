package com.lothrazar.pickybags.item.bag;

import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class BagContainerProvider implements MenuProvider {

  private int slot;

  public BagContainerProvider(int s) {
    this.slot = s;
  }

  @Override
  public Component getDisplayName() {
    return Component.translatable("item.pickybags.bag");
  }

  @Override
  public AbstractContainerMenu createMenu(int i, Inventory playerInventory, Player player) {
    return new BagContainer(i, playerInventory, player, this.slot);
  }
}
