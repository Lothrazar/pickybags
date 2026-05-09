package com.lothrazar.pickybags.net;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class PacketRegistry {

  public static void setup(IEventBus bus) {
    bus.addListener(PacketRegistry::registerPayloads);
  }

  private static void registerPayloads(RegisterPayloadHandlersEvent event) {
    PayloadRegistrar registrar = event.registrar("1");
    registrar.playToServer(PacketOpenBag.TYPE, PacketOpenBag.STREAM_CODEC, PacketOpenBag::handle);
    registrar.playToServer(PacketInsertBag.TYPE, PacketInsertBag.STREAM_CODEC, PacketInsertBag::handle);
  }
}
