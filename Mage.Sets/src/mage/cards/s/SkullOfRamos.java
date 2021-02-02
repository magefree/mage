
package mage.cards.s;

import java.util.UUID;
import mage.Mana;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author Jgod
 */
public final class SkullOfRamos extends CardImpl {

    public SkullOfRamos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {tap}: Add {B}.
        this.addAbility(new BlackManaAbility());
        
        // Sacrifice Skull of Ramos: Add {B}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlackMana(1), new SacrificeSourceCost()));
    }

    private SkullOfRamos(final SkullOfRamos card) {
        super(card);
    }

    @Override
    public SkullOfRamos copy() {
        return new SkullOfRamos(this);
    }
}
