package com.lothrazar.pickybags.net;

import com.lothrazar.pickybags.ModBags;
import com.lothrazar.pickybags.item.bag.BagContainerProvider;
import com.lothrazar.pickybags.item.bag.BagItem;
import com.lothrazar.pickybags.item.foodbox.ContainerProviderLunchbox;
import com.lothrazar.pickybags.item.foodbox.ItemLunchbox;
import com.lothrazar.pickybags.item.pickup.PickupBagContainerProvider;
import com.lothrazar.pickybags.item.pickup.PickupBagItem;
import com.lothrazar.pickybags.item.slab.CraftingSlabContainerProvider;
import com.lothrazar.pickybags.item.slab.CraftingSlabItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record PacketOpenBag(int slot, Item item, boolean isCurios) implements CustomPacketPayload {

  public static final Type<PacketOpenBag> TYPE = new Type<>(
      Identifier.fromNamespaceAndPath(ModBags.MODID, "open_bag")
  );

  public static final StreamCodec<RegistryFriendlyByteBuf, PacketOpenBag> STREAM_CODEC =
      StreamCodec.composite(
          ByteBufCodecs.INT, PacketOpenBag::slot,
          ByteBufCodecs.registry(Registries.ITEM), PacketOpenBag::item,
          ByteBufCodecs.BOOL, PacketOpenBag::isCurios,
          PacketOpenBag::new
      );

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }

  public static void handle(PacketOpenBag message, IPayloadContext ctx) {
    ctx.enqueueWork(() -> {
      ServerPlayer player = (ServerPlayer) ctx.player();
      if (message.item() instanceof PickupBagItem) {
        player.openMenu(new PickupBagContainerProvider(message.slot(), message.item(), message.isCurios()), buf -> {
          buf.writeInt(message.slot());
//          buf.writeItem(new ItemStack(message.item()));
          ItemStack.OPTIONAL_STREAM_CODEC.encode(buf, new ItemStack(message.item()));
          buf.writeBoolean(message.isCurios());
        });
      } else if (message.item() instanceof BagItem) {
        player.openMenu(new BagContainerProvider(message.slot()), buf -> buf.writeInt(message.slot()));
      } else if (message.item() instanceof CraftingSlabItem) {
        player.openMenu(new CraftingSlabContainerProvider(message.slot()), buf -> buf.writeInt(message.slot()));
      } else if (message.item() instanceof ItemLunchbox) {
        player.openMenu(new ContainerProviderLunchbox(message.slot()), buf -> buf.writeInt(message.slot()));
      }
    });
  }
}
