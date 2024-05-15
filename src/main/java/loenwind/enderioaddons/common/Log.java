package loenwind.enderioaddons.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import loenwind.enderioaddons.EnderIOAddons;

public final class Log {

    public static final Logger LOGGER = LogManager.getLogger(EnderIOAddons.MODID);

    public static void warn(String msg) {
        LOGGER.warn(msg);
    }

    public static void error(String msg) {
        LOGGER.error(msg);
    }

    public static void info(String msg) {
        LOGGER.info(msg);
    }

    public static void debug(String msg) {
        LOGGER.debug(msg);
    }

    private Log() {}

}
