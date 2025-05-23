package loenwind.autosave.handlers.java;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;

import loenwind.autosave.IHandler;
import loenwind.autosave.Registry;
import loenwind.autosave.annotations.Store;

public class HandleEnum implements IHandler<Enum> {

    public HandleEnum() {}

    @Override
    public boolean canHandle(Class<?> clazz) {
        return Enum.class.isAssignableFrom(clazz);
    }

    @Override
    public boolean store(@Nonnull Registry registry, @Nonnull Set<Store.StoreFor> phase, @Nonnull NBTTagCompound nbt,
        @Nonnull String name, @Nonnull Enum object) throws IllegalArgumentException, IllegalAccessException {
        nbt.setInteger(name, object.ordinal());
        return true;
    }

    @Override
    public Enum read(@Nonnull Registry registry, @Nonnull Set<Store.StoreFor> phase, @Nonnull NBTTagCompound nbt,
        @Nonnull String name, @Nullable Enum object) {
        if (nbt.hasKey(name) && object != null) {
            return object.getClass()
                .getEnumConstants()[nbt.getInteger(name)];
        } else {
            return object;
        }
    }

}
