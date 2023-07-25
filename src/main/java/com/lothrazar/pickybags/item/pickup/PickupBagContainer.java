package com.lothrazar.pickybags.item.pickup;

import com.lothrazar.library.core.Const;
import com.lothrazar.library.gui.ContainerFlib;
import com.lothrazar.pickybags.item.BagsMenuRegistry;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class PickupBagContainer extends ContainerFlib {

  public ItemStack bag = ItemStack.EMPTY;
  public int slot;

  public PickupBagContainer(int id, Inventory playerInventory, Player player, int slot) {
    super(BagsMenuRegistry.PICKUP.get(), id);
    this.playerEntity = player;
    this.playerInventory = playerInventory;
    if (player.getMainHandItem().getItem() instanceof PickupBagItem) {
      this.bag = player.getMainHandItem();
      this.slot = player.getInventory().selected;
    }
    else if (player.getOffhandItem().getItem() instanceof PickupBagItem) {
      this.bag = player.getOffhandItem();
      this.slot = 40;
    }
    else {
      for (int x = 0; x < playerInventory.getContainerSize(); x++) {
        ItemStack stack = playerInventory.getItem(x);
        if (stack.getItem() instanceof PickupBagItem) {
          bag = stack;
          slot = x;
          break;
        }
      }
    }
    this.playerEntity = player;
    this.playerInventory = playerInventory;
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
    return true;
    //    return hand == null
    //        || playerIn.getItemInHand(hand).getItem() instanceof CraftingStickItem;
  }
}
