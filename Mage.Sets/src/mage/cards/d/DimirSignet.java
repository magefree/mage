

package mage.cards.d;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class DimirSignet extends CardImpl {

    public DimirSignet (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(0, 1, 1, 0, 0, 0, 0, 0), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private DimirSignet(final DimirSignet card) {
        super(card);
    }

    @Override
    public DimirSignet copy() {
        return new DimirSignet(this);
    }
}