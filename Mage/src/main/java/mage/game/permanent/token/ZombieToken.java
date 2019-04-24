
package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.util.RandomUtil;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class ZombieToken extends TokenImpl {

    final static private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("10E", "M10", "M11", "M12", "M13", "M14", "M15", "MBS", "ALA", "ISD", "C14", "C15", "C16", "C17", "CNS",
                "MMA", "BNG", "KTK", "DTK", "ORI", "OGW", "SOI", "EMN", "EMA", "MM3", "AKH", "CMA", "E01"));
    }

    public ZombieToken() {
        super("Zombie", "2/2 black Zombie creature token");
        availableImageSetCodes = tokenImageSets;
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.ZOMBIE);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);
        if (getOriginalExpansionSetCode().equals("ISD")) {
            this.setTokenType(RandomUtil.nextInt(3) + 1);
        }
        if (getOriginalExpansionSetCode().equals("C14")) {
            this.setTokenType(2);
        }
        if (getOriginalExpansionSetCode().equals("EMN")) {
            this.setTokenType(RandomUtil.nextInt(4) + 1);
        }
    }

    public ZombieToken(final ZombieToken token) {
        super(token);
    }

    @Override
    public ZombieToken copy() {
        return new ZombieToken(this); //To change body of generated methods, choose Tools | Templates.
    }
}
