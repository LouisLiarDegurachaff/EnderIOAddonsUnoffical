package loenwind.enderioaddons.machine.drain.filter;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import loenwind.enderioaddons.fluid.Fluids;

public class FoodFluidFilter implements FluidFilter {

    private FluidStack[] foodFluids;

    public FoodFluidFilter() {}

    public FluidStack[] getFluids() {
        if (foodFluids == null) {
            // delay this to allow fluids to be initialized
            foodFluids = new FluidStack[] { new FluidStack(Fluids.MILK.getFluid(), 0) };
        }
        return foodFluids;
    }

    @Override
    public boolean isFluid(FluidStack fluid) {
        if (fluid == null || fluid.getFluid() == null) {
            return false;
        }
        for (FluidStack fluidStack : getFluids()) {
            if (fluidStack.isFluidEqual(fluid)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isFluid(Fluid fluid) {
        if (fluid == null) {
            return false;
        }
        for (FluidStack fluidStack : getFluids()) {
            if (fluidStack.getFluid() == fluid) {
                return true;
            }
        }
        return false;
    }
}
