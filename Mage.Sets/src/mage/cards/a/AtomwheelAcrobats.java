package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DieRolledEvent;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author jimga150
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
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AtomwheelAcrobatsDieRollEffect(), new ManaCostsImpl<>("{2}{G}"));
        this.addAbility(ability);
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
        return "Whenever you roll a 1 or 2, put that many +1/+1 counters on Atomwheel Acrobats.";
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
        Effect effect = null;
        if (amount == 1) {
            effect = new AddCountersSourceEffect(CounterType.P1P1.createInstance(1));
        }
        if (amount == 2) {
            effect = new AddCountersSourceEffect(CounterType.P1P1.createInstance(2));
        }
        if (effect != null) {
            effect.apply(game, source);
            return true;
        }
        return amount >= 1;
    }
}

// Based on Mr. House, President and CEO
class AtomwheelAcrobatsDieRollEffect extends OneShotEffect {

    AtomwheelAcrobatsDieRollEffect() {
        super(Outcome.Benefit);
        this.staticText = "Roll a six-sided die.";
    }

    private AtomwheelAcrobatsDieRollEffect(final AtomwheelAcrobatsDieRollEffect effect) {
        super(effect);
    }

    @Override
    public AtomwheelAcrobatsDieRollEffect copy() {
        return new AtomwheelAcrobatsDieRollEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.rollDice(outcome, source, game, 6,
                    1, 0);
            return true;
        }
        return false;
    }
}
