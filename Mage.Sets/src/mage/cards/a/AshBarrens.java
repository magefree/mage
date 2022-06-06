
package mage.cards.a;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.BasicLandcyclingAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author spjspj
 */
public final class AshBarrens extends CardImpl {

    public AshBarrens(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        
        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // Basic landcycling {1}
        this.addAbility(new BasicLandcyclingAbility(new ManaCostsImpl<>("{1}")));
    }

    private AshBarrens(final AshBarrens card) {
        super(card);
    }

    @Override
    public AshBarrens copy() {
        return new AshBarrens(this);
    }
}
