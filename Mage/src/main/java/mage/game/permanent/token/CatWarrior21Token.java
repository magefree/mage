package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Susucr
 */
public final class CatWarrior21Token extends TokenImpl {

    public CatWarrior21Token() {
        super("Cat Warrior Token", "2/1 white Cat Warrior creature token");

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.color.setWhite(true);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WARRIOR);
        this.cardType.add(CardType.CREATURE);
    }

    private CatWarrior21Token(final CatWarrior21Token token) {
        super(token);
    }

    public CatWarrior21Token copy() {
        return new CatWarrior21Token(this);
    }
}
