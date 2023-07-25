package com.lothrazar.pickybags.item.pickup;

import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class PickupBagContainerProvider implements MenuProvider {

  private int slot;

  public PickupBagContainerProvider(int s) {
    this.slot = s;
  }

  @Override
  public Component getDisplayName() {
    return Component.translatable("item.pickybags.pickup");
  }

  @Override
  public AbstractContainerMenu createMenu(int i, Inventory playerInventory, Player player) {
    return new PickupBagContainer(i, playerInventory, player, this.slot);
  }
}
