
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
public final class ScaledWurm extends CardImpl {

    public ScaledWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{7}{G}");
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(7);
        this.toughness = new MageInt(6);
    }

    private ScaledWurm(final ScaledWurm card) {
        super(card);
    }

    @Override
    public ScaledWurm copy() {
        return new ScaledWurm(this);
    }
}
