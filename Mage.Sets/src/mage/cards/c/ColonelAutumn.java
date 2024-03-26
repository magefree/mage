package mage.cards.c;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.constants.*;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.ExploitAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author Cguy7777
 */
public final class ColonelAutumn extends CardImpl {

    public ColonelAutumn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Exploit
        this.addAbility(new ExploitAbility());

        // Other legendary creatures you control have exploit.
        this.addAbility(new SimpleStaticAbility(
                new GainAbilityControlledEffect(
                        new ExploitAbility(),
                        Duration.WhileOnBattlefield,
                        StaticFilters.FILTER_CREATURES_LEGENDARY,
                        true)));

        // Whenever a creature you control exploits a creature, put a +1/+1 counter on each creature you control.
        this.addAbility(new ColonelAutumnTriggeredAbility());
    }

    private ColonelAutumn(final ColonelAutumn card) {
        super(card);
    }

    @Override
    public ColonelAutumn copy() {
        return new ColonelAutumn(this);
    }
}

// Based on SkullSkaabTriggeredAbility
class ColonelAutumnTriggeredAbility extends TriggeredAbilityImpl {

    ColonelAutumnTriggeredAbility() {
        super(Zone.BATTLEFIELD,
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED));
        setTriggerPhrase("Whenever a creature you control exploits a creature, ");
    }

    private ColonelAutumnTriggeredAbility(final ColonelAutumnTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.EXPLOITED_CREATURE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent exploiter = game.getPermanentOrLKIBattlefield(event.getSourceId());
        Permanent exploited = game.getPermanentOrLKIBattlefield(event.getTargetId());

        return exploiter != null && exploited != null
                && exploiter.isCreature(game)
                && exploited.isCreature(game)
                && exploiter.isControlledBy(this.getControllerId());
    }

    @Override
    public ColonelAutumnTriggeredAbility copy() {
        return new ColonelAutumnTriggeredAbility(this);
    }
}
