package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.ExileFaceDownTopNLibraryYouMayPlayAsLongAsExiledTargetEffect;
import mage.abilities.effects.common.CreateTokenEffect;
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
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{2}{R}{R}", "Expensive Taste", "{2}{B}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Decadent Dragon attacks, create a Treasure token.
        this.addAbility(new AttacksTriggeredAbility(new CreateTokenEffect(new TreasureToken())));

        // Expensive Taste
        // Exile the top two cards of target opponent's library face down. You may look at and play those cards for as long as they remain exiled.
        this.getSpellCard().getSpellAbility().addEffect(
                new ExileFaceDownTopNLibraryYouMayPlayAsLongAsExiledTargetEffect(false, CastManaAdjustment.NONE, 2)
                        .setText("Exile the top two cards of target opponent's library face down. "
                                + "You may look at and play those cards for as long as they remain exiled.")
        );
        this.getSpellCard().getSpellAbility().addTarget(new TargetOpponent());

        this.finalizeAdventure();
    }

    private DecadentDragon(final DecadentDragon card) {
        super(card);
    }

    @Override
    public DecadentDragon copy() {
        return new DecadentDragon(this);
    }
}