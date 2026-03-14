package mage.cards.l;

import mage.abilities.common.SagaAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.FoodToken;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LeavesFromTheVine extends CardImpl {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.LESSON.getPredicate()
        ));
    }

    private static final Condition condition = new CardsInControllerGraveyardCondition(1, filter);
    private static final Hint hint = new ConditionHint(
            condition, "There's a creature or Lesson card in your graveyard"
    );

    public LeavesFromTheVine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Mill three cards, then create a Food token.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I,
                new MillCardsControllerEffect(3),
                new CreateTokenEffect(new FoodToken()).concatBy(", then")
        );

        // II -- Put a +1/+1 counter on each of up to two target creatures you control.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II,
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                new TargetControlledCreaturePermanent(0, 2)
        );

        // III -- Draw a card if there's a creature or Lesson card in your graveyard.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III,
                new ConditionalOneShotEffect(
                        new DrawCardSourceControllerEffect(1), condition,
                        "draw a card if there's a creature or Lesson card in your graveyard"
                )
        );
        this.addAbility(sagaAbility);
    }

    private LeavesFromTheVine(final LeavesFromTheVine card) {
        super(card);
    }

    @Override
    public LeavesFromTheVine copy() {
        return new LeavesFromTheVine(this);
    }
}
