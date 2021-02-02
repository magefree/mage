
package mage.cards.h;

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
public final class HulkingDevil extends CardImpl {

    public HulkingDevil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.DEVIL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(2);
    }

    private HulkingDevil(final HulkingDevil card) {
        super(card);
    }

    @Override
    public HulkingDevil copy() {
        return new HulkingDevil(this);
    }
}
