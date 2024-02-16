
package mage.cards.v;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class VesselOfVolatility extends CardImpl {

    public VesselOfVolatility(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}");

        // {1}{R}, Sacrifice Vessel of Volatility: Add {R}{R}{R}{R}.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.RedMana(4), new ManaCostsImpl<>("{1}{R}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private VesselOfVolatility(final VesselOfVolatility card) {
        super(card);
    }

    @Override
    public VesselOfVolatility copy() {
        return new VesselOfVolatility(this);
    }
}
