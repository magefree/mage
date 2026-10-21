package mage.cards.u;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.abilities.keyword.WardAbility;
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
public final class USSEnterpriseAFreeSpirit extends CardImpl {

    public USSEnterpriseAFreeSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPACECRAFT);

        // Ward {2}
        this.addAbility(new WardAbility(new GenericManaCost(2)));

        // When U.S.S. Enterprise-A enters, create three 1/1 red Officer creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new OfficerToken(), 3)));

        // Station
        this.addAbility(new StationAbility());

        // STATION 6+
        // Flying, lifelink
        // 5/5
        this.addAbility(new StationLevelAbility(6)
            .withLevelAbility(FlyingAbility.getInstance())
            .withLevelAbility(LifelinkAbility.getInstance())
            .withPT(5, 5));
    }

    private USSEnterpriseAFreeSpirit(final USSEnterpriseAFreeSpirit card) {
        super(card);
    }

    @Override
    public USSEnterpriseAFreeSpirit copy() {
        return new USSEnterpriseAFreeSpirit(this);
    }
}
