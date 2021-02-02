
package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.keyword.HorsemanshipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class YellowScarvesGeneral extends CardImpl {

    public YellowScarvesGeneral(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Horsemanship
        this.addAbility(HorsemanshipAbility.getInstance());
        // Yellow Scarves General can't block.
        this.addAbility(new CantBlockAbility());
    }

    private YellowScarvesGeneral(final YellowScarvesGeneral card) {
        super(card);
    }

    @Override
    public YellowScarvesGeneral copy() {
        return new YellowScarvesGeneral(this);
    }
}
