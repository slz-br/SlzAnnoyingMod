package io.github.slz_br.slz_annoying_mod.mixin;

import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.registry.Registries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.entity.mob.SpiderEntity$AttackGoal")
class SpiderAttackGoalMixin extends MeleeAttackGoal {

    public SpiderAttackGoalMixin(PathAwareEntity mob, double speed) {
        super(mob, speed, false);
    }

    // When this Mixin is applied, for some reason, spiders can't attack the player.
    // They still try to attack the player, but they don't deal damage.
    // Todo(Solve this, so spiders can deal damage to players when this mixin is applied.)
//    @Inject(at = @At("HEAD"), method = "canStart", cancellable = true)
//    private void canStart(CallbackInfoReturnable<Boolean> cir) {
//        if (super.canStart() && !this.mob.hasPassengers()) {
//            mob.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 60), this.mob);
//        }
//        cir.setReturnValue(super.canStart() && !mob.hasPassengers());
//    }

}
