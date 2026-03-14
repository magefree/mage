package mage.cards.t;

import mage.abilities.common.SagaAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.WaterbendCost;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.ExhaustAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.SpiritWorldToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheLegendOfKuruk extends TransformingDoubleFacedCard {

    public TheLegendOfKuruk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{}, new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.SAGA}, "{2}{U}{U}",
                "Avatar Kuruk",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.AVATAR}, ""
        );

        // The Legend of Kuruk
        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this.getLeftHalfCard());

        // I, II -- Scry 2, then draw a card.
        sagaAbility.addChapterEffect(
                this.getLeftHalfCard(), SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                new Effects(
                        new ScryEffect(2, false),
                        new DrawCardSourceControllerEffect(1).concatBy(", then")
                )
        );

        // III -- Exile this Saga, then return it to the battlefield transformed under your control.
        sagaAbility.addChapterEffect(this.getLeftHalfCard(), SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());
        this.getLeftHalfCard().addAbility(sagaAbility);

        // Avatar Kuruk
        this.getRightHalfCard().setPT(4, 3);

        // Whenever you cast a spell, create a 1/1 colorless Spirit creature token with "This token can't block or be blocked by non-Spirit creatures."
        this.getRightHalfCard().addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new SpiritWorldToken()), StaticFilters.FILTER_SPELL_A, false
        ));

        // Exhaust -- Waterbend {20}: Take an extra turn after this one.
        this.getRightHalfCard().addAbility(new ExhaustAbility(new AddExtraTurnControllerEffect(), new WaterbendCost(20)));
    }

    private TheLegendOfKuruk(final TheLegendOfKuruk card) {
        super(card);
    }

    @Override
    public TheLegendOfKuruk copy() {
        return new TheLegendOfKuruk(this);
    }
}
