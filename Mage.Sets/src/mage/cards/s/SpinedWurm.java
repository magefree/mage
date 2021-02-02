

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class SpinedWurm extends CardImpl {

    public SpinedWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

    }

    private SpinedWurm(final SpinedWurm card) {
        super(card);
    }

    @Override
    public SpinedWurm copy() {
        return new SpinedWurm(this);
    }

}
