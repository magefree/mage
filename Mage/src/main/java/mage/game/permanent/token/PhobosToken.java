package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 * @author TheElk801
 */
public final class PhobosToken extends TokenImpl {

    public PhobosToken() {
        super("Phobos", "Phobos, a legendary 3/2 red Horse creature token");
        this.supertype.add(SuperType.LEGENDARY);
        this.cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.HORSE);
        this.color.setRed(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
    }

    private PhobosToken(final PhobosToken token) {
        super(token);
    }

    @Override
    public PhobosToken copy() {
        return new PhobosToken(this);
    }
}
