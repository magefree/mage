

package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class TowerOfEons extends CardImpl {

    public TowerOfEons (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(10), new GenericManaCost(8));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private TowerOfEons(final TowerOfEons card) {
        super(card);
    }

    @Override
    public TowerOfEons copy() {
        return new TowerOfEons(this);
    }

}
