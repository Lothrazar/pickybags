package com.lothrazar.pickybags.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public interface IPickupable extends IOpenable {

  public static final String HOLDING = "holding";

  public boolean canInsert(ItemStack itemPickup);

  default void setBoxInsertable(ItemStack box, boolean edible) {
    CompoundTag tag = box.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
    tag.putBoolean(HOLDING, edible);
    box.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
  }
}
