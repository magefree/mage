package mage.cards.u;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.OfficerToken;

import java.util.UUID;

/**
 * @author muz
 */
public final class USSCerritosFollowingUp extends CardImpl {

    public USSCerritosFollowingUp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPACECRAFT);

        // When U.S.S. Cerritos enters, create two 1/1 red Officer creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new OfficerToken(), 2)));

        // Station
        this.addAbility(new StationAbility());

        // STATION 6+
        // Flying
        // 4/4
        this.addAbility(new StationLevelAbility(6).withLevelAbility(FlyingAbility.getInstance()).withPT(4, 4));
    }

    private USSCerritosFollowingUp(final USSCerritosFollowingUp card) {
        super(card);
    }

    @Override
    public USSCerritosFollowingUp copy() {
        return new USSCerritosFollowingUp(this);
    }
}
