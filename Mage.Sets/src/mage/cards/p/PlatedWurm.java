
package mage.cards.p;

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
public final class PlatedWurm extends CardImpl {

    public PlatedWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);
    }

    private PlatedWurm(final PlatedWurm card) {
        super(card);
    }

    @Override
    public PlatedWurm copy() {
        return new PlatedWurm(this);
    }
}
