
package mage.cards.l;

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
public final class LoamdraggerGiant extends CardImpl {

    public LoamdraggerGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R/G}{R/G}{R/G}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(7);
        this.toughness = new MageInt(6);
    }

    private LoamdraggerGiant(final LoamdraggerGiant card) {
        super(card);
    }

    @Override
    public LoamdraggerGiant copy() {
        return new LoamdraggerGiant(this);
    }
}
