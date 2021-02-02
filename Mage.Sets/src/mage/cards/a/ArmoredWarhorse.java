

package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Loki
 */
public final class ArmoredWarhorse extends CardImpl {

    public ArmoredWarhorse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{W}");
        this.subtype.add(SubType.HORSE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
    }

    private ArmoredWarhorse(final ArmoredWarhorse card) {
        super(card);
    }

    @Override
    public ArmoredWarhorse copy() {
        return new ArmoredWarhorse(this);
    }

}
