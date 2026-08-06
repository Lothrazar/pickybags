package com.lothrazar.pickybags.item.pickup;

import com.lothrazar.pickybags.item.bag.BagCapability;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.neoforge.items.ItemStackHandler;

public class PickupBagCapability extends ItemStackHandler {

  private final ItemStack stack;
  private final PickupBagItem bag;

  public PickupBagCapability(ItemStack stack) {
    super(PickupBagItem.SLOTS);
    this.stack = stack;
    this.bag = (PickupBagItem) stack.getItem();
    load();
  }

  @Override
  public boolean isItemValid(int slot, ItemStack s) {
    return !(s.getItem() instanceof PickupBagItem)
        && bag.canInsert(s)
        && super.isItemValid(slot, s);
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
      ValueInput input = TagValueInput.create(ProblemReporter.DISCARDING, provider, tag);
      this.deserialize(input);
    }
  }

  private void save() {
    HolderLookup.Provider provider = BagCapability.getProvider();
    if (provider == null) return;
    TagValueOutput output = TagValueOutput.createWithContext(ProblemReporter.DISCARDING, provider);
    this.serialize(output);
    CompoundTag tag = output.buildResult();
    int emptySlots = 0;
    for (int i = 0; i < getSlots(); i++) {
      if (getStackInSlot(i).isEmpty()) emptySlots++;
    }
    tag.putInt("count_empty", emptySlots);
    tag.putInt("count_max", getSlots());
    stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
  }
}
