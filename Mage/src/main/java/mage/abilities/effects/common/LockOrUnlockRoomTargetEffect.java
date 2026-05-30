package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author TheElk801, Grath
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
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || permanent == null) {
            return false;
        }
        boolean useLeftDoor;
        boolean lockTheDoor;
        if (!permanent.isLeftDoorUnlocked() && !permanent.isRightDoorUnlocked()) {
            useLeftDoor = player.chooseUse(
                    Outcome.Neutral, "Unlock the left door or the right door?",
                    null, "Left", "Right", source, game
            );
            lockTheDoor = false;
        } else if (!permanent.isLeftDoorUnlocked() && permanent.isRightDoorUnlocked()) {
            useLeftDoor = player.chooseUse(
                    Outcome.Neutral, "Unlock the left door or lock the right door?",
                    null, "Unlock left", "Lock right", source, game
            );
            lockTheDoor = !useLeftDoor;
        } else if (permanent.isLeftDoorUnlocked() && !permanent.isRightDoorUnlocked()) {
            useLeftDoor = player.chooseUse(
                    Outcome.Neutral, "Lock the left door or unlock the right door?",
                    null, "Lock left", "Unlock right", source, game
            );
            lockTheDoor = useLeftDoor;
        } else {
            useLeftDoor = player.chooseUse(
                    Outcome.Neutral, "Lock the left door or the right door?",
                    null, "Left", "Right", source, game
            );
            lockTheDoor = true;
        }
        if (lockTheDoor) {
            return permanent.lockDoor(game, source, useLeftDoor);
        } else {
            return permanent.unlockDoor(game, source, useLeftDoor);
        }
    }
}
