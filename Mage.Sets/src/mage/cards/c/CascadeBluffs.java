

package mage.cards.c;

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
 * @author Loki
 */
public final class CascadeBluffs extends CardImpl {

    public CascadeBluffs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);
        
        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {U/R}, {tap}: Add {U}{U}, {U}{R}, or {R}{R}.
        SimpleManaAbility ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlueMana(2), new ManaCostsImpl<>("{U/R}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        ability = new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(0, 1, 0, 1, 0, 0, 0, 0), new ManaCostsImpl<>("{U/R}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.RedMana(2), new ManaCostsImpl<>("{U/R}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);        
    }

    private CascadeBluffs(final CascadeBluffs card) {
        super(card);
    }

    @Override
    public CascadeBluffs copy() {
        return new CascadeBluffs(this);
    }
}
