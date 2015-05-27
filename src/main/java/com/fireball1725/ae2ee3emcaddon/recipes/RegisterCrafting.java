package com.fireball1725.ae2ee3emcaddon.recipes;

import appeng.api.exceptions.MissingIngredientError;
import appeng.api.exceptions.RegistrationError;
import appeng.api.recipes.IIngredient;
import appeng.recipes.game.ShapedRecipe;
import appeng.recipes.game.ShapelessRecipe;
import appeng.util.Platform;
import com.fireball1725.ae2ee3emcaddon.core.AELog;
import com.pahimar.ee3.api.exchange.RecipeRegistryProxy;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import java.util.ArrayList;
import java.util.List;

public class RegisterCrafting {
    public static void initRecipes() {
        int recipeCount = 0;

        for (Object recipeObject : CraftingManager.getInstance().getRecipeList()) {
            if (recipeObject instanceof ShapelessRecipe || recipeObject instanceof ShapedRecipe) {
                IRecipe recipe = (IRecipe) recipeObject;
                ItemStack recipeOutput = recipe.getRecipeOutput();

                if (recipeOutput != null) {
                    List<ItemStack> recipeInputs = getRecipeInputs(recipe, recipeOutput);

                    AELog.debug(">>> EE3 Recipe Register >>> Output: " + recipeOutput.toString() + " >>> Input(s): " + recipeInputs.toString());

                    if (!recipeInputs.isEmpty()) {
                        recipeCount++;
                        RecipeRegistryProxy.addRecipe(recipeOutput, recipeInputs);
                    }
                }
            }
        }

        AELog.info("Told EE3 about " + recipeCount + " crafting recipes...");
    }

    public static List<ItemStack> getRecipeInputs(IRecipe recipe, ItemStack recipeOutput) {
        ArrayList<ItemStack> recipeInputs = new ArrayList<ItemStack>();

        if (recipe instanceof ShapedRecipe) {
            ShapedRecipe shapedOreRecipe = (ShapedRecipe) recipe;
            for (int i = 0; i < shapedOreRecipe.getInput().length; i++) {
                if (shapedOreRecipe.getInput()[i] instanceof IIngredient) {
                    IIngredient ing = (IIngredient) shapedOreRecipe.getInput()[i];

                    try {
                        ItemStack[] is = ing.getItemStackSet().clone();
                        Object preferred = Platform.findPreferred(is);
                        ItemStack itemStack;
                        if (preferred instanceof ItemStack) {
                            itemStack = (ItemStack) preferred;
                        } else {
                            itemStack = is[0];
                        }

                        if (itemStack.stackSize == 0)
                            itemStack.stackSize = 1;

                        recipeInputs.add(itemStack);
                    } catch (RegistrationError ignored) {
                        ignored.printStackTrace();
                    } catch (MissingIngredientError ignored) {
                        ignored.printStackTrace();
                    }
                }
            }

        } else if (recipe instanceof ShapelessRecipe) {
            ShapelessRecipe shapelessOreRecipe = (ShapelessRecipe) recipe;

            for (int i = 0; i < shapelessOreRecipe.getInput().size(); i++) {
                if (shapelessOreRecipe.getInput().get(i) instanceof IIngredient) {
                    IIngredient ing = (IIngredient) shapelessOreRecipe.getInput().get(i);

                    try {
                        ItemStack[] is = ing.getItemStackSet().clone();
                        Object preferred = Platform.findPreferred(is);
                        ItemStack itemStack;
                        if (preferred instanceof ItemStack) {
                            itemStack = (ItemStack) preferred;
                        } else {
                            itemStack = is[0];
                        }

                        if (itemStack.stackSize == 0)
                            itemStack.stackSize = 1;

                        recipeInputs.add(itemStack);
                    } catch (RegistrationError ignored) {
                        ignored.printStackTrace();
                    } catch (MissingIngredientError ignored) {
                        ignored.printStackTrace();
                    }
                }
            }
        }

        if (clearBucketRecipe(recipeInputs, recipeOutput))
            recipeInputs.clear();

        return recipeInputs;
    }

    private static boolean clearBucketRecipe(ArrayList<ItemStack> itemStacklist, ItemStack itemOutput) {
        if (itemStacklist.size() == 2) {
            boolean inputSameOutput = false;
            boolean containsWaterBucket = false;
            for (ItemStack is : itemStacklist) {
                if (is.isItemEqual(new ItemStack(Items.water_bucket, 1)))
                    containsWaterBucket = true;
                if (is.isItemEqual(itemOutput))
                    inputSameOutput = true;
            }
            if (inputSameOutput && containsWaterBucket) {
                AELog.debug(">>> DynEMC Loop <<<");
                AELog.debug(">>> Output: " + itemOutput.toString() + " >>> Input: " + itemStacklist.toString());
                AELog.debug(">>> Recipe will cause issues with DynEMC, clearing recipe...");

                return true;
            }
        }
        return false;
    }
}
