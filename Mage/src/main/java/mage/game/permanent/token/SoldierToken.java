package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.util.RandomUtil;

import java.util.Arrays;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class SoldierToken extends TokenImpl {

    public SoldierToken() {
        super("Soldier Token", "1/1 white Soldier creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.SOLDIER);
        power = new MageInt(1);
        toughness = new MageInt(1);

        availableImageSetCodes = Arrays.asList("10E", "M15", "C14", "ORI", "ALA", "DDF", "THS", "M12", "M13",
                "MM2", "MMA", "RTR", "SOM", "DDO", "M10", "ORI", "EMN", "EMA", "CN2", "C16", "MM3", "E01",
                "DOM", "MH1", "M20", "C20", "M21", "CMR", "KHC", "TSR");
    }

    public SoldierToken(final SoldierToken token) {
        super(token);
    }

    @Override
    public SoldierToken copy() {
        return new SoldierToken(this);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("CN2")) {
            this.setTokenType(RandomUtil.nextInt(2) + 1);
        }
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("CN2")) {
            this.setTokenType(RandomUtil.nextInt(2) + 1);
        }
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("MM3")) {
            this.setTokenType(RandomUtil.nextInt(2) + 1);
        }
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("THS")) {
            this.setTokenType(RandomUtil.nextInt(2) + 1);
        }
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("CMR")) {
            this.setTokenType(RandomUtil.nextInt(2) + 1);
        }
    }
}
