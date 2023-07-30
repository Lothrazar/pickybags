package com.lothrazar.pickybags.item.foodbox;

import com.lothrazar.library.core.Const;
import com.lothrazar.library.gui.ContainerFlib;
import com.lothrazar.pickybags.ModBags;
import com.lothrazar.pickybags.registry.BagsMenuRegistry;
import com.lothrazar.pickybags.registry.ModBagsRegistry;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerLunchbox extends ContainerFlib {

  public ItemStack bag = ItemStack.EMPTY;
  public int slot;
  public int slotCount;

  public ContainerLunchbox(int id, Inventory playerInventory, Player player, int slot) {
    super(BagsMenuRegistry.LUNCHBOX.get(), id);
    this.playerEntity = player;
    this.playerInventory = playerInventory;
    this.slot = slot;
    this.bag = playerInventory.getItem(this.slot);
    if (this.bag.getItem() != ModBagsRegistry.BOX.get()) {
      ModBags.LOGGER.error("error: bag not found from client slot");
      if (player.getMainHandItem().getItem() instanceof ItemLunchbox) {
        this.bag = player.getMainHandItem();
        this.slot = player.getInventory().selected;
      }
      else if (player.getOffhandItem().getItem() instanceof ItemLunchbox) {
        this.bag = player.getOffhandItem();
        this.slot = 40;
      }
      else {
        for (int x = 0; x < playerInventory.getContainerSize(); x++) {
          ItemStack stack = playerInventory.getItem(x);
          if (stack.getItem() instanceof ItemLunchbox) {
            bag = stack;
            slot = x;
            break;
          }
        }
      }
    }
    bag.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
      this.slotCount = h.getSlots();
      this.endInv = h.getSlots();
      for (int j = 0; j < 3; j++) {
        for (int k = 0; k < 3; k++) {
          addSlot(new SlotItemHandler(h, k + j * 3, 62 + k * Const.SQ, 17 + j * Const.SQ));
        }
      }
    });
    layoutPlayerInventorySlots(8, 84);
  }

  @Override
  public boolean stillValid(Player playerIn) {
    return true;
  }

  @Override
  public void clicked(int slotId, int dragType, ClickType clickTypeIn, Player player) {
    if (!(slotId < 0 || slotId >= this.slots.size())) {
      if (this.slots.get(slotId).getItem().is(ModBagsRegistry.BOX.get())) {
        //lock the bag in place by returning  
        return;
      }
    }
    super.clicked(slotId, dragType, clickTypeIn, player);
  }
}
