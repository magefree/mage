package mage.cards.c;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author jmharmon
 */

public final class ClericOfLifesBond extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("another Cleric");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(SubType.CLERIC.getPredicate());
    }

    public ClericOfLifesBond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever another Cleric enters the battlefield under your control, you gain 1 life.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new GainLifeEffect(1), filter));

        // Whenever you gain life for the first time each turn, put a +1/+1 counter on Cleric of Life’s Bond.
        this.addAbility(new ClericOfLifesBondCounterTriggeredAbility());

    }

    public ClericOfLifesBond(final ClericOfLifesBond card) {
        super(card);
    }

    @Override
    public ClericOfLifesBond copy() {
        return new ClericOfLifesBond(this);
    }
}

class ClericOfLifesBondCounterTriggeredAbility extends TriggeredAbilityImpl {

    private boolean triggeredOnce = false;

    ClericOfLifesBondCounterTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false);
    }

    private ClericOfLifesBondCounterTriggeredAbility(final ClericOfLifesBondCounterTriggeredAbility ability) {
        super(ability);
        this.triggeredOnce = ability.triggeredOnce;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.GAINED_LIFE
                || event.getType() == GameEvent.EventType.END_PHASE_POST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.END_PHASE_POST) {
            triggeredOnce = false;
            return false;
        }
        if (event.getType() != GameEvent.EventType.GAINED_LIFE
                || !event.getPlayerId().equals(getControllerId())) {
            return false;
        }
        if (triggeredOnce) {
            return false;
        }
        triggeredOnce = true;
        return true;
    }

    @Override
    public String getRule() {
        return "When you gain life for the first time each turn, put a +1/+1 counter on {this}.";
    }

    @Override
    public ClericOfLifesBondCounterTriggeredAbility copy() {
        return new ClericOfLifesBondCounterTriggeredAbility(this);
    }
}
