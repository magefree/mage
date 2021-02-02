
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class RowanTreefolk extends CardImpl {

    public RowanTreefolk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.TREEFOLK);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
    }

    private RowanTreefolk(final RowanTreefolk card) {
        super(card);
    }

    @Override
    public RowanTreefolk copy() {
        return new RowanTreefolk(this);
    }
}
