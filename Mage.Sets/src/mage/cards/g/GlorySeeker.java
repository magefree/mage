

package mage.cards.g;

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
public final class GlorySeeker extends CardImpl {

    public GlorySeeker (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
    }

    private GlorySeeker(final GlorySeeker card) {
        super(card);
    }

    @Override
    public GlorySeeker copy() {
        return new GlorySeeker(this);
    }

}
