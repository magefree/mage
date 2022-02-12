package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.util.RandomUtil;

import java.util.Arrays;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class WolfToken extends TokenImpl {

    public WolfToken() {
        super("Wolf", "2/2 green Wolf creature token");

        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.WOLF);
        power = new MageInt(2);
        toughness = new MageInt(2);

        availableImageSetCodes = Arrays.asList("BNG", "C14", "C15", "CMA", "CMD", "CNS", "DKA", "EVE", "ISD",
                "LRW", "M10", "M14", "MM2", "MOR", "SHM", "SOI", "SOM", "V10", "WWK", "ZEN", "WAR", "M20",
                "THB", "AFR", "MID", "VOW");
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("ISD")) {
            this.setTokenType(RandomUtil.nextInt(2) + 1); // 2 images
        }
    }

    public WolfToken(final WolfToken token) {
        super(token);
    }

    @Override
    public Token copy() {
        return new WolfToken(this);
    }
}
