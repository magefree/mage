package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.util.RandomUtil;

import java.util.Arrays;

/**
 * @author fireshoes
 */
public final class ServoToken extends TokenImpl {

    public ServoToken() {
        super("Servo Token", "1/1 colorless Servo artifact creature token");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SERVO);
        power = new MageInt(1);
        toughness = new MageInt(1);

        availableImageSetCodes = Arrays.asList("C18", "KLD", "WAR", "KHC", "AFC", "2XM", "BRC");
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("KLD")) {
            this.setTokenType(RandomUtil.nextInt(3) + 1);
        }
    }

    public ServoToken(final ServoToken token) {
        super(token);
    }

    @Override
    public ServoToken copy() {
        return new ServoToken(this);
    }

}
