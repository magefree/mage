


package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;

/**
 *
 * @author spjspj
 */
public final class AerieWorshippersBirdToken extends TokenImpl {

    public AerieWorshippersBirdToken() {
        super("Bird Token", "2/2 blue Bird enchantment creature token with flying");
        cardType.add(CardType.ENCHANTMENT);
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.BIRD);
        power = new MageInt(2);
        toughness = new MageInt(2);
        this.addAbility(FlyingAbility.getInstance());
        this.setOriginalExpansionSetCode("BNG");
        this.setTokenType(2);
    }

    public AerieWorshippersBirdToken(final AerieWorshippersBirdToken token) {
        super(token);
    }

    public AerieWorshippersBirdToken copy() {
        return new AerieWorshippersBirdToken(this);
    }
}

