package com.mayatout.mccourse.entity;

import com.mayatout.mccourse.MCCourseMod;
import com.mayatout.mccourse.util.Registration;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

public class ModEntityTypes {
    public static final RegistryObject<EntityType<BuffaloEntity>> BUFFALO = Registration.ENTITY_TYPES.register("buffalo",
            () -> EntityType.Builder.create(BuffaloEntity::new, EntityClassification.CREATURE)
                    .size(1.5f, 1.5f)
                    .build(new ResourceLocation(MCCourseMod.Mod_ID + "buffalo").toString()));

    public static void register() { }
}
