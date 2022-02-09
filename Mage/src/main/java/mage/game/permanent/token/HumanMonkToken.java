package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.mana.GreenManaAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class HumanMonkToken extends TokenImpl {

    public HumanMonkToken() {
        super("Human Monk token", "1/1 green Human Monk creature token with \"{T}: Add {G}.\"");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.HUMAN);
        subtype.add(SubType.MONK);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(new GreenManaAbility());

        availableImageSetCodes = Arrays.asList("NEO");
    }

    public HumanMonkToken(final HumanMonkToken token) {
        super(token);
    }

    public HumanMonkToken copy() {
        return new HumanMonkToken(this);
    }
}
