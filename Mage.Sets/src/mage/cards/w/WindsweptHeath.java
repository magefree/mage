
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
public final class WindsweptHeath extends CardImpl {

    public WindsweptHeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.frameColor = new ObjectColor("GW");

        // {tap}, Pay 1 life, Sacrifice Windswept Heath: Search your library for a Forest or Plains card and put it onto the battlefield. Then shuffle your library.
        this.addAbility(new FetchLandActivatedAbility(SubType.FOREST, SubType.PLAINS));
    }

    private WindsweptHeath(final WindsweptHeath card) {
        super(card);
    }

    @Override
    public WindsweptHeath copy() {
        return new WindsweptHeath(this);
    }
}
