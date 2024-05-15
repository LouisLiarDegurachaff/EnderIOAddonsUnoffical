package loenwind.enderioaddons.gui;

import com.enderio.core.api.client.gui.IGuiScreen;
import com.enderio.core.client.gui.button.CycleButton;
import com.enderio.core.common.util.BlockCoord;

import crazypants.enderio.network.PacketHandler;
import loenwind.enderioaddons.EnderIOAddons;

public class AdvancedRedstoneModeButton extends CycleButton<AdvancedRedstoneMode> {

    private IAdvancedRedstoneModeControlable model;

    private BlockCoord bc;

    private String tooltipKey = "gui.tooltip.redstoneControlMode";

    public AdvancedRedstoneModeButton(IGuiScreen gui, int id, int x, int y, IAdvancedRedstoneModeControlable model) {
        this(gui, id, x, y, model, null);
    }

    public AdvancedRedstoneModeButton(IGuiScreen gui, int id, int x, int y, IAdvancedRedstoneModeControlable model,
        BlockCoord bc) {
        super(gui, id, x, y, AdvancedRedstoneMode.class);
        this.model = model;
        this.bc = bc;
        setMode(model.getAdvancedRedstoneControlMode());
    }

    @Override
    public void setMode(AdvancedRedstoneMode newMode) {
        if (model == null) {
            return;
        }
        super.setMode(newMode);
        model.setAdvancedRedstoneControlMode(getMode());
        if (bc != null) {
            PacketHandler.INSTANCE.sendToServer(new PacketAdvancedRedstoneMode(model, bc.x, bc.y, bc.z));
        }
        setTooltipKey(tooltipKey); // forces our behavior
    }

    public void setTooltipKey(String key) {
        tooltipKey = key;
        setToolTip(EnderIOAddons.lang.localize(tooltipKey), getMode().getTooltip());
    }
}
