package loenwind.enderioaddons.nei;

import static loenwind.enderioaddons.config.Config.cobbleWorksEnabled;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import loenwind.enderioaddons.EnderIOAddons;
import loenwind.enderioaddons.config.Config;
import loenwind.enderioaddons.fluid.Fluids;
import loenwind.enderioaddons.machine.afarm.AgriDetector;
import loenwind.enderioaddons.machine.afarm.BlockAfarm;
import loenwind.enderioaddons.machine.afarm.item.ItemModule;
import loenwind.enderioaddons.machine.chassis.BlockChassis;
import loenwind.enderioaddons.machine.cobbleworks.BlockCobbleworks;
import loenwind.enderioaddons.machine.flag.BlockFlag;
import loenwind.enderioaddons.machine.framework.AbstractBlockFramework;
import loenwind.enderioaddons.machine.ihopper.BlockIHopper;
import loenwind.enderioaddons.machine.magcharger.BlockMagCharger;
import loenwind.enderioaddons.machine.niard.BlockNiard;
import loenwind.enderioaddons.machine.part.ItemMachinePart;
import loenwind.enderioaddons.machine.part.MachinePart;
import loenwind.enderioaddons.machine.pmon.BlockPMon;
import loenwind.enderioaddons.machine.tcom.BlockTcom;
import loenwind.enderioaddons.machine.voidtank.BlockVoidTank;
import loenwind.enderioaddons.machine.waterworks.BlockWaterworks;

public class NEIEnderIOConfig implements IConfigureNEI {

    @Override
    public void loadConfig() {
        API.registerRecipeHandler(new TeaserRecipeHandler());
        API.registerUsageHandler(new TeaserRecipeHandler());

        if (!Config.fortuneCookiesEnabled.getBoolean()) {
            API.hideItem(new ItemStack(ItemMachinePart.itemMachinePart, 1, MachinePart.COOKIE.ordinal()));
        }
        API.hideItem(new ItemStack(ItemMachinePart.itemMachinePart, 1, MachinePart.COOKIESTRIP.ordinal()));

        API.hideItem(new ItemStack(AbstractBlockFramework.blockDummy, 1, OreDictionary.WILDCARD_VALUE));
        if (!cobbleWorksEnabled.getBoolean()) {
            API.hideItem(new ItemStack(ItemMachinePart.itemMachinePart, 1, MachinePart.COBBLE_CONTROLLER.ordinal()));
            API.hideItem(new ItemStack(BlockCobbleworks.blockCobbleworks, 1, OreDictionary.WILDCARD_VALUE));
        }
        if (!Config.waterWorksEnabled.getBoolean()) {
            API.hideItem(new ItemStack(ItemMachinePart.itemMachinePart, 1, MachinePart.HEATING_ELEMENT.ordinal()));
            API.hideItem(new ItemStack(ItemMachinePart.itemMachinePart, 1, MachinePart.FILTER_ELEMENT.ordinal()));
            API.hideItem(new ItemStack(ItemMachinePart.itemMachinePart, 1, MachinePart.WATER_CONTROLLER.ordinal()));
            API.hideItem(new ItemStack(BlockWaterworks.blockWaterworks, 1, OreDictionary.WILDCARD_VALUE));
            for (Fluids fluid : Fluids.values()) {
                API.hideItem(new ItemStack(fluid.getBlock(), 1, OreDictionary.WILDCARD_VALUE));
                API.hideItem(new ItemStack(fluid.getBucket(), 1, OreDictionary.WILDCARD_VALUE));
            }
        }
        if (!Config.tcomEnabled.getBoolean()) {
            API.hideItem(new ItemStack(ItemMachinePart.itemMachinePart, 1, MachinePart.TRAY.ordinal()));
            API.hideItem(new ItemStack(ItemMachinePart.itemMachinePart, 1, MachinePart.PYLON.ordinal()));
            API.hideItem(new ItemStack(ItemMachinePart.itemMachinePart, 1, MachinePart.PYLONTANK.ordinal()));
            API.hideItem(new ItemStack(ItemMachinePart.itemMachinePart, 1, MachinePart.TCOM_CONTROLLER.ordinal()));
            API.hideItem(new ItemStack(BlockTcom.blockTcom, 1, OreDictionary.WILDCARD_VALUE));
        }
        if (!Config.impulseHopperEnabled.getBoolean()) {
            API.hideItem(new ItemStack(ItemMachinePart.itemMachinePart, 1, MachinePart.IHOPPER_CONTROLLER.ordinal()));
            API.hideItem(new ItemStack(BlockIHopper.blockIHopper, 1, OreDictionary.WILDCARD_VALUE));
        }
        if (!Config.niardEnabled.getBoolean()) {
            API.hideItem(new ItemStack(BlockNiard.blockNiard, 1, OreDictionary.WILDCARD_VALUE));
        }
        if (!Config.voidTankEnabled.getBoolean()) {
            API.hideItem(new ItemStack(BlockVoidTank.blockVoidTank, 1, OreDictionary.WILDCARD_VALUE));
        }
        if (!Config.pMonEnabled.getBoolean()) {
            API.hideItem(new ItemStack(BlockPMon.blockPMon, 1, OreDictionary.WILDCARD_VALUE));
        }
        boolean farmEnabled = Config.farmEnabled.getBoolean() && AgriDetector.hasAgri;
        if (!farmEnabled && !Config.flagEnabled.getBoolean()
            && !Config.magcEnabled.getBoolean()
            && !Config.decoBlockEnabled.getBoolean()) {
            API.hideItem(new ItemStack(ItemMachinePart.itemMachinePart, 8, MachinePart.CHASSIPARTS.ordinal()));
        }
        if (!Config.flagEnabled.getBoolean() && !Config.magcEnabled.getBoolean()) {
            API.hideItem(new ItemStack(ItemMachinePart.itemMachinePart, 2, MachinePart.SIMPLEMAGNET.ordinal()));
        }
        if (Config.flagEnabled.getBoolean() && Config.magcEnabled.getBoolean()) {
            API.registerRecipeHandler(new MagChargerRecipeHandler());
            API.registerUsageHandler(new MagChargerRecipeHandler());
        }
        if (!Config.flagEnabled.getBoolean()) {
            API.hideItem(new ItemStack(ItemMachinePart.itemMachinePart, 8, MachinePart.FLAGPARTS.ordinal()));
            API.hideItem(new ItemStack(BlockFlag.blockFlag, 64, OreDictionary.WILDCARD_VALUE));
        }
        if (!Config.flagEnabled.getBoolean()) {
            API.hideItem(new ItemStack(BlockMagCharger.blockMagCharger, 1, OreDictionary.WILDCARD_VALUE));
        }
        if (!Config.decoBlockEnabled.getBoolean()) {
            API.hideItem(new ItemStack(BlockChassis.blockChassis, 1, OreDictionary.WILDCARD_VALUE));
        }
        if (!farmEnabled) {
            for (MachinePart part : MachinePart.values()) {
                if (part.isAgri(part.ordinal())) {
                    API.hideItem(new ItemStack(ItemMachinePart.itemMachinePart, 1, part.ordinal()));
                }
            }
        }
        if (!Config.farmEnabled.getBoolean() && AgriDetector.hasAgri) {
            // Note: Only hide items that don't exist when there is no agricraft if there is an agricraft
            API.hideItem(new ItemStack(BlockAfarm.blockAfarm, 1, OreDictionary.WILDCARD_VALUE));
            API.hideItem(new ItemStack(ItemModule.itemModule, 1, OreDictionary.WILDCARD_VALUE));
        }
        if (AgriDetector.hasAgri) {
            API.hideItem(new ItemStack(ItemMachinePart.itemMachinePart, 1, MachinePart.AFARMINFO.ordinal()));
        }
    }

    @Override
    public String getName() {
        return "Ender IO Addons NEI Plugin";
    }

    @Override
    public String getVersion() {
        return EnderIOAddons.VERSION;
    }

}
