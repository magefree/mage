package mage.target.targetpointer;

import mage.abilities.Ability;
import mage.game.Game;

import java.util.UUID;
import java.util.stream.Stream;

public class SecondTargetPointer extends NonFixedTargetPointer {

    public SecondTargetPointer() {
        super();
    }

    public SecondTargetPointer(final SecondTargetPointer targetPointer) {
        super(targetPointer);
    }

    @Override
    public SecondTargetPointer copy() {
        return new SecondTargetPointer(this);
    }

    @Override
    protected Stream<UUID> getTargetStream(Game game, Ability source) {
        if (source.getTargets().size() < 2) {
            return Stream.of();
        }
        return source.getTargets().get(1).getTargets().stream();
    }
}
