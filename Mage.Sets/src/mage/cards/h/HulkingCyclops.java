
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class HulkingCyclops extends CardImpl {

    public HulkingCyclops(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.CYCLOPS);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Hulking Cyclops can't block.
        this.addAbility(new CantBlockAbility());
    }

    private HulkingCyclops(final HulkingCyclops card) {
        super(card);
    }

    @Override
    public HulkingCyclops copy() {
        return new HulkingCyclops(this);
    }
}
