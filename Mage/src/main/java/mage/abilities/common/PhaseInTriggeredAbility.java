package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author TheElk801
 */
public class PhaseInTriggeredAbility extends TriggeredAbilityImpl {

    private FilterPermanent filter;

    public PhaseInTriggeredAbility(Effect effect, FilterPermanent filter) {
        this(effect, filter, false);
    }

    public PhaseInTriggeredAbility(Effect effect, FilterPermanent filter, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filter = filter;
        setTriggerPhrase("Whenever " + filter.getMessage() + " phases in, ");
    }

    public PhaseInTriggeredAbility(final PhaseInTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PhaseInTriggeredAbility copy() {
        return new PhaseInTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PHASED_IN;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        return permanent != null && filter.match(permanent, getControllerId(), this, game);
    }
}
