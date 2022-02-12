package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.util.RandomUtil;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class ZombieArmyToken extends TokenImpl {

    public ZombieArmyToken() {
        super("Zombie Army", "0/0 black Zombie Army creature token");

        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.ZOMBIE);
        subtype.add(SubType.ARMY);
        power = new MageInt(0);
        toughness = new MageInt(0);

        availableImageSetCodes = Arrays.asList("WAR", "MH2", "MIC");
    }

    private ZombieArmyToken(final ZombieArmyToken token) {
        super(token);
    }

    @Override
    public ZombieArmyToken copy() {
        return new ZombieArmyToken(this);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("WAR")) {
            this.setTokenType(RandomUtil.nextInt(3) + 1); // 1..3
        }
    }
}
