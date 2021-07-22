package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author spjspj
 */
public final class WurmWithDeathtouchToken extends TokenImpl {

    public WurmWithDeathtouchToken() {
        super("Phyrexian Wurm", "3/3 colorless Phyrexian Wurm artifact creature token with deathtouch");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.PHYREXIAN);
        subtype.add(SubType.WURM);
        power = new MageInt(3);
        toughness = new MageInt(3);
        this.addAbility(DeathtouchAbility.getInstance());

        availableImageSetCodes = Arrays.asList("C14", "SOM");
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);
    }

    public WurmWithDeathtouchToken(final WurmWithDeathtouchToken token) {
        super(token);
    }

    public WurmWithDeathtouchToken copy() {
        return new WurmWithDeathtouchToken(this);
    }
}
