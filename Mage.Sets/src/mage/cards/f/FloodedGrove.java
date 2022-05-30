

package mage.cards.f;

import java.util.UUID;
import mage.Mana;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
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
public final class FloodedGrove extends CardImpl {

    public FloodedGrove (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);
        
        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        
        // {(G/U)}, {T}: Add {G}{G}, {G}{U}, or {U}{U}. 
        SimpleManaAbility ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.GreenMana(2), new ManaCostsImpl<>("{G/U}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        ability = new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(0, 1, 0, 0, 1, 0, 0, 0), new ManaCostsImpl<>("{G/U}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlueMana(2), new ManaCostsImpl<>("{G/U}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);           
        
    }

    public FloodedGrove (final FloodedGrove card) {
        super(card);
    }

    @Override
    public FloodedGrove copy() {
        return new FloodedGrove(this);
    }

}
