package element4th.snapshotmc.common.item.custom;

import element4th.snapshotmc.common.block.custom.RopeBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SideShapeType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class RopeBlockItem extends BlockItem {
    public RopeBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    protected boolean canPlace(ItemPlacementContext context, BlockState state) {
        if((!context.getWorld().getBlockState(context.getBlockPos().up()).isSideSolid(context.getWorld(), context.getBlockPos().up(), Direction.DOWN, SideShapeType.FULL)) && !(context.getWorld().getBlockState(context.getBlockPos().up()).getBlock() instanceof RopeBlock)) return false;
        return super.canPlace(context, state);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos pos = context.getBlockPos();
        World world = context.getWorld();

        // Check if the initial block is a RopeBlock
        if (world.getBlockState(pos).getBlock() instanceof RopeBlock) {
            // Traverse downward to find the bottom-most rope block
            while (world.getBlockState(pos.down()).getBlock() instanceof RopeBlock) {
                pos = pos.down();
            }

            // Place the new rope block at the bottom
            BlockState newState = world.getBlockState(context.getBlockPos());

            if(!newState.canPlaceAt(world, pos)) return super.useOnBlock(context);

            this.place(new ItemPlacementContext(context.getPlayer(), context.getHand(), context.getStack(), new BlockHitResult(context.getPlayer().getPos(), Direction.DOWN, pos, false)));

            return ActionResult.SUCCESS;
        }

        return super.useOnBlock(context);
    }

}
