package com.lothrazar.pickybags.item.slab;

import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class CraftingSlabContainerProvider implements MenuProvider {

  private int slot;

  public CraftingSlabContainerProvider(int s) {
    this.slot = s;
  }

  @Override
  public Component getDisplayName() {
    return Component.translatable("item.pickybags.slab");
  }

  @Override
  public AbstractContainerMenu createMenu(int i, Inventory playerInventory, Player player) {
    return new CraftingSlabContainer(i, playerInventory, player, this.slot);
  }
}
