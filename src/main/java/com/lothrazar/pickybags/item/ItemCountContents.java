package com.lothrazar.pickybags.item;

import java.util.List;
import com.lothrazar.library.item.ItemFlib;
import com.lothrazar.library.util.ItemStackUtil;
import com.lothrazar.pickybags.ModBags;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;

/**
 * TODO: contribute to FLIB
 *
 */
public class ItemCountContents extends ItemFlib {

  protected static final String COUNT_MAX = "count_max";
  protected static final String COUNT_EMPTY = "count_empty";

  public ItemCountContents(Properties prop, ItemFlib.Settings s) {
    super(prop, s);
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
    super.appendHoverText(stack, worldIn, tooltip, flagIn);
    if (stack.hasTag()) {
      CompoundTag stackTag = stack.getTag();
      if (stackTag.contains(COUNT_EMPTY) && stackTag.contains(COUNT_MAX)) {
        int maxCount = stackTag.getInt(COUNT_MAX);
        int emptyCount = stackTag.getInt(COUNT_EMPTY);
        if (maxCount > 0) { //its a valid data set  
          tooltip.add(Component.translatable(ModBags.MODID + "." + COUNT_EMPTY).append("" + emptyCount).withStyle(ChatFormatting.GRAY));
        }
      }
    }
  }

  @Override
  public CompoundTag getShareTag(ItemStack stack) {
    CompoundTag nbt = stack.getOrCreateTag();
    IItemHandler handler = stack.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(null);
    //on server  this runs . also has correct values.
    //set data for sync to client
    if (handler != null) {
      int empty = ItemStackUtil.countEmptySlots(handler);
      nbt.putInt(COUNT_EMPTY, empty);
      nbt.putInt(COUNT_MAX, handler.getSlots());
    }
    return nbt;
  }

  //clientside read tt
  @Override
  public void readShareTag(ItemStack stack, CompoundTag nbt) {
    if (nbt != null) {
      CompoundTag stackTag = stack.getOrCreateTag();
      stackTag.putInt(COUNT_EMPTY, nbt.getInt(COUNT_EMPTY));
      stackTag.putInt(COUNT_MAX, nbt.getInt(COUNT_MAX));
    }
    super.readShareTag(stack, nbt);
  }
}
