package mage.cards.a;

import java.util.UUID;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.RobotVillainToken;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.ForEachPlayerTargetsAdjuster;
import mage.abilities.common.SagaAbility;
import mage.abilities.dynamicvalue.common.OpponentsCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;

/**
 *
 * @author muz
 */
public final class AgeOfUltron extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonartifact creature");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("artifact creatures you control");

    static {
        filter.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
        filter2.add(CardType.ARTIFACT.getPredicate());
        filter2.add(TargetController.YOU.getControllerPredicate());
    }

    public AgeOfUltron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{B}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- For each opponent, destroy up to one target nonartifact creature that player controls.
        sagaAbility.addChapterEffect(
            this, SagaChapter.CHAPTER_I,
            ability -> {
                ability.addEffect(new DestroyTargetEffect()
                    .setText("For each opponent, destroy up to one target nonartifact creature that player controls"));
                ability.addTarget(new TargetPermanent(0, 1, filter));
                ability.setTargetAdjuster(new ForEachPlayerTargetsAdjuster(false, true));
            }
        );

        // II -- For each opponent, you create a 2/2 colorless Robot Villain artifact creature token.
        sagaAbility.addChapterEffect(
            this, SagaChapter.CHAPTER_II,
            ability -> {
                ability.addEffect(new CreateTokenEffect(new RobotVillainToken(), OpponentsCount.instance)
                    .setText("For each opponent, you create a 2/2 colorless Robot Villain artifact creature token"));
            }
        );

        // III -- Artifact creatures you control gain deathtouch until end of turn. Put a +1/+1 counter on each of them.
        sagaAbility.addChapterEffect(
            this, SagaChapter.CHAPTER_III,
            ability -> {
                ability.addEffect(new GainAbilityControlledEffect(
                    DeathtouchAbility.getInstance(), Duration.EndOfTurn,
                    StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE
                ));
                ability.addEffect(new AddCountersAllEffect(
                    CounterType.P1P1.createInstance(), filter2
                ).setText("Put a +1/+1 counter on each of them"));
            }
        );

        this.addAbility(sagaAbility);
    }

    private AgeOfUltron(final AgeOfUltron card) {
        super(card);
    }

    @Override
    public AgeOfUltron copy() {
        return new AgeOfUltron(this);
    }
}
