
package mage.cards.s;

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
public final class StreetbreakerWurm extends CardImpl {

    public StreetbreakerWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{G}");
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(6);
        this.toughness = new MageInt(4);
    }

    private StreetbreakerWurm(final StreetbreakerWurm card) {
        super(card);
    }

    @Override
    public StreetbreakerWurm copy() {
        return new StreetbreakerWurm(this);
    }
}
