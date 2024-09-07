package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.RabbitToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HeadOfTheHomestead extends CardImpl {

    public HeadOfTheHomestead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G/W}{G/W}");

        this.subtype.add(SubType.RABBIT);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Head of the Homestead enters, create two 1/1 white Rabbit creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new RabbitToken(), 2)));
    }

    private HeadOfTheHomestead(final HeadOfTheHomestead card) {
        super(card);
    }

    @Override
    public HeadOfTheHomestead copy() {
        return new HeadOfTheHomestead(this);
    }
}
