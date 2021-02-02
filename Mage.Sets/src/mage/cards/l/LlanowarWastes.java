
package mage.cards.l;

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
public final class LlanowarWastes extends CardImpl {

    public LlanowarWastes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Tap: Add 1.
        this.addAbility(new ColorlessManaAbility());

        // Tap: Add Black or Green. Llanowar Wastes deals 1 damage to you.
        Ability blackManaAbility = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlackMana(1), new TapSourceCost());
        blackManaAbility.addEffect(new DamageControllerEffect(1));
        this.addAbility(blackManaAbility);
        Ability greenManaAbility = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.GreenMana(1), new TapSourceCost());
        greenManaAbility.addEffect(new DamageControllerEffect(1));
        this.addAbility(greenManaAbility);
    }

    private LlanowarWastes(final LlanowarWastes card) {
        super(card);
    }

    @Override
    public LlanowarWastes copy() {
        return new LlanowarWastes(this);
    }
}
