package mage.cards.t;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheModernAge extends TransformingDoubleFacedCard {

    public TheModernAge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.SAGA}, "{1}{U}",
                "Vector Glider",
                new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, new SubType[]{SubType.SPIRIT}, "U"
        );

        // The Modern Age
        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this.getLeftHalfCard());

        // I, II — Draw a card, then discard a card.
        sagaAbility.addChapterEffect(
                this.getLeftHalfCard(), SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                new DrawDiscardControllerEffect(1, 1)
        );

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        sagaAbility.addChapterEffect(this.getLeftHalfCard(), SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());

        this.getLeftHalfCard().addAbility(sagaAbility);

        // Vector Glider
        this.getRightHalfCard().setPT(2, 3);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());
    }

    private TheModernAge(final TheModernAge card) {
        super(card);
    }

    @Override
    public TheModernAge copy() {
        return new TheModernAge(this);
    }
}
