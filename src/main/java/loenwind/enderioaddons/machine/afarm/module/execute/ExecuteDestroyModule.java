package loenwind.enderioaddons.machine.afarm.module.execute;

import static loenwind.enderioaddons.config.Config.farmRFperSeedHarvest;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import loenwind.enderioaddons.machine.afarm.Notif;
import loenwind.enderioaddons.machine.afarm.WorkTile;

public class ExecuteDestroyModule extends ExecuteModule {

    @Override
    public void doWork(WorkTile workTile) {
        if (workTile.doDestroy) {
            if (workTile.farm.canUsePower(farmRFperSeedHarvest.getInt())) {
                if (damageHoe(workTile)) {
                    workTile.farm.usePower(farmRFperSeedHarvest.getInt());
                    List<ItemStack> result = null;
                    final World world = workTile.farm.getWorldObj();
                    result = workTile.agricraft.destroy(world, workTile.bc.x, workTile.bc.y, workTile.bc.z);
                    spawnParticles(workTile);
                    depositItems(workTile, result);
                    workTile.doneSomething = true;
                }
            } else {
                workTile.farm.notifications.add(Notif.NO_POWER);
            }
        }
    }

}
