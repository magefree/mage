
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class RiverBear extends CardImpl {

    public RiverBear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.BEAR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(new IslandwalkAbility());
    }

    private RiverBear(final RiverBear card) {
        super(card);
    }

    @Override
    public RiverBear copy() {
        return new RiverBear(this);
    }
}
