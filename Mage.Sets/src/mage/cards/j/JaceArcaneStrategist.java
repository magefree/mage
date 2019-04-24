package mage.cards.j;

import mage.abilities.LoyaltyAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JaceArcaneStrategist extends CardImpl {

    public JaceArcaneStrategist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{U}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.JACE);
        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(4));

        // Whenever you draw your second card each turn, put a +1/+1 counter on target creature you control.
        this.addAbility(new JaceArcaneStrategistTriggeredAbility());

        // +1: Draw a card.
        this.addAbility(new LoyaltyAbility(new DrawCardSourceControllerEffect(1), 1));

        // -7: Creatures you control can't be blocked this turn.
        this.addAbility(new LoyaltyAbility(new CantBeBlockedAllEffect(
                StaticFilters.FILTER_CONTROLLED_CREATURE, Duration.EndOfTurn
        ), -7));
    }

    private JaceArcaneStrategist(final JaceArcaneStrategist card) {
        super(card);
    }

    @Override
    public JaceArcaneStrategist copy() {
        return new JaceArcaneStrategist(this);
    }
}

class JaceArcaneStrategistTriggeredAbility extends TriggeredAbilityImpl {

    private boolean triggeredOnce = false;
    private boolean triggeredTwice = false;

    JaceArcaneStrategistTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false);
        this.addTarget(new TargetControlledCreaturePermanent());
    }

    private JaceArcaneStrategistTriggeredAbility(final JaceArcaneStrategistTriggeredAbility ability) {
        super(ability);
        this.triggeredOnce = ability.triggeredOnce;
        this.triggeredTwice = ability.triggeredTwice;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DREW_CARD
                || event.getType() == GameEvent.EventType.END_PHASE_POST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.END_PHASE_POST) {
            triggeredOnce = triggeredTwice = false;
            return false;
        }
        if (event.getType() == GameEvent.EventType.DREW_CARD
                && event.getPlayerId().equals(controllerId)) {
            if (triggeredOnce) {
                if (triggeredTwice) {
                    return false;
                } else {
                    triggeredTwice = true;
                    return true;
                }
            } else {
                triggeredOnce = true;
                return false;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you draw your second card each turn, put a +1/+1 counter on target creature you control.";
    }

    @Override
    public JaceArcaneStrategistTriggeredAbility copy() {
        return new JaceArcaneStrategistTriggeredAbility(this);
    }
}
