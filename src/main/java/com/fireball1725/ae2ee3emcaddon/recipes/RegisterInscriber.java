package com.fireball1725.ae2ee3emcaddon.recipes;

import appeng.api.AEApi;
import appeng.api.definitions.IDefinitions;
import appeng.api.definitions.IMaterials;
import appeng.api.features.IInscriberRecipe;
import com.fireball1725.ae2ee3emcaddon.core.AELog;
import com.pahimar.ee3.api.exchange.RecipeRegistryProxy;
import net.minecraft.item.ItemStack;

import java.util.List;

public class RegisterInscriber {
    public static void initRecipes() {
        int recipeCount = 0;

        for (IInscriberRecipe recipe : AEApi.instance().registries().inscriber().getRecipes()) {
            final IDefinitions definitions = AEApi.instance().definitions();
            final IMaterials materials = definitions.materials();

            List<ItemStack> input = recipe.getInputs();

            for (ItemStack top : recipe.getTopOptional().asSet()) {
                if (!top.isItemEqual(materials.calcProcessorPress().maybeStack(1).get()) && !top.isItemEqual(materials.engProcessorPress().maybeStack(1).get()) && !top.isItemEqual(materials.logicProcessorPress().maybeStack(1).get()) && !top.isItemEqual(materials.siliconPress().maybeStack(1).get())) {
                    input.add(top);
                }
            }

            for (ItemStack bottom : recipe.getBottomOptional().asSet()) {
                input.add(bottom);
            }

            AELog.debug(">>> EE3 Recipe Register >>> Output: " + recipe.getOutput().toString() + " >>> Input(s): " + input.toString());
            recipeCount++;
            RecipeRegistryProxy.addRecipe(recipe.getOutput(), input);
        }

        AELog.info("Told EE3 about " + recipeCount + " inscriber recipes...");
    }
}
