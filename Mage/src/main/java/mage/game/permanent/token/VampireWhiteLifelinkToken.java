package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;
import java.util.Collections;

public class VampireWhiteLifelinkToken extends TokenImpl {

    public VampireWhiteLifelinkToken() {
        super("Vampire Token", "1/1 white Vampire creature token with lifelink");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.VAMPIRE);
        power = new MageInt(1);
        toughness = new MageInt(1);

        this.addAbility(LifelinkAbility.getInstance());

        availableImageSetCodes = Collections.singletonList("2X2");
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("2X2")) {
            setTokenType(1);
        }
    }

    private VampireWhiteLifelinkToken(final VampireWhiteLifelinkToken token) {
        super(token);
    }

    @Override
    public VampireWhiteLifelinkToken copy() {
        return new VampireWhiteLifelinkToken(this);
    }
}
