
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class ThornhideWolves extends CardImpl {

    public ThornhideWolves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);
    }

    private ThornhideWolves(final ThornhideWolves card) {
        super(card);
    }

    @Override
    public ThornhideWolves copy() {
        return new ThornhideWolves(this);
    }
}
