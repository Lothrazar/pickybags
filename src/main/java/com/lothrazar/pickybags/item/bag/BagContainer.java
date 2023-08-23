package com.lothrazar.pickybags.item.bag;

import com.lothrazar.library.core.Const;
import com.lothrazar.library.gui.ContainerFlib;
import com.lothrazar.pickybags.ModBags;
import com.lothrazar.pickybags.registry.BagsMenuRegistry;
import com.lothrazar.pickybags.registry.ModBagsRegistry;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class BagContainer extends ContainerFlib {

  public ItemStack bag = ItemStack.EMPTY;
  public int slot;

  public BagContainer(int id, Inventory playerInventory, Player player, int slot) {
    super(BagsMenuRegistry.BAG.get(), id);
    this.playerEntity = player;
    this.playerInventory = playerInventory;
    this.slot = slot;
    this.bag = playerInventory.getItem(this.slot);
    this.endInv = BagItem.SLOTS;
    if (this.bag.getItem() != ModBagsRegistry.BAG.get()) {
      ModBags.LOGGER.error(slot + "error: bag not found from client slot");
      if (player.getMainHandItem().getItem() instanceof BagItem) {
        this.bag = player.getMainHandItem();
        this.slot = player.getInventory().selected;
      }
      else if (player.getOffhandItem().getItem() instanceof BagItem) {
        this.bag = player.getOffhandItem();
        this.slot = 40;
      }
      else {
        for (int x = 0; x < playerInventory.getContainerSize(); x++) {
          ItemStack stack = playerInventory.getItem(x);
          if (stack.getItem() instanceof BagItem) {
            bag = stack;
            slot = x;
            break;
          }
        }
      }
    }
    this.playerEntity = player;
    this.playerInventory = playerInventory;
    bag.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
      //      this.endInv = h.getSlots();
      final int numRows = 6;
      for (int j = 0; j < numRows; ++j) {
        for (int k = 0; k < 9; ++k) {
          this.addSlot(new SlotItemHandler(h, k + j * 9,
              8 + k * Const.SQ,
              Const.SQ + j * Const.SQ));
        }
      }
    });
    layoutPlayerInventorySlots(8, 140);
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
