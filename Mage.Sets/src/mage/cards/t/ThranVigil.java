package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.CardsLeaveGraveyardTriggeredAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThranVigil extends CardImpl {

    public ThranVigil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        // Whenever one or more artifact and/or creature cards leave your graveyard during your turn, put a +1/+1 counter on target creature you control.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new CardsLeaveGraveyardTriggeredAbility(
                        new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                        StaticFilters.FILTER_CARD_ARTIFACT_OR_CREATURE
                ), MyTurnCondition.instance, "Whenever one or more artifact and/or creature cards " +
                "leave your graveyard during your turn, put a +1/+1 counter on target creature you control."
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private ThranVigil(final ThranVigil card) {
        super(card);
    }

    @Override
    public ThranVigil copy() {
        return new ThranVigil(this);
    }
}
