
package mage.cards.h;

import java.util.UUID;
import mage.Mana;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author Jgod
 */
public final class HornOfRamos extends CardImpl {

    public HornOfRamos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {tap}: Add {G}.
        this.addAbility(new GreenManaAbility());
        
        // Sacrifice Horn of Ramos: Add {G}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.GreenMana(1), new SacrificeSourceCost()));
    }

    private HornOfRamos(final HornOfRamos card) {
        super(card);
    }

    @Override
    public HornOfRamos copy() {
        return new HornOfRamos(this);
    }
}
