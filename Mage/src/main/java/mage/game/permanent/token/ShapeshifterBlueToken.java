package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.ChangelingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class ShapeshifterBlueToken extends TokenImpl {

    public ShapeshifterBlueToken() {
        super("Shapeshifter Token", "2/2 blue Shapeshifter creature token with changeling");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SHAPESHIFTER);
        color.setBlue(true);
        power = new MageInt(2);
        toughness = new MageInt(2);

        addAbility(new ChangelingAbility());

        availableImageSetCodes = Arrays.asList("KHM", "CLB");
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("CLB")) {
            this.setTokenType(4);
        }
    }

    private ShapeshifterBlueToken(final ShapeshifterBlueToken token) {
        super(token);
    }

    public ShapeshifterBlueToken copy() {
        return new ShapeshifterBlueToken(this);
    }
}
