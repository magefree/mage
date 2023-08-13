
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author North
 */
public final class LadyOrca extends CardImpl {

    public LadyOrca(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{B}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(7);
        this.toughness = new MageInt(4);
    }

    private LadyOrca(final LadyOrca card) {
        super(card);
    }

    @Override
    public LadyOrca copy() {
        return new LadyOrca(this);
    }
}
