
package mage.cards.w;

import java.util.UUID;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.condition.common.CreatureCountCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.filter.FilterPermanent;

/**
 *
 * @author L_J
 */
public final class WitheringWisps extends CardImpl {

    private static final String ruleText = "At the beginning of the end step, if no creatures are on the battlefield, sacrifice {this}.";

    public WitheringWisps(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{B}");

        // At the beginning of the end step, if no creatures are on the battlefield, sacrifice Withering Wisps.
        TriggeredAbility triggered = new OnEventTriggeredAbility(GameEvent.EventType.END_TURN_STEP_PRE, "beginning of the end step", true, new SacrificeSourceEffect());
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(triggered, new CreatureCountCondition(0, TargetController.ANY), ruleText));

        // {B}: Withering Wisps deals 1 damage to each creature and each player. Activate this ability no more times each turn than the number of snow Swamps you control.
        this.addAbility(new WitheringWispsActivatedAbility());
    }

    private WitheringWisps(final WitheringWisps card) {
        super(card);
    }

    @Override
    public WitheringWisps copy() {
        return new WitheringWisps(this);
    }
}

class WitheringWispsActivatedAbility extends ActivatedAbilityImpl {

    private static final FilterPermanent filter = new FilterPermanent("snow Swamps you control");

    static {
        filter.add(SuperType.SNOW.getPredicate());
        filter.add(SubType.SWAMP.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    @Override
    public int getMaxActivationsPerTurn(Game game) {
        return game.getBattlefield().getAllActivePermanents(filter, game).size();
    }

    public WitheringWispsActivatedAbility() {
        super(Zone.BATTLEFIELD, new DamageEverythingEffect(1), new ManaCostsImpl<>("{B}"));

    }

    public WitheringWispsActivatedAbility(final WitheringWispsActivatedAbility ability) {
        super(ability);
    }

    @Override
    public String getRule() {
        return super.getRule() + " Activate this ability no more times each turn than the number of snow Swamps you control.";
    }

    @Override
    public WitheringWispsActivatedAbility copy() {
        return new WitheringWispsActivatedAbility(this);
    }
}
