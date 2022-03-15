package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

public final class OozeToken extends TokenImpl {

    public OozeToken(int power, int toughness) {
        super("Ooze Token", power + "/" + toughness + " green ooze creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.OOZE);
        this.power = new MageInt(power);
        this.toughness = new MageInt(toughness);
    }

    public OozeToken() {
        super("Ooze Token", "X/X green ooze creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.OOZE);
        power = new MageInt(0);
        toughness = new MageInt(0);
    }

    public OozeToken(final OozeToken token) {
        super(token);
    }

    public OozeToken copy() {
        return new OozeToken(this);
    }
}
