package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class SpiritRedToken extends TokenImpl {

    public SpiritRedToken() {
        super("Spirit Token", "2/2 red Spirit creature token with menace");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SPIRIT);
        color.setRed(true);
        power = new MageInt(2);
        toughness = new MageInt(2);
        addAbility(new MenaceAbility());

        availableImageSetCodes = Arrays.asList("NEO");
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("NEO")) {
            setTokenType(4);
        }
    }

    public SpiritRedToken(final SpiritRedToken token) {
        super(token);
    }

    public SpiritRedToken copy() {
        return new SpiritRedToken(this);
    }
}
