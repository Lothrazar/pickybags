package com.lothrazar.pickybags.net;

import com.lothrazar.pickybags.ModBags;
import com.lothrazar.pickybags.item.IPickupable;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record PacketInsertBag(int slot, Item item) implements CustomPacketPayload {

  public static final Type<PacketInsertBag> TYPE = new Type<>(
      Identifier.fromNamespaceAndPath(ModBags.MODID, "insert_bag")
  );

  public static final StreamCodec<RegistryFriendlyByteBuf, PacketInsertBag> STREAM_CODEC =
      StreamCodec.composite(
          ByteBufCodecs.INT, PacketInsertBag::slot,
          ByteBufCodecs.registry(Registries.ITEM), PacketInsertBag::item,
          PacketInsertBag::new
      );

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }

  public static void handle(PacketInsertBag message, IPayloadContext ctx) {
    ctx.enqueueWork(() -> {
      ServerPlayer player = (ServerPlayer) ctx.player();
      if (message.item() instanceof IPickupable pug) {
        ItemStack itemMouse = player.containerMenu.getCarried();
        if (itemMouse.isEmpty() && !pug.canInsert(itemMouse)) {
          return;
        }
        ItemStack theBag = player.getInventory().getItem(message.slot());
        IItemHandler boxCap = com.lothrazar.pickybags.CapabilityUtil.getItemHandler(theBag);
        if (boxCap == null) {
          return;
        }
        int i = 0;
        while (i < boxCap.getSlots()) {
          itemMouse = boxCap.insertItem(i, itemMouse, false);
          i++;
        }
        player.containerMenu.setCarried(itemMouse);
      }
    });
  }
}
