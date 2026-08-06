package com.lothrazar.pickybags.item.pickup;

import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class PickupBagContainerProvider implements MenuProvider {

  private int slot;
  private Item item;
  private boolean isCurios;

  public PickupBagContainerProvider(int s, Item item) {
    this(s, item, false);
  }

  public PickupBagContainerProvider(int slot, Item item, boolean isCurios) {
    this.slot = slot;
    this.item = item;
    this.isCurios = isCurios;
  }

  @Override
  public Component getDisplayName() {
    return item.getName(new ItemStack(item));
  }

  @Override
  public AbstractContainerMenu createMenu(int i, Inventory playerInventory, Player player) {
    return new PickupBagContainer(i, playerInventory, player, this.slot, this.item, this.isCurios);
  }
}
