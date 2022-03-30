package mage.target.targetpointer;

import mage.abilities.Ability;
import mage.game.Game;

import java.util.UUID;
import java.util.stream.Stream;

public class FirstTargetPointer extends NonFixedTargetPointer {

    public FirstTargetPointer() {
        super();
    }

    private FirstTargetPointer(final FirstTargetPointer targetPointer) {
        super(targetPointer);
    }

    @Override
    public FirstTargetPointer copy() {
        return new FirstTargetPointer(this);
    }

    @Override
    protected Stream<UUID> getTargetStream(Game game, Ability source) {
        if (source.getTargets().isEmpty()) {
            return Stream.of();
        }
        return source.getTargets().get(0).getTargets().stream();
    }
}
