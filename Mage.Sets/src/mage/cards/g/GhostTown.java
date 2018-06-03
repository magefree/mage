
package mage.cards.g;

import java.util.UUID;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author emerald000
 */
public final class GhostTown extends CardImpl {

    public GhostTown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        
        // {0}: Return Ghost Town to its owner's hand. Activate this ability only if it's not your turn.
        this.addAbility(new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandSourceEffect(true), new GenericManaCost(0), new InvertCondition(MyTurnCondition.instance)));
    }

    public GhostTown(final GhostTown card) {
        super(card);
    }

    @Override
    public GhostTown copy() {
        return new GhostTown(this);
    }
}
