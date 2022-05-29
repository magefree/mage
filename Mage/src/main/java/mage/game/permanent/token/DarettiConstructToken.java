

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.util.RandomUtil;

import java.util.Arrays;

/**
 *
 * @author spjspj
 */
public final class DarettiConstructToken extends TokenImpl {

    public DarettiConstructToken() {
        this("CNS");
    }

    public DarettiConstructToken(String setCode) {
        super("Construct Token", "1/1 colorless Construct artifact creature token with defender");
        this.setOriginalExpansionSetCode(setCode);
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.CONSTRUCT);
        power = new MageInt(1);
        toughness = new MageInt(1);

        addAbility(DefenderAbility.getInstance());

        availableImageSetCodes = Arrays.asList("CNS", "CN2", "MED");
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("MED")) {
            setTokenType(2);
        }
    }

    public DarettiConstructToken(final DarettiConstructToken token) {
        super(token);
    }

    public DarettiConstructToken copy() {
        return new DarettiConstructToken(this);
    }
}
