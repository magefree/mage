

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
public final class WanderingOnes extends CardImpl {

    public WanderingOnes (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
    }

    private WanderingOnes(final WanderingOnes card) {
        super(card);
    }

    @Override
    public WanderingOnes copy() {
        return new WanderingOnes(this);
    }

}
