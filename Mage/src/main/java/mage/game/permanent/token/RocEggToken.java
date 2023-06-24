package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class RocEggToken extends TokenImpl {

    public RocEggToken() {
        super("Bird Token", "3/3 white Bird creature token with flying");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.BIRD);
        color.setWhite(true);
        power = new MageInt(3);
        toughness = new MageInt(3);
        addAbility(FlyingAbility.getInstance());
    }

    public RocEggToken(final RocEggToken token) {
        super(token);
    }

    public RocEggToken copy() {
        return new RocEggToken(this);
    }
}
