package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.util.RandomUtil;

import java.util.Arrays;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class ZombieToken extends TokenImpl {

    public ZombieToken() {
        super("Zombie Token", "2/2 black Zombie creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.ZOMBIE);
        power = new MageInt(2);
        toughness = new MageInt(2);

        availableImageSetCodes = Arrays.asList("10E", "M10", "M11", "M12",
                "M13", "M14", "M15", "C18",
                "MBS", "ALA", "ISD", "C14",
                "C15", "C16", "C17", "CNS",
                "MMA", "BNG", "KTK", "DTK",
                "ORI", "OGW", "SOI", "EMN",
                "EMA", "MM3", "AKH", "CMA",
                "RNA", "WAR", "MH1", "M20",
                "C19", "C20", "THB", "M21",
                "CMR", "C21", "MH2", "AFR",
                "MIC", "VOW", "UMA", "NCC",
                "MED", "BBD", "M19", "CM2",
                "PCA", "AVR", "DDQ", "CN2",
                "2X2", "CC2", "DMC", "GN3",
                "DMR");
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode().equals("ISD")) {
            this.setTokenType(RandomUtil.nextInt(3) + 1);
        }
        if (getOriginalExpansionSetCode().equals("C14")) {
            this.setTokenType(1);
        }
        if (getOriginalExpansionSetCode().equals("EMN")) {
            this.setTokenType(RandomUtil.nextInt(3) + 1);
        }
        if (getOriginalExpansionSetCode().equals("C19")) {
            this.setTokenType(RandomUtil.nextInt(2) + 1);
        }
        if (getOriginalExpansionSetCode().equals("MIC")) {
            this.setTokenType(1);
        }
    }

    public ZombieToken(final ZombieToken token) {
        super(token);
    }

    @Override
    public ZombieToken copy() {
        return new ZombieToken(this);
    }
}
