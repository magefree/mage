
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author jonubuu
 */
public final class HorizonCanopy extends CardImpl {

    public HorizonCanopy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {tap}, Pay 1 life: Add {G} or {W}.
        Ability ability1 = new GreenManaAbility();
        ability1.addCost(new PayLifeCost(1));
        this.addAbility(ability1);
        Ability ability2 = new WhiteManaAbility();
        ability2.addCost(new PayLifeCost(1));
        this.addAbility(ability2);
        // {1}, {tap}, Sacrifice Horizon Canopy: Draw a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private HorizonCanopy(final HorizonCanopy card) {
        super(card);
    }

    @Override
    public HorizonCanopy copy() {
        return new HorizonCanopy(this);
    }
}
