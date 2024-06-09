

package mage.cards.r;

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
public final class RazorfieldThresher extends CardImpl {

    public RazorfieldThresher (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{7}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(4);
    }

    private RazorfieldThresher(final RazorfieldThresher card) {
        super(card);
    }

    @Override
    public RazorfieldThresher copy() {
        return new RazorfieldThresher(this);
    }

}
