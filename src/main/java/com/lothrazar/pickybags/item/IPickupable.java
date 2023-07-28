package com.lothrazar.pickybags.item;

import net.minecraft.world.item.ItemStack;

public interface IPickupable extends IOpenable {

  public boolean canInsert(ItemStack itemPickup);
}
