
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author spjspj
 */
public final class CamaridToken extends TokenImpl {

    public CamaridToken() {
        super("Camarid Token", "1/1 blue Camarid creature tokens");
        this.setOriginalExpansionSetCode("FEM");
        this.getPower().modifyBaseValue(1);
        this.getToughness().modifyBaseValue(1);
        this.color.setBlue(true);
        this.subtype.add(SubType.CAMARID);
        this.cardType.add(CardType.CREATURE);
    }

    public CamaridToken(final CamaridToken token) {
        super(token);
    }

    public CamaridToken copy() {
        return new CamaridToken(this);
    }
}
