package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.util.RandomUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class SoldierToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("10E", "M15", "C14", "ORI", "ALA", "DDF", "THS", "M12", "M13", "MM2", "MMA", "RTR",
                "SOM", "DDO", "M10", "ORI", "EMN", "EMA", "CN2", "C16", "MM3", "E01", "DOM", "MH1", "M20"));
    }

    public SoldierToken() {
        super("Soldier", "1/1 white Soldier creature token");
        availableImageSetCodes = tokenImageSets;

        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.SOLDIER);
        power = new MageInt(1);
        toughness = new MageInt(1);

    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("THS")) {
            this.setTokenType(RandomUtil.nextInt(2) + 1);
        }
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("CN2") || getOriginalExpansionSetCode().equals("MM3")) {
            setTokenType(1);
        }
    }

    public SoldierToken(final SoldierToken token) {
        super(token);
    }

    @Override
    public SoldierToken copy() {
        return new SoldierToken(this); //To change body of generated methods, choose Tools | Templates.
    }
}
