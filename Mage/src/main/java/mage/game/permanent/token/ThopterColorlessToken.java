package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.util.RandomUtil;

import java.util.Arrays;

/**
 * @author fireshoes
 */
public final class ThopterColorlessToken extends TokenImpl {

    public ThopterColorlessToken() {
        super("Thopter Token", "1/1 colorless Thopter artifact creature token with flying");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.THOPTER);
        power = new MageInt(1);
        toughness = new MageInt(1);

        addAbility(FlyingAbility.getInstance());

        availableImageSetCodes = Arrays.asList("C18", "EXO", "KLD", "MBS", "ORI", "VMA", "M19", "ZNC",
                "KHC", "C21", "MH2", "AFC", "VOC", "NEC", "2XM");
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("C18")) {
            this.setTokenType(RandomUtil.nextInt(3) + 1);
        }
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("KLD")) {
            this.setTokenType(RandomUtil.nextInt(3) + 1);
        }
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("ORI")) {
            this.setTokenType(RandomUtil.nextInt(2) + 1);
        }
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("2XM")) {
            this.setTokenType(1);
        }

    }

    public ThopterColorlessToken(final ThopterColorlessToken token) {
        super(token);
    }

    @Override
    public ThopterColorlessToken copy() {
        return new ThopterColorlessToken(this);
    }

}
