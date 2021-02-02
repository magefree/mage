
package mage.cards.y;

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
public final class YavimayaCoast extends CardImpl {

    public YavimayaCoast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Tap: Add 1.
        this.addAbility(new ColorlessManaAbility());

        // Tap: Add Green or Blue. Yavimaya Coast deals 1 damage to you.
        Ability greenManaAbility = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.GreenMana(1), new TapSourceCost());
        greenManaAbility.addEffect(new DamageControllerEffect(1));
        this.addAbility(greenManaAbility);
        Ability blueManaAbility = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlueMana(1), new TapSourceCost());
        blueManaAbility.addEffect(new DamageControllerEffect(1));
        this.addAbility(blueManaAbility);
    }

    private YavimayaCoast(final YavimayaCoast card) {
        super(card);
    }

    @Override
    public YavimayaCoast copy() {
        return new YavimayaCoast(this);
    }
}
