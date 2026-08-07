package mage.cards.t;

import java.util.UUID;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.keyword.RecruitEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SagaChapter;

/**
 *
 * @author muz
 */
public final class TheMountainKingsReturn extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature card with mana value 3 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public TheMountainKingsReturn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Recruit.
        sagaAbility.addChapterEffect(
            this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_I,
            new RecruitEffect()
        );

        // II -- Return target creature card with mana value 3 or less from your graveyard to the battlefield.
        sagaAbility.addChapterEffect(
            this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_II,
            new ReturnFromGraveyardToBattlefieldTargetEffect(), new TargetCardInYourGraveyard(filter)
        );

        // III -- Put a +1/+1 counter on up to one target creature.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_III,
            new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
            new TargetCreaturePermanent(0, 1)
        );

        this.addAbility(sagaAbility);
    }

    private TheMountainKingsReturn(final TheMountainKingsReturn card) {
        super(card);
    }

    @Override
    public TheMountainKingsReturn copy() {
        return new TheMountainKingsReturn(this);
    }
}
