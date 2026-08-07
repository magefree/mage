package mage.cards.t;

import java.util.UUID;

import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author muz
 */
public final class TrollNegotiations extends CardImpl {

    public TrollNegotiations(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}{G}");

        // Put two +1/+1 counters on target creature you control. Then it fights target creature an opponent controls.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)));
        this.getSpellAbility().addEffect(new FightTargetsEffect().setText(
                "Then it fights target creature an opponent controls. " +
                "<i>(Each deals damage equal to its power to the other.)</i>"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
    }

    private TrollNegotiations(final TrollNegotiations card) {
        super(card);
    }

    @Override
    public TrollNegotiations copy() {
        return new TrollNegotiations(this);
    }
}
