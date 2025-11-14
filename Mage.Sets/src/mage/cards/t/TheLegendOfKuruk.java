package mage.cards.t;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
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
public final class TheLegendOfKuruk extends CardImpl {

    public TheLegendOfKuruk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}");

        this.subtype.add(SubType.SAGA);
        this.secondSideCardClazz = mage.cards.a.AvatarKuruk.class;

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II -- Scry 2, then draw a card.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                new Effects(
                        new ScryEffect(2, false),
                        new DrawCardSourceControllerEffect(1).concatBy(", then")
                )
        );

        // III -- Exile this Saga, then return it to the battlefield transformed under your control.
        this.addAbility(new TransformAbility());
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());
        this.addAbility(sagaAbility);
    }

    private TheLegendOfKuruk(final TheLegendOfKuruk card) {
        super(card);
    }

    @Override
    public TheLegendOfKuruk copy() {
        return new TheLegendOfKuruk(this);
    }
}
