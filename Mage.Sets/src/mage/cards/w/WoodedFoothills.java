
package mage.cards.w;

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
public final class WoodedFoothills extends CardImpl {

    public WoodedFoothills(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.frameColor = new ObjectColor("RG");

        // {tap}, Pay 1 life, Sacrifice Wooded Foothills: Search your library for a Mountain or Forest card and put it onto the battlefield. Then shuffle your library.
        this.addAbility(new FetchLandActivatedAbility(SubType.MOUNTAIN, SubType.FOREST));
    }

    private WoodedFoothills(final WoodedFoothills card) {
        super(card);
    }

    @Override
    public WoodedFoothills copy() {
        return new WoodedFoothills(this);
    }
}
