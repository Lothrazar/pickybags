package com.lothrazar.pickybags;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.lothrazar.pickybags.item.BagsMenuRegistry;
import com.lothrazar.pickybags.item.ModBagsRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ModBags.MODID)
public class ModBags {

  public static final String MODID = "pickybags";
  public static final Logger LOGGER = LogManager.getLogger();

  public ModBags() {
    IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    //    ModBagsRegistry.BLOCKS.register(bus); // template; rename to bus. fix mod registry TAB group defaults  
    //    ModBagsRegistry.TILE_ENTITIES.register(bus);
    ModBagsRegistry.ITEMS.register(bus);
    BagsMenuRegistry.CONTAINERS.register(bus);
    ConfigManager.setup();
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    FMLJavaModLoadingContext.get().getModEventBus().addListener(BagsMenuRegistry::setupClient);
  }

  private void setup(final FMLCommonSetupEvent event) {
    MinecraftForge.EVENT_BUS.register(new PickupEvents());
  }
}
