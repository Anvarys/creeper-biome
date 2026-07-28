package me.trruki;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.ModInitializer;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.level.biome.Biome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreeperBiome implements ModInitializer {
	public static final String MOD_ID = "creeper-biome";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final ResourceKey<Biome> CREEPER_BIOME = ResourceKey.create(
			Registries.BIOME,
			Identifier.fromNamespaceAndPath("creeper-biome", "creeper_forest")
	);

	public static final MapCodec<ExplosiveArrowEffect> EXPLOSIVE_ARROW =
			registerEnchantmentEffect("explosive_arrow", ExplosiveArrowEffect.CODEC);

	private static <T extends EnchantmentEntityEffect> MapCodec<T> registerEnchantmentEffect(String id, MapCodec<T> codec) {
		return Registry.register(
				BuiltInRegistries.ENCHANTMENT_ENTITY_EFFECT_TYPE,
				Identifier.fromNamespaceAndPath(MOD_ID, id),
				codec
		);
	}

	@Override
	public void onInitialize() {

	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
