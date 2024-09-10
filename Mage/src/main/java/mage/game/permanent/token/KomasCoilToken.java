package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

public final class KomasCoilToken extends TokenImpl {

    public KomasCoilToken() {
        super("Koma's Coil", "3/3 blue Serpent creature token named Koma's Coil");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SERPENT);
        color.setBlue(true);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }

    private KomasCoilToken(final KomasCoilToken token) {
        super(token);
    }

    @Override
    public KomasCoilToken copy() {
        return new KomasCoilToken(this);
    }
}
