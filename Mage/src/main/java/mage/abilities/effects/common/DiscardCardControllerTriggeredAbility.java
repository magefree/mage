package mage.abilities.effects.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author TheElk801
 */
public class DiscardCardControllerTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterCard filter;

    public DiscardCardControllerTriggeredAbility(Effect effect, boolean isOptional) {
        this(effect, isOptional, StaticFilters.FILTER_CARD_A);
    }

    public DiscardCardControllerTriggeredAbility(Effect effect, boolean isOptional, FilterCard filter) {
        super(Zone.BATTLEFIELD, effect, isOptional);
        this.filter = filter;
        setTriggerPhrase("Whenever you discard " + filter.getMessage() + ", ");
    }

    private DiscardCardControllerTriggeredAbility(final DiscardCardControllerTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
    }

    @Override
    public DiscardCardControllerTriggeredAbility copy() {
        return new DiscardCardControllerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DISCARDED_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (isControlledBy(event.getPlayerId())
                && filter.match(game.getCard(event.getTargetId()), this.getControllerId(), this, game)) {
            this.getEffects().setValue("discardedCard", game.getCard(event.getTargetId()));
            return true;
        }
        return false;
    }
}
