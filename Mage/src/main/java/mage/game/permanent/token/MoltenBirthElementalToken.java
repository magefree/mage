
package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.util.RandomUtil;

/**
 *
 * @author spjspj
 */
public final class MoltenBirthElementalToken extends TokenImpl {

    public MoltenBirthElementalToken() {
        super("Elemental", "1/1 red Elemental creature");
        this.setOriginalExpansionSetCode("M14");
        this.setTokenType(RandomUtil.nextInt(2) + 1);
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public MoltenBirthElementalToken(final MoltenBirthElementalToken token) {
        super(token);
    }

    public MoltenBirthElementalToken copy() {
        return new MoltenBirthElementalToken(this);
    }
}
