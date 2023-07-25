package com.lothrazar.pickybags.item.bag;

import com.lothrazar.library.item.ItemFlib;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.network.NetworkHooks;

public class BagItem extends ItemFlib {

  public static final int SLOTS = 81;

  public BagItem(Properties properties) {
    super(properties);
  }

  @Override
  public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
    return new BagCapability(stack, nbt);
  }

  @Override
  public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
    if (!worldIn.isClientSide && !playerIn.isCrouching()) {
      int slot = handIn == InteractionHand.MAIN_HAND ? playerIn.getInventory().selected : 40;
      NetworkHooks.openScreen((ServerPlayer) playerIn, new BagContainerProvider(slot), buf -> buf.writeInt(slot));
    }
    return super.use(worldIn, playerIn, handIn);
  }
}
