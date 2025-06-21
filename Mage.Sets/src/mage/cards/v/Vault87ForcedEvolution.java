package mage.cards.v;

import mage.abilities.common.SagaAbility;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

import static mage.constants.Duration.WhileControlled;
import static mage.constants.SagaChapter.CHAPTER_I;

/**
 * @author Cguy7777
 */
public final class Vault87ForcedEvolution extends CardImpl {

    private static final FilterCreaturePermanent filterNonMutant = new FilterCreaturePermanent("non-Mutant creature");

    static {
        filterNonMutant.add(Predicates.not(SubType.MUTANT.getPredicate()));
    }

    private static final FilterControlledPermanent filterMutant = new FilterControlledPermanent("Mutants you control");

    static {
        filterMutant.add(SubType.MUTANT.getPredicate());
    }

    private static final GreatestAmongPermanentsValue xValue = new GreatestAmongPermanentsValue(GreatestAmongPermanentsValue.Quality.Power, filterMutant);
    private static final Hint hint = xValue.getHint();

    public Vault87ForcedEvolution(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}{U}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Gain control of target non-Mutant creature for as long as you control Vault 87.
        sagaAbility.addChapterEffect(
                this,
                CHAPTER_I,
                new GainControlTargetEffect(WhileControlled),
                new TargetPermanent(filterNonMutant));

        // II -- Put a +1/+1 counter on target creature you control. It becomes a Mutant in addition to its other types.
        sagaAbility.addChapterEffect(
                this,
                SagaChapter.CHAPTER_II,
                new Effects(
                        new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                        new AddCardSubTypeTargetEffect(SubType.MUTANT, Duration.Custom)
                                .setText("It becomes a Mutant in addition to its other types")),
                new TargetControlledCreaturePermanent());

        // III -- Draw cards equal to the greatest power among Mutants you control.
        sagaAbility.addChapterEffect(
                this,
                SagaChapter.CHAPTER_III,
                new DrawCardSourceControllerEffect(xValue)
                        .setText("draw cards equal to the greatest power among Mutants you control"));
        this.addAbility(sagaAbility.addHint(hint));
    }

    private Vault87ForcedEvolution(final Vault87ForcedEvolution card) {
        super(card);
    }

    @Override
    public Vault87ForcedEvolution copy() {
        return new Vault87ForcedEvolution(this);
    }
}
