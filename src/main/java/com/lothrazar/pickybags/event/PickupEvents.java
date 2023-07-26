package com.lothrazar.pickybags.event;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import com.lothrazar.library.util.SoundUtil;
import com.lothrazar.pickybags.item.ModBagsRegistry;
import com.lothrazar.pickybags.item.pickup.PickupBagItem;
import com.lothrazar.pickybags.net.PacketOpenBag;
import com.lothrazar.pickybags.net.PacketRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.ItemHandlerHelper;

public class PickupEvents {

  @SubscribeEvent(priority = EventPriority.HIGH) // WAS MouseClickedEvent
  public void onMouseEvent(ScreenEvent.MouseButtonPressed.Pre event) {
    if (event.getScreen() == null || !(event.getScreen() instanceof AbstractContainerScreen<?>)) {
      return;
    }
    AbstractContainerScreen<?> gui = (AbstractContainerScreen<?>) event.getScreen();
    boolean rightClickDown = event.getButton() == 1;
    if (rightClickDown && gui.getSlotUnderMouse() != null) {
      Slot slotHit = gui.getSlotUnderMouse();
      ItemStack itemClicked = slotHit.getItem();
      if (isBag(itemClicked) || itemClicked.getItem() == ModBagsRegistry.SLAB.get() || itemClicked.getItem() == ModBagsRegistry.BAG.get()) {
        PacketRegistry.INSTANCE.sendToServer(new PacketOpenBag(slotHit.index, itemClicked.getItem()));
        event.setCanceled(true);
        SoundUtil.playSound(Minecraft.getInstance().player, SoundEvents.UI_BUTTON_CLICK.get());
      }
    }
  }

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
      }
    }
  }

  public static ItemStack tryInsert(ItemStack bag, ItemStack itemPickup) {
    if (!isBag(bag) || !canInsert(bag, itemPickup)) {
      return itemPickup; // bounce it back un-touched
    }
    AtomicReference<ItemStack> returnStack = new AtomicReference<>(ItemHandlerHelper.copyStackWithSize(itemPickup, itemPickup.getCount()));
    bag.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(ih -> {
      returnStack.set(ItemHandlerHelper.insertItem(ih, itemPickup, false));
    });
    return returnStack.get();
  }

  public static List<Integer> getAllBagSlots(Player player) {
    List<Integer> slots = new ArrayList<>();
    for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
      if (isBag(player.getInventory().getItem(i))) {
        slots.add(i);
      }
    }
    return slots;
  }

  private static boolean isBag(ItemStack bag) {
    return bag.getItem() instanceof PickupBagItem;
  }

  private static boolean canInsert(ItemStack bag, ItemStack itemPickup) {
    if (bag.getItem() instanceof PickupBagItem pu) {
      return PickupBagItem.canInsert(pu, itemPickup);
    }
    return false;
  }
}
