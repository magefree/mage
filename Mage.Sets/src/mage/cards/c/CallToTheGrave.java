
package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.condition.common.CreatureCountCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 *
 * @author nantuko
 */
public final class CallToTheGrave extends CardImpl {

    private static final String ruleText = "At the beginning of the end step, if no creatures are on the battlefield, sacrifice {this}.";
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Zombie creature");

    static {
        filter.add(Predicates.not(SubType.ZOMBIE.getPredicate()));
    }

    public CallToTheGrave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{B}");

        // At the beginning of each player's upkeep, that player sacrifices a non-Zombie creature.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new SacrificeEffect(filter, 1, "that player"), TargetController.ANY, false);
        this.addAbility(ability);
        // At the beginning of the end step, if no creatures are on the battlefield, sacrifice Call to the Grave.
        TriggeredAbility triggered = new OnEventTriggeredAbility(GameEvent.EventType.END_TURN_STEP_PRE, "beginning of the end step", true, new SacrificeSourceEffect());
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(triggered, new CreatureCountCondition(0, TargetController.ANY), ruleText));
    }

    private CallToTheGrave(final CallToTheGrave card) {
        super(card);
    }

    @Override
    public CallToTheGrave copy() {
        return new CallToTheGrave(this);
    }
}
