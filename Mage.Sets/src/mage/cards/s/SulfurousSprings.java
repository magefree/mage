
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
public final class SulfurousSprings extends CardImpl {

    public SulfurousSprings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        this.addAbility(new ColorlessManaAbility());

        Ability blackManaAbility = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlackMana(1), new TapSourceCost());
        blackManaAbility.addEffect(new DamageControllerEffect(1));
        this.addAbility(blackManaAbility);
        Ability redManaAbility = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.RedMana(1), new TapSourceCost());
        redManaAbility.addEffect(new DamageControllerEffect(1));
        this.addAbility(redManaAbility);
    }

    private SulfurousSprings(final SulfurousSprings card) {
        super(card);
    }

    @Override
    public SulfurousSprings copy() {
        return new SulfurousSprings(this);
    }
}
