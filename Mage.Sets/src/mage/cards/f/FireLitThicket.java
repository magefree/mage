
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
 * @author jonubuu
 */
public final class FireLitThicket extends CardImpl {

    public FireLitThicket(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {RG}, {tap}: Add {R}{R}, {R}{G}, or {G}{G}.
        SimpleManaAbility ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.RedMana(2), new ManaCostsImpl<>("{R/G}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        ability = new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(0, 0, 0, 1, 1, 0, 0, 0), new ManaCostsImpl<>("{R/G}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.GreenMana(2), new ManaCostsImpl<>("{R/G}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private FireLitThicket(final FireLitThicket card) {
        super(card);
    }

    @Override
    public FireLitThicket copy() {
        return new FireLitThicket(this);
    }
}
