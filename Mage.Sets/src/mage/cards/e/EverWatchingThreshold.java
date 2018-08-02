package mage.cards.e;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class EverWatchingThreshold extends CardImpl {

    public EverWatchingThreshold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // Whenever an opponent attacks you and/or a planeswalker you control with one or more creatures, draw a card.
        this.addAbility(new EverWatchingThresholdTriggeredAbility());
    }

    public EverWatchingThreshold(final EverWatchingThreshold card) {
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

    public EverWatchingThresholdTriggeredAbility(final EverWatchingThresholdTriggeredAbility ability) {
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
        Player player = game.getPlayer(this.getControllerId());
        if (player == null) {
            return false;
        }
        for (UUID attacker : game.getCombat().getAttackers()) {
            Permanent creature = game.getPermanent(attacker);
            if (creature != null
                    && player.hasOpponent(creature.getControllerId(), game)
                    && player.getId().equals(game.getCombat().getDefendingPlayerId(attacker, game))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent attacks you "
                + "and/or a planeswalker you control "
                + "with one or more creatures, draw a card.";
    }
}
