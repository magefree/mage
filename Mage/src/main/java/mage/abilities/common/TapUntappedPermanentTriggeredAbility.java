package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author Susucr
 */
public class TapUntappedPermanentTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterPermanent filter;

    public TapUntappedPermanentTriggeredAbility(Effect effect, FilterPermanent filter) {
        super(Zone.BATTLEFIELD, effect);
        this.filter = filter.copy();
        setTriggerPhrase("Whenever you tap an untapped " + filter.getMessage() + ", ");
    }

    protected TapUntappedPermanentTriggeredAbility(final TapUntappedPermanentTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter.copy();
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (permanent == null) {
            return false;
        }
        return getControllerId().equals(event.getPlayerId())
                && filter.match(permanent, getControllerId(), this, game);
    }

    @Override
    public TapUntappedPermanentTriggeredAbility copy() {
        return new TapUntappedPermanentTriggeredAbility(this);
    }
}
