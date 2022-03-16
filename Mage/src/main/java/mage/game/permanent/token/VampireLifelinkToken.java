package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 *
 * @author weirddan455
 */
public class VampireLifelinkToken extends TokenImpl {

    public VampireLifelinkToken() {
        super("Vampire Token", "2/3 black Vampire creature token with flying and lifelink");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.VAMPIRE);
        power = new MageInt(2);
        toughness = new MageInt(3);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(LifelinkAbility.getInstance());

        availableImageSetCodes = Arrays.asList("VOW");
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("VOW")) {
            setTokenType(2);
        }
    }

    private VampireLifelinkToken(final VampireLifelinkToken token) {
        super(token);
    }

    @Override
    public VampireLifelinkToken copy() {
        return new VampireLifelinkToken(this);
    }
}
