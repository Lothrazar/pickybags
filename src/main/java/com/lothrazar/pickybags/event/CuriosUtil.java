package com.lothrazar.pickybags.event;

import java.util.List;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

public class CuriosUtil {

  public static ItemStack getInSlot(Player player, int slot) {
    try {
      var itemHandler = CuriosApi.getCuriosHelper().getCuriosHandler(player).orElse(null).getEquippedCurios();
      return itemHandler.getStackInSlot(slot);
    }
    catch (Exception e) {
      return ItemStack.EMPTY;
    }
  }

  public static void fillWithBags(Player player, List<ItemStack> slots) {
    try {
      List<SlotResult> results = CuriosApi.getCuriosHelper().findCurios(player, PickupEvents::isContainer);
      //merge them up
      for (SlotResult sr : results) {
        if (sr != null && !sr.stack().isEmpty()) {
          //finds the stack as expected
          slots.add(sr.stack());
        }
      }
    }
    catch (Exception e) {
      //curios not installed? 
    }
    //no return value, slots list is modified
  }
}
