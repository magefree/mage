
package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author L_J
 */
public final class SheepToken extends TokenImpl {

    public SheepToken() {
        super("Sheep Token", "0/1 green Sheep creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.SHEEP);
        power = new MageInt(0);
        toughness = new MageInt(1);

        setOriginalExpansionSetCode("DMR");
    }

    public SheepToken(final SheepToken token) {
        super(token);
    }

    public SheepToken copy() {
        return new SheepToken(this);
    }
}
