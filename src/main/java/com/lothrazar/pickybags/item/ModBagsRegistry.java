package com.lothrazar.pickybags.item;

import com.lothrazar.pickybags.ModBags;
import com.lothrazar.pickybags.item.bag.BagItem;
import com.lothrazar.pickybags.item.pickup.PickupBagItem;
import com.lothrazar.pickybags.item.slab.CraftingSlabItem;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBagsRegistry {

  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModBags.MODID);
  //  public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ModBags.MODID);
  //  public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ModBags.MODID);
  public static final RegistryObject<Item> SLAB = ITEMS.register("slab", () -> new CraftingSlabItem(new Item.Properties()));
  public static final RegistryObject<Item> BAG = ITEMS.register("bag", () -> new BagItem(new Item.Properties()));
  public static final RegistryObject<PickupBagItem> PICKUP_ROCKS = ITEMS.register("pickup_rocks", () -> new PickupBagItem(new Item.Properties()) {

    @Override
    public boolean canInsert(ItemStack itemPickup) { // GREY STONE COLOR
      return itemPickup.is(ItemTags.TERRACOTTA)
          || itemPickup.is(Tags.Items.COBBLESTONE)
          || itemPickup.is(Tags.Items.NETHERRACK)
          || itemPickup.is(Tags.Items.END_STONES)
          || itemPickup.is(Tags.Items.STONE)
          || itemPickup.is(Tags.Items.GRAVEL);
    }
  });
  public static final RegistryObject<PickupBagItem> PICKUP_DIRT = ITEMS.register("pickup_dirt", () -> new PickupBagItem(new Item.Properties()) {

    @Override
    public boolean canInsert(ItemStack itemPickup) { // BROWN OR DARK BLUE
      return itemPickup.is(ItemTags.DIRT)
          || itemPickup.is(Items.CLAY) // TODO clay tag?
          || itemPickup.is(ItemTags.SAND)
          || itemPickup.is(Tags.Items.GRAVEL);
    }
  });
  public static final RegistryObject<PickupBagItem> PICKUP_GEMS = ITEMS.register("pickup_gems", () -> new PickupBagItem(new Item.Properties()) {

    @Override
    public boolean canInsert(ItemStack itemPickup) { // PURPLE OR LIGHT BLUE
      return itemPickup.is(ItemTags.COALS)
          || itemPickup.is(Tags.Items.ORES)
          || itemPickup.is(Tags.Items.ORES_IN_GROUND_STONE)
          || itemPickup.is(Tags.Items.GEMS);
    }
  });
  public static final RegistryObject<PickupBagItem> PICKUP_PLANTS = ITEMS.register("pickup_plants", () -> new PickupBagItem(new Item.Properties()) {

    @Override
    public boolean canInsert(ItemStack itemPickup) { // LIGHT GREEN
      return itemPickup.is(ItemTags.LEAVES) // WOOL?
          || itemPickup.is(ItemTags.SAPLINGS)
          || itemPickup.is(ItemTags.FLOWERS)
          || itemPickup.is(ItemTags.TALL_FLOWERS)
          || itemPickup.is(ItemTags.FLOWERS)
          || itemPickup.is(Tags.Items.SEEDS);
    }
  });
  public static final RegistryObject<PickupBagItem> PICKUP_TREES = ITEMS.register("pickup_trees", () -> new PickupBagItem(new Item.Properties()) {

    @Override
    public boolean canInsert(ItemStack itemPickup) { // DARK GREEN
      return itemPickup.is(ItemTags.LOGS)
          || itemPickup.is(ItemTags.PLANKS)
          || itemPickup.is(ItemTags.WOODEN_SLABS)
          || itemPickup.is(ItemTags.WOODEN_STAIRS)
          || itemPickup.is(Tags.Items.RODS_WOODEN);
    }
  });
}
