
package mage.cards.t;

import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.AssistAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.XTargetsCountAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheCrowdGoesWild extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("each creature with a +1/+1 counter on it");

    static {
        filter.add(CounterType.P1P1.getPredicate());
    }

    public TheCrowdGoesWild(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{G}");

        // Assist
        this.addAbility(new AssistAbility());

        // Support X (Put a +1/+1 counter on each of up to X target creatures.)
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                .setText("Support X <i>(Put a +1/+1 counter on each of up to X target creatures.)</i><br>")
        );
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1));
        this.getSpellAbility().setTargetAdjuster(new XTargetsCountAdjuster());

        // Each creature with a +1/+1 counter on it gains trample until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityAllEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, filter));
    }

    private TheCrowdGoesWild(final TheCrowdGoesWild card) {
        super(card);
    }

    @Override
    public TheCrowdGoesWild copy() {
        return new TheCrowdGoesWild(this);
    }
}
