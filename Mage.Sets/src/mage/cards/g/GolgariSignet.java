

package mage.cards.g;

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
public final class GolgariSignet extends CardImpl {

    public GolgariSignet (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(0, 0, 1, 0, 1, 0, 0, 0), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private GolgariSignet(final GolgariSignet card) {
        super(card);
    }

    @Override
    public GolgariSignet copy() {
        return new GolgariSignet(this);
    }
}