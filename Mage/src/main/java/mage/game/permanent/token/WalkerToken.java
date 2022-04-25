package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.util.RandomUtil;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class WalkerToken extends TokenImpl {

    public WalkerToken() {
        super("Walker Token", "Walker token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.ZOMBIE);
        power = new MageInt(2);
        toughness = new MageInt(2);

        availableImageSetCodes = Arrays.asList("SLD");
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("SLD")) {
            setTokenType(RandomUtil.nextInt(5) + 1);
        }
    }

    public WalkerToken(final WalkerToken token) {
        super(token);
    }

    @Override
    public WalkerToken copy() {
        return new WalkerToken(this);
    }
}
