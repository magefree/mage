package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author LoneFox
 */
public final class DragonToken2 extends TokenImpl {

    public DragonToken2() {
        super("Dragon Token", "5/5 red Dragon creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.DRAGON);
        power = new MageInt(5);
        toughness = new MageInt(5);

        addAbility(FlyingAbility.getInstance());

        availableImageSetCodes = Arrays.asList("10E", "BFZ", "C15", "C19", "CMA", "CMD", "SCG", "WWK", "M19", "KHM", "AFC", "IMA", "PCA", "CN2");
    }

    public DragonToken2(final DragonToken2 token) {
        super(token);
    }

    public DragonToken2 copy() {
        return new DragonToken2(this);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("M19")) {
            this.setTokenType(2);
        }
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("IMA")) {
            this.setTokenType(2);
        }
    }
}
