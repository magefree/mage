
package mage.cards.s;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author Plopman
 */
public final class SkycloudExpanse extends CardImpl {

    public SkycloudExpanse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {1}, {tap}: Add {W}{U}.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(1, 1, 0, 0, 0, 0, 0, 0), new ManaCostsImpl<>("{1}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private SkycloudExpanse(final SkycloudExpanse card) {
        super(card);
    }

    @Override
    public SkycloudExpanse copy() {
        return new SkycloudExpanse(this);
    }
}
