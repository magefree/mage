package mage.cards.m;

import java.util.UUID;

import mage.abilities.condition.common.CastFromGraveyardSourceCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author muz
 */
public final class MomentOfGlory extends CardImpl {

    public MomentOfGlory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}");

        // Put a +1/+1 counter on target creature you control. If this spell was cast from a graveyard, also put a +1/+1 counter on each other creature you control.
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
            new AddCountersAllEffect(CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE),
            new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
            CastFromGraveyardSourceCondition.instance,
            "Put a +1/+1 counter on target creature you control. If this spell was cast from a graveyard, also put a +1/+1 counter on each other creature you control."
        ));

        // Flashback {4}{W}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{4}{W}")));
    }

    private MomentOfGlory(final MomentOfGlory card) {
        super(card);
    }

    @Override
    public MomentOfGlory copy() {
        return new MomentOfGlory(this);
    }
}
