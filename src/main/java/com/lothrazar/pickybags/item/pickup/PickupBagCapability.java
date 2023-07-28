package com.lothrazar.pickybags.item.pickup;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;

public class PickupBagCapability implements ICapabilitySerializable<CompoundTag> {

  private ItemStackHandler invo = new ItemStackHandler(PickupBagItem.SLOTS) {

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
      return !(stack.getItem() instanceof PickupBagItem)
          && bag.canInsert(stack)
          && super.isItemValid(slot, stack);
    }
  };
  private final LazyOptional<ItemStackHandler> inventoryCap = LazyOptional.of(() -> invo);
  private PickupBagItem bag;

  public PickupBagCapability(ItemStack stack, CompoundTag nbt) {
    this.bag = (PickupBagItem) stack.getItem();
  }

  @Override
  public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
    if (cap == ForgeCapabilities.ITEM_HANDLER) {
      return inventoryCap.cast();
    }
    return LazyOptional.empty();
  }

  @Override
  public CompoundTag serializeNBT() {
    return invo.serializeNBT();
  }

  @Override
  public void deserializeNBT(CompoundTag nbt) {
    invo.deserializeNBT(nbt);
  }
}
