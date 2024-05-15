package loenwind.enderioaddons.machine.afarm.module.execute;

import static loenwind.enderioaddons.config.Config.farmRFperCrops;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import loenwind.enderioaddons.machine.afarm.Notif;
import loenwind.enderioaddons.machine.afarm.WorkTile;

public class ExecuteCropsModule extends ExecuteModule {

    @Override
    public void doWork(WorkTile workTile) {
        if (workTile.doCrops) {
            if (workTile.farm.canUsePower(farmRFperCrops.getInt())) {
                workTile.farm.usePower(farmRFperCrops.getInt());
                final World world = workTile.farm.getWorldObj();
                final ItemStack stack = workTile.farm.getStackInSlot(workTile.cropsSlot);
                workTile.agricraft.placeCrops(world, workTile.bc.x, workTile.bc.y, workTile.bc.z, stack);
                if (stack.stackSize <= 0) {
                    workTile.farm.setInventorySlotContents(workTile.cropsSlot, null);
                }
                spawnParticles(workTile);
                workTile.farm.markDirty();
                workTile.doneSomething = true;
            } else {
                workTile.farm.notifications.add(Notif.NO_POWER);
            }
        }
    }

    @Override
    public int getPriority() {
        return super.getPriority() + 1;
    }

}
