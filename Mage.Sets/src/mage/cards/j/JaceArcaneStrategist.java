package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.DrawCardTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
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
        this.setStartingLoyalty(4);

        // Whenever you draw your second card each turn, put a +1/+1 counter on target creature you control.
        Ability ability = new DrawCardTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false, 2
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

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
