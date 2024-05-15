package loenwind.enderioaddons.machine.afarm.module.execute;

import static loenwind.enderioaddons.config.Config.farmRFperFertilize;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import loenwind.enderioaddons.machine.afarm.Notif;
import loenwind.enderioaddons.machine.afarm.WorkTile;

public class ExecuteFertilizerModule extends ExecuteModule {

    @Override
    public void doWork(WorkTile workTile) {
        if (workTile.doFertilize) {
            if (workTile.farm.canUsePower(farmRFperFertilize.getInt())) {
                final World world = workTile.farm.getWorldObj();
                final ItemStack stack = workTile.farm.getStackInSlot(workTile.fertilizerSlot);
                boolean ret = workTile.agricraft
                    .applyFertilizer(world, workTile.bc.x, workTile.bc.y, workTile.bc.z, stack);
                if (ret) {
                    workTile.farm.usePower(farmRFperFertilize.getInt());
                    spawnParticles(workTile);
                }
                if (stack.stackSize <= 0) {
                    workTile.farm.setInventorySlotContents(workTile.fertilizerSlot, null);
                }
                workTile.farm.markDirty();
            } else {
                workTile.farm.notifications.add(Notif.NO_POWER);
            }
        }
    }

}
