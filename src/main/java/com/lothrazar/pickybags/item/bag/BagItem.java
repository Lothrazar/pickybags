package com.lothrazar.pickybags.item.bag;

import com.lothrazar.library.item.ItemFlib;
import com.lothrazar.pickybags.item.IOpenable;
import com.lothrazar.pickybags.item.ItemCountContents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.network.NetworkHooks;

public class BagItem extends ItemCountContents implements IOpenable {

  public static final int SLOTS = 6 * 9;

  public BagItem(Properties properties) {
    super(properties.stacksTo(1), new ItemFlib.Settings().tooltip());
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
