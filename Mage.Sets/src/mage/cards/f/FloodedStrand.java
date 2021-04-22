
package mage.cards.f;

import java.util.EnumSet;
import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.common.FetchLandActivatedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author jonubuu
 */
public final class FloodedStrand extends CardImpl {

    public FloodedStrand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.frameColor = new ObjectColor("UW");

        // {tap}, Pay 1 life, Sacrifice Flooded Strand: Search your library for a Plains or Island card and put it onto the battlefield. Then shuffle your library.
        this.addAbility(new FetchLandActivatedAbility(SubType.PLAINS, SubType.ISLAND));
    }

    private FloodedStrand(final FloodedStrand card) {
        super(card);
    }

    @Override
    public FloodedStrand copy() {
        return new FloodedStrand(this);
    }
}
