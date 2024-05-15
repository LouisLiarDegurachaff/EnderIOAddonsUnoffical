package loenwind.enderioaddons.machine.niard;

import net.minecraft.entity.player.InventoryPlayer;

import crazypants.enderio.machine.gui.AbstractMachineContainer;
import loenwind.enderioaddons.gui.StdSlot;

public class ContainerNiard extends AbstractMachineContainer<TileNiard> {

    public ContainerNiard(InventoryPlayer playerInv, TileNiard te) {
        super(playerInv, te);
    }

    @Override
    protected void addMachineSlots(InventoryPlayer playerInv) {
        addSlotToContainer(new StdSlot(getInv(), 0, 44, 21));
        addSlotToContainer(new StdSlot(getInv(), 1, 44, 52));
    }

}
