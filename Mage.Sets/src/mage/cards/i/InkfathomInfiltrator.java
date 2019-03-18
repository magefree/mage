
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class InkfathomInfiltrator extends CardImpl {

    public InkfathomInfiltrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U/B}{U/B}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(new CantBlockAbility());
        this.addAbility(new CantBeBlockedSourceAbility());
    }

    public InkfathomInfiltrator(final InkfathomInfiltrator card) {
        super(card);
    }

    @Override
    public InkfathomInfiltrator copy() {
        return new InkfathomInfiltrator(this);
    }
}
