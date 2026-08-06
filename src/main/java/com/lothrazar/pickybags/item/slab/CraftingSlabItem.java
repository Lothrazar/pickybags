package com.lothrazar.pickybags.item.slab;

import com.lothrazar.library.item.ItemFlib;
import com.lothrazar.pickybags.item.IOpenable;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CraftingSlabItem extends ItemFlib implements IOpenable {

  public CraftingSlabItem(Properties properties) {
    super(properties.stacksTo(1), new ItemFlib.Settings().noTooltip().burnTime(200));
  }

  @Override
  public InteractionResult use(Level worldIn, Player playerIn, InteractionHand handIn) {
    if (!worldIn.isClientSide() && !playerIn.isCrouching()) {
      int slot = handIn == InteractionHand.MAIN_HAND ? playerIn.getInventory().getSelectedSlot() : 40;
      ((ServerPlayer) playerIn).openMenu(new CraftingSlabContainerProvider(slot), buf -> buf.writeInt(slot));
    }
    return super.use(worldIn, playerIn, handIn);
  }
}
