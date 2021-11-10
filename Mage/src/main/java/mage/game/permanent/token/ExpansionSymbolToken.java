
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author L_J
 */
public final class ExpansionSymbolToken extends TokenImpl {

    public ExpansionSymbolToken() {
        super("Expansion-Symbol Token", "1/1 colorless Expansion-Symbol creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.EXPANSION_SYMBOL);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public ExpansionSymbolToken(final ExpansionSymbolToken token) {
        super(token);
    }

    public ExpansionSymbolToken copy() {
        return new ExpansionSymbolToken(this);
    }
}
