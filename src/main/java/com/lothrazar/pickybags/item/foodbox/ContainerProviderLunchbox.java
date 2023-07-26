package com.lothrazar.pickybags.item.foodbox;

import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class ContainerProviderLunchbox implements MenuProvider {

  private int slot;

  public ContainerProviderLunchbox(int s) {
    this.slot = s;
  }

  @Override
  public Component getDisplayName() {
    return Component.translatable("item.pickybags.lunchbox");
  }

  @Override
  public AbstractContainerMenu createMenu(int i, Inventory playerInventory, Player player) {
    return new ContainerLunchbox(i, playerInventory, player, slot);
  }
}
