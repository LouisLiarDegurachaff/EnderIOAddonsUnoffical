package loenwind.enderioaddons.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import crazypants.enderio.network.PacketHandler;
import loenwind.enderioaddons.network.PacketSlotVisibility;

public class StdSlot extends Slot {

    private static final int MAGIC_NUMBER = -3000;
    private final int realx, realy;

    public StdSlot(IInventory inventory, int slotIndex, int x, int y) {
        super(inventory, slotIndex, x, y);
        realx = x;
        realy = y;
    }

    @Override
    public boolean isItemValid(ItemStack itemStack) {
        return xDisplayPosition != MAGIC_NUMBER && inventory.isItemValidForSlot(getSlotIndex(), itemStack);
    }

    public void enable(boolean enable) {
        if ((xDisplayPosition != MAGIC_NUMBER) != enable && inventory instanceof TileEntity
            && ((TileEntity) inventory).getWorldObj().isRemote) {
            PacketHandler.INSTANCE.sendToServer(new PacketSlotVisibility(getSlotIndex(), !enable));
        }
        if (enable) {
            xDisplayPosition = realx;
            yDisplayPosition = realy;
        } else {
            xDisplayPosition = MAGIC_NUMBER;
            yDisplayPosition = MAGIC_NUMBER;
        }
    }

}
