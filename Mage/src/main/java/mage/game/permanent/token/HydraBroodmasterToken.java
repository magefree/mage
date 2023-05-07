package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class HydraBroodmasterToken extends TokenImpl {

    public HydraBroodmasterToken() {
        this(1, 1);
    }

    public HydraBroodmasterToken(int power, int toughness) {
        super("Hydra Token", "green Hydra creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.HYDRA);
        this.power = new MageInt(power);
        this.toughness = new MageInt(toughness);
    }

    public HydraBroodmasterToken(final HydraBroodmasterToken token) {
        super(token);
    }

    public HydraBroodmasterToken copy() {
        return new HydraBroodmasterToken(this);
    }
}

