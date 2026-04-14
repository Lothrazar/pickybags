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

  @SubscribeEvent
  public void test(ItemEntityPickupEvent.Post event) { // was EntityItemPickupEvent
//    if (event.getEntity() instanceof Player) {
    ModBags.LOGGER.debug(" Post event fired with getOriginalStack ::: ? " + event.getOriginalStack());
    ModBags.LOGGER.debug(" Post event fired with getItemEntity ::: ? " + event.getItemEntity());
    ModBags.LOGGER.debug(" Post event fired with getCurrentStack ::: ? " + event.getCurrentStack());
  }

  @SubscribeEvent
  public void onPlayerPickup(ItemEntityPickupEvent.Pre event) { // was EntityItemPickupEvent
//    if (event.getEntity() instanceof Player) {
    ModBags.LOGGER.debug(  " PRE event fired with state ::: ? " + event.canPickup());

    if(event.canPickup() == TriState.TRUE)
    {
      // TODO
    }
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
      if (resultStack.getCount() != origCount) {
        itemEntity.setItem(resultStack);
        SoundUtil.playSound(player, SoundEvents.ITEM_PICKUP);
        if (resultStack.isEmpty()) {
          // dont pickup empty
          event.setCanPickup(TriState.FALSE);
        }
        else {
          // else pickup the rest ?
          event.setCanPickup(TriState.TRUE);
        }
      }

  }

  public static ItemStack tryInsert(final ItemStack bag, ItemStack itemPickup) {
    if (bag.getItem() instanceof IPickupable pug) {
      ModBags.LOGGER.debug(pug+" Can Insert "+ itemPickup + " ::: ? " + pug.canInsert(itemPickup));
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
