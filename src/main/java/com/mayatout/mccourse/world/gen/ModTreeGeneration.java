package com.mayatout.mccourse.world.gen;

import com.google.common.collect.Lists;
import com.mayatout.mccourse.MCCourseMod;
import com.mayatout.mccourse.block.RedwoodTree;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.*;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;


    @Mod.EventBusSubscriber(modid = MCCourseMod.Mod_ID)
    public class ModTreeGeneration
    {
        @SubscribeEvent
        public static void onBiomeLoad(BiomeLoadingEvent event)
        {
            RegistryKey<Biome> key = RegistryKey.getOrCreateKey(Registry.BIOME_KEY, event.getName());
            Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(key);

            if (types.contains(BiomeDictionary.Type.PLAINS))
            {
                List<Supplier<ConfiguredFeature<?, ?>>> base =
                        event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION);

                base.add(() -> Feature.TREE.withConfiguration(RedwoodTree.REDWOOD_TREE_CONFIG)
                        .withPlacement(Placement.DARK_OAK_TREE.configure(NoPlacementConfig.INSTANCE)));
            }
        }
    }

