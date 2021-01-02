package dev.onyxstudios.bff.tileentity;

import com.mojang.authlib.GameProfile;
import dev.onyxstudios.bff.registry.ModEntitites;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.TileEntityFunctionalFlower;

import java.util.UUID;

/**
 * Honeysuckle Flower
 * Flower acts similar a PureDaisy
 * Place a chest next to it with shears or a bottle
 * If there are Beehives in it's area with honey ready it will be harvested and placed back into the chest.
 * If there are shears in the chest, it will only shear regardless of any bottles in the chest.
 * Consumes 100 mana per operation
 */
public class TileEntityHoneySuckle extends TileEntityFunctionalFlower {

    public static final int MAX_MANA = 1000;
    public static final int MANA_USAGE = 100;
    public static final int HONEY_AMOUNT = 5;
    public BlockPos inventoryPos;

    public GameProfile gameProfile = new GameProfile(UUID.fromString("fc5ba67f-0d82-4870-ae9a-825cc4a1e852"), "HoneySuckle_FakePlayer");
    public FakePlayer fakePlayer = null;

    public TileEntityHoneySuckle() {
        super(ModEntitites.honeysuckleType.get());
    }

    @Override
    public void readFromPacketNBT(CompoundNBT cmp) {
        super.readFromPacketNBT(cmp);

        if(cmp.contains("chestPos")) {
            inventoryPos = NBTUtil.readBlockPos(cmp.getCompound("inventoryPos"));
        }
    }

    @Override
    public void writeToPacketNBT(CompoundNBT cmp) {
        super.writeToPacketNBT(cmp);
        if(inventoryPos != null) {
            cmp.put("inventoryPos", NBTUtil.writeBlockPos(inventoryPos));
        }
    }

    @Override
    public void tickFlower() {
        super.tickFlower();

        if(world.isRemote())
            return;

        if(fakePlayer == null)
            fakePlayer = new FakePlayer((ServerWorld) world, gameProfile);

        if(inventoryPos == null)
            inventoryPos = findValidInventory();

        if(inventoryPos != null) {
            TileEntity tile = world.getTileEntity(inventoryPos);

            if (tile != null && getMana() >= MANA_USAGE) {
                tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(itemHandler -> {
                    boolean hasShears = false;
                    int slot = -1;
                    int insertSlot = -1;

                    for (int i = 0; i < itemHandler.getSlots(); i++) {
                        ItemStack stack = itemHandler.getStackInSlot(i);

                        if (!stack.isEmpty()) {
                            if (stack.isItemEqualIgnoreDurability(Items.SHEARS.getDefaultInstance())) {
                                insertSlot = canInsertItem(itemHandler, new ItemStack(Items.HONEYCOMB, 3));
                                hasShears = true;
                                slot = i;
                                break;
                            } else if (stack.isItemEqualIgnoreDurability(Items.GLASS_BOTTLE.getDefaultInstance())) {
                                insertSlot = canInsertItem(itemHandler, new ItemStack(Items.HONEY_BOTTLE));
                                slot = i;
                            }
                        }
                    }

                    if (slot >= 0 && insertSlot >= 0) {
                        for (BlockPos localPos : TileEntityPulverose.POSITIONS) {
                            BlockPos offsetPos = pos.add(localPos);

                            if (world.getBlockState(offsetPos).isIn(Blocks.BEEHIVE)) {
                                int honeyLevel = world.getBlockState(offsetPos).get(BeehiveBlock.HONEY_LEVEL);

                                if (honeyLevel >= HONEY_AMOUNT) {
                                    if (hasShears) {
                                        itemHandler.insertItem(insertSlot, new ItemStack(Items.HONEYCOMB, 3), false);
                                        itemHandler.getStackInSlot(slot).damageItem(1, fakePlayer, (player) -> {});
                                    } else {
                                        itemHandler.insertItem(insertSlot, new ItemStack(Items.HONEY_BOTTLE), false);
                                        itemHandler.extractItem(slot, 1, false);
                                    }

                                    addMana(-MANA_USAGE);
                                    world.setBlockState(offsetPos, world.getBlockState(offsetPos).with(BeehiveBlock.HONEY_LEVEL, Integer.valueOf(0)), 3);
                                    world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
                                    break;
                                }
                            }
                        }
                    }
                });
            }
        }
    }

    public int canInsertItem(IItemHandler inventory, ItemStack stack) {
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack slotStack = inventory.getStackInSlot(i);
            if(slotStack.isEmpty() || slotStack.isItemEqualIgnoreDurability(stack)) {
                return slotStack.getCount() - stack.getCount() <= stack.getMaxStackSize() ? i : -1;
            }
        }

        return -1;
    }

    public BlockPos findValidInventory() {
        for (Direction direction : Direction.values()) {
            BlockPos offsetPos = pos.offset(direction);
            TileEntity tile = world.getTileEntity(offsetPos);

            if(tile != null && tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).isPresent()) {
                return offsetPos;
            }
        }

        return null;
    }

    @Override
    public RadiusDescriptor getRadius() {
        return new RadiusDescriptor.Square(this.getEffectivePos(), 1);
    }

    @Override
    public int getMaxMana() {
        return MAX_MANA;
    }

    @Override
    public int getColor() {
        return 16776960;
    }
}
