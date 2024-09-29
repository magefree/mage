package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.GnomeToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class OltecCloudGuard extends CardImpl {

    public OltecCloudGuard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Oltec Cloud Guard enters the battlefield, create a 1/1 colorless Gnome artifact creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new GnomeToken())));
    }

    private OltecCloudGuard(final OltecCloudGuard card) {
        super(card);
    }

    @Override
    public OltecCloudGuard copy() {
        return new OltecCloudGuard(this);
    }
}
