package com.lothrazar.pickybags.net;

import java.util.function.Supplier;
import com.lothrazar.library.packet.PacketFlib;
import com.lothrazar.pickybags.item.IPickupable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.network.NetworkEvent;

public class PacketInsertBag extends PacketFlib {

  private int slot;
  private Item item;

  public PacketInsertBag(int slot, Item item) {
    this.slot = slot;
    this.item = item;
  }

  public static void handle(PacketInsertBag message, Supplier<NetworkEvent.Context> ctx) {
    ctx.get().enqueueWork(() -> {
      ServerPlayer player = ctx.get().getSender();
      if (message.item instanceof IPickupable pug) {
        //put the thing in the bag
        ItemStack itemMouse = player.containerMenu.getCarried();
        if (itemMouse.isEmpty()
            && !pug.canInsert(itemMouse)) {
          return;
        }
        ItemStack theBag = player.getInventory().getItem(message.slot);//why is it air
        IItemHandler boxCap = theBag.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(null);
        if (boxCap == null) {
          return;
        }
        //try to put/stack into each slot
        int i = 0;
        while (i < boxCap.getSlots()) {
          itemMouse = boxCap.insertItem(i, itemMouse, false);
          i++;
        }
        player.containerMenu.setCarried(itemMouse);
      }
    });
    message.done(ctx);
  }

  public static PacketInsertBag decode(FriendlyByteBuf buf) {
    return new PacketInsertBag(buf.readInt(), buf.readItem().getItem());
  }

  public static void encode(PacketInsertBag msg, FriendlyByteBuf buf) {
    buf.writeInt(msg.slot);
    buf.writeItem(new ItemStack(msg.item));
  }
}
