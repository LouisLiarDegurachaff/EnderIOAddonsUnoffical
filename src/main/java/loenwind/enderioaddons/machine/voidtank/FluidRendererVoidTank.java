package loenwind.enderioaddons.machine.voidtank;

import static net.minecraftforge.common.util.ForgeDirection.SOUTH;

import javax.annotation.Nullable;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

import com.enderio.core.client.render.BoundingBox;
import com.enderio.core.client.render.RenderUtil;
import com.enderio.core.client.render.VertexRotationFacing;
import com.enderio.core.common.vecmath.Vector3d;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import loenwind.enderioaddons.render.FaceRenderer;

@SideOnly(Side.CLIENT)
public class FluidRendererVoidTank extends TileEntitySpecialRenderer {

    private static final VertexRotationFacing xform = new VertexRotationFacing(SOUTH);
    static {
        xform.setCenter(new Vector3d(0.5, 0.5, 0.5));
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTick) {
        renderTankFluid((TileVoidTank) te, (float) x, (float) y, (float) z);
    }

    public static void renderTankFluid(@Nullable TileVoidTank voidTank, float x, float y, float z) {
        if (voidTank == null || voidTank.tank.getFluid() == null || voidTank.tank.getFluidAmount() <= 0) {
            return;
        }
        IIcon icon_fluid = voidTank.tank.getFluid()
            .getFluid()
            .getStillIcon();
        if (icon_fluid == null) {
            return;
        }

        xform.setRotation(voidTank.getFacingDir());
        float xscale = 15f / 16f * 0.98f;
        float yScale = 0.98f * voidTank.tank.getFluidAmount() / 16000;
        float zscale = 0.98f;
        BoundingBox bb = BoundingBox.UNIT_CUBE.scale(xscale, yScale, zscale);
        bb = bb.translate(-.5f / 16f, -(1 - yScale) / 2 + 0.01f, 0);

        GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_ALPHA_TEST);

        RenderUtil.bindBlockTexture();

        Tessellator.instance.startDrawingQuads();
        Tessellator.instance.addTranslation(x, y, z);
        FaceRenderer.renderSkirt(bb, icon_fluid, 0, 16, 0, yScale * 16, xform, null, false);
        FaceRenderer.renderSingleFace(bb, ForgeDirection.UP, icon_fluid, xform, null, false);
        FaceRenderer.renderSingleFace(bb, ForgeDirection.DOWN, icon_fluid, xform, null, false);
        Tessellator.instance.addTranslation(-x, -y, -z);
        Tessellator.instance.draw();

        GL11.glPopAttrib();
    }

}
