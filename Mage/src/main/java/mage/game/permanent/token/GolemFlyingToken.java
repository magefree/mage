package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class GolemFlyingToken extends TokenImpl {

    public GolemFlyingToken() {
        super("Golem Token", "3/3 colorless Golem artifact creature token with flying");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.GOLEM);
        power = new MageInt(3);
        toughness = new MageInt(3);

        addAbility(FlyingAbility.getInstance());

        availableImageSetCodes = Arrays.asList("C21");
    }

    private GolemFlyingToken(final GolemFlyingToken token) {
        super(token);
    }

    public GolemFlyingToken copy() {
        return new GolemFlyingToken(this);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("C21")) {
            setTokenType(1);
        }
    }
}
