package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.util.RandomUtil;

/**
 * @author spjspj
 */
public final class YoungPyromancerElementalToken extends TokenImpl {

    public YoungPyromancerElementalToken() {
        super("Elemental", "1/1 red Elemental creature token");
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("M14")) {
            setTokenType(RandomUtil.nextInt(2) + 1);
        }
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("EMA")) {
            setTokenType(1);
        }
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("SHM")) {
            setTokenType(2);
        }
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("MH1")) {
            setTokenType(1);
        }
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public YoungPyromancerElementalToken(final YoungPyromancerElementalToken token) {
        super(token);
    }

    public YoungPyromancerElementalToken copy() {
        return new YoungPyromancerElementalToken(this);
    }
}
