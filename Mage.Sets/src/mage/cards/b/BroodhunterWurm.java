
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class BroodhunterWurm extends CardImpl {

    public BroodhunterWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);
    }

    private BroodhunterWurm(final BroodhunterWurm card) {
        super(card);
    }

    @Override
    public BroodhunterWurm copy() {
        return new BroodhunterWurm(this);
    }
}
