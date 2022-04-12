package com.almostreliable.lazierae2.core;

import com.almostreliable.lazierae2.core.Setup.Recipes.Serializers;
import com.almostreliable.lazierae2.machine.MachineBlock;
import com.almostreliable.lazierae2.machine.MachineContainer;
import com.almostreliable.lazierae2.machine.MachineEntity;
import com.almostreliable.lazierae2.machine.MachineType;
import com.almostreliable.lazierae2.recipe.type.MachineRecipe;
import com.almostreliable.lazierae2.recipe.type.MachineRecipe.MachineRecipeSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

import static com.almostreliable.lazierae2.core.Constants.*;

public final class Setup {

    private static final Tab TAB = new Tab(MOD_ID);

    private Setup() {}

    public static void init(IEventBus modEventBus) {
        Blocks.REGISTRY.register(modEventBus);
        Items.REGISTRY.register(modEventBus);
        BlockEntities.REGISTRY.register(modEventBus);
        Containers.REGISTRY.register(modEventBus);
        Serializers.REGISTRY.register(modEventBus);
    }

    public static final class BlockEntities {

        private static final DeferredRegister<BlockEntityType<?>> REGISTRY
            = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MOD_ID);

        private BlockEntities() {}

        @SuppressWarnings("ConstantConditions")
        public static final RegistryObject<BlockEntityType<MachineEntity>> MACHINE = REGISTRY.register(MACHINE_ID,
            () -> Builder.of(MachineEntity::new,
                Blocks.AGGREGATOR.get(),
                Blocks.CENTRIFUGE.get(),
                Blocks.ENERGIZER.get(),
                Blocks.ETCHER.get()
            ).build(null)
        );
    }

    public static final class Containers {

        private static final DeferredRegister<MenuType<?>> REGISTRY
            = DeferredRegister.create(ForgeRegistries.CONTAINERS, MOD_ID);
        public static final RegistryObject<MenuType<MachineContainer>> MACHINE = REGISTRY.register(MACHINE_ID,
            () -> IForgeMenuType.create((containerID, inventory, data) -> {
                var entity = inventory.player.level.getBlockEntity(data.readBlockPos());
                if (!(entity instanceof MachineEntity)) {
                    throw new IllegalStateException("Tile is not a LazierAE2 machine!");
                }
                return new MachineContainer(containerID, (MachineEntity) entity, inventory);
            })
        );

        private Containers() {}
    }

    private static final class Tab extends CreativeModeTab {

        private Tab(String label) {
            super(label);
        }

        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Blocks.AGGREGATOR.get());
        }
    }

    public static final class Blocks {

        private static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);

        public static final RegistryObject<MachineBlock> AGGREGATOR = register(MachineBlock::new,
            MachineType.AGGREGATOR
        );
        public static final RegistryObject<MachineBlock> CENTRIFUGE = register(MachineBlock::new,
            MachineType.CENTRIFUGE
        );
        public static final RegistryObject<MachineBlock> ENERGIZER = register(MachineBlock::new, MachineType.ENERGIZER);
        public static final RegistryObject<MachineBlock> ETCHER = register(MachineBlock::new, MachineType.ETCHER);

        private Blocks() {}

        private static <B extends MachineBlock> RegistryObject<B> register(
            Function<? super MachineType, ? extends B> constructor, MachineType machineType
        ) {
            RegistryObject<B> block = REGISTRY.register(machineType.getId(), () -> constructor.apply(machineType));
            Items.REGISTRY.register(machineType.getId(), () -> new BlockItem(block.get(), new Properties().tab(TAB)));
            return block;
        }
    }

    public static final class Items {

        private static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
        public static final RegistryObject<Item> CARB_FLUIX_DUST = register(CARB_FLUIX_DUST_ID);
        public static final RegistryObject<Item> COAL_DUST = register(COAL_DUST_ID);
        public static final RegistryObject<Item> FLUIX_IRON = register(FLUIX_IRON_ID);
        public static final RegistryObject<Item> FLUIX_STEEL = register(FLUIX_STEEL_ID);
        public static final RegistryObject<Item> GROWTH_CHAMBER = register(GROWTH_CHAMBER_ID);
        public static final RegistryObject<Item> LOGIC_UNIT = register(LOGIC_UNIT_ID);
        public static final RegistryObject<Item> PARALLEL_PRINTED = register(PARALLEL_PRINTED_ID);
        public static final RegistryObject<Item> PARALLEL_PROCESSOR = register(PARALLEL_PROCESSOR_ID);
        public static final RegistryObject<Item> RESONATING_GEM = register(RESONATING_GEM_ID);
        public static final RegistryObject<Item> SPEC_CORE_1 = register(SPEC_CORE_1_ID);
        public static final RegistryObject<Item> SPEC_CORE_2 = register(SPEC_CORE_2_ID);
        public static final RegistryObject<Item> SPEC_CORE_4 = register(SPEC_CORE_4_ID);
        public static final RegistryObject<Item> SPEC_CORE_8 = register(SPEC_CORE_8_ID);
        public static final RegistryObject<Item> SPEC_CORE_16 = register(SPEC_CORE_16_ID);
        public static final RegistryObject<Item> SPEC_CORE_32 = register(SPEC_CORE_32_ID);
        public static final RegistryObject<Item> SPEC_CORE_64 = register(SPEC_CORE_64_ID);
        public static final RegistryObject<Item> SPEC_PRINTED = register(SPEC_PRINTED_ID);
        public static final RegistryObject<Item> SPEC_PROCESSOR = register(SPEC_PROCESSOR_ID);
        public static final RegistryObject<Item> UNIVERSAL_PRESS = register(UNIVERSAL_PRESS_ID);

        private Items() {}

        private static RegistryObject<Item> register(String id) {
            return REGISTRY.register(id, () -> new Item(new Properties().tab(TAB)));
        }
    }

    public static final class Tags {

        private Tags() {}

        public static final class Items {
            public static final TagKey<Item> DUSTS_COAL = forge("dusts/coal");
            public static final TagKey<Item> DUSTS_CARBONIC_FLUIX = forge("dusts/carbonic_fluix");
            public static final TagKey<Item> GEMS_RESONATING = forge("gems/resonating");
            public static final TagKey<Item> INGOTS_FLUIX_IRON = forge("ingots/fluix_iron");
            public static final TagKey<Item> INGOTS_FLUIX_STEEL = forge("ingots/fluix_steel");

            public static final TagKey<Item> PROCESSOR_PARALLEL = mod("processors/parallel");
            public static final TagKey<Item> PROCESSOR_SPEC = mod("processors/speculative");

            // Applied Energistics 2
            public static final TagKey<Item> SILICON = forge("silicon");

            private Items() {}

            private static TagKey<Item> forge(String path) {
                return ItemTags.create(new ResourceLocation("forge", path));
            }

            private static TagKey<Item> mod(String path) {
                return ItemTags.create(new ResourceLocation(MOD_ID, path));
            }
        }

        public static final class Blocks {

            private static final String MACHINE_ENTRY = "machines/";
            public static final TagKey<Block> AGGREGATOR = mod(MACHINE_ENTRY + MachineType.AGGREGATOR.getId());
            public static final TagKey<Block> CENTRIFUGE = mod(MACHINE_ENTRY + MachineType.CENTRIFUGE.getId());
            public static final TagKey<Block> ENERGIZER = mod(MACHINE_ENTRY + MachineType.ENERGIZER.getId());
            public static final TagKey<Block> ETCHER = mod(MACHINE_ENTRY + MachineType.ETCHER.getId());

            private Blocks() {}

            private static TagKey<Block> mod(String path) {
                return BlockTags.create(new ResourceLocation(MOD_ID, path));
            }
        }
    }

    public static final class Recipes {

        private Recipes() {}

        public static final class Serializers {

            private static final DeferredRegister<RecipeSerializer<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS,
                MOD_ID
            );

            public static final RegistryObject<RecipeSerializer<MachineRecipe>> AGGREGATOR
                = register(MachineType.AGGREGATOR);
            public static final RegistryObject<RecipeSerializer<MachineRecipe>> CENTRIFUGE
                = register(MachineType.CENTRIFUGE);
            public static final RegistryObject<RecipeSerializer<MachineRecipe>> ENERGIZER
                = register(MachineType.ENERGIZER);
            public static final RegistryObject<RecipeSerializer<MachineRecipe>> ETCHER = register(MachineType.ETCHER);

            private Serializers() {}

            private static RegistryObject<RecipeSerializer<MachineRecipe>> register(
                MachineType machineType
            ) {
                return REGISTRY.register(machineType.getId(), () -> new MachineRecipeSerializer(machineType));
            }
        }
    }
}
