

package mage.cards.r;

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
public final class RuggedPrairie extends CardImpl {

    public RuggedPrairie (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        
        // {R/W}, {tap}: Add {R}{R}, {R}{W}, or {W}{W}.
        SimpleManaAbility ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.RedMana(2), new ManaCostsImpl<>("{R/W}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        ability = new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(1, 0, 0, 1, 0, 0, 0, 0), new ManaCostsImpl<>("{R/W}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.WhiteMana(2), new ManaCostsImpl<>("{R/W}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);          
    }

    public RuggedPrairie (final RuggedPrairie card) {
        super(card);
    }

    @Override
    public RuggedPrairie copy() {
        return new RuggedPrairie(this);
    }
}
