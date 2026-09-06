package com.lothrazar.pickybags.item;

import java.util.List;
import com.lothrazar.library.item.ItemFlib;
import com.lothrazar.pickybags.ModBags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

/**
 * TODO: contribute to FLIB
 *
 */
public class ItemCountContents extends ItemFlib {

  public static final String COUNT_MAX = "count_max";
  public static final String COUNT_EMPTY = "count_empty";

  public ItemCountContents(Properties prop, ItemFlib.Settings s) {
    super(prop, s);
  }

  @Override
  public void appendHoverText(ItemStack stack, TooltipContext context, net.minecraft.world.item.component.TooltipDisplay tooltipDisplay,
      java.util.function.Consumer<Component> tooltip, TooltipFlag flagIn) {
    super.appendHoverText(stack, context, tooltipDisplay, tooltip, flagIn);
    if (stack.has(DataComponents.CUSTOM_DATA)) {
      CompoundTag stackTag = stack.get(DataComponents.CUSTOM_DATA).copyTag();
      if (stackTag.contains(COUNT_EMPTY) && stackTag.contains(COUNT_MAX)) {
        int maxCount = stackTag.getIntOr(COUNT_MAX, 0);
        int emptyCount = stackTag.getIntOr(COUNT_EMPTY, 0);
        if (maxCount > 0) { //its a valid data set
          tooltip.accept(Component.translatable(ModBags.MODID + "." + COUNT_EMPTY).append("" + emptyCount).withStyle(ChatFormatting.GRAY));
        }
      }
    }
  }
}
