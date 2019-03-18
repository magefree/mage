
package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.util.RandomUtil;

/**
 *
 * @author fireshoes
 */
public final class ThopterColorlessToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("MBS", "ORI", "KLD"));
    }

    public ThopterColorlessToken() {
        super("Thopter", "1/1 colorless Thopter artifact creature token with flying");
        availableImageSetCodes = tokenImageSets;
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.THOPTER);
        power = new MageInt(1);
        toughness = new MageInt(1);

        addAbility(FlyingAbility.getInstance());
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("ORI")) {
            this.setTokenType(RandomUtil.nextInt(2) + 1);
        }
        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("KLD")) {
            this.setTokenType(RandomUtil.nextInt(3) + 1);
        }
    }

    public ThopterColorlessToken(final ThopterColorlessToken token) {
        super(token);
    }

    @Override
    public ThopterColorlessToken copy() {
        return new ThopterColorlessToken(this); //To change body of generated methods, choose Tools | Templates.
    }

}
