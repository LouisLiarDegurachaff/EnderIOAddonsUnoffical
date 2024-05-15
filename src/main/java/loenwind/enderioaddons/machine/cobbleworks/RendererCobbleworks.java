package loenwind.enderioaddons.machine.cobbleworks;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.obj.GroupObject;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import loenwind.enderioaddons.machine.framework.GroupObjectWithIcon;
import loenwind.enderioaddons.machine.framework.RendererFrameworkMachine;
import loenwind.enderioaddons.render.OverlayRenderer;

public class RendererCobbleworks implements ISimpleBlockRenderingHandler {

    @Nonnull
    private final RendererFrameworkMachine frameRenderer;

    public RendererCobbleworks(@Nonnull RendererFrameworkMachine frameRenderer) {
        this.frameRenderer = frameRenderer;
        GroupObject controllerActive = new GroupObjectWithIcon(
            frameRenderer.getControllerPart(1),
            BlockCobbleworks.getBlock());
        GroupObject controllerPassive = new GroupObjectWithIcon(
            frameRenderer.getControllerPart(2),
            BlockCobbleworks.getBlock());

        frameRenderer.registerController(
            BlockCobbleworks.getBlock()
                .getControllerModelName(),
            controllerActive,
            controllerPassive);
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        frameRenderer.renderInventoryBlock(block, metadata, modelId, renderer);
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        if (OverlayRenderer.renderOverlays(
            world,
            x,
            y,
            z,
            null,
            renderer.overrideBlockTexture,
            BlockCobbleworks.blockCobbleworks,
            (TileCobbleworks) null,
            true)) {
            return true;
        }
        return frameRenderer.renderWorldBlock(world, x, y, z, block, modelId, renderer);
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return BlockCobbleworks.blockCobbleworks.getRenderType();
    }

}
