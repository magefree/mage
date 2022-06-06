
package mage.cards.g;

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
public final class GravenCairns extends CardImpl {

    public GravenCairns(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        
        // {B/R}, {tap}: Add {B}{B}, {B}{R}, or {R}{R}.
        SimpleManaAbility ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlackMana(2), new ManaCostsImpl<>("{B/R}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        ability = new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(0, 0, 1, 1, 0, 0, 0, 0), new ManaCostsImpl<>("{B/R}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.RedMana(2), new ManaCostsImpl<>("{B/R}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);          
    }

    private GravenCairns(final GravenCairns card) {
        super(card);
    }

    @Override
    public GravenCairns copy() {
        return new GravenCairns(this);
    }
}
