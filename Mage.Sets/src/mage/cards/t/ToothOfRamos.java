
package mage.cards.t;

import java.util.UUID;
import mage.Mana;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.mana.SimpleManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author Jgod
 */
public final class ToothOfRamos extends CardImpl {

    public ToothOfRamos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {tap}: Add {W}.
        this.addAbility(new WhiteManaAbility());
        
        // Sacrifice Tooth of Ramos: Add {W}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.WhiteMana(1), new SacrificeSourceCost()));
    }

    private ToothOfRamos(final ToothOfRamos card) {
        super(card);
    }

    @Override
    public ToothOfRamos copy() {
        return new ToothOfRamos(this);
    }
}
