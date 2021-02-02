
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class RootbreakerWurm extends CardImpl {

    public RootbreakerWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}{G}");
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        this.addAbility(TrampleAbility.getInstance());
    }

    private RootbreakerWurm(final RootbreakerWurm card) {
        super(card);
    }

    @Override
    public RootbreakerWurm copy() {
        return new RootbreakerWurm(this);
    }
}
