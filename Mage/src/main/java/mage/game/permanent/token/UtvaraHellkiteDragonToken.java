package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class UtvaraHellkiteDragonToken extends TokenImpl {

    public UtvaraHellkiteDragonToken() {
        super("Dragon Token", "6/6 red Dragon creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.DRAGON);
        power = new MageInt(6);
        toughness = new MageInt(6);
        addAbility(FlyingAbility.getInstance());
    }

    public UtvaraHellkiteDragonToken(final UtvaraHellkiteDragonToken token) {
        super(token);
    }

    public UtvaraHellkiteDragonToken copy() {
        return new UtvaraHellkiteDragonToken(this);
    }
}
