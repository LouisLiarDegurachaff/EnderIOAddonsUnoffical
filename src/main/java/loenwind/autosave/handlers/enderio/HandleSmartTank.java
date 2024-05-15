package loenwind.autosave.handlers.enderio;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;

import crazypants.enderio.tool.SmartTank;
import loenwind.autosave.IHandler;
import loenwind.autosave.Registry;
import loenwind.autosave.annotations.Store.StoreFor;
import loenwind.autosave.exceptions.NoHandlerFoundException;

public class HandleSmartTank implements IHandler<SmartTank> {

    public HandleSmartTank() {}

    @Override
    public boolean canHandle(Class<?> clazz) {
        return SmartTank.class.isAssignableFrom(clazz);
    }

    @Override
    public boolean store(@Nonnull Registry registry, @Nonnull Set<StoreFor> phase, @Nonnull NBTTagCompound nbt,
        @Nonnull String name, @Nonnull SmartTank object)
        throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoHandlerFoundException {
        object.writeCommon(name, nbt);
        return true;
    }

    @Override
    public SmartTank read(@Nonnull Registry registry, @Nonnull Set<StoreFor> phase, @Nonnull NBTTagCompound nbt,
        @Nonnull String name, @Nullable SmartTank object)
        throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoHandlerFoundException {
        if (nbt.hasKey(name) && object != null) {
            object.readCommon(name, nbt);
        }
        return object;
    }

}
