
package mage.cards.p;

import java.util.UUID;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.CreatureCountCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.events.GameEvent;

/**
 *
 * @author fireshoes
 */
public final class Pyrohemia extends CardImpl {

    private static final String ruleText = "At the beginning of the end step, if no creatures are on the battlefield, sacrifice {this}.";

    public Pyrohemia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}{R}");

        // At the beginning of the end step, if no creatures are on the battlefield, sacrifice Pyrohemia.
        TriggeredAbility triggered = new OnEventTriggeredAbility(GameEvent.EventType.END_TURN_STEP_PRE, "beginning of the end step", true, new SacrificeSourceEffect());
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(triggered, new CreatureCountCondition(0, TargetController.ANY), ruleText));

        // {R}: Pyrohemia deals 1 damage to each creature and each player.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageEverythingEffect(1), new ManaCostsImpl<>("{R}")));
    }

    private Pyrohemia(final Pyrohemia card) {
        super(card);
    }

    @Override
    public Pyrohemia copy() {
        return new Pyrohemia(this);
    }
}
