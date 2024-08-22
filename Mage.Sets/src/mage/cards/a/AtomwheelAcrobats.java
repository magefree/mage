package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RollDiceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DieRolledEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author Sidorovich77
 */
public final class AtomwheelAcrobats extends CardImpl {

    public AtomwheelAcrobats(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.PERFORMER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever you roll a 1 or 2, put that many +1/+1 counters on Atomwheel Acrobats.
        this.addAbility(new AtomwheelAcrobatsTriggeredAbility());

        // {2}{G}: Roll a six-sided die.
        this.addAbility(new SimpleActivatedAbility(new RollDiceEffect(6), new ManaCostsImpl<>("{2}{G}")));
    }

    private AtomwheelAcrobats(final AtomwheelAcrobats card) {
        super(card);
    }

    @Override
    public AtomwheelAcrobats copy() {
        return new AtomwheelAcrobats(this);
    }
}

// Based on Mr. House, President and CEO
class AtomwheelAcrobatsTriggeredAbility extends TriggeredAbilityImpl {

    public AtomwheelAcrobatsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AtomwheelAcrobatsCountersEffect(), false);
    }

    private AtomwheelAcrobatsTriggeredAbility(final AtomwheelAcrobatsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AtomwheelAcrobatsTriggeredAbility copy() {
        return new AtomwheelAcrobatsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DIE_ROLLED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DieRolledEvent drEvent = (DieRolledEvent) event;
        if (this.isControlledBy(event.getTargetId()) && drEvent.getRollDieType() == RollDieType.NUMERICAL) {
            int result = drEvent.getResult();
            if (result == 1 || result == 2) {
                this.getEffects().setValue("rolled", result);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you roll a 1 or 2, put that many +1/+1 counters on {this}.";
    }
}

class AtomwheelAcrobatsCountersEffect extends OneShotEffect {

    AtomwheelAcrobatsCountersEffect() {
        super(Outcome.Benefit);
    }

    private AtomwheelAcrobatsCountersEffect(final AtomwheelAcrobatsCountersEffect effect) {
        super(effect);
    }

    @Override
    public AtomwheelAcrobatsCountersEffect copy() {
        return new AtomwheelAcrobatsCountersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (getValue("rolled") == null) {
            return false;
        }
        int amount = (Integer) getValue("rolled");
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        if (sourcePermanent == null) {
            return false;
        }
        sourcePermanent.addCounters(CounterType.P1P1.createInstance(amount), source, game);
        return true;
    }
}
