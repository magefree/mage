

package mage.cards.r;

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
public final class RuneclawBear extends CardImpl {

    public RuneclawBear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.BEAR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

    }

    private RuneclawBear(final RuneclawBear card) {
        super(card);
    }

    @Override
    public RuneclawBear copy() {
        return new RuneclawBear(this);
    }

}
