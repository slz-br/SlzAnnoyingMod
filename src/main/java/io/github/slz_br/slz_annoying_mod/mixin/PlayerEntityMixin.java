package io.github.slz_br.slz_annoying_mod.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
abstract class PlayerEntityMixin extends LivingEntity {

    @Shadow public abstract void addExhaustion(float exhaustion);

    @Shadow @Final private PlayerAbilities abilities;

    @Shadow public abstract void increaseStat(Identifier stat, int amount);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("HEAD"), method = "jump", cancellable = true)
    private void jump(CallbackInfo ci) {
        super.jump();
        this.increaseStat(Stats.JUMP, 1);
        if (isSprinting()) addExhaustion(.5F);
        else addExhaustion(0.25F);
        ci.cancel();
    }

    @Inject(at = @At("HEAD"), method = "handleFallDamage", cancellable = true)
    private void handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        if (this.abilities.allowFlying) {
            cir.setReturnValue(false);
        } else {
            if (fallDistance >= 2.0F) {
                this.increaseStat(Stats.FALL_ONE_CM, (int)Math.round((double)fallDistance * 100.0));
            }

            cir.setReturnValue(super.handleFallDamage(fallDistance, damageMultiplier * 5f, damageSource));
        }
        cir.setReturnValue(false);
    }

    @Inject(at = @At("HEAD"), method = "damage", cancellable = true)
    private void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (source.getName().equals("inFire")) {
            super.damage(source, 7.5f);
            cir.setReturnValue(true);
        }
    }

    @Inject(at = @At("HEAD"), method = "eatFood")
    private void eatFood(World world, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        if (stack.isFood() && !stack.getName().contains(Text.of("cooked"))) {
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 5), this);
        }
    }

}
