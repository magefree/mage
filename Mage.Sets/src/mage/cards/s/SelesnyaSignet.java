

package mage.cards.s;

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
public final class SelesnyaSignet extends CardImpl {

    public SelesnyaSignet (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(1, 0, 0, 0, 1, 0, 0, 0), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private SelesnyaSignet(final SelesnyaSignet card) {
        super(card);
    }

    @Override
    public SelesnyaSignet copy() {
        return new SelesnyaSignet(this);
    }
}