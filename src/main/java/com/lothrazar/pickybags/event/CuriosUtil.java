package com.lothrazar.pickybags.event;

import java.util.List;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

public class CuriosUtil {

  public static ItemStack getInSlot(Player player, int slot, Item item) {
    try {
      List<SlotResult> found = CuriosApi.getCuriosInventory(player).orElse(null).findCurios((st) -> {
        return st.getItem() == item;
      });
      //its usually in the correct slot
      if (slot < found.size()) {
        return found.get(slot).stack();
      }
      else {
        return found.get(0).stack();
      }
    }
    catch (Exception e) {
      return ItemStack.EMPTY;
    }
  }

  public static void fillWithBags(Player player, List<ItemStack> slots) {
    try {
      List<SlotResult> results = CuriosApi.getCuriosInventory(player).orElse(null).findCurios(PickupEvents::isContainer);
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
