package loenwind.enderioaddons.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import crazypants.enderio.network.PacketHandler;
import loenwind.enderioaddons.common.GuiIds;
import loenwind.enderioaddons.common.InitAware;
import loenwind.enderioaddons.fluid.Fluids;
import loenwind.enderioaddons.gui.PacketAdvancedRedstoneMode;
import loenwind.enderioaddons.machine.afarm.AgriDetector;
import loenwind.enderioaddons.machine.afarm.BlockAfarm;
import loenwind.enderioaddons.machine.afarm.item.ItemModule;
import loenwind.enderioaddons.machine.chassis.BlockChassis;
import loenwind.enderioaddons.machine.cobbleworks.BlockCobbleworks;
import loenwind.enderioaddons.machine.drain.BlockDrain;
import loenwind.enderioaddons.machine.drain.InfiniteWaterSourceStopper;
import loenwind.enderioaddons.machine.flag.BlockFlag;
import loenwind.enderioaddons.machine.framework.AbstractBlockFramework;
import loenwind.enderioaddons.machine.ihopper.BlockIHopper;
import loenwind.enderioaddons.machine.magcharger.BlockMagCharger;
import loenwind.enderioaddons.machine.niard.BlockNiard;
import loenwind.enderioaddons.machine.part.ItemMachinePart;
import loenwind.enderioaddons.machine.pmon.BlockPMon;
import loenwind.enderioaddons.machine.rlever.BlockRLever;
import loenwind.enderioaddons.machine.tcom.BlockTcom;
import loenwind.enderioaddons.machine.voidtank.BlockVoidTank;
import loenwind.enderioaddons.machine.waterworks.BlockWaterworks;

public class ClientAndServerProxy implements InitAware {

    @Override
    public void init(FMLPreInitializationEvent event) {
        GuiIds.compute_GUI_IDs();
        Fluids.init(event);
        BlockDrain.create();
        AbstractBlockFramework.create();
        BlockCobbleworks.create();
        BlockWaterworks.create();
        BlockIHopper.create();
        ItemMachinePart.create();
        BlockNiard.create();
        BlockVoidTank.create();
        BlockPMon.create();
        BlockTcom.create();
        BlockFlag.create();
        BlockMagCharger.create();
        BlockChassis.create();
        if (AgriDetector.hasAgri) {
            BlockAfarm.create();
            ItemModule.create();
        }
        BlockRLever.create();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        InfiniteWaterSourceStopper.register();
        PacketHandler.INSTANCE.registerMessage(
            PacketAdvancedRedstoneMode.class,
            PacketAdvancedRedstoneMode.class,
            PacketHandler.nextID(),
            Side.SERVER);
        if (AgriDetector.hasAgri) {
            AgriDetector.registerPlants();
        }
    }

    @Override
    public void init(FMLPostInitializationEvent event) {}

}
