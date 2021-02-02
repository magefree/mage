
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class TowerOfFortunes extends CardImpl {

    public TowerOfFortunes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // {8}, {T} : Draw four cards.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(4), new GenericManaCost(8));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private TowerOfFortunes(final TowerOfFortunes card) {
        super(card);
    }

    @Override
    public TowerOfFortunes copy() {
        return new TowerOfFortunes(this);
    }

}
