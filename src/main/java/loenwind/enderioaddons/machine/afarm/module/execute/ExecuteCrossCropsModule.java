package loenwind.enderioaddons.machine.afarm.module.execute;

import static loenwind.enderioaddons.config.Config.farmRFperCrossCrops;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import loenwind.enderioaddons.machine.afarm.Notif;
import loenwind.enderioaddons.machine.afarm.WorkTile;

public class ExecuteCrossCropsModule extends ExecuteModule {

    @Override
    public void doWork(WorkTile workTile) {
        if (workTile.doCrossCrops) {
            if (workTile.farm.canUsePower(farmRFperCrossCrops.getInt())) {
                workTile.farm.usePower(farmRFperCrossCrops.getInt());
                final World world = workTile.farm.getWorldObj();
                final ItemStack stack = workTile.farm.getStackInSlot(workTile.cropsSlot);
                workTile.agricraft.placeCrossCrops(world, workTile.bc.x, workTile.bc.y, workTile.bc.z, stack);
                if (stack.stackSize <= 0) {
                    workTile.farm.setInventorySlotContents(workTile.cropsSlot, null);
                }
                spawnParticles(workTile);
                workTile.farm.markDirty();
            } else {
                workTile.farm.notifications.add(Notif.NO_POWER);
            }
        }
    }

    @Override
    public int getPriority() {
        return super.getPriority() + 2;
    }

}
