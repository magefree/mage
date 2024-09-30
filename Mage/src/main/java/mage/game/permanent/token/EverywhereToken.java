package mage.game.permanent.token;

import mage.abilities.mana.AnyColorManaAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class EverywhereToken extends TokenImpl {

    public EverywhereToken() {
        super("Everywhere", "colorless land token named Everywhere that is every basic land type");
        cardType.add(CardType.LAND);
        subtype.add(SubType.PLAINS, SubType.ISLAND, SubType.SWAMP, SubType.MOUNTAIN, SubType.FOREST);

        this.addAbility(new AnyColorManaAbility());
    }

    private EverywhereToken(final EverywhereToken token) {
        super(token);
    }

    public EverywhereToken copy() {
        return new EverywhereToken(this);
    }
}
