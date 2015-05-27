package com.fireball1725.ae2ee3emcaddon.recipes;

import appeng.api.AEApi;
import appeng.api.definitions.IDefinitions;
import appeng.api.definitions.IItems;
import appeng.api.definitions.IMaterials;
import appeng.core.AEConfig;
import appeng.core.features.AEFeature;
import com.fireball1725.ae2ee3emcaddon.core.AELog;
import com.pahimar.ee3.api.exchange.RecipeRegistryProxy;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class RegisterWorld {
    public static void initRecipes() {
        int recipeCount = 0;

        final IDefinitions definitions = AEApi.instance().definitions();
        final IMaterials materials = definitions.materials();
        final IItems items = definitions.items();

        if (AEConfig.instance.isFeatureEnabled(AEFeature.inWorldFluix)) {
            addRecipe(materials.fluixCrystal().maybeStack(1).get(), Arrays.asList(new ItemStack[]{materials.certusQuartzCrystalCharged().maybeStack(1).get(), new ItemStack(Items.redstone, 1), new ItemStack(Items.quartz, 1)}));
            recipeCount++;
        }

        if (AEConfig.instance.isFeatureEnabled(AEFeature.inWorldPurification)) {
            addRecipe(materials.purifiedCertusQuartzCrystal().maybeStack(1).get(), Arrays.asList(new ItemStack[]{new ItemStack(items.crystalSeed().maybeItem().get(), 1)}));
            recipeCount++;
            addRecipe(materials.purifiedFluixCrystal().maybeStack(1).get(), Arrays.asList(new ItemStack[]{new ItemStack(items.crystalSeed().maybeItem().get(), 1, 600)}));
            recipeCount++;
            addRecipe(materials.purifiedNetherQuartzCrystal().maybeStack(1).get(), Arrays.asList(new ItemStack[]{new ItemStack(items.crystalSeed().maybeItem().get(), 1, 1200)}));
            recipeCount++;
        }

        AELog.info("Told EE3 about " + recipeCount + " world recipes...");
    }

    private static void addRecipe(ItemStack output, List<ItemStack> input) {
        AELog.debug(">>> EE3 Recipe Register >>> Output: " + output.toString() + " >>> Input(s): " + input.toString());
        RecipeRegistryProxy.addRecipe(output, input);
    }
}
