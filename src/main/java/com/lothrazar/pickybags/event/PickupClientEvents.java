package com.lothrazar.pickybags.event;

import com.lothrazar.library.util.SoundUtil;
import com.lothrazar.pickybags.item.IOpenable;
import com.lothrazar.pickybags.item.IPickupable;
import com.lothrazar.pickybags.net.PacketInsertBag;
import com.lothrazar.pickybags.net.PacketOpenBag;
import com.lothrazar.pickybags.net.PacketRegistry;
import com.lothrazar.pickybags.registry.ModBagsRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class PickupClientEvents {

  //RIGHT CLICK to open bag
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

  //INSERT into  the container on Left click
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
          // never send item only slot in packet
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

  //render toggle
  @SubscribeEvent
  public void onScreenRender(ScreenEvent.Render.Pre event) {
    Minecraft mc = Minecraft.getInstance();
    Screen screen = mc.screen;
    if (screen instanceof AbstractContainerScreen<?> gui && !(screen instanceof CreativeModeInventoryScreen)) {
      ItemStack maybeFood = mc.player.containerMenu.getCarried();
      Inventory playerInventory = mc.player.getInventory();
      for (int x = 0; x < playerInventory.getContainerSize(); x++) {
        ItemStack stack = playerInventory.getItem(x);
        if (stack.getItem() instanceof IPickupable pu) {
          //its edible food // it fits into the box 
          pu.setBoxInsertable(stack, pu.canInsert(maybeFood));
        }
      }
    }
  }

  @OnlyIn(Dist.CLIENT)
  @SubscribeEvent
  public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
    event.register((stack, tintIndex) -> {
      if (tintIndex == 0) { //layer zero is outline, ignore this 
        return 0xFFFFFFFF;
      }
      //layer 1 is overlay  
      return getColour(stack);
    }, ModBagsRegistry.BOX.get()); // TODO: edit textures of bags for layered insert
  }

  private static int getColour(ItemStack stack) {
    if (stack.getItem() instanceof IPickupable &&
        stack.hasTag() && stack.getTag().getBoolean(IPickupable.HOLDING)) {
      // green? return 0x00AAAAFF;
      return 0x000000FF; //  0xFFFF0011;
    }
    return 0xFFFFFFFF;
  }
}
