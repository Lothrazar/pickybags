/*******************************************************************************
 * The MIT License (MIT)
 *
 * Copyright (C) 2014-2018 Sam Bassett (aka Lothrazar)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package com.lothrazar.pickybags.item.foodbox;

import com.lothrazar.library.item.ItemFlib;
import com.lothrazar.library.util.ChatUtil;
import com.lothrazar.pickybags.item.IPickupable;
import com.lothrazar.pickybags.item.ItemCountContents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.network.NetworkHooks;

public class ItemLunchbox extends ItemCountContents implements IPickupable {

  public static final int SLOTS = 9;
  public static final int COLOUR_FOOD_BAR = 0xCFFF04;

  public ItemLunchbox(Properties prop) {
    super(prop.stacksTo(1), new ItemFlib.Settings().tooltip());
  }

  @Override
  public Rarity getRarity(ItemStack stack) {
    return Rarity.UNCOMMON;
  }

  @Override
  public int getUseDuration(ItemStack st) {
    return 34;
  }

  @Override
  public UseAnim getUseAnimation(ItemStack st) {
    return UseAnim.EAT;
  }

  @Override
  public boolean isBarVisible(ItemStack stack) {
    return stack.hasTag() || super.isBarVisible(stack);
  }

  //show emptiness in fake durability bar
  @Override
  public int getBarColor(ItemStack stack) {
    return COLOUR_FOOD_BAR;
  }

  @Override
  public int getBarWidth(ItemStack stack) {
    if (!stack.hasTag()) {
      return 0;
    }
    float max = stack.getTag().getInt(COUNT_MAX);
    float current = max - stack.getTag().getInt(COUNT_EMPTY);
    return (max == 0) ? 0 : Math.round(13.0F * current / max);
  }

  @Override
  public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
    if (!worldIn.isClientSide && entityLiving instanceof Player player) {
      IItemHandler handler = stack.getCapability(ForgeCapabilities.ITEM_HANDLER).orElse(null);
      if (handler != null) {
        ItemStack found = ItemStack.EMPTY;
        //just go left to right and eat in order
        for (int i = 0; i < handler.getSlots(); i++) {
          ItemStack test = handler.getStackInSlot(i);
          if (test.isEdible() && !player.getCooldowns().isOnCooldown(test.getItem())) {
            found = test;
            break;
          }
        }
        if (!found.isEmpty()) {
          ChatUtil.addServerChatMessage(player, found.getDisplayName());
          //eat the food
          found.getItem().finishUsingItem(found, worldIn, entityLiving);
        }
      }
    }
    return super.finishUsingItem(stack, worldIn, entityLiving);
  }

  @Override
  public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
    if (playerIn.isCrouching()) {
      if (!worldIn.isClientSide) {
        int slot = handIn == InteractionHand.MAIN_HAND ? playerIn.getInventory().selected : 40;
        NetworkHooks.openScreen((ServerPlayer) playerIn, new ContainerProviderLunchbox(slot), buf -> buf.writeInt(slot));
      }
      return super.use(worldIn, playerIn, handIn);
    }
    else if (playerIn.canEat(false)) {
      //not crouching so try to eat it
      //if we arent full 
      playerIn.startUsingItem(handIn);
    }
    return super.use(worldIn, playerIn, handIn);
  }

  @Override
  public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
    return new CapabilityLunchbox(stack, nbt);
  }
  //
  //  public static void setHoldingEdible(ItemStack box, boolean edible) {
  //    box.getOrCreateTag().putBoolean("holding", edible);
  //  }
  //
  //  public static int getColour(ItemStack stack) {
  //    if (stack.hasTag() && stack.getTag().getBoolean("holding")) {
  //      // green? return 0x00AAAAFF;
  //      return 0x000000FF; //  0xFFFF0011;
  //    }
  //    return 0xFFFFFFFF;
  //  }

  @Override
  public boolean canInsert(ItemStack itemPickup) {
    return itemPickup.isEdible();
  }
}
