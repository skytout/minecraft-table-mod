package com.mayatout.mccourse;

import com.mayatout.mccourse.block.ModBlocks;
import com.mayatout.mccourse.block.ModFluids;
import com.mayatout.mccourse.container.ModContainers;
import com.mayatout.mccourse.entity.BuffaloEntity;
import com.mayatout.mccourse.entity.ModEntityTypes;
import com.mayatout.mccourse.events.ModEvents;
import com.mayatout.mccourse.item.ModItems;
import com.mayatout.mccourse.setup.ClientProxy;
import com.mayatout.mccourse.setup.IProxy;
import com.mayatout.mccourse.setup.ServerProxy;
import com.mayatout.mccourse.tileentity.ModTileEntities;
import com.mayatout.mccourse.util.Config;
import com.mayatout.mccourse.util.Registration;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MCCourseMod.Mod_ID)
public class MCCourseMod
{
    public static final String Mod_ID = "mccourse";
    public static final ItemGroup COURSE_TAB = new ItemGroup("courseTab") {
        @Override
        public ItemStack createIcon()
        {
            return new ItemStack(ModItems.COPPER_WIRE.get());
        }
    };
    public static IProxy proxy;

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public MCCourseMod() {
        proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        registerModAdditions();

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        registerConfigs();

        proxy.init();

        loadConfigs();

        DeferredWorkQueue.runLater(() -> {
            GlobalEntityTypeAttributes.put(ModEntityTypes.BUFFALO.get(), BuffaloEntity.setCustomAttribute().create());
        });

    }
    private void registerConfigs() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_CONFIG);
    }
    private void loadConfigs() {
        Config.loadConfigFile(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve("mccourse-client.toml").toString());
        Config.loadConfigFile(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve("mccourse-server.toml").toString());
    }
    private void registerModAdditions() {
        //Inits the registration of our additions
        Registration.init();
        // registers items, blocks etc added by the mod
        ModItems.register();
        ModBlocks.register();
        ModFluids.register();
        ModTileEntities.register();
        ModContainers.register();
        ModEntityTypes.register();

        // register mod events
        MinecraftForge.EVENT_BUS.register(new ModEvents());
    }



    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }
}
