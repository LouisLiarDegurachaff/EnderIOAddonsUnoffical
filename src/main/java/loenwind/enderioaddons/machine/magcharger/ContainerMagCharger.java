package loenwind.enderioaddons.machine.magcharger;

import net.minecraft.entity.player.InventoryPlayer;

import crazypants.enderio.machine.gui.AbstractMachineContainer;
import loenwind.enderioaddons.gui.StdOutputSlot;
import loenwind.enderioaddons.gui.StdSlot;

public class ContainerMagCharger extends AbstractMachineContainer<TileMagCharger> {

    public ContainerMagCharger(InventoryPlayer playerInv, TileMagCharger te) {
        super(playerInv, te);
    }

    @Override
    protected void addMachineSlots(InventoryPlayer playerInv) {
        addSlotToContainer(new StdSlot(getInv(), 0, 59, 34));
        addSlotToContainer(new StdOutputSlot(getInv(), 1, 112, 34));
    }

}
