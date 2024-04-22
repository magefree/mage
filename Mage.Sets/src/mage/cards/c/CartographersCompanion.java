package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.MapToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CartographersCompanion extends CardImpl {

    public CartographersCompanion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.GNOME);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Cartographer's Companion enters the battlefield, create a Map token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new MapToken())));
    }

    private CartographersCompanion(final CartographersCompanion card) {
        super(card);
    }

    @Override
    public CartographersCompanion copy() {
        return new CartographersCompanion(this);
    }
}
