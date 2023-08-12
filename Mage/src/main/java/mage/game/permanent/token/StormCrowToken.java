
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;

/**
 * @author spjspj
 */
public final class StormCrowToken extends TokenImpl {

    public StormCrowToken() {
        super("Storm Crow", "1/2 blue Bird creature token with flying named Storm Crow");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.BIRD);
        power = new MageInt(1);
        toughness = new MageInt(2);
        this.addAbility(FlyingAbility.getInstance());
    }

    protected StormCrowToken(final StormCrowToken token) {
        super(token);
    }

    public StormCrowToken copy() {
        return new StormCrowToken(this);
    }
}
