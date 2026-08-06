package com.lothrazar.pickybags;

import com.lothrazar.pickybags.item.bag.BagCapability;
import com.lothrazar.pickybags.item.foodbox.CapabilityLunchbox;
import com.lothrazar.pickybags.item.pickup.PickupBagCapability;
import com.lothrazar.pickybags.registry.ModBagsRegistry;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class ModCapabilities {

  @SubscribeEvent
  public static void register(RegisterCapabilitiesEvent event) {
    event.registerItem(Capabilities.Item.ITEM,
        (stack, ctx) -> new IItemHandlerResourceHandler(new BagCapability(stack)),
        ModBagsRegistry.BAG.get()
    );
    event.registerItem(Capabilities.Item.ITEM,
        (stack, ctx) -> new IItemHandlerResourceHandler(new PickupBagCapability(stack)),
        ModBagsRegistry.PICKUP_ROCKS.get(),
        ModBagsRegistry.PICKUP_GEMS.get(),
        ModBagsRegistry.PICKUP_PLANTS.get(),
        ModBagsRegistry.PICKUP_TREES.get()
    );
    event.registerItem(Capabilities.Item.ITEM,
        (stack, ctx) -> new IItemHandlerResourceHandler(new CapabilityLunchbox(stack)),
        ModBagsRegistry.BOX.get()
    );
  }
}
