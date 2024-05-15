package loenwind.enderioaddons.machine.afarm;

import net.minecraft.item.ItemStack;

import loenwind.enderioaddons.machine.afarm.module.IAfarmControlModule;

public interface IAfarmControlModuleItem {

    IAfarmControlModule getWorker(ItemStack stack);

}
