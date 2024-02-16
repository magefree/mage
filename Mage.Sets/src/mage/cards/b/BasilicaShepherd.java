package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.PhyrexianMiteToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BasilicaShepherd extends CardImpl {

    public BasilicaShepherd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Basilica Shepherd enters the battlefield, create two 1/1 colorless Phyrexian Mite artifact creature tokens with toxic 1 and "This creature can't block."
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new PhyrexianMiteToken(), 2)));
    }

    private BasilicaShepherd(final BasilicaShepherd card) {
        super(card);
    }

    @Override
    public BasilicaShepherd copy() {
        return new BasilicaShepherd(this);
    }
}
