package com.fireball1725.ae2ee3emcaddon.core;

import appeng.core.AEConfig;
import appeng.core.features.AEFeature;
import appeng.tile.AEBaseTile;
import appeng.util.Platform;
import cpw.mods.fml.relauncher.FMLRelaunchLog;
import org.apache.logging.log4j.Level;

public class AELog {
    public static final FMLRelaunchLog INSTANCE = FMLRelaunchLog.log;

    private AELog() {
    }

    public static void warning(String format, Object... data) {
        log(Level.WARN, format, data);
    }

    private static void log(Level level, String format, Object... data) {
        if (AEConfig.instance == null || AEConfig.instance.isFeatureEnabled(AEFeature.Logging)) {
            FMLRelaunchLog.log("AE2EE3Compat:" + (Platform.isServer() ? "S" : "C"), level, format, data);
        }
    }

    public static void grinder(String o) {
        if (AEConfig.instance.isFeatureEnabled(AEFeature.GrinderLogging)) {
            log(Level.DEBUG, "grinder: " + o);
        }
    }

    public static void integration(Throwable exception) {
        if (AEConfig.instance.isFeatureEnabled(AEFeature.IntegrationLogging)) {
            error(exception);
        }
    }

    public static void error(Throwable e) {
        if (AEConfig.instance.isFeatureEnabled(AEFeature.Logging)) {
            severe("Error: " + e.getClass().getName() + " : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void severe(String format, Object... data) {
        log(Level.ERROR, format, data);
    }

    public static void blockUpdate(int xCoord, int yCoord, int zCoord, AEBaseTile aeBaseTile) {
        if (AEConfig.instance.isFeatureEnabled(AEFeature.UpdateLogging)) {
            info(aeBaseTile.getClass().getName() + " @ " + xCoord + ", " + yCoord + ", " + zCoord);
        }
    }

    public static void info(String format, Object... data) {
        log(Level.INFO, format, data);
    }

    public static void debug(String format, Object... data) {
        log(Level.DEBUG, format, data);
    }

    public static void trace(String format, Object... data) {
        log(Level.TRACE, format, data);
    }

    public static void crafting(String format, Object... data) {
        if (AEConfig.instance.isFeatureEnabled(AEFeature.CraftingLog)) {
            log(Level.INFO, format, data);
        }
    }
}
