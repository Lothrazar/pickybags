package com.lothrazar.pickybags;

import com.lothrazar.pickybags.item.CraftingSlabContainer;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@JeiPlugin
public class BagsPluginJEI implements IModPlugin {

  private static final int PLAYER_INV_SIZE = 4 * 9;
  private static final ResourceLocation ID = new ResourceLocation(ModBags.MODID, "jei");

  @Override
  public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {}

  @Override
  public ResourceLocation getPluginUid() {
    return ID;
  }

  @Override
  public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
    registration.addRecipeCatalyst(new ItemStack(ModBagsRegistry.SLAB.get()), RecipeTypes.CRAFTING);
  }

  @Override
  public void registerRecipeTransferHandlers(IRecipeTransferRegistration registry) {
    registry.addRecipeTransferHandler(CraftingSlabContainer.class, BagsMenuRegistry.SLAB.get(), RecipeTypes.CRAFTING,
        1, 9, //recipeSLotStart, recipeSlotCount
        10, PLAYER_INV_SIZE); // inventorySlotStart, inventorySlotCount
  }
}
