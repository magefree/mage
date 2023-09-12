

package mage.cards.m;

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
public final class MoriokReaver extends CardImpl {

    public MoriokReaver (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
    }

    private MoriokReaver(final MoriokReaver card) {
        super(card);
    }

    @Override
    public MoriokReaver copy() {
        return new MoriokReaver(this);
    }

}
