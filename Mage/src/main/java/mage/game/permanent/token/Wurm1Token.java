
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;

/**
 *
 * @author spjspj
 */
public final class Wurm1Token extends TokenImpl {

    public Wurm1Token() {
        this("MBS");
    }
    public Wurm1Token(String setCode) {
        super("Wurm", "3/3 colorless Wurm artifact creature token with deathtouch");
        setOriginalExpansionSetCode(setCode);
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.WURM);
        power = new MageInt(3);
        toughness = new MageInt(3);
        this.addAbility(DeathtouchAbility.getInstance());
    }

    public Wurm1Token(final Wurm1Token token) {
        super(token);
    }

    public Wurm1Token copy() {
        return new Wurm1Token(this);
    }
}
