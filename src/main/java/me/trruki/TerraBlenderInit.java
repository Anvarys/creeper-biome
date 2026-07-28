package me.trruki;

import net.minecraft.resources.Identifier;
import terrablender.api.RegionType;
import terrablender.api.Regions;
import terrablender.api.TerraBlenderApi;

public class TerraBlenderInit implements TerraBlenderApi {
    @Override
    public void onTerraBlenderInitialized() {
        Regions.register(new CreeperRegion(
                Identifier.fromNamespaceAndPath("creeper-biome", "creeper_forest"),
                1
        ));
    }
}