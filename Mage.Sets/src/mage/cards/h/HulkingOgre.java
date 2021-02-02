
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
 * @author LoneFox
 */
public final class HulkingOgre extends CardImpl {

    public HulkingOgre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.OGRE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Hulking Ogre can't block.
        this.addAbility(new CantBlockAbility());
    }

    private HulkingOgre(final HulkingOgre card) {
        super(card);
    }

    @Override
    public HulkingOgre copy() {
        return new HulkingOgre(this);
    }
}
