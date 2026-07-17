package mage.cards.s;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.CountersAddedFirstTimeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StalwartSuccessor extends CardImpl {

    public StalwartSuccessor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever one or more counters are put on a creature you control, if it's the first time counters have been put on that creature this turn, put a +1/+1 counter on that creature.
        this.addAbility(new StalwartSuccessorTriggeredAbility());
    }

    private StalwartSuccessor(final StalwartSuccessor card) {
        super(card);
    }

    @Override
    public StalwartSuccessor copy() {
        return new StalwartSuccessor(this);
    }
}

class StalwartSuccessorTriggeredAbility extends TriggeredAbilityImpl {

    StalwartSuccessorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.setTriggerPhrase("Whenever one or more counters are put on a creature you control, " +
                "if it's the first time counters have been put on that creature this turn, ");
        this.addWatcher(new CountersAddedFirstTimeWatcher());
    }

    private StalwartSuccessorTriggeredAbility(final StalwartSuccessorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public StalwartSuccessorTriggeredAbility copy() {
        return new StalwartSuccessorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        int zccOffset = 0;
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null) {
            permanent = game.getPermanentEntering(event.getTargetId());
            zccOffset = 1;
        }
        if (permanent == null
                || !permanent.isCreature(game)
                || !permanent.isControlledBy(getControllerId())
                || !CountersAddedFirstTimeWatcher.checkEvent(event, permanent, game, zccOffset)) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTarget(permanent.getId(), permanent.getZoneChangeCounter(game) + zccOffset));
        return true;
    }
}
