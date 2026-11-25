package mage.cards.u;

import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.keyword.DiscoverEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author muz
 */
public final class USSDiscoveryAtBlackAlert extends CardImpl {

    public USSDiscoveryAtBlackAlert(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPACECRAFT);

        // Whenever U.S.S. Discovery enters or attacks, discover 5.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new DiscoverEffect(5)));

        // Station
        this.addAbility(new StationAbility());

        // STATION 6+
        // Flying
        // 5/5
        this.addAbility(new StationLevelAbility(6).withLevelAbility(FlyingAbility.getInstance()).withPT(5, 5));
    }

    private USSDiscoveryAtBlackAlert(final USSDiscoveryAtBlackAlert card) {
        super(card);
    }

    @Override
    public USSDiscoveryAtBlackAlert copy() {
        return new USSDiscoveryAtBlackAlert(this);
    }
}
