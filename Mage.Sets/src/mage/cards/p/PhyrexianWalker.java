
package mage.cards.p;

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
public final class PhyrexianWalker extends CardImpl {

    public PhyrexianWalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{0}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);
    }

    private PhyrexianWalker(final PhyrexianWalker card) {
        super(card);
    }

    @Override
    public PhyrexianWalker copy() {
        return new PhyrexianWalker(this);
    }
}
