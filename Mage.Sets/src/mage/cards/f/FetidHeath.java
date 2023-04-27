

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
public final class FetidHeath extends CardImpl {

    public FetidHeath (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);
        
        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        
        // {W/B}, {T}: Add {W}{W}, {W}{B}, or {B}{B}.
        SimpleManaAbility ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.WhiteMana(2), new ManaCostsImpl<>("{W/B}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        ability = new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(1, 0, 1, 0, 0, 0, 0, 0), new ManaCostsImpl<>("{W/B}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlackMana(2), new ManaCostsImpl<>("{W/B}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);          
    }

    public FetidHeath (final FetidHeath card) {
        super(card);
    }

    @Override
    public FetidHeath copy() {
        return new FetidHeath(this);
    }

}
