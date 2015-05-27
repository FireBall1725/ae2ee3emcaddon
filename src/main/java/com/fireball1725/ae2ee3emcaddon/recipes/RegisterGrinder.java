package com.fireball1725.ae2ee3emcaddon.recipes;

import appeng.api.AEApi;
import appeng.api.features.IGrinderEntry;
import com.fireball1725.ae2ee3emcaddon.core.AELog;
import com.pahimar.ee3.api.exchange.RecipeRegistryProxy;
import net.minecraft.item.ItemStack;

import java.util.Arrays;

public class RegisterGrinder {
    public static void initRecipes() {
        int recipeCount = 0;

        for (IGrinderEntry recipe : AEApi.instance().registries().grinder().getRecipes()) {
            AELog.debug(">>> EE3 Recipe Register >>> Output: " + recipe.getOutput() + " >>> Input(s): " + recipe.getInput().toString());
            recipeCount++;
            RecipeRegistryProxy.addRecipe(recipe.getOutput(), Arrays.asList(new ItemStack[]{recipe.getInput()}));
        }

        AELog.info("Told EE3 about " + recipeCount + " grinder recipes...");
    }
}
