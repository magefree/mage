package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author jmharmon
 */

public final class SimicAscendancy extends CardImpl {

    public SimicAscendancy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}{U}");

        // {1}{G}{U}: Put a +1/+1 counter on target creature you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P1P1.createInstance()), new ManaCostsImpl<>("{1}{G}{U}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Whenever one or more +1/+1 counters are put on a creature you control, put that many growth counters on Simic Ascendancy.
        this.addAbility(new SimicAscendancyTriggeredAbility());

        // At the beginning of your upkeep, if Simic Ascendancy has twenty or more growth counters on it, you win the game.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(
                        Zone.BATTLEFIELD, new WinGameSourceControllerEffect(),
                        TargetController.YOU, false
                ), new SourceHasCounterCondition(CounterType.GROWTH, 20, Integer.MAX_VALUE),
                "At the beginning of your upkeep, if {this} has twenty " +
                        "or more growth counters on it, you win the game"
        ));
    }

    private SimicAscendancy(final SimicAscendancy card) {
        super(card);
    }

    @Override
    public SimicAscendancy copy() {
        return new SimicAscendancy(this);
    }
}

class SimicAscendancyTriggeredAbility extends TriggeredAbilityImpl {

    SimicAscendancyTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.GROWTH.createInstance()), false);
    }

    private SimicAscendancyTriggeredAbility(final SimicAscendancyTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SimicAscendancyTriggeredAbility copy() {
        return new SimicAscendancyTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getData().equals(CounterType.P1P1.getName()) && event.getAmount() > 0) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (permanent == null) {
                permanent = game.getPermanentEntering(event.getTargetId());
            }
            if (permanent != null
                    && !event.getTargetId().equals(this.getSourceId())
                    && permanent.isCreature(game)
                    && permanent.isControlledBy(this.getControllerId())) {
                this.getEffects().clear();
                if (event.getAmount() > 0) {
                    this.addEffect(new AddCountersSourceEffect(CounterType.GROWTH.createInstance(event.getAmount())));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever one or more +1/+1 counters are put on a creature you control, put that many growth counters on {this}.";
    }
}
