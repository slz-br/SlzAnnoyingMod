package io.github.slz_br.slz_annoying_mod.mixin;


import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SpiderEntity.class)
abstract class SpiderEntityMixin extends HostileEntity implements RangedAttackMob {

    public SpiderEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        FallingBlockEntity.class.getConstructors()[1].setAccessible(true);
    }

    @Override
    public void shootAt(LivingEntity target, float pullProgress) {
        return;
    }
}
