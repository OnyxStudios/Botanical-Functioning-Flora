package dev.onyxstudios.bff.tileentity;

import dev.onyxstudios.bff.registry.ModEntitites;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.AxisAlignedBB;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.TileEntityFunctionalFlower;

import java.util.List;

/**
 * Lingfei Thiefily Flower
 * Will disarm any entity within it's radius and drop it's equipment
 * Attempts to disarm one item at a time from one entity, every 5 seconds
 * Cooldown will activate even if no entity is disarmed
 * Uses 10,000 Mana per item disarmed
 * Radius of 4
 */
public class TileEntityLingfeiThiefily extends TileEntityFunctionalFlower {

    public static final int ENTITY_RADIUS = 4;
    public static final int MAX_MANA = 100000;
    public static final int MANA_USAGE = 10000;

    //5 Second Cooldown
    public static final int MAX_COOLDOWN = 20 * 5;

    public int cooldown = 0;
    public boolean isCooldown = false;

    public TileEntityLingfeiThiefily() {
        super(ModEntitites.lingfeiThiefilyType.get());
    }

    @Override
    public void readFromPacketNBT(CompoundNBT cmp) {
        super.readFromPacketNBT(cmp);
        this.cooldown = cmp.getInt("cooldown");
        this.isCooldown = cmp.getBoolean("isCooldown");
    }

    @Override
    public void writeToPacketNBT(CompoundNBT cmp) {
        super.writeToPacketNBT(cmp);
        cmp.putInt("cooldown", this.cooldown);
        cmp.putBoolean("isCooldown", this.isCooldown);
    }

    @Override
    public void tickFlower() {
        super.tickFlower();

        if(world.isRemote)
            return;

        if(isCooldown) {
            cooldown--;

            if(cooldown <= 0) {
                isCooldown = false;
                cooldown = 0;
            }

            return;
        }

        //First checks that mana is not 0, and that we can disarm at least one item.
        if(getMana() > MANA_USAGE) {
            List<LivingEntity> entities = world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(pos.add(-ENTITY_RADIUS, 0, -ENTITY_RADIUS), pos.add(ENTITY_RADIUS, ENTITY_RADIUS, ENTITY_RADIUS)));

            if(!entities.isEmpty()) {
                boolean hasWorked = false;

                for (LivingEntity entity : entities) {
                    if(entity instanceof PlayerEntity) continue;
                    else if(hasWorked) break;

                    for (EquipmentSlotType slot : EquipmentSlotType.values()) {
                        if(getMana() < MANA_USAGE) {
                            hasWorked = true;
                            break;
                        }

                        ItemStack stack = entity.getItemStackFromSlot(slot);
                        if(stack.isEmpty()) continue;

                        InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
                        entity.setItemStackToSlot(slot, ItemStack.EMPTY);
                        addMana(-MANA_USAGE);
                        world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
                        hasWorked = true;
                        break;
                    }
                }

                cooldown = MAX_COOLDOWN;
                isCooldown = true;
            }else {
                //Starts cooldown even if no entities are disarmed
                cooldown = MAX_COOLDOWN;
                isCooldown = true;
            }
        }
    }

    @Override
    public RadiusDescriptor getRadius() {
        return new RadiusDescriptor.Square(getEffectivePos(), ENTITY_RADIUS);
    }

    @Override
    public int getColor() {
        return 9126799;
    }

    @Override
    public int getMaxMana() {
        return MAX_MANA;
    }
}
