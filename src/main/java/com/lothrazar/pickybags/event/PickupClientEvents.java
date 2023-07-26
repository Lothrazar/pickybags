package com.lothrazar.pickybags.event;

import com.lothrazar.library.util.SoundUtil;
import com.lothrazar.pickybags.item.pickup.PickupBagItem;
import com.lothrazar.pickybags.net.PacketOpenBag;
import com.lothrazar.pickybags.net.PacketRegistry;
import com.lothrazar.pickybags.registry.ModBagsRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PickupClientEvents {

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

  private static boolean isBag(ItemStack bag) {
    return bag.getItem() instanceof PickupBagItem;
  }
}
