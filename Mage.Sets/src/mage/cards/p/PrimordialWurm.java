
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class PrimordialWurm extends CardImpl {

    public PrimordialWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(7);
        this.toughness = new MageInt(6);
    }

    private PrimordialWurm(final PrimordialWurm card) {
        super(card);
    }

    @Override
    public PrimordialWurm copy() {
        return new PrimordialWurm(this);
    }
}
