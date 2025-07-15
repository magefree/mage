package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.CanBlockOnlyFlyingAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class DroneToken2 extends TokenImpl {

    public DroneToken2() {
        super("Drone Token", "1/1 colorless Drone artifact creature token with flying and \"This token can block only creatures with flying.\"");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.DRONE);
        power = new MageInt(1);
        toughness = new MageInt(1);

        addAbility(FlyingAbility.getInstance());
        addAbility(new CanBlockOnlyFlyingAbility());
    }

    private DroneToken2(final DroneToken2 token) {
        super(token);
    }

    @Override
    public DroneToken2 copy() {
        return new DroneToken2(this);
    }
}
