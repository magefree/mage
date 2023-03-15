package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class SoldierVigilanceToken extends TokenImpl {

    public SoldierVigilanceToken() {
        super("Soldier Token", "2/2 white Soldier creature token with vigilance");

        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.SOLDIER);
        power = new MageInt(2);
        toughness = new MageInt(2);
        addAbility(VigilanceAbility.getInstance());

        availableImageSetCodes = Arrays.asList("WAR", "ONC");
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("ONC")) {
            this.setTokenType(2);
        }
    }

    private SoldierVigilanceToken(final SoldierVigilanceToken token) {
        super(token);
    }

    @Override
    public SoldierVigilanceToken copy() {
        return new SoldierVigilanceToken(this);
    }
}
