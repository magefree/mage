package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author LoneFox
 */
public final class SoldierTokenWithHaste extends TokenImpl {

    public SoldierTokenWithHaste() {
        super("Soldier Token", "1/1 red and white Soldier creature token with haste");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        color.setRed(true);
        subtype.add(SubType.SOLDIER);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(HasteAbility.getInstance());

        availableImageSetCodes = Arrays.asList("GTC", "MM3", "NCC");
    }


    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("MM3")) {
            setTokenType(2);
        }
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("NCC")) {
            setTokenType(2);
        }
    }

    public SoldierTokenWithHaste(final SoldierTokenWithHaste token) {
        super(token);
    }

    @Override
    public SoldierTokenWithHaste copy() {
        return new SoldierTokenWithHaste(this); //To change body of generated methods, choose Tools | Templates.
    }
}
