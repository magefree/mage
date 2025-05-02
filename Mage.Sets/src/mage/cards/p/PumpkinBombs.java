package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TargetPlayerGainControlSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PumpkinBombs extends CardImpl {

    private static final DynamicValue xValue = new CountersSourceCount(CounterType.FUSE);

    public PumpkinBombs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}");

        // {T}, Discard two cards: Draw three cards, then put a fuse counter on this artifact. It deals damage equal to the number of fuse counters on it to target opponent. They gain control of this artifact.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(3), new TapSourceCost());
        ability.addCost(new DiscardTargetCost(new TargetCardInHand(2, StaticFilters.FILTER_CARD_CARDS)));
        ability.addEffect(new AddCountersSourceEffect(CounterType.FUSE.createInstance()).concatBy(", then"));
        ability.addEffect(new DamageTargetEffect(xValue)
                .setText("it deals damage equal to the number of fuse counters on it to target opponent"));
        ability.addEffect(new TargetPlayerGainControlSourceEffect("they"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private PumpkinBombs(final PumpkinBombs card) {
        super(card);
    }

    @Override
    public PumpkinBombs copy() {
        return new PumpkinBombs(this);
    }
}
