
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.MageInt;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.SubType;

/**
 *
 * @author TheElk801
 */
public final class PirateToken extends TokenImpl {

    public PirateToken() {
        super("Pirate Token", "2/2 black Pirate creature token with menace");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.PIRATE);
        power = new MageInt(2);
        toughness = new MageInt(2);
        addAbility(new MenaceAbility());
    }

    public PirateToken(final PirateToken token) {
        super(token);
    }

    public PirateToken copy() {
        return new PirateToken(this);
    }
}
