package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.ReachAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class TreefolkReachToken extends TokenImpl {

    public TreefolkReachToken() {
        super("Treefolk Token", "3/4 green Treefolk creature token with reach");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.TREEFOLK);
        power = new MageInt(3);
        toughness = new MageInt(4);

        addAbility(ReachAbility.getInstance());
    }

    private TreefolkReachToken(final TreefolkReachToken token) {
        super(token);
    }

    public TreefolkReachToken copy() {
        return new TreefolkReachToken(this);
    }
}
