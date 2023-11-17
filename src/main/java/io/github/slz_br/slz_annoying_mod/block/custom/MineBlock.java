package io.github.slz_br.slz_annoying_mod.block.custom;

import io.github.slz_br.slz_annoying_mod.sound.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MineBlock extends Block {

    private final static ScheduledExecutorService se = Executors.newScheduledThreadPool(1);
    private final static BooleanProperty INVISIBLE = BooleanProperty.of("invisible");

    public MineBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(INVISIBLE, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(INVISIBLE);
    }

    /**
     * Explode the block whenever the block is invisible and someone steps on it. If the block
     * is visible, {@link #onSteppedOn(World, BlockPos, BlockState, Entity)} will be called instead.
     * Check {@link net.minecraft.block.AbstractBlock#onEntityCollision(BlockState, World, BlockPos, Entity)} to know why.
     */
    @SuppressWarnings("deprecation") // when invisible the block's hitbox doesn't exist, so this method is needed.
    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        world.setBlockState(pos, state.with(INVISIBLE, false));
        se.schedule(
                () -> {},
                500, TimeUnit.MILLISECONDS
        );
        world.playSound(null, pos, ModSounds.MINE_ACTIVATED, SoundCategory.BLOCKS, 1f, 1f);
        entity.damage(entity.getDamageSources().explosion(world.createExplosion(entity, pos.getX(), pos.getY(), pos.getZ(), 15f, true, World.ExplosionSourceType.TNT)), 20f);
    }

    /**
     *  Explode the block whenever the block is visible and someone steps on it.
     *  if the block is invisible this won't work, check {@link io.github.slz_br.slz_annoying_mod.block.custom.MineBlock#onEntityCollision(BlockState, World, BlockPos, Entity)} to know more.
     */
    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        world.setBlockState(pos, state.with(INVISIBLE, false));
        se.schedule(
                () -> {},
                500, TimeUnit.MILLISECONDS
        );
        world.playSound(null, pos, ModSounds.MINE_ACTIVATED, SoundCategory.BLOCKS, 1f, 1f);
        entity.damage(entity.getDamageSources().explosion(world.createExplosion(entity, pos.getX(), pos.getY(), pos.getZ(), 15f, true, World.ExplosionSourceType.TNT)), 20f);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        se.schedule(
                () -> {
                    if (world.getBlockState(pos).getBlock().equals(this)) {
                        world.playSound(null, pos, ModSounds.MINE_BECOMING_INVISIBLE, SoundCategory.BLOCKS, 1f, 1f);
                        world.setBlockState(pos, state.with(INVISIBLE, true));
                    }
                }, 3, TimeUnit.SECONDS
        );
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        if (state.equals(getDefaultState().with(INVISIBLE, true))) {
            return BlockRenderType.INVISIBLE;
        }
        return BlockRenderType.MODEL;
    }


    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.equals(getDefaultState().with(INVISIBLE, true))) {
            return VoxelShapes.empty();
        }
        return VoxelShapes.cuboid(.05, 0, .05, .85, 0.45, .85);
    }

}
