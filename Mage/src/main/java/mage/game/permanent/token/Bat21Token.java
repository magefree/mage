package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class Bat21Token extends TokenImpl {

    public Bat21Token() {
        super("Bat Token", "2/1 black Bat creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.BAT);
        power = new MageInt(2);
        toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
    }

    private Bat21Token(final Bat21Token token) {
        super(token);
    }

    public Bat21Token copy() {
        return new Bat21Token(this);
    }
}
