
package mage.cards.s;

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
 * @author jonubuu
 */
public final class SunkenRuins extends CardImpl {

    public SunkenRuins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {UB}, {tap}: Add {U}{U}, {U}{B}, or {B}{B}.
        SimpleManaAbility ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlueMana(2), new ManaCostsImpl<>("{U/B}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        ability = new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(0, 1, 1, 0, 0, 0, 0, 0), new ManaCostsImpl<>("{U/B}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlackMana(2), new ManaCostsImpl<>("{U/B}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private SunkenRuins(final SunkenRuins card) {
        super(card);
    }

    @Override
    public SunkenRuins copy() {
        return new SunkenRuins(this);
    }
}
