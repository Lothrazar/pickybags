package com.lothrazar.pickybags;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.lothrazar.pickybags.event.PickupClientEvents;
import com.lothrazar.pickybags.event.PickupEvents;
import com.lothrazar.pickybags.item.bag.BagScreen;
import com.lothrazar.pickybags.item.foodbox.ScreenLunchbox;
import com.lothrazar.pickybags.item.pickup.PickupBagScreen;
import com.lothrazar.pickybags.item.slab.CraftingSlabScreen;
import com.lothrazar.pickybags.net.PacketRegistry;
import com.lothrazar.pickybags.registry.BagsMenuRegistry;
import com.lothrazar.pickybags.registry.ModBagsRegistry;
import com.lothrazar.pickybags.registry.PickupTags;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ModBags.MODID)
public class ModBags {

  public static final String MODID = "pickybags";
  public static final Logger LOGGER = LogManager.getLogger();

  public ModBags() {
    IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    ModBagsRegistry.ITEMS.register(bus);
    BagsMenuRegistry.CONTAINERS.register(bus);
    PickupTags.setup();
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
  }

  private void setup(final FMLCommonSetupEvent event) {
    PacketRegistry.setup();
    MinecraftForge.EVENT_BUS.register(new PickupEvents());
  }

  private void setupClient(final FMLClientSetupEvent event) {
    //for client side only setup
    MenuScreens.register(BagsMenuRegistry.SLAB.get(), CraftingSlabScreen::new);
    MenuScreens.register(BagsMenuRegistry.BAG.get(), BagScreen::new);
    MenuScreens.register(BagsMenuRegistry.LUNCHBOX.get(), ScreenLunchbox::new);
    MenuScreens.register(BagsMenuRegistry.PICKUP.get(), PickupBagScreen::new);
    MinecraftForge.EVENT_BUS.register(new PickupClientEvents());
  }
}
