package mage.abilities.common;

import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.filter.FilterStackObject;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.common.NumberOfTimesPermanentTargetedATurnWatcher;

/**
 * @author xenohedron
 */
public class BecomesTargetSourceFirstTimeTriggeredAbility extends BecomesTargetSourceTriggeredAbility {

    public BecomesTargetSourceFirstTimeTriggeredAbility(Effect effect, FilterStackObject filter,
                                                        SetTargetPointer setTargetPointer, boolean optional) {
        super(effect, filter, setTargetPointer, optional);
        this.addWatcher(new NumberOfTimesPermanentTargetedATurnWatcher());
        setTriggerPhrase("Whenever {this} becomes the target of " + filter.getMessage() + " for the first time each turn, ");
    }

    protected BecomesTargetSourceFirstTimeTriggeredAbility(final BecomesTargetSourceFirstTimeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BecomesTargetSourceFirstTimeTriggeredAbility copy() {
        return new BecomesTargetSourceFirstTimeTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        NumberOfTimesPermanentTargetedATurnWatcher watcher = game.getState().getWatcher(NumberOfTimesPermanentTargetedATurnWatcher.class);
        if (permanent == null || watcher == null || watcher.numTimesTargetedThisTurn(permanent, game) > 1) {
            return false;
        }
        return super.checkTrigger(event, game);
    }
}
