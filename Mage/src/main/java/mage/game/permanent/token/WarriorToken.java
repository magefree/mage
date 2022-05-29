

package mage.game.permanent.token;

import java.util.Arrays;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.util.RandomUtil;

/**
 *
 * @author LoneFox
 */
public final class WarriorToken extends TokenImpl {

    public WarriorToken() {
        super("Warrior Token", "1/1 white Warrior creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.WARRIOR);
        power = new MageInt(1);
        toughness = new MageInt(1);
        availableImageSetCodes.addAll(Arrays.asList("KTK", "DTK", "BBD"));
    }

    public WarriorToken(final WarriorToken token) {
        super(token);
    }

    @Override
        public WarriorToken copy() {
        return new WarriorToken(this);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);
        if (getOriginalExpansionSetCode().equals("KTK")) {
            this.setTokenType(RandomUtil.nextInt(2) + 1);
        }
    }
}
