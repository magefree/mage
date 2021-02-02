
package mage.cards.s;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class ShivanReef extends CardImpl {

    public ShivanReef(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Tap: Add 1.
        this.addAbility(new ColorlessManaAbility());

        //Tap: Add Blue or Red. Shivan Reef deals 1 damage to you.
        Ability blueManaAbility = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlueMana(1), new TapSourceCost());
        blueManaAbility.addEffect(new DamageControllerEffect(1));
        this.addAbility(blueManaAbility);
        Ability redManaAbility = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.RedMana(1), new TapSourceCost());
        redManaAbility.addEffect(new DamageControllerEffect(1));
        this.addAbility(redManaAbility);
    }

    private ShivanReef(final ShivanReef card) {
        super(card);
    }

    @Override
    public ShivanReef copy() {
        return new ShivanReef(this);
    }
}
