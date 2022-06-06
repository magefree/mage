


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
public final class EnchantmentBirdToken extends TokenImpl {

    public EnchantmentBirdToken() {
        super("Bird Token", "2/2 blue Bird enchantment creature token with flying");
        cardType.add(CardType.ENCHANTMENT);
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.BIRD);
        power = new MageInt(2);
        toughness = new MageInt(2);
        this.addAbility(FlyingAbility.getInstance());

        availableImageSetCodes = Arrays.asList("BNG");
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("BNG")) {
            setTokenType(1);
        }
    }

    public EnchantmentBirdToken(final EnchantmentBirdToken token) {
        super(token);
    }

    public EnchantmentBirdToken copy() {
        return new EnchantmentBirdToken(this);
    }
}

