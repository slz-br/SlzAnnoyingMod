package io.github.slz_br.slz_annoying_mod.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(CreeperEntity.class)
class CreeperEntityMixin extends HostileEntity {

    @Unique
    private final static Random rng = new Random();

    @Shadow
    private int fuseTime;

    @Shadow
    private int explosionRadius;

    protected CreeperEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("TAIL"), method = "<init>")
    private void injectIntoConstructor(EntityType<? extends CreeperEntity> entityType, World world, CallbackInfo ci) {
        fuseTime = rng.nextInt(60);
        explosionRadius = 9;
    }

    @Inject(at = @At("TAIL"), method = "initGoals")
    private void initGoals(CallbackInfo ci) {
        goalSelector.clear((goal) -> goal instanceof FleeEntityGoal<?> || goal instanceof MeleeAttackGoal);
        targetSelector.clear((goal -> goal instanceof ActiveTargetGoal<?>));
        goalSelector.add(2, new MeleeAttackGoal(this, 1.0, false));
        targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, false));
    }

    @Inject(at = @At("HEAD"), method = "createCreeperAttributes", cancellable = true)
    private static void createCreeperAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        cir.setReturnValue(createHostileAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, .5)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 45.0)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 15.0)
        );
    }

}
