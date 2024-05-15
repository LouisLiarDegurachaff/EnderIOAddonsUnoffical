package loenwind.autosave.handlers.enderioaddons;

import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;

import loenwind.autosave.IHandler;
import loenwind.autosave.Registry;
import loenwind.autosave.annotations.Store.StoreFor;
import loenwind.autosave.exceptions.NoHandlerFoundException;
import loenwind.enderioaddons.machine.waterworks.engine.Stash;

public class HandleStash implements IHandler<Stash> {

    public HandleStash() {}

    @Override
    public boolean canHandle(Class<?> clazz) {
        return Stash.class.isAssignableFrom(clazz);
    }

    @Override
    public boolean store(@Nonnull Registry registry, @Nonnull Set<StoreFor> phase, @Nonnull NBTTagCompound nbt,
        @Nonnull String name, @Nonnull Stash object)
        throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoHandlerFoundException {
        NBTTagCompound tag = new NBTTagCompound();
        for (Entry<String, Double> entry : object.getContents()
            .entrySet()) {
            tag.setDouble(entry.getKey(), entry.getValue());
        }
        nbt.setTag(name, tag);
        return true;
    }

    @Override
    public Stash read(@Nonnull Registry registry, @Nonnull Set<StoreFor> phase, @Nonnull NBTTagCompound nbt,
        @Nonnull String name, @Nullable Stash object)
        throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoHandlerFoundException {
        Stash stash = object;
        if (stash == null) {
            stash = new Stash();
        }
        if (nbt.hasKey(name)) {
            NBTTagCompound tag = nbt.getCompoundTag(name);
            stash.getContents()
                .clear();
            Set<String> keySet = tag.func_150296_c();
            for (String key : keySet) {
                stash.getContents()
                    .put(key, tag.getDouble(key));
            }
        }
        return stash;
    }

}
