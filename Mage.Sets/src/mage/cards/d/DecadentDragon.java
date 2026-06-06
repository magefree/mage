package mage.cards.d;

import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileFaceDownTopNLibraryYouMayPlayAsLongAsExiledTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CastManaAdjustment;
import mage.constants.SubType;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class DecadentDragon extends AdventureCard {

    public DecadentDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.DRAGON}, "{2}{R}{R}",
                "Expensive Taste",
                new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Decadent Dragon
        this.getLeftHalfCard().setPT(4, 4);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Trample
        this.getLeftHalfCard().addAbility(TrampleAbility.getInstance());

        // Whenever Decadent Dragon attacks, create a Treasure token.
        this.getLeftHalfCard().addAbility(new AttacksTriggeredAbility(new CreateTokenEffect(new TreasureToken())));

        // Expensive Taste
        // Exile the top two cards of target opponent's library face down. You may look at and play those cards for as long as they remain exiled.
        this.getRightHalfCard().getSpellAbility().addEffect(
                new ExileFaceDownTopNLibraryYouMayPlayAsLongAsExiledTargetEffect(false, CastManaAdjustment.NONE, 2)
                        .setText("Exile the top two cards of target opponent's library face down. "
                                + "You may look at and play those cards for as long as they remain exiled.")
        );
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetOpponent());

        finalizeCard();
    }

    private DecadentDragon(final DecadentDragon card) {
        super(card);
    }

    @Override
    public DecadentDragon copy() {
        return new DecadentDragon(this);
    }
}