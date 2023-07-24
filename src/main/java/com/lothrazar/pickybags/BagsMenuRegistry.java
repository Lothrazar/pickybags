package com.lothrazar.pickybags;

import com.lothrazar.pickybags.item.CraftingSlabContainer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BagsMenuRegistry {

  public static final ResourceLocation V_CRAFTING = new ResourceLocation("minecraft", "textures/gui/container/crafting_table.png");
  public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, ModBags.MODID);
  public static final RegistryObject<MenuType<CraftingSlabContainer>> SLAB = CONTAINERS.register("slab", () -> IForgeMenuType.create((windowId, inv, data) -> new CraftingSlabContainer(windowId, inv, inv.player, data.readInt())));
}
