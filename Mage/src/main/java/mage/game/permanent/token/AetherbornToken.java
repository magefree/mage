package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author grimreap124
 */
public final class AetherbornToken extends TokenImpl {

    public AetherbornToken() {
        this(0, 0);
    }

    public AetherbornToken(int power, int toughness) {
        super("Aetherborn Token",
                "X/X black Aetherborn creature token, where X is the amount of {E} paid this way");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.AETHERBORN);
        color.setBlack(true);
        this.power = new MageInt(power);
        this.toughness = new MageInt(toughness);
    }

    private AetherbornToken(final AetherbornToken token) {
        super(token);
    }

    public AetherbornToken copy() {
        return new AetherbornToken(this);
    }
}
