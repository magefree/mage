package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.util.RandomUtil;

import java.util.Arrays;

/**
 * @author spjspj
 */
public final class TheLocustGodInsectToken extends TokenImpl {

    public TheLocustGodInsectToken() {
        super("Insect Token", "1/1 blue and red Insect creature token with flying and haste");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        color.setRed(true);
        subtype.add(SubType.INSECT);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(FlyingAbility.getInstance());
        addAbility(HasteAbility.getInstance());

        availableImageSetCodes = Arrays.asList("HOU", "C20");
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("C20")) {
            this.setTokenType(2);
        }
    }

    public TheLocustGodInsectToken(final TheLocustGodInsectToken token) {
        super(token);
    }

    public TheLocustGodInsectToken copy() {
        return new TheLocustGodInsectToken(this);
    }
}
