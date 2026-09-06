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

  public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ModBags.MODID);
  //  public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ModBags.MODID);
  //  public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ModBags.MODID);
  public static final DeferredHolder<Item, Item> SLAB = ITEMS.registerItem("slab", props -> new CraftingSlabItem(props));
  public static final DeferredHolder<Item, Item> BAG = ITEMS.registerItem("bag", props -> new BagItem(props));
  public static final DeferredHolder<Item, Item> BOX = ITEMS.registerItem("lunchbox", props -> new ItemLunchbox(props.rarity(Rarity.UNCOMMON)));
  //
  public static final DeferredHolder<Item, PickupBagItem> PICKUP_ROCKS = ITEMS.registerItem("pickup_rocks", props -> new PickupBagItem(props));
  public static final DeferredHolder<Item, PickupBagItem> PICKUP_GEMS = ITEMS.registerItem("pickup_gems", props -> new PickupBagItem(props));
  public static final DeferredHolder<Item, PickupBagItem> PICKUP_PLANTS = ITEMS.registerItem("pickup_plants", props -> new PickupBagItem(props));
  public static final DeferredHolder<Item, PickupBagItem> PICKUP_TREES = ITEMS.registerItem("pickup_trees", props -> new PickupBagItem(props));
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
