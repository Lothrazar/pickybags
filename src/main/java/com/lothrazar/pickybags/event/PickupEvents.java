package com.lothrazar.pickybags.event;

import java.util.ArrayList;
import java.util.List;
import com.lothrazar.library.util.SoundUtil;
import com.lothrazar.pickybags.item.IPickupable;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;

public class PickupEvents {

  @SubscribeEvent
  public void onPlayerPickup(ItemEntityPickupEvent.Pre event) { // was EntityItemPickupEvent
//    if (event.getEntity() instanceof Player) {
      Player player = event.getPlayer();
      ItemEntity itemEntity = event.getItemEntity();
      ItemStack resultStack = itemEntity.getItem();
      int origCount = resultStack.getCount();
      for (ItemStack bag : getAllBagSlots(player)) {
        resultStack = tryInsert(bag, resultStack);
        // loopback
        if (resultStack.isEmpty()) {
          break;
        }
      }
      if (resultStack.getCount() != origCount) { //eat it
        itemEntity.setItem(resultStack);
//        event.setResult(net.neoforged.bus.api.Event.Result.ALLOW);
        SoundUtil.playSound(player, SoundEvents.ITEM_PICKUP);
      }

  }

  public static ItemStack tryInsert(final ItemStack bag, ItemStack itemPickup) {
    if (bag.getItem() instanceof IPickupable pug) {
      if (pug.canInsert(itemPickup)) {
        //its a pickup bag with insert allowed
        IItemHandler ih = bag.getCapability(Capabilities.ItemHandler.ITEM);
        if (ih != null) {
          itemPickup = ItemHandlerHelper.insertItem(ih, itemPickup, false);
        }
        return itemPickup;
      }
    }
    return itemPickup;
  }

  public static List<ItemStack> getAllBagSlots(Player player) {
    List<ItemStack> slots = new ArrayList<>();
    //first priority
    //get bags from curios mod (if installed and equipped)
    if (ModList.get().isLoaded("curios")) {
      //
      CuriosUtil.fillWithBags(player, slots);
    }
    //next
    //get bags from player inventory
    for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
      ItemStack itemStack = player.getInventory().getItem(i);
      if (isContainer(itemStack)) {
        slots.add(itemStack);
      }
    }
    return slots;
  }

  public static boolean isContainer(ItemStack bag) {
    return bag.getItem() instanceof IPickupable;
  }
}
