package dev.onyxstudios.bff.tileentity;

import dev.onyxstudios.bff.registry.ModEntitites;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.TileEntityFunctionalFlower;

import java.util.List;
import java.util.Random;

/**
 * Greedaffodil Flower
 * Flower will generate Ancient Debris
 * Supply with any gold (Except nuggets) to feed the flower
 * Once gold amount reaches a threshhold (Currently Set to 20), flower will consume mana and generate Ancient Debris
 * (20% Chance to generate ancient debris)
 * Gold will be consumed whether debris is generated or not
 */
public class TileEntityGreedaffodil extends TileEntityFunctionalFlower {

    public static final int CONSUME_RADIUS = 3;
    public static final int CONSUME_AMOUNT = 20000;
    public static final int GOLD_NEEDED = 20;
    public static final float NETHERITE_CHANCE = 0.2f;
    //5 Minute Cooldown
    public static final int MAX_COOLDOWN = 6000;
    public int goldConsumed = 0;
    public boolean isCooldown = false;
    public int cooldownTimer = 0;
    public Random random = new Random();

    public TileEntityGreedaffodil() {
        super(ModEntitites.greedaffodilType.get());
    }

    @Override
    public void writeToPacketNBT(CompoundNBT cmp) {
        super.writeToPacketNBT(cmp);
        cmp.putInt("goldConsumed", this.goldConsumed);
        cmp.putBoolean("isCooldown", this.isCooldown);
        cmp.putInt("cooldownTimer", this.cooldownTimer);
    }

    @Override
    public void readFromPacketNBT(CompoundNBT cmp) {
        super.readFromPacketNBT(cmp);
        this.goldConsumed = cmp.getInt("goldConsumed");
        this.isCooldown = cmp.getBoolean("isCooldown");
        this.cooldownTimer = cmp.getInt("cooldownTimer");
    }

    @Override
    public void tickFlower() {
        super.tickFlower();

        if(isCooldown) {
            cooldownTimer--;

            if(world.isRemote && cooldownTimer % 10 == 0) {
                Vector3d offset = getBlockState().getOffset(world, pos);
                world.addParticle(ParticleTypes.SMOKE, pos.getX() + offset.x + 0.5f, pos.getY() + 0.8f, pos.getZ() + offset.z + 0.5f, 0, 0.02f, 0);
            }

            if(cooldownTimer <= 0) {
                cooldownTimer = 0;
                isCooldown = false;
            }else {
                return;
            }
        }

        if (getMana() >= CONSUME_AMOUNT) {
            if(world.getDimensionType().getHasCeiling()) {
                List<ItemEntity> items = world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(pos.add(-CONSUME_RADIUS, 0, -CONSUME_RADIUS), pos.add(CONSUME_RADIUS, CONSUME_RADIUS, CONSUME_RADIUS)));

                if (!items.isEmpty()) {
                    for (ItemEntity entity : items) {
                        if (!ItemTags.PIGLIN_LOVED.contains(items.get(0).getItem().getItem()))
                            continue;

                        int count = entity.getItem().getCount();
                        if(getMana() >= count) {
                            goldConsumed += count;
                            addMana(-(count * 200));
                            entity.remove();
                        }
                    }
                }
            }

            if(goldConsumed >= GOLD_NEEDED) {
                float chance = random.nextFloat();
                if(chance <= NETHERITE_CHANCE) {
                    InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Blocks.ANCIENT_DEBRIS));
                    addMana(-CONSUME_AMOUNT);
                }

                goldConsumed = 0;
                cooldownTimer = MAX_COOLDOWN;
                isCooldown = true;
            }
        }
    }

    @Override
    public RadiusDescriptor getRadius() {
        return new RadiusDescriptor.Square(getEffectivePos(), 2);
    }

    @Override
    public int getMaxMana() {
        return CONSUME_AMOUNT;
    }

    @Override
    public int getColor() {
        return 12203041;
    }
}
