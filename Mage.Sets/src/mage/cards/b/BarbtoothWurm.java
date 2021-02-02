
package mage.cards.b;

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
public final class BarbtoothWurm extends CardImpl {

    public BarbtoothWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}");
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(6);
        this.toughness = new MageInt(4);
    }

    private BarbtoothWurm(final BarbtoothWurm card) {
        super(card);
    }

    @Override
    public BarbtoothWurm copy() {
        return new BarbtoothWurm(this);
    }
}
