package mage.cards.c;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.FoodToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CuriousPair extends AdventureCard {

    public CuriousPair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.PEASANT}, "{1}{G}",
                "Treats to Share",
                new CardType[]{CardType.SORCERY}, "{G}");

        // Curious Pair
        this.getLeftHalfCard().setPT(1, 3);

        // Treats to Share
        // Create a Food token.
        this.getRightHalfCard().getSpellAbility().addEffect(new CreateTokenEffect(new FoodToken()));

        finalizeCard();
    }

    private CuriousPair(final CuriousPair card) {
        super(card);
    }

    @Override
    public CuriousPair copy() {
        return new CuriousPair(this);
    }
}
