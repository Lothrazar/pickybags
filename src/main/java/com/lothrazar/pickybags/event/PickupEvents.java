package com.lothrazar.pickybags.event;

import java.util.ArrayList;
import java.util.List;
import com.lothrazar.library.util.SoundUtil;
import com.lothrazar.pickybags.ModBags;
import com.lothrazar.pickybags.item.IPickupable;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;

public class PickupEvents {

//  @SubscribeEvent
//  public void test(ItemEntityPickupEvent.Post event) {
//    ModBags.LOGGER.debug(" Post event fired with getOriginalStack ::: ? " + event.getOriginalStack());
//    ModBags.LOGGER.debug(" Post event fired with getItemEntity ::: ? " + event.getItemEntity());
//    ModBags.LOGGER.debug(" Post event fired with getCurrentStack ::: ? " + event.getCurrentStack());
//  }

  @SubscribeEvent
  public void onPlayerPickup(ItemEntityPickupEvent.Pre event) { // was EntityItemPickupEvent

      ItemEntity itemEntity = event.getItemEntity();
      if (itemEntity.hasPickUpDelay()) {
        //ex: if you throw an item, listen to the normal delay
        return;
      }
      ItemStack resultStack = itemEntity.getItem();
      int origCount = resultStack.getCount();
      Player player = event.getPlayer();

      for (ItemStack bag : getAllBagSlots(player)) {
        resultStack = tryInsert(bag, resultStack);
        // loopback
        if (resultStack.isEmpty()) {
          break;
        }
      }
      if (resultStack.getCount() != origCount) {
        itemEntity.setItem(resultStack);
        SoundUtil.playSound(player, SoundEvents.ITEM_PICKUP);
        if (resultStack.isEmpty()) {
          //  block vanilla pickup of the empty
          event.setCanPickup(TriState.FALSE);
        }
      }

  }

  public static ItemStack tryInsert(final ItemStack bag, ItemStack itemPickup) {
    if (bag.getItem() instanceof IPickupable pug) {
      if (pug.canInsert(itemPickup)) {

        //its a pickup bag with insert allowed
        IItemHandler ih = bag.getCapability(Capabilities.ItemHandler.ITEM);
        if (ih != null) {
          itemPickup = ItemHandlerHelper.insertItem(ih, itemPickup, false);
          ModBags.LOGGER.debug(bag.getItem()  +" Insert item into bag " + itemPickup);
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
