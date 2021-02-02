
package mage.cards.m;

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
public final class MetallicSliver extends CardImpl {

    public MetallicSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{1}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
    }

    private MetallicSliver(final MetallicSliver card) {
        super(card);
    }

    @Override
    public MetallicSliver copy() {
        return new MetallicSliver(this);
    }
}
