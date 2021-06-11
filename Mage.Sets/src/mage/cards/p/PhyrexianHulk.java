
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
public final class PhyrexianHulk extends CardImpl {

    public PhyrexianHulk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{6}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.GOLEM);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);
    }

    private PhyrexianHulk(final PhyrexianHulk card) {
        super(card);
    }

    @Override
    public PhyrexianHulk copy() {
        return new PhyrexianHulk(this);
    }
}
