package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class SpiritGreenToken extends TokenImpl {

    public SpiritGreenToken() {
        super("Spirit Token", "4/5 green Spirit creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SPIRIT);
        color.setGreen(true);
        power = new MageInt(4);
        toughness = new MageInt(5);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("NEO")) {
            setTokenType(3);
        }
    }

    public SpiritGreenToken(final SpiritGreenToken token) {
        super(token);
    }

    public SpiritGreenToken copy() {
        return new SpiritGreenToken(this);
    }
}
