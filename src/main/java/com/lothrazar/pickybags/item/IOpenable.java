package com.lothrazar.pickybags.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface IOpenable {

  default boolean isContainer(ItemStack bag) {
    return isContainer(bag.getItem());
  }

  default boolean isContainer(Item bag) {
    return bag == this;
  }
}
