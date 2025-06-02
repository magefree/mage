package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.CrewAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class VehicleToken extends TokenImpl {

    public VehicleToken() {
        super("Vehicle Token", "3/2 colorless Vehicle artifact token with crew 1");
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.VEHICLE);
        power = new MageInt(3);
        toughness = new MageInt(2);

        this.addAbility(new CrewAbility(1));
    }

    private VehicleToken(final VehicleToken token) {
        super(token);
    }

    public VehicleToken copy() {
        return new VehicleToken(this);
    }
}
