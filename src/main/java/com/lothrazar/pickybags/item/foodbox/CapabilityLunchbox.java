package com.lothrazar.pickybags.item.foodbox;

import com.lothrazar.library.util.ItemStackUtil;
import com.lothrazar.pickybags.item.bag.BagCapability;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.neoforge.items.ItemStackHandler;

public class CapabilityLunchbox extends ItemStackHandler {

  private final ItemStack stack;

  public CapabilityLunchbox(ItemStack stack) {
    super(ItemLunchbox.SLOTS);
    this.stack = stack;
    load();
  }

  @Override
  public boolean isItemValid(int slot, ItemStack s) {
    return ItemStackUtil.isEdible(s) && super.isItemValid(slot, s);
  }

  @Override
  protected void onContentsChanged(int slot) {
    save();
  }

  private void load() {
    HolderLookup.Provider provider = BagCapability.getProvider();
    if (provider == null) return;
    CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
    if (!tag.isEmpty()) {
      this.deserializeNBT(provider, tag);
    }
  }

  private void save() {
    HolderLookup.Provider provider = BagCapability.getProvider();
    if (provider == null) return;
    CompoundTag tag = this.serializeNBT(provider);
    int emptySlots = 0;
    for (int i = 0; i < getSlots(); i++) {
      if (getStackInSlot(i).isEmpty()) emptySlots++;
    }
    tag.putInt("count_empty", emptySlots);
    tag.putInt("count_max", getSlots());
    stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
  }
}
