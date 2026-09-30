package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HexproofAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/*
* @author muz
*/
public final class Leviathan88Token extends TokenImpl {

    public Leviathan88Token() {
        super("Leviathan Token", "8/8 blue Leviathan creature token with hexproof");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.LEVIATHAN);
        power = new MageInt(8);
        toughness = new MageInt(8);

        addAbility(HexproofAbility.getInstance());
    }

    private Leviathan88Token(final Leviathan88Token token) {
        super(token);
    }

    public Leviathan88Token copy() {
        return new Leviathan88Token(this);
    }
}
