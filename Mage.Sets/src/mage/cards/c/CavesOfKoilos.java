
package mage.cards.c;

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
public final class CavesOfKoilos extends CardImpl {

    public CavesOfKoilos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");


        // Tap: Add 1.
        this.addAbility(new ColorlessManaAbility());

        // Tap: Add White or Black. Caves of Koilos deals 1 damage to you.
        Ability whiteManaAbility = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.WhiteMana(1), new TapSourceCost());
        whiteManaAbility.addEffect(new DamageControllerEffect(1));
        this.addAbility(whiteManaAbility);
        Ability blackManaAbility = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlackMana(1), new TapSourceCost());
        blackManaAbility.addEffect(new DamageControllerEffect(1));
        this.addAbility(blackManaAbility);
    }

    private CavesOfKoilos(final CavesOfKoilos card) {
        super(card);
    }

    @Override
    public CavesOfKoilos copy() {
        return new CavesOfKoilos(this);
    }
}
