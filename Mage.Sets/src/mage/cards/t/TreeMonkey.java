
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class TreeMonkey extends CardImpl {

    public TreeMonkey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.MONKEY);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(ReachAbility.getInstance());
    }

    private TreeMonkey(final TreeMonkey card) {
        super(card);
    }

    @Override
    public TreeMonkey copy() {
        return new TreeMonkey(this);
    }
}
