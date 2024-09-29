package mage.cards.c;

import mage.MageInt;
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
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{1}{G}", "Treats to Share", "{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Treats to Share
        // Create a Food token.
        this.getSpellCard().getSpellAbility().addEffect(new CreateTokenEffect(new FoodToken()));

        this.finalizeAdventure();
    }

    private CuriousPair(final CuriousPair card) {
        super(card);
    }

    @Override
    public CuriousPair copy() {
        return new CuriousPair(this);
    }
}
