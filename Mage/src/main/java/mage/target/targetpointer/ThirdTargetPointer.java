package mage.target.targetpointer;

import mage.abilities.Ability;
import mage.game.Game;

import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author LevelX2
 */
public class ThirdTargetPointer extends NonFixedTargetPointer {

    public ThirdTargetPointer() {
        super();
    }

    public ThirdTargetPointer(final ThirdTargetPointer targetPointer) {
        super(targetPointer);
    }

    @Override
    public ThirdTargetPointer copy() {
        return new ThirdTargetPointer(this);
    }

    @Override
    protected Stream<UUID> getTargetStream(Game game, Ability source) {
        if (source.getTargets().size() < 3) {
            return Stream.of();
        }
        return source.getTargets().get(2).getTargets().stream();
    }
}
