
package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class OrderedMigrationBirdToken extends TokenImpl {

    public OrderedMigrationBirdToken() {
        super("Bird Token", "1/1 blue Bird creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.BIRD);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(FlyingAbility.getInstance());
    }

    public OrderedMigrationBirdToken(final OrderedMigrationBirdToken token) {
        super(token);
    }

    public OrderedMigrationBirdToken copy() {
        return new OrderedMigrationBirdToken(this);
    }
}
