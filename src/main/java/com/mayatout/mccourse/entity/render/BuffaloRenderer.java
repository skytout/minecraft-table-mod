package com.mayatout.mccourse.entity.render;

import com.mayatout.mccourse.MCCourseMod;
import com.mayatout.mccourse.entity.BuffaloEntity;
import com.mayatout.mccourse.entity.model.BuffaloModel;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class BuffaloRenderer  extends MobRenderer<BuffaloEntity, BuffaloModel<BuffaloEntity>> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(MCCourseMod.Mod_ID,
            "textures/entity/buffalo.png");

    public BuffaloRenderer(EntityRendererManager renderManagerIn)
    {
        super(renderManagerIn, new BuffaloModel<>(), 0.9f);
    }

    @Override
    public ResourceLocation getEntityTexture(BuffaloEntity entity)
    {
        return TEXTURE;
    }
}
