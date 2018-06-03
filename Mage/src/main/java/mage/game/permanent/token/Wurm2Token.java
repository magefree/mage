
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.LifelinkAbility;

/**
 *
 * @author spjspj
 */
public final class Wurm2Token extends TokenImpl {

    public Wurm2Token() {
        this("MBS");
    }

    public Wurm2Token(String setCode) {
        super("Wurm", "3/3 colorless Wurm artifact creature token with lifelink");
        setOriginalExpansionSetCode(setCode);
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.WURM);
        power = new MageInt(3);
        toughness = new MageInt(3);
        this.addAbility(LifelinkAbility.getInstance());

        setTokenType(2); // for image
    }

    public Wurm2Token(final Wurm2Token token) {
        super(token);
    }

    public Wurm2Token copy() {
        return new Wurm2Token(this);
    }
}
