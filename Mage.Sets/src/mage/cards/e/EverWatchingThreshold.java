package mage.cards.e;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EverWatchingThreshold extends CardImpl {

    public EverWatchingThreshold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // Whenever an opponent attacks you and/or a planeswalker you control with one or more creatures, draw a card.
        this.addAbility(new EverWatchingThresholdTriggeredAbility());
    }

    private EverWatchingThreshold(final EverWatchingThreshold card) {
        super(card);
    }

    @Override
    public EverWatchingThreshold copy() {
        return new EverWatchingThreshold(this);
    }
}

class EverWatchingThresholdTriggeredAbility extends TriggeredAbilityImpl {

    public EverWatchingThresholdTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), false);
    }

    private EverWatchingThresholdTriggeredAbility(final EverWatchingThresholdTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EverWatchingThresholdTriggeredAbility copy() {
        return new EverWatchingThresholdTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getCombat()
                .getAttackers()
                .stream()
                .filter(attacker -> isControlledBy(game.getCombat().getDefendingPlayerId(attacker, game)))
                .map(game::getControllerId)
                .anyMatch(game.getOpponents(getControllerId())::contains);
    }

    @Override
    public String getRule() {
        return "Whenever an opponent attacks, if they attacked you and/or a planeswalker you control, draw a card.";
    }
}
