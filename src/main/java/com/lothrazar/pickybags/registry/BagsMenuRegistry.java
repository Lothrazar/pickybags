package com.lothrazar.pickybags.registry;

import com.lothrazar.pickybags.ModBags;
import com.lothrazar.pickybags.item.bag.BagContainer;
import com.lothrazar.pickybags.item.foodbox.ContainerLunchbox;
import com.lothrazar.pickybags.item.pickup.PickupBagContainer;
import com.lothrazar.pickybags.item.slab.CraftingSlabContainer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BagsMenuRegistry {

  public static final Identifier V_CRAFTING = Identifier.withDefaultNamespace("textures/gui/container/crafting_table.png");
  public static final Identifier GENERIC_54 = Identifier.withDefaultNamespace("textures/gui/container/generic_54.png");
  public static final Identifier SQUARE = Identifier.withDefaultNamespace("textures/gui/container/dispenser.png");
  public static final Identifier SHULKER = Identifier.withDefaultNamespace("textures/gui/container/shulker_box.png");
  public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(Registries.MENU, ModBags.MODID);
  public static final Supplier<MenuType<CraftingSlabContainer>> SLAB = CONTAINERS.register("slab", () -> IMenuTypeExtension.create((windowId, inv, data) -> new CraftingSlabContainer(windowId, inv, inv.player, data.readInt())));
  public static final Supplier<MenuType<BagContainer>> BAG = CONTAINERS.register("bag", () -> IMenuTypeExtension.create((windowId, inv, data) -> new BagContainer(windowId, inv, inv.player, data.readInt())));
  public static final Supplier<MenuType<PickupBagContainer>> PICKUP = CONTAINERS.register("pickup", () -> IMenuTypeExtension.create((windowId, inv, data) -> new PickupBagContainer(windowId, inv, inv.player, data.readInt(), ItemStack.OPTIONAL_STREAM_CODEC.decode(data).getItem(), data.readBoolean())));
  public static final Supplier<MenuType<ContainerLunchbox>> LUNCHBOX = CONTAINERS.register("lunchbox", () -> IMenuTypeExtension.create((windowId, inv, data) -> new ContainerLunchbox(windowId, inv, inv.player, data.readInt())));
}
