
package mage.cards.t;

import java.util.UUID;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.condition.common.CreatureCountCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.game.events.GameEvent;

/**
 *
 * @author LevelX2
 */
public final class ThranQuarry extends CardImpl {

    public ThranQuarry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // At the beginning of the end step, if you control no creatures, sacrifice Thran Quarry.
        TriggeredAbility triggered = new OnEventTriggeredAbility(GameEvent.EventType.END_TURN_STEP_PRE, "beginning of the end step", true, new SacrificeSourceEffect());
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(triggered, new CreatureCountCondition(0, TargetController.YOU),
                "At the beginning of the end step, if you control no creatures, sacrifice {this}."));

        // {tap}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

    }

    private ThranQuarry(final ThranQuarry card) {
        super(card);
    }

    @Override
    public ThranQuarry copy() {
        return new ThranQuarry(this);
    }
}
