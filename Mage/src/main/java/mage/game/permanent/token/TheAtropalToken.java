package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class TheAtropalToken extends TokenImpl {

    public TheAtropalToken() {
        super("The Atropal", "The Atropal, a legendary 4/4 black God Horror creature token with deathtouch");
        supertype.add(SuperType.LEGENDARY);
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.GOD);
        subtype.add(SubType.HORROR);
        power = new MageInt(4);
        toughness = new MageInt(4);

        // Deathtouch
        addAbility(DeathtouchAbility.getInstance());

        availableImageSetCodes = Arrays.asList("AFR");
    }

    public TheAtropalToken(final TheAtropalToken token) {
        super(token);
    }

    public TheAtropalToken copy() {
        return new TheAtropalToken(this);
    }
}
