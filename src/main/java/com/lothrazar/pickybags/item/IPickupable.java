package com.lothrazar.pickybags.item;

import net.minecraft.world.item.ItemStack;

public interface IPickupable extends IOpenable {

  public static final String HOLDING = "holding";

  public boolean canInsert(ItemStack itemPickup);

  default void setBoxInsertable(ItemStack box, boolean edible) {
    box.getOrCreateTag().putBoolean(HOLDING, edible);
  }
}
