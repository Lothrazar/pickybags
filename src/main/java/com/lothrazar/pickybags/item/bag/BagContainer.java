package com.lothrazar.pickybags.item.bag;

import com.lothrazar.library.gui.ContainerFlib;
import com.lothrazar.pickybags.item.BagsMenuRegistry;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;

public class BagContainer extends ContainerFlib {
  //  private final TransientCraftingContainer craftMatrix = new TransientCraftingContainer(this, 3, 3);
  //  final ResultContainer craftResult = new ResultContainer();

  //does NOT save inventory into the stack, very simple and plain
  public BagContainer(int id, Inventory playerInventory, Player player, int slot) {
    super(BagsMenuRegistry.BAG.get(), id);
    this.playerEntity = player;
    this.playerInventory = playerInventory;
    //    this.endInv = 10;
    //    this.addSlot(new ResultSlot(playerInventory.player, this.craftMatrix, this.craftResult, 0, 124, 35));
    //    for (int i = 0; i < 3; i++) {
    //      for (int j = 0; j < 3; j++) {
    //        addSlot(new Slot(craftMatrix, j + i * 3, 30 + j * Const.SQ, 17 + i * Const.SQ));
    //      }
    //    }
    layoutPlayerInventorySlots(8, 84);
  }

  @Override
  public void removed(Player playerIn) {
    super.removed(playerIn);
    //this is not the saving version
    //    clearContainer(playerIn, craftMatrix);
  }
  // 
  //  @Override
  //  public boolean canTakeItemForPickAll(ItemStack itemStack, Slot slot) {
  //    return slot.container != craftResult && super.canTakeItemForPickAll(itemStack, slot);
  //  }

  @Override
  public boolean stillValid(Player playerIn) {
    return true;
    //    return hand == null
    //        || playerIn.getItemInHand(hand).getItem() instanceof CraftingStickItem;
  }
}
