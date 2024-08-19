package com.lothrazar.pickybags.item.bag;

import java.util.HashSet;
import java.util.Set;
import com.lothrazar.library.item.ItemFlib;
import com.lothrazar.library.util.SoundUtil;
import com.lothrazar.pickybags.item.IOpenable;
import com.lothrazar.pickybags.item.ItemCountContents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
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

  @Override
  public InteractionResult useOn(UseOnContext context) {
    BlockPos pos = context.getClickedPos();
    Level world = context.getLevel();
    BlockEntity te = world.getBlockEntity(pos);
    if (te == null) {
      return InteractionResult.PASS;
    }
    Direction face = context.getClickedFace();
    ItemStack bag = context.getItemInHand();
    // we assume the bag is valid here
    IItemHandler h = bag.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(null);
    //
    if (h instanceof ItemStackHandler handler && te.getCapability(ForgeCapabilities.ITEM_HANDLER, face).isPresent()) {

      IItemHandler teHandler = te.getCapability(ForgeCapabilities.ITEM_HANDLER, face).orElse(null);
      Set<Item> itemsInTargetInventory = new HashSet<>();
      if (teHandler != null) {
        for (int j = 0; j < teHandler.getSlots(); j++) {
          itemsInTargetInventory.add(teHandler.getStackInSlot(j).getItem());
        }
      }
      // DONT dump everything in there
      //this is a mixed bag of stuff, long term use
      //so the player will want to put stuff all over the place
      //only put it in if theres a match
      for (int i = 0; i < handler.getSlots(); i++) {
        ItemStack stack = handler.getStackInSlot(i);
        ItemStack remaining = ItemHandlerHelper.copyStackWithSize(stack, stack.getCount());
        if (!stack.isEmpty()) {
          if (itemsInTargetInventory.contains(stack.getItem())) {
            remaining = ItemHandlerHelper.insertItem(teHandler, stack, false);
            handler.setStackInSlot(i, remaining);
          }
        }
      }
      SoundUtil.playSound(context.getPlayer(), SoundEvents.UI_BUTTON_CLICK.get());
      return InteractionResult.SUCCESS;
    }
    return InteractionResult.PASS;
  }
}
