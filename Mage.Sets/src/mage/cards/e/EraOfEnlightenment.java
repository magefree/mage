package mage.cards.e;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EraOfEnlightenment extends CardImpl {

    public EraOfEnlightenment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        this.subtype.add(SubType.SAGA);
        this.secondSideCardClazz = mage.cards.h.HandOfEnlightenment.class;

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I — Scry 2.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new ScryEffect(2));

        // II — You gain 2 life.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new GainLifeEffect(2));

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        this.addAbility(new TransformAbility());
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());

        this.addAbility(sagaAbility);
    }

    private EraOfEnlightenment(final EraOfEnlightenment card) {
        super(card);
    }

    @Override
    public EraOfEnlightenment copy() {
        return new EraOfEnlightenment(this);
    }
}
