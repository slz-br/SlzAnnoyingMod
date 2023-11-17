package io.github.slz_br.slz_annoying_mod.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IronGolemEntity.class)
class IronGolemEntityMixin extends GolemEntity {

    protected IronGolemEntityMixin(EntityType<? extends GolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("TAIL"), method = "initGoals")
    private void initGoals(CallbackInfo ci) {
        targetSelector.add(0, new ActiveTargetGoal<>(this, PlayerEntity.class, false));
    }

    @Inject(at = @At("HEAD"), method = "createIronGolemAttributes", cancellable = true)
    private static void createIronGolemAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        cir.setReturnValue(
                MobEntity.createMobAttributes()
                        .add(EntityAttributes.GENERIC_MAX_HEALTH, 200.0)
                        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, .5)
                        .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                        .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 19.0)
                        .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 126.0)
        );
    }

}
