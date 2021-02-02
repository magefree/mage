
package mage.cards.y;

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
public final class YellowScarvesTroops extends CardImpl {

    public YellowScarvesTroops(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Yellow Scarves Troops can't block.
        this.addAbility(new CantBlockAbility());
    }

    private YellowScarvesTroops(final YellowScarvesTroops card) {
        super(card);
    }

    @Override
    public YellowScarvesTroops copy() {
        return new YellowScarvesTroops(this);
    }
}
