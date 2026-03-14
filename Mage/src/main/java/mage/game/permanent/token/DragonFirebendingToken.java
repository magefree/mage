package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FirebendingAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class DragonFirebendingToken extends TokenImpl {

    public DragonFirebendingToken() {
        super("Dragon Token", "4/4 red Dragon creature token with flying and firebending 4");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.DRAGON);
        power = new MageInt(4);
        toughness = new MageInt(4);
        addAbility(FlyingAbility.getInstance());
        addAbility(new FirebendingAbility(4));
    }

    private DragonFirebendingToken(final DragonFirebendingToken token) {
        super(token);
    }

    public DragonFirebendingToken copy() {
        return new DragonFirebendingToken(this);
    }
}
