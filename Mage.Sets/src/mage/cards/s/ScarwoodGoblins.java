
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class ScarwoodGoblins extends CardImpl {

    public ScarwoodGoblins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}{G}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
    }

    private ScarwoodGoblins(final ScarwoodGoblins card) {
        super(card);
    }

    @Override
    public ScarwoodGoblins copy() {
        return new ScarwoodGoblins(this);
    }
}
