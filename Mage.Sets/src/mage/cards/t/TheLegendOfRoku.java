package mage.cards.t;

import mage.abilities.common.SagaAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.keyword.FirebendingAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.game.permanent.token.DragonFirebendingToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheLegendOfRoku extends TransformingDoubleFacedCard {

    public TheLegendOfRoku(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{}, new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.SAGA}, "{2}{R}{R}",
                "Avatar Roku",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.AVATAR}, ""
        );

        // The Legend of Roku
        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this.getLeftHalfCard());

        // I -- Exile the top three cards of your library. Until the end of your next turn, you may play those cards.
        sagaAbility.addChapterEffect(
                this.getLeftHalfCard(), SagaChapter.CHAPTER_I,
                new ExileTopXMayPlayUntilEffect(3, Duration.UntilEndOfYourNextTurn)
        );

        // II -- Add one mana of any color.
        sagaAbility.addChapterEffect(this.getLeftHalfCard(), SagaChapter.CHAPTER_II, new AddManaOfAnyColorEffect(1));

        // III -- Exile this Saga, then return it to the battlefield transformed under your control.
        sagaAbility.addChapterEffect(this.getLeftHalfCard(), SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());
        this.getLeftHalfCard().addAbility(sagaAbility);

        // Avatar Roku
        this.getRightHalfCard().setPT(4, 4);

        // Firebending 4
        this.getRightHalfCard().addAbility(new FirebendingAbility(4));

        // {8}: Create a 4/4 red Dragon creature token with flying and firebending 4.
        this.getRightHalfCard().addAbility(new SimpleActivatedAbility(
                new CreateTokenEffect(new DragonFirebendingToken()), new GenericManaCost(8)
        ));
    }

    private TheLegendOfRoku(final TheLegendOfRoku card) {
        super(card);
    }

    @Override
    public TheLegendOfRoku copy() {
        return new TheLegendOfRoku(this);
    }
}
