package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/*
* @author muz
*/
public final class CadetToken extends TokenImpl {

    public CadetToken() {
        super("Cadet", "2/2 colorless Wizard Soldier creature token named Cadet");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.WIZARD);
        subtype.add(SubType.SOLDIER);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    private CadetToken(final CadetToken token) {
        super(token);
    }

    @Override
    public CadetToken copy() {
        return new CadetToken(this);
    }
}
