package me.trruki;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public record ExplosiveArrowEffect(double chancePerLevel, LevelBasedValue power, boolean destroyBlocks) implements EnchantmentEntityEffect {

    public static final MapCodec<ExplosiveArrowEffect> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.DOUBLE.optionalFieldOf("chance_per_level", 0.1).forGetter(ExplosiveArrowEffect::chancePerLevel),
                    LevelBasedValue.CODEC.optionalFieldOf("power", LevelBasedValue.perLevel(1.0f, 0.5f)).forGetter(ExplosiveArrowEffect::power),
                    Codec.BOOL.optionalFieldOf("destroy_blocks", true).forGetter(ExplosiveArrowEffect::destroyBlocks)
            ).apply(instance, ExplosiveArrowEffect::new)
    );

    @Override
    public void apply(ServerLevel serverLevel, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 position) {
        double chance = chancePerLevel * enchantmentLevel;

        if (serverLevel.getRandom().nextDouble() >= chance) return;

        float explosionPower = power.calculate(enchantmentLevel);

        serverLevel.explode(
                entity,
                position.x,
                position.y+0.1,
                position.z,
                explosionPower,
                destroyBlocks ? Level.ExplosionInteraction.TNT : Level.ExplosionInteraction.NONE
        );

        if (entity instanceof AbstractArrow arrow) {
            arrow.discard();
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
