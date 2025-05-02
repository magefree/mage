package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;

/**
 * @author TheElk801
 */
public class LockOrUnlockRoomTargetEffect extends OneShotEffect {

    public LockOrUnlockRoomTargetEffect() {
        super(Outcome.Benefit);
        staticText = "lock or unlock a door of target Room you control";
    }

    private LockOrUnlockRoomTargetEffect(final LockOrUnlockRoomTargetEffect effect) {
        super(effect);
    }

    @Override
    public LockOrUnlockRoomTargetEffect copy() {
        return new LockOrUnlockRoomTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // TODO: Implement this
        return true;
    }
}
