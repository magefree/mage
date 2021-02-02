

package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class WarpathGhoul extends CardImpl {

    public WarpathGhoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
    }

    private WarpathGhoul(final WarpathGhoul card) {
        super(card);
    }

    @Override
    public WarpathGhoul copy() {
        return new WarpathGhoul(this);
    }

}
