
package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.util.RandomUtil;

/**
 *
 * @author fireshoes
 */
public final class ServoToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Collections.singletonList("KLD"));
        tokenImageSets.addAll(Collections.singletonList("WAR"));
    }

    public ServoToken() {
        super("Servo", "1/1 colorless Servo artifact creature token");
        availableImageSetCodes = tokenImageSets;
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SERVO);
        power = new MageInt(1);
        toughness = new MageInt(1);
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
