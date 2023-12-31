package com.lothrazar.pickybags.event;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import com.lothrazar.library.util.SoundUtil;
import com.lothrazar.pickybags.item.IPickupable;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.ItemHandlerHelper;

public class PickupEvents {

  @SubscribeEvent
  public void onPlayerPickup(EntityItemPickupEvent event) {
    if (event.getEntity() instanceof Player) {
      Player player = event.getEntity();
      ItemEntity itemEntity = event.getItem();
      ItemStack resultStack = itemEntity.getItem();
      int origCount = resultStack.getCount();
      for (Integer i : getAllBagSlots(player)) {
        resultStack = tryInsert(player.getInventory().getItem(i), resultStack);
        // loopback 
        if (resultStack.isEmpty()) {
          break;
        }
      }
      if (resultStack.getCount() != origCount) { //eat it
        itemEntity.setItem(resultStack);
        event.setResult(Result.ALLOW);
        SoundUtil.playSound(player, SoundEvents.ITEM_PICKUP);
      }
    }
  }

  public static ItemStack tryInsert(ItemStack bag, ItemStack itemPickup) {
    if (bag.getItem() instanceof IPickupable pug) {
      if (pug.canInsert(itemPickup)) {
        //its a pickup bag with insert allowed
        AtomicReference<ItemStack> returnStack = new AtomicReference<>(ItemHandlerHelper.copyStackWithSize(itemPickup, itemPickup.getCount()));
        bag.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(ih -> {
          returnStack.set(ItemHandlerHelper.insertItem(ih, itemPickup, false));
        });
        return returnStack.get();
      }
    }
    return itemPickup;
  }

  public static List<Integer> getAllBagSlots(Player player) {
    List<Integer> slots = new ArrayList<>();
    for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
      if (isContainer(player.getInventory().getItem(i))) {
        slots.add(i);
      }
    }
    return slots;
  }

  private static boolean isContainer(ItemStack bag) {
    return bag.getItem() instanceof IPickupable;
  }
}
