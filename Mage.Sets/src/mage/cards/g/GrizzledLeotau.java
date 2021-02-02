
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
public final class GrizzledLeotau extends CardImpl {

    public GrizzledLeotau(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{W}");
        this.subtype.add(SubType.CAT);



        this.power = new MageInt(1);
        this.toughness = new MageInt(5);
    }

    private GrizzledLeotau(final GrizzledLeotau card) {
        super(card);
    }

    @Override
    public GrizzledLeotau copy() {
        return new GrizzledLeotau(this);
    }
}
