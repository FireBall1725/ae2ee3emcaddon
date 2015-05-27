package com.fireball1725.ae2ee3emcaddon.recipes;

import appeng.api.AEApi;
import appeng.api.definitions.IBlocks;
import appeng.api.definitions.IDefinitions;
import appeng.api.definitions.IMaterials;
import com.fireball1725.ae2ee3emcaddon.core.AELog;
import com.pahimar.ee3.api.exchange.RecipeRegistryProxy;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class RegisterFurnace {
    public static void initRecipes() {
        int recipeCount = 2;

        final IDefinitions definitions = AEApi.instance().definitions();
        final IMaterials materials = definitions.materials();
        final IBlocks blocks = definitions.blocks();

        addRecipe(materials.silicon().maybeStack(1).get(), Arrays.asList(new ItemStack[]{materials.certusQuartzDust().maybeStack(1).get()}));
        recipeCount++;
        addRecipe(new ItemStack(blocks.skyStone().maybeItem().get(), 1, 1), Arrays.asList(new ItemStack[]{blocks.skyStone().maybeStack(1).get()}));
        recipeCount++;

        AELog.info("Told EE3 about " + recipeCount + " furnace recipes...");
    }

    private static void addRecipe(ItemStack output, List<ItemStack> input) {
        AELog.debug(">>> EE3 Recipe Register >>> Output: " + output.toString() + " >>> Input(s): " + input.toString());
        RecipeRegistryProxy.addRecipe(output, input);
    }
}
