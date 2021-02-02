

package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class DuskdaleWurm extends CardImpl {

    public DuskdaleWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}{G}");
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        this.addAbility(TrampleAbility.getInstance());
    }

    private DuskdaleWurm(final DuskdaleWurm card) {
        super(card);
    }

    @Override
    public DuskdaleWurm copy() {
        return new DuskdaleWurm(this);
    }

}

