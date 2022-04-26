

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;

import java.util.Arrays;

/**
 *
 * @author spjspj
 */
public final class ThopterToken extends TokenImpl {

    public ThopterToken() {
        super("Thopter Token", "1/1 blue Thopter artifact creature token with flying");
        cardType.add(CardType.CREATURE);
        cardType.add(CardType.ARTIFACT);
        color.setBlue(true);
        subtype.add(SubType.THOPTER);
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());

        availableImageSetCodes = Arrays.asList("ALA", "C16", "C18", "2XM");
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode().equals("2XM")) {
            this.setTokenType(2);
        }
    }

    public ThopterToken(final ThopterToken token) {
        super(token);
    }

    public ThopterToken copy() {
        return new ThopterToken(this);
    }
}
