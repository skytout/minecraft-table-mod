package com.mayatout.mccourse.events;

import com.mayatout.mccourse.item.ModItems;
import com.mayatout.mccourse.util.Config;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.LogManager;

import java.util.Collection;


public class ModEvents {
        @SubscribeEvent
        public void onCopperedSheep(AttackEntityEvent event) {
            if(event.getPlayer().getHeldItemMainhand().getItem() == ModItems.COPPERED_APPLE.get()) {
                if(event.getTarget().isAlive()) {
                    LivingEntity target = (LivingEntity) event.getTarget();
                    if(target instanceof SheepEntity) {
                        PlayerEntity player = event.getPlayer();
                        player.getHeldItemMainhand().shrink(1);
                        target.addPotionEffect(new EffectInstance(Effects.GLOWING, Config.COPPERED_GLOW_DURATION.get()));
                        if(!player.world.isRemote()) {
                            String msg = TextFormatting.YELLOW + "sheep be glowing";
                            player.sendMessage(new StringTextComponent(msg), player.getUniqueID());
                        }
                    }
                }
            }

    }
    /*
    public void onCopperedSheepDrops(LivingDropsEvent event) {
            LivingEntity entity = event.getEntityLiving();
            if(entity instanceof SheepEntity) {
                World world = entity.getEntityWorld();
                Collection<ItemEntity> drops = event.getDrops();
                LogManager.getLogger().debug(entity.getActivePotionEffects());
                if(entity.isPotionActive(Effects.GLOWING)) {
                    drops.add(new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), new ItemStack(ModItems.COPPER_INGOT.get())));
                }

            }
    }

     */
}
