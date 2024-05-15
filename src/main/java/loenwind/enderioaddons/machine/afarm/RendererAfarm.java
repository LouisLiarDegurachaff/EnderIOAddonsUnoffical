package loenwind.enderioaddons.machine.afarm;

import static loenwind.enderioaddons.machine.afarm.BlockAfarm.farmlight;
import static net.minecraftforge.common.util.ForgeDirection.DOWN;
import static net.minecraftforge.common.util.ForgeDirection.UP;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import com.enderio.core.api.client.render.VertexTransform;
import com.enderio.core.client.render.BoundingBox;
import com.enderio.core.client.render.RenderUtil;
import com.enderio.core.common.util.BlockCoord;
import com.enderio.core.common.vecmath.Vector3d;
import com.enderio.core.common.vecmath.Vector3f;
import com.enderio.core.common.vecmath.Vertex;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import loenwind.enderioaddons.common.Profiler;
import loenwind.enderioaddons.render.CachableRenderStatement;
import loenwind.enderioaddons.render.CacheRenderer;
import loenwind.enderioaddons.render.FaceRenderer;
import loenwind.enderioaddons.render.OverlayRenderer;
import loenwind.enderioaddons.render.RenderingContext;

public class RendererAfarm implements ISimpleBlockRenderingHandler {

    private static final VertXForm xform_main = new VertXForm(0.85, 0.5, true, true, true);
    private static final BoundingBox bb_body = BoundingBox.UNIT_CUBE;
    private static final float hatThickness = 0.15f;
    private static final BoundingBox bb_hat = BoundingBox.UNIT_CUBE.scale(1, hatThickness, 1)
        .translate(0, 0.3f + hatThickness / 2f, 0);
    private static final BoundingBox bb_hati = new BoundingBox(
        bb_hat.minX,
        bb_hat.minY,
        bb_hat.minZ,
        bb_hat.maxX,
        bb_hat.maxY - 0.05,
        bb_hat.maxZ);

    private static final float scale_cross = 0.7f;
    private static final float width_cross = 0.5f;
    private static final float trans_cross = (1 - scale_cross) / 2;
    private static final VertXForm xform_cross1 = new VertXForm(0.5, 0.5, true, false, false);
    private static final BoundingBox bb_cross1 = BoundingBox.UNIT_CUBE.scale(width_cross, scale_cross, 1)
        .translate(0, -trans_cross, 0);
    private static final float scale_light = bb_hat.minY - bb_cross1.maxY;
    private static final float trans_light = (1 - scale_light) / 2;
    private static final BoundingBox bb_light1 = BoundingBox.UNIT_CUBE.scale(width_cross, scale_light, 1)
        .translate(0, -trans_light + bb_cross1.maxY, 0);
    private static final VertXForm xform_cross2 = new VertXForm(0.5, 0.5, false, true, false);
    private static final BoundingBox bb_cross2 = BoundingBox.UNIT_CUBE.scale(1, scale_cross, width_cross)
        .translate(0, -trans_cross, 0);
    private static final BoundingBox bb_light2 = BoundingBox.UNIT_CUBE.scale(1, scale_light, width_cross)
        .translate(0, -trans_light + bb_cross1.maxY, 0);

    private static List<CachableRenderStatement> csr_online, csr_offline;
    private static boolean reloadHandlerRegistered = false;

    public RendererAfarm() {
        if (!reloadHandlerRegistered) {
            RenderUtil.registerReloadListener(new textReload());
            reloadHandlerRegistered = true;
        }
    }

    private static void setup() {
        if (csr_online == null || csr_offline == null) {
            long id = Profiler.client.start_always();
            IIcon[] icons = RenderUtil.getBlockTextures(BlockAfarm.blockAfarm, 0);

            CacheRenderer r = new CacheRenderer().setLighting(true)
                .setBrightnessPerSide(FaceRenderer.stdBrightness);

            r.setBB(bb_body)
                .setXform(xform_main)
                .setBrightness(1f, 0.7f)
                .addSkirt(icons, false);
            r.setBrightness(1f, 1f)
                .addSide(UP, icons, false)
                .addSide(DOWN, icons, false);
            r.setBB(bb_hat)
                .setXform(xform_main)
                .addSkirt(icons, false);
            r.setBB(bb_hati)
                .setXform(xform_main)
                .setBrightness(0f, 1f)
                .addSkirt(icons, true);
            r.setBB(bb_cross1)
                .setXform(xform_cross1)
                .setBrightness(1f, 0.8f)
                .addSkirt(icons, false);
            r.setBB(bb_cross2)
                .setXform(xform_cross2)
                .setBrightness(1f, 0.8f)
                .addSkirt(icons, false);

            CacheRenderer r2 = r.copy();

            r2.setBB(bb_cross1)
                .setXform(xform_cross1)
                .setBrightness(0.8f, 0.8f)
                .addSide(UP, icons, false);
            r2.setBB(bb_cross2)
                .setXform(xform_cross2)
                .setBrightness(0.8f, 0.8f)
                .addSide(UP, icons, false);

            r.setLighting((15 << 20) | (15 << 4), 240, 240)
                .setBrightnessPerSide(null)
                .setBrightness(1f, 1f);
            r.setBB(bb_light1)
                .setXform(xform_cross1)
                .addSkirt(farmlight, false);
            r.setBB(bb_light2)
                .setXform(xform_cross2)
                .addSkirt(farmlight, false);

            csr_online = r.finishDrawing();
            csr_offline = r2.finishDrawing();
            Profiler.client.stop(id, "farm setup");
        }
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {}

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        setup();

        boolean active = false;
        TileAfarm te = null;
        if (world != null) {
            TileEntity tileEntity = world.getTileEntity(x, y, z);
            if (tileEntity instanceof TileAfarm) {
                te = (TileAfarm) tileEntity;
                active = te.isActive();
            }
        }

        Tessellator.instance.addTranslation(x, y, z);
        if (renderer.overrideBlockTexture != null) {
            FaceRenderer.setLightingReference(world, BlockAfarm.blockAfarm, x, y, z);
            FaceRenderer.renderCube(bb_body, renderer.overrideBlockTexture, xform_main, null, false);
            FaceRenderer.renderCube(bb_cross1, renderer.overrideBlockTexture, xform_cross1, null, false);
            FaceRenderer.renderCube(bb_cross2, renderer.overrideBlockTexture, xform_cross2, null, false);
            FaceRenderer.clearLightingReference();
        } else {
            OverlayRenderer.renderOverlays(world, x, y, z, null, null, BlockAfarm.blockAfarm, te);
            new RenderingContext(world, new BlockCoord(x, y, z))
                .execute(active ? csr_online : csr_offline, "farm world");
        }
        Tessellator.instance.addTranslation(-x, -y, -z);

        return true;
    }

    public static void renderBlock(boolean active) {
        setup();
        new RenderingContext().execute(active ? csr_online : csr_offline, "farm item");
        return;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return BlockAfarm.blockAfarm.getRenderType();
    }

    private static class VertXForm implements VertexTransform {

        private final double cutoff, pinch;
        private final boolean pinchX, pinchZ, scaleY;

        public VertXForm(double cutoff, double pinch, boolean pinchX, boolean pinchZ, boolean scaleY) {
            this.cutoff = cutoff;
            this.pinch = pinch;
            this.pinchX = pinchX;
            this.pinchZ = pinchZ;
            this.scaleY = scaleY;
        }

        @Override
        public void apply(Vertex vertex) {
            apply(vertex.xyz);
        }

        @Override
        public void apply(Vector3d vec) {
            if (vec.y > cutoff) {
                double pinchi = scaleY ? pinch : 1.0 - (pinch * vec.y);
                if (pinchX) {
                    vec.x -= 0.5;
                    vec.x *= pinchi;
                    vec.x += 0.5;
                }
                if (pinchZ) {
                    vec.z -= 0.5;
                    vec.z *= pinchi;
                    vec.z += 0.5;
                }
            }
            if (scaleY || vec.y > cutoff) {
                double scale = 0.8;
                vec.y -= 0.5;
                vec.y *= scale;
                vec.y += (0.5 * scale);
            }
        }

        @Override
        public void applyToNormal(Vector3f vec) {}

    }

    private class textReload implements IResourceManagerReloadListener {

        @Override
        public void onResourceManagerReload(IResourceManager p_110549_1_) {
            csr_online = csr_offline = null;
        }

    }

}
