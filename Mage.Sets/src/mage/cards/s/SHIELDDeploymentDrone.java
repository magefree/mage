package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.game.permanent.token.SoldierToken;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class SHIELDDeploymentDrone extends CardImpl {

    public SHIELDDeploymentDrone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, create a 1/1 white Soldier creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new SoldierToken())));
    }

    private SHIELDDeploymentDrone(final SHIELDDeploymentDrone card) {
        super(card);
    }

    @Override
    public SHIELDDeploymentDrone copy() {
        return new SHIELDDeploymentDrone(this);
    }
}
