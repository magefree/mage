package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author padfoothelix
 */


public final class Mutant33DeathtouchToken extends TokenImpl {

    public Mutant33DeathtouchToken() {
        super("Mutant Token", "3/3 green mutant creature token with deathtouch");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.MUTANT);
        color.setGreen(true);
        power = new MageInt(3);
        toughness = new MageInt(3);

        addAbility(DeathtouchAbility.getInstance());
    }

    private Mutant33DeathtouchToken(final Mutant33DeathtouchToken token) {
        super(token);
    }

    public Mutant33DeathtouchToken copy() {
        return new Mutant33DeathtouchToken(this);
    }
}

