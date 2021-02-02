
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class GiantOctopus extends CardImpl {

    public GiantOctopus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.OCTOPUS);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
    }

    private GiantOctopus(final GiantOctopus card) {
        super(card);
    }

    @Override
    public GiantOctopus copy() {
        return new GiantOctopus(this);
    }
}
