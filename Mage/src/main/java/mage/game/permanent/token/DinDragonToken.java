package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class DinDragonToken extends TokenImpl {

    public DinDragonToken() {
        super("Dinosaur Dragon Token", "4/4 red Dinosaur Dragon creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.DINOSAUR);
        subtype.add(SubType.DRAGON);
        power = new MageInt(4);
        toughness = new MageInt(4);
        addAbility(FlyingAbility.getInstance());
    }

    private DinDragonToken(final DinDragonToken token) {
        super(token);
    }

    public DinDragonToken copy() {
        return new DinDragonToken(this);
    }
}
