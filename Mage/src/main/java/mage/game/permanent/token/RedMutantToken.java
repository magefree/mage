package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author muz
 */
public final class RedMutantToken extends TokenImpl {

    public RedMutantToken() {
        super("Mutant Token", "2/2 red Mutant creature token");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.MUTANT);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    private RedMutantToken(final RedMutantToken token) {
        super(token);
    }

    @Override
    public RedMutantToken copy() {
        return new RedMutantToken(this);
    }
}
