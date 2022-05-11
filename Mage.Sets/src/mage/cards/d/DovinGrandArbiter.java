package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ThopterColorlessToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DovinGrandArbiter extends CardImpl {

    public DovinGrandArbiter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{W}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DOVIN);
        this.setStartingLoyalty(3);

        // +1: Until end of turn, whenever a creature you control deals combat damage to a player, put a loyalty counter on Dovin, Grand Arbiter.
        this.addAbility(new LoyaltyAbility(new CreateDelayedTriggeredAbilityEffect(
                new DovinGrandArbiterDelayedTriggeredAbility(), false
        ), 1));

        // -1: Create a 1/1 colorless Thopter artifact creature token with flying. You gain 1 life.
        Ability ability = new LoyaltyAbility(new CreateTokenEffect(new ThopterColorlessToken()), -1);
        ability.addEffect(new GainLifeEffect(1));
        this.addAbility(ability);

        // -7: Look at the top ten cards of your library. Put three of them into your hand
        // and the rest on the bottom of your library in a random order.
        this.addAbility(new LoyaltyAbility(new LookLibraryAndPickControllerEffect(
                10, 3, PutCards.HAND, PutCards.BOTTOM_RANDOM), -7));
    }

    private DovinGrandArbiter(final DovinGrandArbiter card) {
        super(card);
    }

    @Override
    public DovinGrandArbiter copy() {
        return new DovinGrandArbiter(this);
    }
}

class DovinGrandArbiterDelayedTriggeredAbility extends DelayedTriggeredAbility {

    DovinGrandArbiterDelayedTriggeredAbility() {
        super(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance()), Duration.EndOfTurn, false);
    }

    private DovinGrandArbiterDelayedTriggeredAbility(final DovinGrandArbiterDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DovinGrandArbiterDelayedTriggeredAbility copy() {
        return new DovinGrandArbiterDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((DamagedPlayerEvent) event).isCombatDamage()) {
            Permanent creature = game.getPermanent(event.getSourceId());
            if (creature != null && creature.isControlledBy(controllerId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Until end of turn, whenever a creature you control " +
                "deals combat damage to a player, " +
                "put a loyalty counter on {this}.";
    }
}
