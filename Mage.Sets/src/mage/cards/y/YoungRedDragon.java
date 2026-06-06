package mage.cards.y;

import mage.abilities.common.CantBlockAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YoungRedDragon extends AdventureCard {

    public YoungRedDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.DRAGON}, "{3}{R}",
                "Bathe in Gold",
                new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Young Red Dragon
        this.getLeftHalfCard().setPT(3, 2);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Young Red Dragon can't block.
        this.getLeftHalfCard().addAbility(new CantBlockAbility());

        // Bathe in Gold
        // Create a Treasure token.
        this.getRightHalfCard().getSpellAbility().addEffect(new CreateTokenEffect(new TreasureToken()));

        finalizeCard();
    }

    private YoungRedDragon(final YoungRedDragon card) {
        super(card);
    }

    @Override
    public YoungRedDragon copy() {
        return new YoungRedDragon(this);
    }
}
