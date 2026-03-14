package mage.cards.e;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EraOfEnlightenment extends TransformingDoubleFacedCard {

    public EraOfEnlightenment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{}, new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.SAGA}, "{1}{W}",
                "Hand of Enlightenment",
                new SuperType[]{}, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.MONK}, "W"
        );

        // Era of Enlightenment
        SagaAbility sagaAbility = new SagaAbility(this.getLeftHalfCard());

        // I — Scry 2.
        sagaAbility.addChapterEffect(this.getLeftHalfCard(), SagaChapter.CHAPTER_I, new ScryEffect(2));

        // II — You gain 2 life.
        sagaAbility.addChapterEffect(this.getLeftHalfCard(), SagaChapter.CHAPTER_II, new GainLifeEffect(2));

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        sagaAbility.addChapterEffect(this.getLeftHalfCard(), SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());
        this.getLeftHalfCard().addAbility(sagaAbility);

        // Hand of Enlightenment
        this.getRightHalfCard().setPT(2, 2);

        // First strike
        this.getRightHalfCard().addAbility(FirstStrikeAbility.getInstance());
    }

    private EraOfEnlightenment(final EraOfEnlightenment card) {
        super(card);
    }

    @Override
    public EraOfEnlightenment copy() {
        return new EraOfEnlightenment(this);
    }
}
