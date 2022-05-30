
package mage.cards.b;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Plopman
 */
public final class BarrenMoor extends CardImpl {

    public BarrenMoor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Barren Moor enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {tap}: Add {B}.
        this.addAbility(new BlackManaAbility());
        // Cycling {B}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{B}")));
    }

    private BarrenMoor(final BarrenMoor card) {
        super(card);
    }

    @Override
    public BarrenMoor copy() {
        return new BarrenMoor(this);
    }
}
