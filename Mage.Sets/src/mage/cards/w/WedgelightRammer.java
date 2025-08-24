package mage.cards.w;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.RobotToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WedgelightRammer extends CardImpl {

    public WedgelightRammer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{W}");

        this.subtype.add(SubType.SPACECRAFT);

        // When this Spacecraft enters, create a 2/2 colorless Robot artifact creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new RobotToken())));

        // Station
        this.addAbility(new StationAbility());

        // STATION 9+
        // Flying
        // First strike
        // 3/4
        this.addAbility(new StationLevelAbility(9)
                .withLevelAbility(FlyingAbility.getInstance())
                .withLevelAbility(FirstStrikeAbility.getInstance())
                .withPT(3, 4));
    }

    private WedgelightRammer(final WedgelightRammer card) {
        super(card);
    }

    @Override
    public WedgelightRammer copy() {
        return new WedgelightRammer(this);
    }
}
