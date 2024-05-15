package loenwind.enderioaddons.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import loenwind.enderioaddons.machine.afarm.AgriDetector;
import loenwind.enderioaddons.machine.afarm.BlockAfarm;
import loenwind.enderioaddons.machine.afarm.ItemRendererAfarm;
import loenwind.enderioaddons.machine.afarm.RendererAfarm;
import loenwind.enderioaddons.machine.afarm.TESRAfarm;
import loenwind.enderioaddons.machine.afarm.TileAfarm;
import loenwind.enderioaddons.machine.chassis.BlockChassis;
import loenwind.enderioaddons.machine.chassis.ItemRendererChassis;
import loenwind.enderioaddons.machine.chassis.RendererChassis;
import loenwind.enderioaddons.machine.cobbleworks.BlockCobbleworks;
import loenwind.enderioaddons.machine.cobbleworks.RendererCobbleworks;
import loenwind.enderioaddons.machine.cobbleworks.TileCobbleworks;
import loenwind.enderioaddons.machine.drain.BlockDrain;
import loenwind.enderioaddons.machine.drain.DrainBlockRenderer;
import loenwind.enderioaddons.machine.drain.DrainFluidRenderer;
import loenwind.enderioaddons.machine.drain.DrainItemRenderer;
import loenwind.enderioaddons.machine.drain.TileDrain;
import loenwind.enderioaddons.machine.flag.BlockFlag;
import loenwind.enderioaddons.machine.flag.ItemRendererFlag;
import loenwind.enderioaddons.machine.flag.TESRFlag;
import loenwind.enderioaddons.machine.flag.TileFlag;
import loenwind.enderioaddons.machine.framework.ItemRendererFramework;
import loenwind.enderioaddons.machine.framework.RendererFrameworkMachine;
import loenwind.enderioaddons.machine.framework.TESRFrameworkMachine;
import loenwind.enderioaddons.machine.ihopper.BlockIHopper;
import loenwind.enderioaddons.machine.ihopper.RendererIHopper;
import loenwind.enderioaddons.machine.ihopper.TileIHopper;
import loenwind.enderioaddons.machine.magcharger.BlockMagCharger;
import loenwind.enderioaddons.machine.magcharger.ItemRendererMagCharger;
import loenwind.enderioaddons.machine.magcharger.RendererMagCharger;
import loenwind.enderioaddons.machine.magcharger.TESRMagCharger;
import loenwind.enderioaddons.machine.magcharger.TileMagCharger;
import loenwind.enderioaddons.machine.niard.BlockNiard;
import loenwind.enderioaddons.machine.niard.BlockRendererNiard;
import loenwind.enderioaddons.machine.niard.FluidRendererNiard;
import loenwind.enderioaddons.machine.niard.ItemRendererNiard;
import loenwind.enderioaddons.machine.niard.TileNiard;
import loenwind.enderioaddons.machine.part.ItemMachinePart;
import loenwind.enderioaddons.machine.part.MachinePartRenderer;
import loenwind.enderioaddons.machine.pmon.BlockPMon;
import loenwind.enderioaddons.machine.pmon.ItemRendererPMon;
import loenwind.enderioaddons.machine.pmon.RendererPMon;
import loenwind.enderioaddons.machine.pmon.TESRPMon;
import loenwind.enderioaddons.machine.pmon.TilePMon;
import loenwind.enderioaddons.machine.tcom.BlockTcom;
import loenwind.enderioaddons.machine.tcom.RendererTcom;
import loenwind.enderioaddons.machine.tcom.TESRTcom;
import loenwind.enderioaddons.machine.tcom.TileTcom;
import loenwind.enderioaddons.machine.voidtank.BlockVoidTank;
import loenwind.enderioaddons.machine.voidtank.FluidRendererVoidTank;
import loenwind.enderioaddons.machine.voidtank.ItemRendererVoidTank;
import loenwind.enderioaddons.machine.voidtank.RendererVoidTank;
import loenwind.enderioaddons.machine.voidtank.TileVoidTank;
import loenwind.enderioaddons.machine.waterworks.BlockWaterworks;
import loenwind.enderioaddons.machine.waterworks.RendererWaterworks;
import loenwind.enderioaddons.machine.waterworks.TileWaterworks;

public class ClientOnlyProxy extends ClientAndServerProxy {

    @Override
    public void init(FMLPreInitializationEvent event) {
        super.init(event);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);

        BlockDrain.blockDrain.localRenderId = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new DrainBlockRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileDrain.class, new DrainFluidRenderer());
        MinecraftForgeClient
            .registerItemRenderer(Item.getItemFromBlock(BlockDrain.blockDrain), new DrainItemRenderer());

        BlockNiard.blockNiard.localRenderId = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new BlockRendererNiard());
        ClientRegistry.bindTileEntitySpecialRenderer(TileNiard.class, new FluidRendererNiard());
        MinecraftForgeClient
            .registerItemRenderer(Item.getItemFromBlock(BlockNiard.blockNiard), new ItemRendererNiard());

        BlockVoidTank.blockVoidTank.localRenderId = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new RendererVoidTank());
        ClientRegistry.bindTileEntitySpecialRenderer(TileVoidTank.class, new FluidRendererVoidTank());
        MinecraftForgeClient
            .registerItemRenderer(Item.getItemFromBlock(BlockVoidTank.blockVoidTank), new ItemRendererVoidTank());

        BlockPMon.blockPMon.localRenderId = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new RendererPMon());
        ClientRegistry.bindTileEntitySpecialRenderer(TilePMon.class, new TESRPMon());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockPMon.blockPMon), new ItemRendererPMon());

        RendererFrameworkMachine rendererFrameworkMachine = new RendererFrameworkMachine();
        ItemRendererFramework itemRendererFramework = new ItemRendererFramework(rendererFrameworkMachine);

        BlockCobbleworks.blockCobbleworks.localRenderId = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new RendererCobbleworks(rendererFrameworkMachine));
        ClientRegistry.bindTileEntitySpecialRenderer(TileCobbleworks.class, new TESRFrameworkMachine());
        MinecraftForgeClient
            .registerItemRenderer(Item.getItemFromBlock(BlockCobbleworks.blockCobbleworks), itemRendererFramework);

        BlockWaterworks.blockWaterworks.localRenderId = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new RendererWaterworks(rendererFrameworkMachine));
        ClientRegistry.bindTileEntitySpecialRenderer(TileWaterworks.class, new TESRFrameworkMachine());
        MinecraftForgeClient
            .registerItemRenderer(Item.getItemFromBlock(BlockWaterworks.blockWaterworks), itemRendererFramework);

        BlockIHopper.blockIHopper.localRenderId = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new RendererIHopper(rendererFrameworkMachine));
        ClientRegistry.bindTileEntitySpecialRenderer(TileIHopper.class, new TESRFrameworkMachine());
        MinecraftForgeClient
            .registerItemRenderer(Item.getItemFromBlock(BlockIHopper.blockIHopper), itemRendererFramework);

        BlockTcom.blockTcom.localRenderId = RenderingRegistry.getNextAvailableRenderId();
        final RendererTcom rendererTcom = new RendererTcom(rendererFrameworkMachine);
        RenderingRegistry.registerBlockHandler(rendererTcom);
        ClientRegistry.bindTileEntitySpecialRenderer(TileTcom.class, new TESRTcom(rendererTcom));
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockTcom.blockTcom), itemRendererFramework);

        MinecraftForgeClient
            .registerItemRenderer(ItemMachinePart.itemMachinePart, new MachinePartRenderer(rendererFrameworkMachine));

        ClientRegistry.bindTileEntitySpecialRenderer(TileFlag.class, new TESRFlag());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockFlag.blockFlag), new ItemRendererFlag());

        BlockMagCharger.blockMagCharger.localRenderId = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new RendererMagCharger());
        ClientRegistry.bindTileEntitySpecialRenderer(TileMagCharger.class, new TESRMagCharger());
        MinecraftForgeClient
            .registerItemRenderer(Item.getItemFromBlock(BlockMagCharger.blockMagCharger), new ItemRendererMagCharger());

        if (AgriDetector.hasAgri) {
            BlockAfarm.blockAfarm.localRenderId = RenderingRegistry.getNextAvailableRenderId();
            RenderingRegistry.registerBlockHandler(new RendererAfarm());
            ClientRegistry.bindTileEntitySpecialRenderer(TileAfarm.class, new TESRAfarm());
            MinecraftForgeClient
                .registerItemRenderer(Item.getItemFromBlock(BlockAfarm.blockAfarm), new ItemRendererAfarm());
        }

        BlockChassis.blockChassis.localRenderId = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new RendererChassis());
        MinecraftForgeClient
            .registerItemRenderer(Item.getItemFromBlock(BlockChassis.blockChassis), new ItemRendererChassis());
    }

    @Override
    public void init(FMLPostInitializationEvent event) {
        super.init(event);
    }

}
