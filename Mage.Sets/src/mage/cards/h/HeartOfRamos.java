
package mage.cards.h;

import java.util.UUID;
import mage.Mana;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author Jgod
 */
public final class HeartOfRamos extends CardImpl {

    public HeartOfRamos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {tap}: Add {R}.
        this.addAbility(new RedManaAbility());
        
        // Sacrifice Heart of Ramos: Add {R}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.RedMana(1), new SacrificeSourceCost()));
    }

    private HeartOfRamos(final HeartOfRamos card) {
        super(card);
    }

    @Override
    public HeartOfRamos copy() {
        return new HeartOfRamos(this);
    }
}
