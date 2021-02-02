
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Plopman
 */
public final class PhyrexianLens extends CardImpl {

    public PhyrexianLens(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {tap}, Pay 1 life: Add one mana of any color.
        Ability ability = new AnyColorManaAbility();
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);
    }

    private PhyrexianLens(final PhyrexianLens card) {
        super(card);
    }

    @Override
    public PhyrexianLens copy() {
        return new PhyrexianLens(this);
    }
}
