
package mage.cards.p;

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
public final class PollutedDelta extends CardImpl {

    public PollutedDelta(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.frameColor = new ObjectColor("UB");

        // {tap}, Pay 1 life, Sacrifice Polluted Delta: Search your library for an Island or Swamp card and put it onto the battlefield. Then shuffle your library.
        this.addAbility(new FetchLandActivatedAbility(SubType.ISLAND, SubType.SWAMP));
    }

    private PollutedDelta(final PollutedDelta card) {
        super(card);
    }

    @Override
    public PollutedDelta copy() {
        return new PollutedDelta(this);
    }
}
