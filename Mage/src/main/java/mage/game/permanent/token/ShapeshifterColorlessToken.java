package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.ChangelingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class ShapeshifterColorlessToken extends TokenImpl {

    public ShapeshifterColorlessToken() {
        super("Shapeshifter Token", "1/1 colorless Shapeshifter creature token with changeling");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SHAPESHIFTER);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(new ChangelingAbility());
    }

    private ShapeshifterColorlessToken(final ShapeshifterColorlessToken token) {
        super(token);
    }

    public ShapeshifterColorlessToken copy() {
        return new ShapeshifterColorlessToken(this);
    }
}
