package mage.cards.t;

import mage.abilities.common.SagaAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.SacrificeOpponentsUnlessPayEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheLongReachOfNight extends CardImpl {

    public TheLongReachOfNight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");

        this.subtype.add(SubType.SAGA);
        this.secondSideCardClazz = mage.cards.a.AnimusOfNightsReach.class;

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II — Each opponent sacrifices a creature unless they discard a card.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                new SacrificeOpponentsUnlessPayEffect(
                        new DiscardCardCost(), StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT
                )
        );

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        this.addAbility(new TransformAbility());
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect()
        );

        this.addAbility(sagaAbility);
    }

    private TheLongReachOfNight(final TheLongReachOfNight card) {
        super(card);
    }

    @Override
    public TheLongReachOfNight copy() {
        return new TheLongReachOfNight(this);
    }
}
