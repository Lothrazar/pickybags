package com.lothrazar.pickybags.item.pickup;

import java.util.HashSet;
import java.util.Set;
import com.lothrazar.library.item.ItemFlib;
import com.lothrazar.library.util.SoundUtil;
import com.lothrazar.pickybags.item.IPickupable;
import com.lothrazar.pickybags.item.ItemCountContents;
import com.lothrazar.pickybags.registry.ModBagsRegistry;
import com.lothrazar.pickybags.registry.PickupTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.ItemStackHandler;

public class PickupBagItem extends ItemCountContents implements IPickupable {

  public static final int SLOTS = 3 * 9;

  public PickupBagItem(Properties properties) {
    super(properties.stacksTo(1).rarity( Rarity.RARE), new ItemFlib.Settings().tooltip());
  }

  //Right click to open
  @Override
  public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
    if (!worldIn.isClientSide && !playerIn.isCrouching()) {
      int slot = handIn == InteractionHand.MAIN_HAND ? playerIn.getInventory().selected : 40;
      playerIn.openMenu(new PickupBagContainerProvider(slot, playerIn.getItemInHand(handIn).getItem()), buf -> {
        buf.writeInt(slot);
//        buf.writeItem(playerIn.getItemInHand(handIn));
        ItemStack.OPTIONAL_STREAM_CODEC.encode(buf, playerIn.getItemInHand(handIn));
        buf.writeBoolean(false);
      });
    }
    return super.use(worldIn, playerIn, handIn);
  }

  @Override
  public boolean canInsert(ItemStack itemPickup) {
    if (this == ModBagsRegistry.PICKUP_GEMS.get()) {
      return itemPickup.is(PickupTags.GEM_LIKE);
    }
    if (this == ModBagsRegistry.PICKUP_PLANTS.get()) {
      return itemPickup.is(PickupTags.PLANT_LIKE);
    }
    if (this == ModBagsRegistry.PICKUP_ROCKS.get()) {
      return itemPickup.is(PickupTags.STONE_LIKE);
    }
    if (this == ModBagsRegistry.PICKUP_TREES.get()) {
      return itemPickup.is(PickupTags.WOOD_LIKE);
    }
    return false;
  }

  @Override
  public InteractionResult useOn(UseOnContext context) {
    BlockPos pos = context.getClickedPos();
    Level world = context.getLevel();
    Direction face = context.getClickedFace();
    ItemStack bag = context.getItemInHand();
    IItemHandler h = bag.getCapability(Capabilities.ItemHandler.ITEM);
    IItemHandler teHandler = world.getCapability(Capabilities.ItemHandler.BLOCK, pos, face);
    if (h instanceof ItemStackHandler handler && teHandler != null) {
      // dump everything in there
      //the player knows whats in the bag and know where they are dumping into
      for (int i = 0; i < handler.getSlots(); i++) {
        ItemStack stack = handler.getStackInSlot(i);
        ItemStack remaining = stack.copyWithCount(stack.getCount());//ItemHandlerHelper.copyStackWithSize(stack, stack.getCount());
        if (!stack.isEmpty()) {
          remaining = ItemHandlerHelper.insertItem(teHandler, stack, false);
          handler.setStackInSlot(i, remaining);
        }
      }
      SoundUtil.playSound(context.getPlayer(), SoundEvents.UI_BUTTON_CLICK.value());
      return InteractionResult.SUCCESS;
    }
    return InteractionResult.PASS;
  }
}
