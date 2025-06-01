package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author padfoothelix
 */


public final class TheCurseOfFenricMutantToken extends TokenImpl {

    public TheCurseOfFenricMutantToken() {
        super("Mutant Token", "3/3 green mutant creature token with deathtouch");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.MUTANT);
        color.setGreen(true);
        power = new MageInt(3);
        toughness = new MageInt(3);

        addAbility(DeathtouchAbility.getInstance());
    }

    private TheCurseOfFenricMutantToken(final TheCurseOfFenricMutantToken token) {
        super(token);
    }

    public TheCurseOfFenricMutantToken copy() {
        return new TheCurseOfFenricMutantToken(this);
    }
}

