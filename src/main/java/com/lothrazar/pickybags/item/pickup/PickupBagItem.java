package com.lothrazar.pickybags.item.pickup;

import com.lothrazar.library.item.ItemFlib;
import com.lothrazar.pickybags.item.ModBagsRegistry;
import com.lothrazar.pickybags.item.PickupTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.network.NetworkHooks;

public class PickupBagItem extends ItemFlib {

  public static final int SLOTS = 3 * 9;

  public PickupBagItem(Properties properties) {
    super(properties);
  }
  //right click to open

  //tooltip qty (SHARE tags )
  // 
  //
  @Override
  public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
    return new PickupBagCapability(stack, nbt);
  }

  @Override
  public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
    if (!worldIn.isClientSide && !playerIn.isCrouching()) {
      int slot = handIn == InteractionHand.MAIN_HAND ? playerIn.getInventory().selected : 40;
      NetworkHooks.openScreen((ServerPlayer) playerIn, new PickupBagContainerProvider(slot, playerIn.getItemInHand(handIn).getItem()), buf -> {
        buf.writeInt(slot);
        buf.writeItem(playerIn.getItemInHand(handIn));
      });
    }
    return super.use(worldIn, playerIn, handIn);
  }

  public static boolean canInsert(Item theBag, ItemStack itemPickup) {
    if (theBag == ModBagsRegistry.PICKUP_GEMS.get()) {
      return itemPickup.is(PickupTags.GEM_LIKE);
    }
    if (theBag == ModBagsRegistry.PICKUP_PLANTS.get()) {
      return itemPickup.is(PickupTags.PLANT_LIKE);
    }
    if (theBag == ModBagsRegistry.PICKUP_ROCKS.get()) {
      return itemPickup.is(PickupTags.STONE_LIKE);
    }
    if (theBag == ModBagsRegistry.PICKUP_TREES.get()) {
      return itemPickup.is(PickupTags.WOOD_LIKE);
    }
    return false;
  }
}
