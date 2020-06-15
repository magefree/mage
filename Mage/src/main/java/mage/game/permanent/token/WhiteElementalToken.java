package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class WhiteElementalToken extends TokenImpl {

    public WhiteElementalToken() {
        super("Elemental", "4/4 white Elemental creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(4);
        toughness = new MageInt(4);
        this.addAbility(FlyingAbility.getInstance());

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("C20")) {
            setTokenType(2);
        }
    }

    public WhiteElementalToken(final WhiteElementalToken token) {
        super(token);
    }

    public WhiteElementalToken copy() {
        return new WhiteElementalToken(this);
    }
}
