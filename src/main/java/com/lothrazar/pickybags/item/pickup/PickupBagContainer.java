package com.lothrazar.pickybags.item.pickup;

import com.lothrazar.library.core.Const;
import com.lothrazar.library.gui.ContainerFlib;
import com.lothrazar.pickybags.ModBags;
import com.lothrazar.pickybags.registry.BagsMenuRegistry;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class PickupBagContainer extends ContainerFlib {

  public ItemStack bag = ItemStack.EMPTY;
  public int slot;

  public PickupBagContainer(int id, Inventory playerInventory, Player player, int slot, Item item) {
    super(BagsMenuRegistry.PICKUP.get(), id);
    this.playerEntity = player;
    this.playerInventory = playerInventory;
    this.slot = slot;
    this.bag = playerInventory.getItem(this.slot);
    if (this.bag.getItem() != item) {
      ModBags.LOGGER.error("error: bag not found from client slot");
      if (player.getMainHandItem().getItem() == item) {
        this.bag = player.getMainHandItem();
        this.slot = player.getInventory().selected;
      }
      else if (player.getOffhandItem().getItem() == item) {
        this.bag = player.getOffhandItem();
        this.slot = 40;
      }
      else {
        for (int x = 0; x < playerInventory.getContainerSize(); x++) {
          ItemStack stack = playerInventory.getItem(x);
          if (stack.getItem() == item) {
            bag = stack;
            this.slot = x;
            break;
          }
        }
      }
    }
    bag.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
      this.endInv = h.getSlots();
      final int numRows = 3;
      for (int j = 0; j < numRows; ++j) {
        for (int k = 0; k < 9; ++k) {
          this.addSlot(new SlotItemHandler(h, k + j * 9,
              8 + k * Const.SQ,
              Const.SQ + j * Const.SQ));
        }
      }
    });
    layoutPlayerInventorySlots(8, 84);
  }

  @Override
  public void removed(Player playerIn) {
    super.removed(playerIn);
  }

  @Override
  public boolean stillValid(Player playerIn) {
    return !bag.isEmpty();
  }
}
