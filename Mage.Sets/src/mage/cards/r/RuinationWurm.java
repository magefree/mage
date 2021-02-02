
package mage.cards.r;

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
public final class RuinationWurm extends CardImpl {

    public RuinationWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{G}");
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(7);
        this.toughness = new MageInt(6);
    }

    private RuinationWurm(final RuinationWurm card) {
        super(card);
    }

    @Override
    public RuinationWurm copy() {
        return new RuinationWurm(this);
    }
}
