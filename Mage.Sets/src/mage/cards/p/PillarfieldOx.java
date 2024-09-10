

package mage.cards.p;

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
public final class PillarfieldOx extends CardImpl {

    public PillarfieldOx (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.OX);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);
    }

    private PillarfieldOx(final PillarfieldOx card) {
        super(card);
    }

    @Override
    public PillarfieldOx copy() {
        return new PillarfieldOx(this);
    }
}
