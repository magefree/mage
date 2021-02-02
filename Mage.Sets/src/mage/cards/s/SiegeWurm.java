
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ConvokeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author jonubuu
 */
public final class SiegeWurm extends CardImpl {

    public SiegeWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}{G}");
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Convoke
        this.addAbility(new ConvokeAbility());
        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private SiegeWurm(final SiegeWurm card) {
        super(card);
    }

    @Override
    public SiegeWurm copy() {
        return new SiegeWurm(this);
    }
}
