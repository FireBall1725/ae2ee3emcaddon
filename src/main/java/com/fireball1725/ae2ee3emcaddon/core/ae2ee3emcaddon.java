package com.fireball1725.ae2ee3emcaddon.core;

import appeng.api.AEApi;
import appeng.api.definitions.IBlocks;
import appeng.api.definitions.IDefinitions;
import appeng.api.definitions.IItems;
import appeng.api.definitions.IMaterials;
import com.fireball1725.ae2ee3emcaddon.recipes.*;
import com.google.common.base.Stopwatch;
import com.pahimar.ee3.api.exchange.EnergyValueRegistryProxy;
import com.pahimar.ee3.api.knowledge.AbilityRegistryProxy;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import java.util.concurrent.TimeUnit;

@Mod(modid = ae2ee3emcaddon.MOD_ID, acceptedMinecraftVersions = "[1.7.10]", name = ae2ee3emcaddon.MOD_NAME, version = ae2ee3emcaddon.MOD_VERSION, dependencies = ae2ee3emcaddon.MOD_DEPENDENCIES)
public final class ae2ee3emcaddon {
    public static final String MOD_ID = "ae2ee3emcaddon";
    public static final String MOD_NAME = "Applied Energistics 2 EE3 EMC Addon";
    public static final String MOD_DEPENDENCIES = "after:appliedenergistics2;";
    public static final String MOD_VERSION = "@version@";

    public static ae2ee3emcaddon instance;

    public ae2ee3emcaddon() {
        instance = this;
    }

    @Mod.EventHandler
    void preInit(FMLPreInitializationEvent event) {
        if (Loader.isModLoaded("EE3")) {
            Stopwatch watch = Stopwatch.createStarted();
            AELog.info("Pre Initialization ( started )");

            final IDefinitions definitions = AEApi.instance().definitions();
            final IMaterials materials = definitions.materials();
            final IItems items = definitions.items();
            final IBlocks blocks = definitions.blocks();

            // Register Base AE Materials
            EnergyValueRegistryProxy.addPreAssignedEnergyValue(blocks.skyStone().maybeBlock().get(), 64);                         // Set the same as obsidian
            EnergyValueRegistryProxy.addPreAssignedEnergyValue(materials.certusQuartzCrystal().maybeStack(1).get(), 256);         //
            EnergyValueRegistryProxy.addPreAssignedEnergyValue(materials.certusQuartzCrystalCharged().maybeStack(1).get(), 256);  //
            EnergyValueRegistryProxy.addPreAssignedEnergyValue(materials.matterBall().maybeStack(1).get(), 256);
            EnergyValueRegistryProxy.addPreAssignedEnergyValue(materials.singularity().maybeStack(1).get(), 256000);

            // Non Learnable AE Materials
            EnergyValueRegistryProxy.addPreAssignedEnergyValue(blocks.quartzOre().maybeBlock().get(), 256);
            EnergyValueRegistryProxy.addPreAssignedEnergyValue(blocks.quartzOreCharged().maybeBlock().get(), 256);
            EnergyValueRegistryProxy.addPreAssignedEnergyValue(items.cellCreative().maybeStack(1).get(), 1725);
            AbilityRegistryProxy.setAsNotLearnable(blocks.quartzOre().maybeBlock().get());
            AbilityRegistryProxy.setAsNotLearnable(blocks.quartzOreCharged().maybeBlock().get());
            AbilityRegistryProxy.setAsNotLearnable(items.cellCreative().maybeStack(1).get());

            AELog.info("Pre Initialization ( ended after " + watch.elapsed(TimeUnit.MILLISECONDS) + "ms )");
        } else {
            AELog.info("EE3 not found, not sure why you would add this mod?");
        }
    }

    @Mod.EventHandler
    void postInit(FMLPostInitializationEvent event) {
        if (Loader.isModLoaded("EE3")) {
            Stopwatch watch = Stopwatch.createStarted();
            AELog.info("Post Initialization ( started )");

            RegisterCrafting.initRecipes();
            RegisterFurnace.initRecipes();
            RegisterFacade.initRecipes();
            RegisterGrinder.initRecipes();
            RegisterInscriber.initRecipes();
            RegisterWorld.initRecipes();

            //RecipeRegistryProxy.dumpRecipeRegistryToLog();

            AELog.info("Post Initialization ( ended after " + watch.elapsed(TimeUnit.MILLISECONDS) + "ms )");
        }
    }
}