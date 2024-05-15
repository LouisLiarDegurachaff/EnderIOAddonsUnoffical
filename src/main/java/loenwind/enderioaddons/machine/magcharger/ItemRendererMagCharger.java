package loenwind.enderioaddons.machine.magcharger;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import com.enderio.core.client.render.RenderUtil;

import loenwind.enderioaddons.render.ItemRendererBase;

public class ItemRendererMagCharger extends ItemRendererBase {

    @Override
    protected void render(ItemStack item, RenderBlocks renderBlocks) {
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        Tessellator.instance.startDrawingQuads();
        Tessellator.instance.setColorOpaque_F(1, 1, 1);
        RenderUtil.bindBlockTexture();
        RendererMagCharger.renderBlock(BlockMagCharger.blockMagCharger, item.getItemDamage());
        Tessellator.instance.draw();
        GL11.glDisable(GL11.GL_ALPHA_TEST);
    }

}
