package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author spjspj
 */
public final class OviyaPashiriSageLifecrafterToken extends TokenImpl {

    public OviyaPashiriSageLifecrafterToken() {
        this(1);
    }

    public OviyaPashiriSageLifecrafterToken(int number) {
        super("Construct Token", "an X/X colorless Construct artifact creature token, where X is the number of creatures you control");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.CONSTRUCT);
        power = new MageInt(number);
        toughness = new MageInt(number);

        availableImageSetCodes = Arrays.asList("KLD");
    }

    public OviyaPashiriSageLifecrafterToken(final OviyaPashiriSageLifecrafterToken token) {
        super(token);
    }

    public OviyaPashiriSageLifecrafterToken copy() {
        return new OviyaPashiriSageLifecrafterToken(this);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("KLD")) {
            setTokenType(1);
        }
    }
}
