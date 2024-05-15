package loenwind.autosave.handlers.enderioaddons;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;

import loenwind.autosave.IHandler;
import loenwind.autosave.Registry;
import loenwind.autosave.annotations.Store;
import loenwind.enderioaddons.machine.afarm.NotifSet;

public class HandleNotifSet implements IHandler<NotifSet> {

    public HandleNotifSet() {}

    @Override
    public boolean canHandle(Class<?> clazz) {
        return NotifSet.class.isAssignableFrom(clazz);
    }

    @Override
    public boolean store(@Nonnull Registry registry, @Nonnull Set<Store.StoreFor> phase, @Nonnull NBTTagCompound nbt,
        @Nonnull String name, @Nonnull NotifSet object) throws IllegalArgumentException, IllegalAccessException {
        nbt.setLong(name, object.getElements());
        return true;
    }

    @Override
    public NotifSet read(@Nonnull Registry registry, @Nonnull Set<Store.StoreFor> phase, @Nonnull NBTTagCompound nbt,
        @Nonnull String name, @Nullable NotifSet object) {
        if (nbt.hasKey(name) && object != null) {
            object.setElements(nbt.getLong(name));
        }
        return object;
    }

}
