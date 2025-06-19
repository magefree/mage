package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author jmharmon
 */
public final class SimicAscendancy extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.GROWTH, 20);

    public SimicAscendancy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}{U}");

        // {1}{G}{U}: Put a +1/+1 counter on target creature you control.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), new ManaCostsImpl<>("{1}{G}{U}")
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // Whenever one or more +1/+1 counters are put on a creature you control, put that many growth counters on Simic Ascendancy.
        this.addAbility(new SimicAscendancyTriggeredAbility());

        // At the beginning of your upkeep, if Simic Ascendancy has twenty or more growth counters on it, you win the game.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new WinGameSourceControllerEffect()).withInterveningIf(condition));
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
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.GROWTH.createInstance(), SavedDamageValue.MANY), false);
        this.setTriggerPhrase("Whenever one or more +1/+1 counters are put on a creature you control, ");
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
        if (!event.getData().equals(CounterType.P1P1.getName()) || event.getAmount() < 1) {
            return false;
        }
        Permanent permanent = Optional
                .ofNullable(event)
                .map(GameEvent::getTargetId)
                .map(game::getPermanentOrLKIBattlefield)
                .orElseGet(() -> game.getPermanentEntering(event.getTargetId()));
        if (permanent == null
                || event.getTargetId().equals(this.getSourceId())
                || !permanent.isCreature(game)
                || !permanent.isControlledBy(this.getControllerId())) {
            return false;
        }
        this.getEffects().setValue("damage", event.getAmount());
        return true;
    }
}
