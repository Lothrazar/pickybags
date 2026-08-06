package com.lothrazar.pickybags.registry;

import com.lothrazar.pickybags.ModBags;
import com.lothrazar.pickybags.ModCapabilities;
import com.lothrazar.pickybags.item.bag.BagItem;
import com.lothrazar.pickybags.item.foodbox.ItemLunchbox;
import com.lothrazar.pickybags.item.pickup.PickupBagItem;
import com.lothrazar.pickybags.item.slab.CraftingSlabItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegisterEvent;

//@EventBusSubscriber(modid = ModBags.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModBagsRegistry {
  public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ModBags.MODID);

  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, ModBags.MODID);
  //  public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ModBags.MODID);
  //  public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ModBags.MODID);
  public static final DeferredHolder<Item, Item> SLAB = ITEMS.register("slab", () -> new CraftingSlabItem(new Item.Properties()));
  public static final DeferredHolder<Item, Item> BAG = ITEMS.register("bag", () -> new BagItem(new Item.Properties()));
  public static final DeferredHolder<Item, Item> BOX = ITEMS.register("lunchbox", () -> new ItemLunchbox(new Item.Properties().rarity(Rarity.UNCOMMON)));
  //
  public static final DeferredHolder<Item, PickupBagItem> PICKUP_ROCKS = ITEMS.register("pickup_rocks", () -> new PickupBagItem(new Item.Properties()));
  public static final DeferredHolder<Item, PickupBagItem> PICKUP_GEMS = ITEMS.register("pickup_gems", () -> new PickupBagItem(new Item.Properties()));
  public static final DeferredHolder<Item, PickupBagItem> PICKUP_PLANTS = ITEMS.register("pickup_plants", () -> new PickupBagItem(new Item.Properties()));
  public static final DeferredHolder<Item, PickupBagItem> PICKUP_TREES = ITEMS.register("pickup_trees", () -> new PickupBagItem(new Item.Properties()));
  private static final ResourceKey<CreativeModeTab> TAB = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath(ModBags.MODID, "tab"));

  static {
    // was  public static void onCreativeModeTabRegister(RegisterEvent event) {
    TABS.register("tab",  () -> CreativeModeTab.builder()
      .icon(() -> new ItemStack(BAG.get()))
      .title(Component.translatable("itemGroup." + ModBags.MODID))
      .displayItems((enabledFlags, populator) -> {
        for (var entry : ITEMS.getEntries()) {
          populator.accept(entry.get());
        }
      }).build());

  }
//  @SubscribeEvent
//  public static void onCreativeModeTabRegister(RegisterEvent event) {
//  }
}
