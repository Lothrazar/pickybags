package com.lothrazar.pickybags;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.item.ItemResource;

// Capabilities.Item.ITEM/BLOCK now resolve to the transactional ResourceHandler<ItemResource> API;
// bridges back to the old IItemHandler so the rest of this mod's inventory-handling code (written
// against the old simulate-flag interface) doesn't need to be rewritten.
public class CapabilityUtil {

  public static IItemHandler getItemHandler(ItemStack stack) {
    ResourceHandler<ItemResource> handler = stack.getCapability(Capabilities.Item.ITEM, ItemAccess.forStack(stack));
    return handler == null ? null : IItemHandler.of(handler);
  }

  public static IItemHandler getItemHandler(Level level, BlockPos pos, Direction face) {
    ResourceHandler<ItemResource> handler = level.getCapability(Capabilities.Item.BLOCK, pos, face);
    return handler == null ? null : IItemHandler.of(handler);
  }
}
