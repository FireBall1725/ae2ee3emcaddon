package com.fireball1725.ae2ee3emcaddon.recipes;

import appeng.api.AEApi;
import appeng.api.definitions.IDefinitions;
import appeng.api.definitions.IItemDefinition;
import appeng.facade.IFacadeItem;
import appeng.items.parts.ItemFacade;
import com.fireball1725.ae2ee3emcaddon.core.AELog;
import com.pahimar.ee3.api.exchange.RecipeRegistryProxy;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class RegisterFacade {
    public static void initRecipes() {
        int recipeCount = 0;

        final IDefinitions definitions = AEApi.instance().definitions();
        final ItemFacade facade = (ItemFacade) definitions.items().facade().maybeItem().get();
        final IItemDefinition anchorDefinition = definitions.parts().cableAnchor();

        final List<ItemStack> facades = facade.getFacades();
        for (ItemStack anchorStack : anchorDefinition.maybeStack(1).asSet()) {
            for (ItemStack is : facades) {
                recipeCount++;
                addItem(facade, anchorStack, is);
            }
        }

        AELog.info("Told EE3 about " + recipeCount + " facade recipes...");
    }

    private static void addItem(IFacadeItem facade, ItemStack anchor, ItemStack output) {
        anchor.stackSize = 4;
        output.stackSize = 4;

        List<ItemStack> input = new ArrayList<ItemStack>();

        input.add(anchor);
        input.add(facade.getTextureItem(output));

        AELog.debug(">>> EE3 Recipe Register >>> Output: " + output.toString() + " >>> Input(s): " + input.toString());
        RecipeRegistryProxy.addRecipe(output, input);
    }
}
