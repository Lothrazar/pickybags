package com.lothrazar.pickybags.item;

import com.lothrazar.pickybags.ModBags;
import com.lothrazar.pickybags.item.bag.BagContainer;
import com.lothrazar.pickybags.item.bag.BagScreen;
import com.lothrazar.pickybags.item.slab.CraftingSlabContainer;
import com.lothrazar.pickybags.item.slab.CraftingSlabScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BagsMenuRegistry {

  public static final ResourceLocation V_CRAFTING = new ResourceLocation("minecraft", "textures/gui/container/crafting_table.png");
  public static final ResourceLocation INVENTORY = new ResourceLocation("pickybags", "textures/gui/inventory.png");
  public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, ModBags.MODID);
  public static final RegistryObject<MenuType<CraftingSlabContainer>> SLAB = CONTAINERS.register("slab", () -> IForgeMenuType.create((windowId, inv, data) -> new CraftingSlabContainer(windowId, inv, inv.player, data.readInt())));
  public static final RegistryObject<MenuType<BagContainer>> BAG = CONTAINERS.register("bag", () -> IForgeMenuType.create((windowId, inv, data) -> new BagContainer(windowId, inv, inv.player, data.readInt())));

  public static void setupClient(final FMLClientSetupEvent event) {
    //for client side only setup
    MenuScreens.register(SLAB.get(), CraftingSlabScreen::new);
    MenuScreens.register(BAG.get(), BagScreen::new);
  }
}