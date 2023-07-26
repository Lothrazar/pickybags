package com.lothrazar.pickybags.item.pickup;

import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;

public class PickupBagContainerProvider implements MenuProvider {

  private int slot;
  private Item item;

  public PickupBagContainerProvider(int s, Item item) {
    this.item = item;
    this.slot = s;
  }

  @Override
  public Component getDisplayName() {
    return item.getDescription();
  }

  @Override
  public AbstractContainerMenu createMenu(int i, Inventory playerInventory, Player player) {
    return new PickupBagContainer(i, playerInventory, player, this.slot, this.item);
  }
}
