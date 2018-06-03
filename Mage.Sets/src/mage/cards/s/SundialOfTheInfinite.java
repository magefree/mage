
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.EndTurnEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author nantuko
 */
public final class SundialOfTheInfinite extends CardImpl {

    public SundialOfTheInfinite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // {1}, {tap}: End the turn. Activate this ability only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new EndTurnEffect(), new GenericManaCost(1), MyTurnCondition.instance);
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public SundialOfTheInfinite(final SundialOfTheInfinite card) {
        super(card);
    }

    @Override
    public SundialOfTheInfinite copy() {
        return new SundialOfTheInfinite(this);
    }
}