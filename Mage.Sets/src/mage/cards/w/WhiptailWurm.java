
package mage.cards.w;

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
public final class WhiptailWurm extends CardImpl {

    public WhiptailWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{G}");
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(8);
        this.toughness = new MageInt(5);
    }

    private WhiptailWurm(final WhiptailWurm card) {
        super(card);
    }

    @Override
    public WhiptailWurm copy() {
        return new WhiptailWurm(this);
    }
}
