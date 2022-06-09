
package mage.cards.w;

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
public final class WoodedBastion extends CardImpl {

    public WoodedBastion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {GW}, {tap}: Add {G}{G}, {G}{W}, or {W}{W}.
        SimpleManaAbility ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.GreenMana(2), new ManaCostsImpl<>("{G/W}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        ability = new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(1, 0, 0, 0, 1, 0, 0, 0), new ManaCostsImpl<>("{G/W}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.WhiteMana(2), new ManaCostsImpl<>("{G/W}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private WoodedBastion(final WoodedBastion card) {
        super(card);
    }

    @Override
    public WoodedBastion copy() {
        return new WoodedBastion(this);
    }
}
