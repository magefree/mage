package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.KnightWhiteBlueToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KnightOfTheNewCoalition extends CardImpl {

    public KnightOfTheNewCoalition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Knight of the New Coalition enters the battlefield, create a 2/2 white and blue Knight creature token with vigilance.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new KnightWhiteBlueToken())));
    }

    private KnightOfTheNewCoalition(final KnightOfTheNewCoalition card) {
        super(card);
    }

    @Override
    public KnightOfTheNewCoalition copy() {
        return new KnightOfTheNewCoalition(this);
    }
}
