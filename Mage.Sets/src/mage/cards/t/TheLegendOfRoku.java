package mage.cards.t;

import mage.Mana;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheLegendOfRoku extends CardImpl {

    public TheLegendOfRoku(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{R}");

        this.subtype.add(SubType.SAGA);
        this.secondSideCardClazz = mage.cards.a.AvatarRoku.class;

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Exile the top three cards of your library. Until the end of your next turn, you may play those cards.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I,
                new ExileTopXMayPlayUntilEffect(3, Duration.UntilEndOfYourNextTurn)
        );

        // II -- Add one mana of any color.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new BasicManaEffect(Mana.AnyMana(1)));

        // III -- Exile this Saga, then return it to the battlefield transformed under your control.
        this.addAbility(new TransformAbility());
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());
        this.addAbility(sagaAbility);
    }

    private TheLegendOfRoku(final TheLegendOfRoku card) {
        super(card);
    }

    @Override
    public TheLegendOfRoku copy() {
        return new TheLegendOfRoku(this);
    }
}
