package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author magenoxx
 */
public final class ElementalTokenWithHaste extends TokenImpl {

    public ElementalTokenWithHaste() {
        super("Elemental", "3/1 red Elemental creature token with haste");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(3);
        toughness = new MageInt(1);
        this.addAbility(HasteAbility.getInstance());

        availableImageSetCodes = Arrays.asList("C20", "PMEI", "OGW", "SOK", "MRD", "CON");
    }

    public ElementalTokenWithHaste(final ElementalTokenWithHaste token) {
        super(token);
    }

    public ElementalTokenWithHaste copy() {
        return new ElementalTokenWithHaste(this);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("OGW")) {
            setTokenType(2);
        }
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("C20")) {
            setTokenType(1);
        }
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("SOK")) {
            setTokenType(1);
        }
    }
}
