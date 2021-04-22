
package mage.cards.b;

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
public final class BloodstainedMire extends CardImpl {

    public BloodstainedMire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.frameColor = new ObjectColor("RB");

        // {tap}, Pay 1 life, Sacrifice Bloodstained Mire: Search your library for a Swamp or Mountain card and put it onto the battlefield. Then shuffle your library.
        this.addAbility(new FetchLandActivatedAbility(SubType.SWAMP,SubType.MOUNTAIN));
    }

    private BloodstainedMire(final BloodstainedMire card) {
        super(card);
    }

    @Override
    public BloodstainedMire copy() {
        return new BloodstainedMire(this);
    }
}
