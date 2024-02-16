package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.RatCantBlockToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EdgewallPack extends CardImpl {

    public EdgewallPack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.DOG);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // When Edgewall Pack enters the battlefield, create a 1/1 black Rat creature token with "This creature can't block."
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new RatCantBlockToken())));
    }

    private EdgewallPack(final EdgewallPack card) {
        super(card);
    }

    @Override
    public EdgewallPack copy() {
        return new EdgewallPack(this);
    }
}
