
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class ManaConfluence extends CardImpl {

    public ManaConfluence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {T}, Pay 1 life: Add one mana of any color.
        Ability ability = new AnyColorManaAbility();
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);
    }

    private ManaConfluence(final ManaConfluence card) {
        super(card);
    }

    @Override
    public ManaConfluence copy() {
        return new ManaConfluence(this);
    }
}
