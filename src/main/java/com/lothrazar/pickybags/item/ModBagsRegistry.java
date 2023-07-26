package com.lothrazar.pickybags.item;

import com.lothrazar.pickybags.ModBags;
import com.lothrazar.pickybags.item.bag.BagItem;
import com.lothrazar.pickybags.item.pickup.PickupBagItem;
import com.lothrazar.pickybags.item.slab.CraftingSlabItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBagsRegistry {

  //
  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModBags.MODID);
  //  public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ModBags.MODID);
  //  public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ModBags.MODID);
  public static final RegistryObject<Item> SLAB = ITEMS.register("slab", () -> new CraftingSlabItem(new Item.Properties()));
  public static final RegistryObject<Item> BAG = ITEMS.register("bag", () -> new BagItem(new Item.Properties()));
  //
  public static final RegistryObject<PickupBagItem> PICKUP_ROCKS = ITEMS.register("pickup_rocks", () -> new PickupBagItem(new Item.Properties()));
  public static final RegistryObject<PickupBagItem> PICKUP_GEMS = ITEMS.register("pickup_gems", () -> new PickupBagItem(new Item.Properties()));
  public static final RegistryObject<PickupBagItem> PICKUP_PLANTS = ITEMS.register("pickup_plants", () -> new PickupBagItem(new Item.Properties()));
  public static final RegistryObject<PickupBagItem> PICKUP_TREES = ITEMS.register("pickup_trees", () -> new PickupBagItem(new Item.Properties()));
  private static final ResourceKey<CreativeModeTab> TAB = ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation(ModBags.MODID, "tab"));

  @SubscribeEvent
  public static void onCreativeModeTabRegister(RegisterEvent event) {
    event.register(Registries.CREATIVE_MODE_TAB, helper -> {
      helper.register(TAB, CreativeModeTab.builder().icon(() -> new ItemStack(BAG.get()))
          .title(Component.translatable("itemGroup." + ModBags.MODID))
          .displayItems((enabledFlags, populator) -> {
            for (RegistryObject<Item> entry : ITEMS.getEntries()) {
              populator.accept(entry.get());
            }
          }).build());
    });
  }
}
