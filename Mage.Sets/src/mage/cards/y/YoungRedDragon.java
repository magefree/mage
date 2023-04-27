package mage.cards.y;

import mage.MageInt;
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
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{3}{R}", "Bathe in Gold", "{1}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Young Red Dragon can't block.
        this.addAbility(new CantBlockAbility());

        // Bathe in Gold
        // Create a Treasure token.
        this.getSpellCard().getSpellAbility().addEffect(new CreateTokenEffect(new TreasureToken()));
    }

    private YoungRedDragon(final YoungRedDragon card) {
        super(card);
    }

    @Override
    public YoungRedDragon copy() {
        return new YoungRedDragon(this);
    }
}
