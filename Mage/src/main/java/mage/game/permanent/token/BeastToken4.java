
package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class BeastToken4 extends TokenImpl {

    public BeastToken4() {
        this(null, 0);
    }

    public BeastToken4(String setCode) {
        this(setCode, 0);
    }

    public BeastToken4(String setCode, int tokenType) {
        super("Beast", "2/2 green Beast creature token");
        setOriginalExpansionSetCode(setCode != null ? setCode : "EXO");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.BEAST);
        power = new MageInt(2);
        toughness = new MageInt(2);

    }

    public BeastToken4(final BeastToken4 token) {
        super(token);
    }

    @Override
    public BeastToken4 copy() {
        return new BeastToken4(this);
    }
}
