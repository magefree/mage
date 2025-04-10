package mage.cards.b;

import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CreatureDiedControlledCondition;
import mage.abilities.effects.common.ChooseModeEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.effects.common.continuous.GainAnchorWordAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ModeChoice;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BarrensteppeSiege extends CardImpl {

    public BarrensteppeSiege(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{B}");

        // As this enchantment enters, choose Abzan or Mardu.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseModeEffect(ModeChoice.ABZAN, ModeChoice.MARDU)));

        // * Abzan -- At the beginning of your end step, put a +1/+1 counter on each creature you control.
        this.addAbility(new SimpleStaticAbility(new GainAnchorWordAbilitySourceEffect(
                new BeginningOfEndStepTriggeredAbility(new AddCountersAllEffect(
                        CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE
                )), ModeChoice.ABZAN
        )));

        // * Mardu -- At the beginning of your end step, if a creature died under your control this turn, each opponent sacrifices a creature of their choice.
        this.addAbility(new SimpleStaticAbility(new GainAnchorWordAbilitySourceEffect(
                new BeginningOfEndStepTriggeredAbility(
                        new SacrificeOpponentsEffect(StaticFilters.FILTER_PERMANENT_CREATURE)
                ).withInterveningIf(CreatureDiedControlledCondition.instance), ModeChoice.MARDU
        )));
    }

    private BarrensteppeSiege(final BarrensteppeSiege card) {
        super(card);
    }

    @Override
    public BarrensteppeSiege copy() {
        return new BarrensteppeSiege(this);
    }
}
