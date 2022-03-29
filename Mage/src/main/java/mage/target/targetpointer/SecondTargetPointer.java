package mage.target.targetpointer;

import mage.abilities.Ability;
import mage.game.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    protected List<UUID> getTargetIds(Game game, Ability source) {
        if (source.getTargets().size() < 2) {
            return new ArrayList<>();
        }
        return source.getTargets().get(1).getTargets();
    }
}
