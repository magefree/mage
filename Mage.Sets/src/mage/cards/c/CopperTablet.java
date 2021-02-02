
package mage.cards.c;

import java.util.UUID;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 *
 * @author KholdFuzion

 */
public final class CopperTablet extends CardImpl {

    public CopperTablet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // At the beginning of each player's upkeep, Copper Tablet deals 1 damage to that player.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1, true, "that player"), TargetController.ANY, false, true));        
    }

    private CopperTablet(final CopperTablet card) {
        super(card);
    }

    @Override
    public CopperTablet copy() {
        return new CopperTablet(this);
    }
}
