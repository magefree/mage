
package mage.cards.b;

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
public final class BattlefieldForge extends CardImpl {

    public BattlefieldForge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        
        // Tap: Add 1.
        this.addAbility(new ColorlessManaAbility());

        // Tap: Add Red or White. Battlefield Forge deals 1 damage to you.
        Ability redManaAbility = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.RedMana(1), new TapSourceCost());
        redManaAbility.addEffect(new DamageControllerEffect(1));
        this.addAbility(redManaAbility);
        Ability whiteManaAbility = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.WhiteMana(1), new TapSourceCost());
        whiteManaAbility.addEffect(new DamageControllerEffect(1));
        this.addAbility(whiteManaAbility);
    }

    private BattlefieldForge(final BattlefieldForge card) {
        super(card);
    }

    @Override
    public BattlefieldForge copy() {
        return new BattlefieldForge(this);
    }
}
