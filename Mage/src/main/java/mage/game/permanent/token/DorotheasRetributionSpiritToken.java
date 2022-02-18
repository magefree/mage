package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author JayDi85
 */
public final class DorotheasRetributionSpiritToken extends TokenImpl {

    public DorotheasRetributionSpiritToken() {
        super("Spirit", "4/4 white Spirit creature token with flying");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SPIRIT);
        color.setWhite(true);
        power = new MageInt(4);
        toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());

        availableImageSetCodes = Arrays.asList("VOW");
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("VOW")) {
            setTokenType(2);
        }
    }

    public DorotheasRetributionSpiritToken(final DorotheasRetributionSpiritToken token) {
        super(token);
    }

    @Override
    public DorotheasRetributionSpiritToken copy() {
        return new DorotheasRetributionSpiritToken(this);
    }
}
