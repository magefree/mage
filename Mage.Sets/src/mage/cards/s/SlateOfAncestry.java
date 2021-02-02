
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardHandCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class SlateOfAncestry extends CardImpl {

    public SlateOfAncestry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // {4}, {tap}, Discard your hand: Draw a card for each creature you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, 
                new DrawCardSourceControllerEffect(new PermanentsOnBattlefieldCount(StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED)),
                new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardHandCost());
        this.addAbility(ability);

    }

    private SlateOfAncestry(final SlateOfAncestry card) {
        super(card);
    }

    @Override
    public SlateOfAncestry copy() {
        return new SlateOfAncestry(this);
    }
}
