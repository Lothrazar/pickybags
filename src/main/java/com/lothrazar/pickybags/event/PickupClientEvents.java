package com.lothrazar.pickybags.event;

import com.lothrazar.library.util.SoundUtil;
import com.lothrazar.pickybags.item.IOpenable;
import com.lothrazar.pickybags.item.IPickupable;
import com.lothrazar.pickybags.net.PacketInsertBag;
import com.lothrazar.pickybags.net.PacketOpenBag;
import com.lothrazar.pickybags.net.PacketRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
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
      if (itemClicked.getItem() instanceof IOpenable) {
        PacketRegistry.INSTANCE.sendToServer(new PacketOpenBag(slotHit.getSlotIndex(), itemClicked.getItem()));
        event.setCanceled(true);
        SoundUtil.playSound(Minecraft.getInstance().player, SoundEvents.UI_BUTTON_CLICK.get());
      }
    }
  }

  @SubscribeEvent
  public void onMouseButtonReleased(ScreenEvent.MouseButtonReleased.Pre event) {
    Minecraft mc = Minecraft.getInstance();
    Screen screen = mc.screen;
    boolean leftClickDown = event.getButton() == 0;
    if (leftClickDown && screen instanceof AbstractContainerScreen<?> gui && !(screen instanceof CreativeModeInventoryScreen)) {
      if (gui.getSlotUnderMouse() != null) {
        Slot slotHit = gui.getSlotUnderMouse();
        ItemStack stackTarget = slotHit.getItem();
        if (stackTarget.getItem() instanceof IPickupable pug
            && mc.player != null
            && mc.player.containerMenu != null) {
          // 
          //
          if (pug.canInsert(mc.player.containerMenu.getCarried())) {
            int slotId = gui.getSlotUnderMouse().getContainerSlot();
            SoundUtil.playSound(mc.player, SoundEvents.UI_BUTTON_CLICK.get());
            PacketRegistry.INSTANCE.sendToServer(new PacketInsertBag(slotId, stackTarget.getItem()));
            event.setCanceled(true);
            //              return;
          }
        }
      }
    }
  }
}
