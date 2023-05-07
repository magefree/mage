package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class LeafdrakeRoostDrakeToken extends TokenImpl {

    public LeafdrakeRoostDrakeToken() {
        super("Drake Token", "2/2 green and blue Drake creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        color.setBlue(true);
        subtype.add(SubType.DRAKE);
        power = new MageInt(2);
        toughness = new MageInt(2);
        this.addAbility(FlyingAbility.getInstance());
    }

    public LeafdrakeRoostDrakeToken(final LeafdrakeRoostDrakeToken token) {
        super(token);
    }

    public LeafdrakeRoostDrakeToken copy() {
        return new LeafdrakeRoostDrakeToken(this);
    }
}
