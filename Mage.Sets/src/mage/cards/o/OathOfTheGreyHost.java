package mage.cards.o;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.game.permanent.token.FoodToken;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OathOfTheGreyHost extends CardImpl {

    public OathOfTheGreyHost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I-- You and target opponent each create a Food token.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_I,
                new Effects(
                        new CreateTokenEffect(new FoodToken()).setText("you"),
                        new CreateTokenTargetEffect(new FoodToken())
                                .setText("and target opponent each create a Food token")
                ), new TargetOpponent()
        );

        // II-- Each opponent loses 3 life. Create a Treasure token.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II,
                new LoseLifeOpponentsEffect(3),
                new CreateTokenEffect(new TreasureToken())
        );

        // III-- Create three tapped 1/1 white Spirit creature tokens with flying.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III,
                new CreateTokenEffect(new SpiritWhiteToken(), 3, true)
        );

        this.addAbility(sagaAbility);
    }

    private OathOfTheGreyHost(final OathOfTheGreyHost card) {
        super(card);
    }

    @Override
    public OathOfTheGreyHost copy() {
        return new OathOfTheGreyHost(this);
    }
}
