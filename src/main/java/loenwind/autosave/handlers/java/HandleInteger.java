package loenwind.autosave.handlers.java;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;

import loenwind.autosave.IHandler;
import loenwind.autosave.Registry;
import loenwind.autosave.annotations.Store;

public class HandleInteger implements IHandler<Integer> {

    public HandleInteger() {}

    @Override
    public boolean canHandle(Class<?> clazz) {
        return Integer.class.isAssignableFrom(clazz) || int.class.isAssignableFrom(clazz);
    }

    @Override
    public boolean store(@Nonnull Registry registry, @Nonnull Set<Store.StoreFor> phase, @Nonnull NBTTagCompound nbt,
        @Nonnull String name, @Nonnull Integer object) throws IllegalArgumentException, IllegalAccessException {
        nbt.setInteger(name, object);
        return true;
    }

    @Override
    public Integer read(@Nonnull Registry registry, @Nonnull Set<Store.StoreFor> phase, @Nonnull NBTTagCompound nbt,
        @Nonnull String name, @Nullable Integer object) {
        return nbt.hasKey(name) ? nbt.getInteger(name) : object != null ? object : 0;
    }

}
