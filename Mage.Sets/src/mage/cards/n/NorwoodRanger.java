
package mage.cards.n;

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
public final class NorwoodRanger extends CardImpl {

    public NorwoodRanger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SCOUT);
        this.subtype.add(SubType.RANGER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);
    }

    private NorwoodRanger(final NorwoodRanger card) {
        super(card);
    }

    @Override
    public NorwoodRanger copy() {
        return new NorwoodRanger(this);
    }
}
