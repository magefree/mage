package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class DragonToken extends TokenImpl {

    public DragonToken() {
        super("Dragon Token", "4/4 red Dragon creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.DRAGON);
        power = new MageInt(4);
        toughness = new MageInt(4);
        addAbility(FlyingAbility.getInstance());
    }

    public DragonToken(final DragonToken token) {
        super(token);
    }

    public DragonToken copy() {
        return new DragonToken(this);
    }
}
