package mage.cards.v;

import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VenomBlast extends CardImpl {

    public VenomBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}{G}");

        // Put two +1/+1 counters on target creature you control. It deals damage equal to its power to up to one other target creature.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)));
        this.getSpellAbility().addEffect(new DamageWithPowerFromOneToAnotherTargetEffect()
                .setText("It deals damage equal to its power to up to one other target creature"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent().setTargetTag(1));
        this.getSpellAbility().addTarget(new TargetPermanent(
                0, 1, StaticFilters.FILTER_ANOTHER_CREATURE_TARGET_2
        ).setTargetTag(2));
    }

    private VenomBlast(final VenomBlast card) {
        super(card);
    }

    @Override
    public VenomBlast copy() {
        return new VenomBlast(this);
    }
}
