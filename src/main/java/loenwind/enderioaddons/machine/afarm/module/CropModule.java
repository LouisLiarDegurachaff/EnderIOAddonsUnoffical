package loenwind.enderioaddons.machine.afarm.module;

import net.minecraft.item.ItemStack;

import loenwind.enderioaddons.machine.afarm.Notif;
import loenwind.enderioaddons.machine.afarm.SlotDefinitionAfarm;
import loenwind.enderioaddons.machine.afarm.SlotDefinitionAfarm.SLOT;
import loenwind.enderioaddons.machine.afarm.WorkTile;

public class CropModule implements IAfarmControlModule {

    @Override
    public void doWork(WorkTile workTile) {
        if ((workTile.allowPlanting || workTile.allowCrossCrops) && !workTile.isCrops) {
            final SlotDefinitionAfarm slotDef = (SlotDefinitionAfarm) workTile.farm.getSlotDefinition();
            for (int slot = slotDef.getMinSlot(SLOT.CROPSTICK); slot <= slotDef.getMaxSlot(SLOT.CROPSTICK); slot++) {
                final ItemStack stack = workTile.farm.getStackInSlot(slot);
                if (stack != null && stack.getItem() != null
                    && workTile.agricraft.canPlaceCrops(
                        workTile.farm.getWorldObj(),
                        workTile.bc.x,
                        workTile.bc.y,
                        workTile.bc.z,
                        stack)) {
                    workTile.doCrops = true;
                    workTile.cropsSlot = slot;
                    workTile.farm.notifications.remove(Notif.NO_CROPS);
                    return;
                }
            }
            if (workTile.farm.tillAggresively) {
                for (int slot = slotDef.getMinSlot(SLOT.CROPSTICK); slot
                    <= slotDef.getMaxSlot(SLOT.CROPSTICK); slot++) {
                    final ItemStack stack = workTile.farm.getStackInSlot(slot);
                    if (stack != null && stack.getItem() != null
                        && !workTile.agricraft.canPlaceCrops(
                            workTile.farm.getWorldObj(),
                            workTile.bc.x,
                            workTile.bc.y,
                            workTile.bc.z,
                            stack)) {
                        workTile.doTill = true; // This is guess work, but based on user input
                        workTile.farm.notifications.remove(Notif.NO_CROPS);
                        return;
                    }
                }
            }
            for (int slot = slotDef.getMinSlot(SLOT.CROPSTICK); slot <= slotDef.getMaxSlot(SLOT.CROPSTICK); slot++) {
                if (workTile.farm.getStackInSlot(slot) != null) {
                    return;
                }
            }
            workTile.farm.notifications.add(Notif.NO_CROPS);
        }
    }

    @Override
    public int getPriority() {
        return 51;
    }

    @Override
    public boolean isCompatibleWith(IAfarmControlModule other) {
        return !(other instanceof CropModule);
    }

}
