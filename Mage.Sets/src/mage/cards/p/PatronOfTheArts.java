package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrDiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PatronOfTheArts extends CardImpl {

    public PatronOfTheArts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When Patron of the Arts enters the battlefield or dies, create a Treasure token.
        this.addAbility(new EntersBattlefieldOrDiesSourceTriggeredAbility(new CreateTokenEffect(new TreasureToken()), false));
    }

    private PatronOfTheArts(final PatronOfTheArts card) {
        super(card);
    }

    @Override
    public PatronOfTheArts copy() {
        return new PatronOfTheArts(this);
    }
}
