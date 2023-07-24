package com.lothrazar.pickybags.item;

import com.lothrazar.pickybags.ModBags;
import com.lothrazar.pickybags.item.bag.BagItem;
import com.lothrazar.pickybags.item.slab.CraftingSlabItem;
import net.minecraft.world.item.Item;
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
}
