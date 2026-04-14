package com.lothrazar.pickybags.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class PickupTags {

  public static final TagKey<Item> PLANT_LIKE = ItemTags.create(ResourceLocation.parse("pickybags:plant_like"));
  public static final TagKey<Item> STONE_LIKE = ItemTags.create(ResourceLocation.parse("pickybags:stone_like"));
  public static final TagKey<Item> WOOD_LIKE = ItemTags.create(ResourceLocation.parse("pickybags:wood_like"));
  public static final TagKey<Item> GEM_LIKE = ItemTags.create(ResourceLocation.parse("pickybags:gem_like"));

  public static void setup() {}
}
