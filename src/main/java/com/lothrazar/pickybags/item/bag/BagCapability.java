package com.lothrazar.pickybags.item.bag;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class BagCapability extends ItemStackHandler {

  private final ItemStack stack;

  public BagCapability(ItemStack stack) {
    super(BagItem.SLOTS);
    this.stack = stack;
    load();
  }

  @Override
  public boolean isItemValid(int slot, ItemStack s) {
    return !(s.getItem() instanceof BagItem) && super.isItemValid(slot, s);
  }

  @Override
  protected void onContentsChanged(int slot) {
    save();
  }

  private void load() {
    HolderLookup.Provider provider = getProvider();
    if (provider == null) return;
    CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
    if (!tag.isEmpty()) {
      this.deserializeNBT(provider, tag);
    }
  }

  private void save() {
    HolderLookup.Provider provider = getProvider();
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

  public static HolderLookup.Provider getProvider() {
    MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
    return server != null ? server.registryAccess() : null;
  }
}
