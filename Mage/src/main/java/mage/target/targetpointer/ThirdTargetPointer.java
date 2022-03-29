package mage.target.targetpointer;

import mage.abilities.Ability;
import mage.game.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    protected List<UUID> getTargetIds(Game game, Ability source) {
        if (source.getTargets().size() < 3) {
            return new ArrayList<>();
        }
        return source.getTargets().get(2).getTargets();
    }
}
