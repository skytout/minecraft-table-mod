package com.mayatout.mccourse.block;

import com.mayatout.mccourse.container.ElectrifierContainer;
import com.mayatout.mccourse.tileentity.ElectrifierTile;
import com.mayatout.mccourse.tileentity.ModTileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.stream.Stream;

public class Electrifier extends Block {
    private  static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    private static final VoxelShape SHAPE_N = Stream.of(
            Block.makeCuboidShape(14, 14, 14, 15.999999999999998, 16, 16),
            Block.makeCuboidShape(1.7763568394002505e-15, 14, 3.552713678800501e-15, 2, 16, 2),
            Block.makeCuboidShape(14, 14, 3.552713678800501e-15, 15.999999999999998, 16, 2),
            Block.makeCuboidShape(1.7763568394002505e-15, 14, 14, 2, 16, 16),
            Block.makeCuboidShape(1, 2, 1, 15, 14, 15),
            Block.makeCuboidShape(14, 0, 14, 15.999999999999998, 2, 16),
            Block.makeCuboidShape(1.7763568394002505e-15, 0, 3.552713678800501e-15, 2, 2, 2),
            Block.makeCuboidShape(14, 0, 3.552713678800501e-15, 15.999999999999998, 2, 2),
            Block.makeCuboidShape(1.7763568394002505e-15, 0, 14, 2, 2, 16),
            Block.makeCuboidShape(1.7763568394002505e-15, 2, 3.552713678800501e-15, 2, 14, 2),
            Block.makeCuboidShape(2, 14, 15, 14, 15, 16),
            Block.makeCuboidShape(15, 14, 2, 15.999999999999998, 15, 14),
            Block.makeCuboidShape(1.7763568394002505e-15, 14, 2, 1, 15, 14),
            Block.makeCuboidShape(2, 14, 3.552713678800501e-15, 14, 16, 2),
            Block.makeCuboidShape(14, 2, 3.552713678800501e-15, 15.999999999999998, 14, 2),
            Block.makeCuboidShape(1.7763568394002505e-15, 2, 15, 1, 14, 16),
            Block.makeCuboidShape(15, 2, 15, 15.999999999999998, 14, 16)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();


    private static final VoxelShape SHAPE_E = Stream.of(
            Block.makeCuboidShape(0.08823529411764852, 14, 14.088235294117649, 2.0882352941176485, 16, 16.088235294117645),
            Block.makeCuboidShape(14.088235294117649, 14, 0.08823529411765207, 16.088235294117645, 16, 2.0882352941176485),
            Block.makeCuboidShape(14.088235294117649, 14, 14.088235294117649, 16.088235294117645, 16, 16.088235294117645),
            Block.makeCuboidShape(0.08823529411764852, 14, 0.08823529411765207, 2.0882352941176485, 16, 2.0882352941176485),
            Block.makeCuboidShape(1.0882352941176485, 2, 1.0882352941176485, 15.088235294117649, 14, 15.088235294117649),
            Block.makeCuboidShape(0.08823529411764852, 0, 14.088235294117649, 2.0882352941176485, 2, 16.088235294117645),
            Block.makeCuboidShape(14.088235294117649, 0, 0.08823529411765207, 16.088235294117645, 2, 2.0882352941176485),
            Block.makeCuboidShape(14.088235294117649, 0, 14.088235294117649, 16.088235294117645, 2, 16.088235294117645),
            Block.makeCuboidShape(0.08823529411764852, 0, 0.08823529411765207, 2.0882352941176485, 2, 2.0882352941176485),
            Block.makeCuboidShape(14.088235294117649, 2, 0.08823529411765207, 16.088235294117645, 14, 2.0882352941176485),
            Block.makeCuboidShape(0.08823529411764852, 14, 2.0882352941176485, 1.0882352941176485, 15, 14.088235294117649),
            Block.makeCuboidShape(2.0882352941176485, 14, 15.088235294117649, 14.088235294117649, 15, 16.088235294117645),
            Block.makeCuboidShape(2.0882352941176485, 14, 0.08823529411765207, 14.088235294117649, 15, 1.0882352941176485),
            Block.makeCuboidShape(14.088235294117649, 14, 2.0882352941176485, 16.088235294117645, 16, 14.088235294117649),
            Block.makeCuboidShape(14.088235294117649, 2, 14.088235294117649, 16.088235294117645, 14, 16.088235294117645),
            Block.makeCuboidShape(0.08823529411764852, 2, 0.08823529411765207, 1.0882352941176485, 14, 1.0882352941176485),
            Block.makeCuboidShape(0.08823529411764852, 2, 15.088235294117649, 1.0882352941176485, 14, 16.088235294117645)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static final VoxelShape SHAPE_S = Stream.of(
            Block.makeCuboidShape(3.552713678800501e-15, 14, 0.17647058823529704, 2, 16, 2.176470588235297),
            Block.makeCuboidShape(14, 14, 14.176470588235297, 15.999999999999996, 16, 16.176470588235293),
            Block.makeCuboidShape(3.552713678800501e-15, 14, 14.176470588235297, 2, 16, 16.176470588235293),
            Block.makeCuboidShape(14, 14, 0.17647058823529704, 15.999999999999996, 16, 2.176470588235297),
            Block.makeCuboidShape(1, 2, 1.176470588235297, 15, 14, 15.176470588235297),
            Block.makeCuboidShape(3.552713678800501e-15, 0, 0.17647058823529704, 2, 2, 2.176470588235297),
            Block.makeCuboidShape(14, 0, 14.176470588235297, 15.999999999999996, 2, 16.176470588235293),
            Block.makeCuboidShape(3.552713678800501e-15, 0, 14.176470588235297, 2, 2, 16.176470588235293),
            Block.makeCuboidShape(14, 0, 0.17647058823529704, 15.999999999999996, 2, 2.176470588235297),
            Block.makeCuboidShape(14, 2, 14.176470588235297, 15.999999999999996, 14, 16.176470588235293),
            Block.makeCuboidShape(2, 14, 0.17647058823529704, 14, 15, 1.176470588235297),
            Block.makeCuboidShape(3.552713678800501e-15, 14, 2.176470588235297, 1, 15, 14.176470588235297),
            Block.makeCuboidShape(15, 14, 2.176470588235297, 15.999999999999996, 15, 14.176470588235297),
            Block.makeCuboidShape(2, 14, 14.176470588235297, 14, 16, 16.176470588235293),
            Block.makeCuboidShape(3.552713678800501e-15, 2, 14.176470588235297, 2, 14, 16.176470588235293),
            Block.makeCuboidShape(15, 2, 0.17647058823529704, 15.999999999999996, 14, 1.176470588235297),
            Block.makeCuboidShape(3.552713678800501e-15, 2, 0.17647058823529704, 1, 14, 1.176470588235297)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static final VoxelShape SHAPE_W = Stream.of(
            Block.makeCuboidShape(13.911764705882351, 14, 0.08823529411765207, 15.911764705882351, 16, 2.0882352941176485),
            Block.makeCuboidShape(-0.08823529411764497, 14, 14.088235294117649, 1.9117647058823515, 16, 16.088235294117645),
            Block.makeCuboidShape(-0.08823529411764497, 14, 0.08823529411765207, 1.9117647058823515, 16, 2.0882352941176485),
            Block.makeCuboidShape(13.911764705882351, 14, 14.088235294117649, 15.911764705882351, 16, 16.088235294117645),
            Block.makeCuboidShape(0.9117647058823515, 2, 1.0882352941176485, 14.911764705882351, 14, 15.088235294117649),
            Block.makeCuboidShape(13.911764705882351, 0, 0.08823529411765207, 15.911764705882351, 2, 2.0882352941176485),
            Block.makeCuboidShape(-0.08823529411764497, 0, 14.088235294117649, 1.9117647058823515, 2, 16.088235294117645),
            Block.makeCuboidShape(-0.08823529411764497, 0, 0.08823529411765207, 1.9117647058823515, 2, 2.0882352941176485),
            Block.makeCuboidShape(13.911764705882351, 0, 14.088235294117649, 15.911764705882351, 2, 16.088235294117645),
            Block.makeCuboidShape(-0.08823529411764497, 2, 14.088235294117649, 1.9117647058823515, 14, 16.088235294117645),
            Block.makeCuboidShape(14.911764705882351, 14, 2.0882352941176485, 15.911764705882351, 15, 14.088235294117649),
            Block.makeCuboidShape(1.9117647058823515, 14, 0.08823529411765207, 13.911764705882351, 15, 1.0882352941176485),
            Block.makeCuboidShape(1.9117647058823515, 14, 15.088235294117649, 13.911764705882351, 15, 16.088235294117645),
            Block.makeCuboidShape(-0.08823529411764497, 14, 2.0882352941176485, 1.9117647058823515, 16, 14.088235294117649),
            Block.makeCuboidShape(-0.08823529411764497, 2, 0.08823529411765207, 1.9117647058823515, 14, 2.0882352941176485),
            Block.makeCuboidShape(14.911764705882351, 2, 15.088235294117649, 15.911764705882351, 14, 16.088235294117645),
            Block.makeCuboidShape(14.911764705882351, 2, 0.08823529411765207, 15.911764705882351, 14, 1.0882352941176485)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    public Electrifier(Properties properties) {
        super(properties);
    }
    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
       switch (state.get(FACING)) {
           case NORTH:
               return SHAPE_N;
           case EAST:
               return SHAPE_E;
           case SOUTH:
               return SHAPE_S;
           case WEST:
               return SHAPE_W;
           default:
               return SHAPE_N;
       }
    }
    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos,
                                             PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
        if (!world.isRemote())
        {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof ElectrifierTile)
            {
                INamedContainerProvider containerProvider = new INamedContainerProvider() {
                    @Override
                    public ITextComponent getDisplayName() {
                        return new TranslationTextComponent("screen.mccourse.electrifier");
                    }

                    @Override
                    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                        return new ElectrifierContainer(i, world, pos, playerInventory, playerEntity);
                    }
                };
                NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, tileEntity.getPos());
            } else {
                throw new IllegalStateException("Our named container provider is missing!");
            }
        }

        return ActionResultType.SUCCESS;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return ModTileEntities.ELECTRIFIER_TILE_ENTITY.get().create();
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }
    @SuppressWarnings("deprecation")
    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }
    @SuppressWarnings("deprecation")
    @Override
    public  BlockState mirror(BlockState state, Mirror mirrorIn) {

        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }
    @Override
    protected  void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
