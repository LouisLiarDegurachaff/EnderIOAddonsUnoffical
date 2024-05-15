package loenwind.enderioaddons.machine.niard;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import org.lwjgl.opengl.GL11;

import com.enderio.core.client.render.BoundingBox;
import com.enderio.core.client.render.RenderUtil;

import loenwind.enderioaddons.render.FaceRenderer;
import loenwind.enderioaddons.render.ItemRendererBase;

public class ItemRendererNiard extends ItemRendererBase {

    @Override
    protected void render(ItemStack item, RenderBlocks renderBlocks) {
        if (item.stackTagCompound != null) {
            TileNiard tt = new TileNiard();
            tt.readCommon(item.stackTagCompound);
            FluidRendererNiard.renderTankFluid(tt, 0f, 0f, 0f);
        }

        GL11.glEnable(GL11.GL_ALPHA_TEST);
        Block block = BlockNiard.blockNiard;
        int meta = item.getItemDamage();

        IIcon[] icons = RenderUtil.getBlockTextures(block, meta);
        BoundingBox bb = BoundingBox.UNIT_CUBE;
        Tessellator.instance.startDrawingQuads();
        FaceRenderer.renderCube(bb, icons, null, FaceRenderer.stdBrightness, false);
        Tessellator.instance.draw();
    }

}
