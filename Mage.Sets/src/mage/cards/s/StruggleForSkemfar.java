package mage.cards.s;

import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StruggleForSkemfar extends CardImpl {

    public StruggleForSkemfar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Put a +1/+1 counter on target creature you control. Then that creature fights up to one target creature you don't control.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addEffect(new FightTargetsEffect().setText(
                "Then that creature fights up to one target creature you don't control. " +
                        "<i>(Each deals damage equal to its power to the other.)</i>"
        ));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(
                0, 1, StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL, false
        ));

        // Foretell {G}
        this.addAbility(new ForetellAbility(this, "{G}"));
    }

    private StruggleForSkemfar(final StruggleForSkemfar card) {
        super(card);
    }

    @Override
    public StruggleForSkemfar copy() {
        return new StruggleForSkemfar(this);
    }
}
