package com.lothrazar.pickybags.item;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

// Item colors are fully data-driven now (RegisterColorHandlersEvent has no Item variant anymore);
// this is a custom ItemTintSource type, registered via RegisterColorHandlersEvent.ItemTintSources
// and referenced by id from the "tints" array in assets/pickybags/items/*.json, for the one case
// (per-stack HOLDING flag) none of the built-in tint source types cover.
public record PickupHoldingTintSource() implements ItemTintSource {

  public static final MapCodec<PickupHoldingTintSource> MAP_CODEC = MapCodec.unit(PickupHoldingTintSource::new);

  @Override
  public int calculate(ItemStack stack, ClientLevel level, LivingEntity owner) {
    if (stack.getItem() instanceof IPickupable
        && stack.has(DataComponents.CUSTOM_DATA)
        && stack.get(DataComponents.CUSTOM_DATA).copyTag().getBooleanOr(IPickupable.HOLDING, false)) {
      // green? return 0x00AAAAFF;
      return 0x000000FF; //  0xFFFF0011;
    }
    return 0xFFFFFFFF;
  }

  @Override
  public MapCodec<PickupHoldingTintSource> type() {
    return MAP_CODEC;
  }
}
