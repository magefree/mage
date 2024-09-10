package mage.cards.c;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.TheRingTemptsYouEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CallOfTheRing extends CardImpl {

    public CallOfTheRing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        // At the beginning of your upkeep, the Ring tempts you.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new TheRingTemptsYouEffect(), TargetController.YOU, false
        ));

        // Whenever you choose a creature as your Ring-bearer, you may pay 2 life. If you do, draw a card.
        this.addAbility(new CallOfTheRingTriggeredAbility());
    }

    private CallOfTheRing(final CallOfTheRing card) {
        super(card);
    }

    @Override
    public CallOfTheRing copy() {
        return new CallOfTheRing(this);
    }
}

class CallOfTheRingTriggeredAbility extends TriggeredAbilityImpl {

    CallOfTheRingTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new PayLifeCost(2)));
        setTriggerPhrase("Whenever you choose a creature as your Ring-bearer, ");
    }

    private CallOfTheRingTriggeredAbility(final CallOfTheRingTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CallOfTheRingTriggeredAbility copy() {
        return new CallOfTheRingTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.RING_BEARER_CHOSEN;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(event.getPlayerId());
    }
}
