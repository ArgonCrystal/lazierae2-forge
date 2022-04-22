package com.almostreliable.lazierae2.content.assembler;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class OptionalDirectionProperty extends EnumProperty<OptionalDirection> {

    public static final OptionalDirectionProperty HORIZONTAL = create("horizontal",
        Direction.NORTH,
        Direction.EAST,
        Direction.SOUTH,
        Direction.WEST
    );
    public static final OptionalDirectionProperty VERTICAL = create("vertical", Direction.UP, Direction.DOWN);

    protected OptionalDirectionProperty(
        String name, Collection<Direction> directions
    ) {
        super(name, OptionalDirection.class, createOptionalDirections(directions));
    }

    public static OptionalDirectionProperty create(String name, Direction... directions) {
        return new OptionalDirectionProperty(name, List.of(directions));
    }

    private static Collection<OptionalDirection> createOptionalDirections(Collection<Direction> directions) {
        Set<OptionalDirection> optionalDirections = EnumSet.noneOf(OptionalDirection.class);
        optionalDirections.add(OptionalDirection.NONE);
        directions.forEach(direction -> optionalDirections.add(OptionalDirection.fromDirection(direction)));
        return optionalDirections;
    }
}
