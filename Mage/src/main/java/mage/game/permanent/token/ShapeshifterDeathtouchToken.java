package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.ChangelingAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class ShapeshifterDeathtouchToken extends TokenImpl {

    public ShapeshifterDeathtouchToken() {
        this(0);
    }

    public ShapeshifterDeathtouchToken(int amount) {
        super("Shapeshifter Token", "X/X colorless Shapeshifter creature token with changeling and deathtouch");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SHAPESHIFTER);
        power = new MageInt(amount);
        toughness = new MageInt(amount);
        addAbility(new ChangelingAbility());
        addAbility(DeathtouchAbility.getInstance());
    }

    private ShapeshifterDeathtouchToken(final ShapeshifterDeathtouchToken token) {
        super(token);
    }

    public ShapeshifterDeathtouchToken copy() {
        return new ShapeshifterDeathtouchToken(this);
    }
}
