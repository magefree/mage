
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.SubType;

/**
 *
 * @author spjspj
 */
public final class WireflyToken extends TokenImpl {

    public WireflyToken() {
        super("Wirefly", "2/2 colorless Insect artifact creature token with flying named Wirefly");
        this.setOriginalExpansionSetCode("DST");
        this.getPower().modifyBaseValue(2);
        this.getToughness().modifyBaseValue(2);
        this.getSubtype(null).add(SubType.INSECT);
        this.addCardType(CardType.ARTIFACT);
        this.addCardType(CardType.CREATURE);
        this.addAbility(FlyingAbility.getInstance());
    }

    public WireflyToken(final WireflyToken token) {
        super(token);
    }

    public WireflyToken copy() {
        return new WireflyToken(this);
    }
}
