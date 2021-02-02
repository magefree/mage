
package mage.cards.t;

import java.util.UUID;
import mage.Mana;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author anonymous
 */
public final class ThranDynamo extends CardImpl {

    public ThranDynamo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // {tap}: Add {C}{C}{C}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.ColorlessMana(3), new TapSourceCost()));
        
    }

    private ThranDynamo(final ThranDynamo card) {
        super(card);
    }

    @Override
    public ThranDynamo copy() {
        return new ThranDynamo(this);
    }
}
