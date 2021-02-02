
package mage.cards.w;

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
public final class WoodlandDruid extends CardImpl {

    public WoodlandDruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);
    }

    private WoodlandDruid(final WoodlandDruid card) {
        super(card);
    }

    @Override
    public WoodlandDruid copy() {
        return new WoodlandDruid(this);
    }
}
