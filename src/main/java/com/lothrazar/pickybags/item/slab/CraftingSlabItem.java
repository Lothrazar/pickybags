package com.lothrazar.pickybags.item.slab;

import com.lothrazar.library.item.ItemFlib;
import com.lothrazar.pickybags.item.IOpenable;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

public class CraftingSlabItem extends ItemFlib implements IOpenable {

  public CraftingSlabItem(Properties properties) {
    super(properties.stacksTo(1), new ItemFlib.Settings().noTooltip().burnTime(200));
  }

  @Override
  public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
    if (!worldIn.isClientSide && !playerIn.isCrouching()) {
      int slot = handIn == InteractionHand.MAIN_HAND ? playerIn.getInventory().selected : 40;
      NetworkHooks.openScreen((ServerPlayer) playerIn, new CraftingSlabContainerProvider(slot), buf -> buf.writeInt(slot));
    }
    return super.use(worldIn, playerIn, handIn);
  }
}
