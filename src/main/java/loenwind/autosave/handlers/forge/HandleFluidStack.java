package loenwind.autosave.handlers.forge;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

import loenwind.autosave.IHandler;
import loenwind.autosave.Registry;
import loenwind.autosave.annotations.Store.StoreFor;
import loenwind.autosave.exceptions.NoHandlerFoundException;

public class HandleFluidStack implements IHandler<FluidStack> {

    public HandleFluidStack() {}

    @Override
    public boolean canHandle(Class<?> clazz) {
        return FluidStack.class.isAssignableFrom(clazz);
    }

    @Override
    public boolean store(@Nonnull Registry registry, @Nonnull Set<StoreFor> phase, @Nonnull NBTTagCompound nbt,
        @Nonnull String name, @Nonnull FluidStack object)
        throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoHandlerFoundException {
        NBTTagCompound tag = new NBTTagCompound();
        object.writeToNBT(tag);
        nbt.setTag(name, tag);
        return false;
    }

    @Override
    public FluidStack read(@Nonnull Registry registry, @Nonnull Set<StoreFor> phase, @Nonnull NBTTagCompound nbt,
        @Nonnull String name, @Nullable FluidStack object)
        throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoHandlerFoundException {
        if (nbt.hasKey(name)) {
            return FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag(name));
        }
        return null;
    }

}
