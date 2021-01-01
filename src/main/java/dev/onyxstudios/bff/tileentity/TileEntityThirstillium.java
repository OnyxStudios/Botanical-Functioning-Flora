package dev.onyxstudios.bff.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import dev.onyxstudios.bff.registry.ModEntitites;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import vazkii.botania.api.BotaniaAPIClient;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.TileEntityFunctionalFlower;

/**
 * Thirstillium Flower
 * Will store 10 buckets of any fluid, right click with a bucket to fill/drain
 * Mana usage/tick 100 mana per tick
 * If fluid tank is not empty and flower has no mana, the flower will slowly void the tank contents
 */
public class TileEntityThirstillium extends TileEntityFunctionalFlower {

    public static final int MANA_USAGE = 100;
    public static final int FLUID_DRAIN_AMOUNT = 10;
    public FluidTank tank = new FluidTank(10000);
    public final LazyOptional<?> capabilityTank = LazyOptional.of(() -> tank);

    public int drainTime = 0;
    public int drainPeriod = 20;

    public TileEntityThirstillium() {
        super(ModEntitites.thirstilliumType.get());
    }

    @Override
    public void readFromPacketNBT(CompoundNBT cmp) {
        super.readFromPacketNBT(cmp);
        this.tank.readFromNBT(cmp.getCompound("fluidData"));
    }

    @Override
    public void writeToPacketNBT(CompoundNBT cmp) {
        super.writeToPacketNBT(cmp);
        cmp.put("fluidData", tank.writeToNBT(new CompoundNBT()));
    }

    @Override
    public void tickFlower() {
        super.tickFlower();

        if(!world.isRemote()) {
            if(tank.getFluidAmount() > 0) {
                if(getMana() < MANA_USAGE) {
                    if(tank.getFluidAmount() < FLUID_DRAIN_AMOUNT)
                        return;

                    drainTime++;
                    if (drainTime >= drainPeriod) {
                        tank.drain(FLUID_DRAIN_AMOUNT, IFluidHandler.FluidAction.EXECUTE);
                        world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
                        drainTime = 0;
                    }
                }else {
                    addMana(-MANA_USAGE);
                }
            }
        }
    }

    @Override
    public RadiusDescriptor getRadius() {
        return super.getRadius();
    }

    @Override
    public int getColor() {
        return 5451744;
    }

    @Override
    public int getMaxMana() {
        return tank.getCapacity();
    }

    @Override
    public void renderHUD(MatrixStack matrixStack, Minecraft minecraft) {
        super.renderHUD(matrixStack, minecraft);

        if(tank.getFluidAmount() > 0) {
            matrixStack.push();
            matrixStack.translate(0, 25, 0);
            String name = tank.getFluid().getDisplayName().getString() + " (" + tank.getFluidAmount() / 1000.0f + " Buckets)";
            BotaniaAPIClient.instance().drawSimpleManaHUD(matrixStack, 16776960, tank.getFluidAmount(), tank.getCapacity(), name);
            matrixStack.pop();
        }
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if(cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return (LazyOptional<T>) capabilityTank;
        }

        return super.getCapability(cap, side);
    }
}
