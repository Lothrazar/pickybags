package com.lothrazar.pickybags.net;

import java.util.function.Supplier;
import com.lothrazar.library.packet.PacketFlib;
import com.lothrazar.pickybags.item.bag.BagContainerProvider;
import com.lothrazar.pickybags.item.bag.BagItem;
import com.lothrazar.pickybags.item.pickup.PickupBagContainerProvider;
import com.lothrazar.pickybags.item.pickup.PickupBagItem;
import com.lothrazar.pickybags.item.slab.CraftingSlabContainerProvider;
import com.lothrazar.pickybags.item.slab.CraftingSlabItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;

public class PacketOpenBag extends PacketFlib {

  private int slot;
  private Item item;

  public PacketOpenBag(int slot, Item item) {
    this.slot = slot;
    this.item = item;
  }

  public static void handle(PacketOpenBag message, Supplier<NetworkEvent.Context> ctx) {
    ctx.get().enqueueWork(() -> {
      ServerPlayer player = ctx.get().getSender();
      if (message.item instanceof PickupBagItem) {
        NetworkHooks.openScreen(player, new PickupBagContainerProvider(message.slot, message.item), buf -> {
          buf.writeInt(message.slot);
          buf.writeItem(new ItemStack(message.item));
        });
      }
      else if (message.item instanceof BagItem) {
        NetworkHooks.openScreen(player, new BagContainerProvider(message.slot), buf -> buf.writeInt(message.slot));
      }
      else if (message.item instanceof CraftingSlabItem) {
        NetworkHooks.openScreen(player, new CraftingSlabContainerProvider(message.slot), buf -> buf.writeInt(message.slot));
      }
    });
    message.done(ctx);
  }

  public static PacketOpenBag decode(FriendlyByteBuf buf) {
    return new PacketOpenBag(buf.readInt(), buf.readItem().getItem());
  }

  public static void encode(PacketOpenBag msg, FriendlyByteBuf buf) {
    buf.writeInt(msg.slot);
    buf.writeItem(new ItemStack(msg.item));
  }
}
