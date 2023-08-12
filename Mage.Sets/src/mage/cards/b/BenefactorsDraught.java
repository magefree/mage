
package mage.cards.b;

import java.util.UUID;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.UntapAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public final class BenefactorsDraught extends CardImpl {

    public BenefactorsDraught(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Untap all creatures.
        this.getSpellAbility().addEffect(new UntapAllEffect(new FilterCreaturePermanent()));

        // Until end of turn, whenever a creature an opponent controls blocks, draw a card.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new BenefactorsDraughtTriggeredAbility()));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private BenefactorsDraught(final BenefactorsDraught card) {
        super(card);
    }

    @Override
    public BenefactorsDraught copy() {
        return new BenefactorsDraught(this);
    }
}

class BenefactorsDraughtTriggeredAbility extends DelayedTriggeredAbility {

    BenefactorsDraughtTriggeredAbility() {
        super(new DrawCardSourceControllerEffect(1), Duration.EndOfTurn, false);
    }

    BenefactorsDraughtTriggeredAbility(final BenefactorsDraughtTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BenefactorsDraughtTriggeredAbility copy() {
        return new BenefactorsDraughtTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent blocker = game.getPermanent(event.getSourceId());
        Player controller = game.getPlayer(this.getControllerId());
        return blocker != null && controller != null && game.isOpponent(controller, blocker.getControllerId());
    }

    @Override
    public String getRule() {
        return "Until end of turn, whenever a creature an opponent controls blocks, draw a card";
    }
}
