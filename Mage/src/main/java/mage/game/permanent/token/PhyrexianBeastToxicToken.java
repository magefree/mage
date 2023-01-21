package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.ToxicAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class PhyrexianBeastToxicToken extends TokenImpl {

    public PhyrexianBeastToxicToken() {
        super("Phyrexian Golem Token", "3/3 green Phyrexian Beast creature token with toxic 1");
        color.setGreen(true);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.PHYREXIAN);
        subtype.add(SubType.BEAST);
        power = new MageInt(3);
        toughness = new MageInt(3);

        addAbility(new ToxicAbility(1));

        availableImageSetCodes = Arrays.asList("ONE");
    }

    public PhyrexianBeastToxicToken(final PhyrexianBeastToxicToken token) {
        super(token);
    }

    public PhyrexianBeastToxicToken copy() {
        return new PhyrexianBeastToxicToken(this);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);
    }
}
