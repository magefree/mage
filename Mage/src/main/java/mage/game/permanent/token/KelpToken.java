package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class KelpToken extends TokenImpl {

    public KelpToken() {
        super("Kelp", "0/1 blue Plant Wall creature token with defender named Kelp");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.PLANT);
        subtype.add(SubType.WALL);
        power = new MageInt(0);
        toughness = new MageInt(1);

        this.addAbility(DefenderAbility.getInstance());
    }

    public KelpToken(final KelpToken token) {
        super(token);
    }

    public KelpToken copy() {
        return new KelpToken(this);
    }
}
