package loenwind.autosave.handlers.minecraft;

import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import loenwind.autosave.IHandler;
import loenwind.autosave.Registry;
import loenwind.autosave.annotations.Store.StoreFor;
import loenwind.autosave.exceptions.NoHandlerFoundException;

public class HandleItem implements IHandler<Item> {

    public HandleItem() {}

    @Override
    public boolean canHandle(Class<?> clazz) {
        return ItemStack.class.isAssignableFrom(clazz);
    }

    // Note: We can use the item ID here because ItemStack also uses it in its NBT-methods. So if the ID breaks,
    // ItemStacks break even harder than we do here.

    @Override
    public boolean store(@Nonnull Registry registry, @Nonnull Set<StoreFor> phase, @Nonnull NBTTagCompound nbt,
        @Nonnull String name, @Nonnull Item object)
        throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoHandlerFoundException {
        nbt.setShort(name, (short) Item.getIdFromItem(object));
        return true;
    }

    @Override
    public Item read(@Nonnull Registry registry, @Nonnull Set<StoreFor> phase, @Nonnull NBTTagCompound nbt,
        @Nonnull String name, @Nullable Item object)
        throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoHandlerFoundException {
        if (nbt.hasKey(name)) {
            return Item.getItemById(nbt.getShort(name));
        }
        return object;
    }

}
