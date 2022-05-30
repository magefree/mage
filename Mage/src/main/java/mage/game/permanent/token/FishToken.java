package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class FishToken extends TokenImpl {

    public FishToken() {
        super("Fish Token", "1/1 blue Fish creature token with \"This creature can't be blocked.\"");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.FISH);
        color.setBlue(true);
        power = new MageInt(1);
        toughness = new MageInt(1);

        addAbility(new CantBeBlockedSourceAbility("this creature can't be blocked"));

        availableImageSetCodes = Arrays.asList("SNC");
    }

    public FishToken(final FishToken token) {
        super(token);
    }

    public FishToken copy() {
        return new FishToken(this);
    }
}
