package com.lothrazar.pickybags;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

// SlotItemHandler#set casts its handler to IItemHandlerModifiable, but the IItemHandler.of(ResourceHandler)
// bridge used by CapabilityUtil (needed since item capabilities now expose ResourceHandler<ItemResource>,
// not IItemHandler directly) never implements it - overwrite via extract+insert instead.
public class SlotItemHandlerFix extends SlotItemHandler {

  public SlotItemHandlerFix(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
    super(itemHandler, index, xPosition, yPosition);
  }

  @Override
  public void set(ItemStack stack) {
    IItemHandler handler = getItemHandler();
    ItemStack existing = handler.getStackInSlot(index);
    if (!existing.isEmpty()) {
      handler.extractItem(index, existing.getCount(), false);
    }
    if (!stack.isEmpty()) {
      handler.insertItem(index, stack, false);
    }
    setChanged();
  }
}
