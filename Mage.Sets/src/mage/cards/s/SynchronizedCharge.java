package mage.cards.s;

import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.abilities.keyword.HarmonizeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.target.common.TargetCreaturePermanentAmount;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SynchronizedCharge extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    public SynchronizedCharge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Distribute two +1/+1 counters among one or two target creatures you control. Creatures you control with counters on them gain vigilance and trample until end of turn.
        this.getSpellAbility().addEffect(new DistributeCountersEffect(CounterType.P1P1));
        this.getSpellAbility().addTarget(new TargetCreaturePermanentAmount(
                2, 1, 2,
                StaticFilters.FILTER_CONTROLLED_CREATURES
        ));
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn, filter
        ).setText("creatures you control with counters on them gain vigilance"));
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn, filter
        ).setText("and trample until end of turn"));

        // Harmonize {4}{G}
        this.addAbility(new HarmonizeAbility(this, "{4}{G}"));
    }

    private SynchronizedCharge(final SynchronizedCharge card) {
        super(card);
    }

    @Override
    public SynchronizedCharge copy() {
        return new SynchronizedCharge(this);
    }
}
