package com.lothrazar.pickybags;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
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
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(ModBags.MODID)
public class ModBags {

  public static final String MODID = "pickybags";
  public static final Logger LOGGER = LogManager.getLogger();

  public ModBags(IEventBus bus, ModContainer modContainer) {
//    IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    ModBagsRegistry.ITEMS.register(bus);
    ModBagsRegistry.TABS.register(bus);
    BagsMenuRegistry.CONTAINERS.register(bus);
    PickupTags.setup();
    bus.addListener(this::setup);
    bus.addListener(this::registerScreens);
    bus.addListener(this::setupClient);
    bus.addListener(ModCapabilities::register);
    PacketRegistry.setup(bus);
    if (FMLEnvironment.dist == net.neoforged.api.distmarker.Dist.CLIENT) {
      bus.addListener(PickupClientEvents::registerItemColors);
    }
  }

  private void setup(final FMLCommonSetupEvent event) {
    NeoForge.EVENT_BUS.register(new PickupEvents());
  }

  //new neoforge no more MenuScreens.register
  private void registerScreens(final RegisterMenuScreensEvent event) {
    event.register(BagsMenuRegistry.SLAB.get(), CraftingSlabScreen::new);
    event.register(BagsMenuRegistry.BAG.get(), BagScreen::new);
    event.register(BagsMenuRegistry.LUNCHBOX.get(), ScreenLunchbox::new);
    event.register(BagsMenuRegistry.PICKUP.get(), PickupBagScreen::new);
  }

  private void setupClient(final FMLClientSetupEvent event) {
    //for client side only setup
    NeoForge.EVENT_BUS.register(new PickupClientEvents());
  }
}
