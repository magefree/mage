
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RemoveAllCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author TheElk801
 */
public final class BloodHound extends CardImpl {

    public BloodHound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HOUND);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever you're dealt damage, you may put that many +1/+1 counters on Blood Hound.
        this.addAbility(new BloodHoundTriggeredAbility());

        // At the beginning of your end step, remove all +1/+1 counters from Blood Hound.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new RemoveAllCountersSourceEffect(CounterType.P1P1), TargetController.YOU, false));
    }

    public BloodHound(final BloodHound card) {
        super(card);
    }

    @Override
    public BloodHound copy() {
        return new BloodHound(this);
    }
}

class BloodHoundTriggeredAbility extends TriggeredAbilityImpl {

    public BloodHoundTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BloodHoundEffect(), true);
    }

    public BloodHoundTriggeredAbility(final BloodHoundTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BloodHoundTriggeredAbility copy() {
        return new BloodHoundTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getControllerId())) {
            this.getEffects().get(0).setValue("damageAmount", event.getAmount());
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you are dealt damage, you may put that many +1/+1 counters on {this}.";
    }
}

class BloodHoundEffect extends OneShotEffect {

    public BloodHoundEffect() {
        super(Outcome.Benefit);
    }

    public BloodHoundEffect(final BloodHoundEffect effect) {
        super(effect);
    }

    @Override
    public BloodHoundEffect copy() {
        return new BloodHoundEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            permanent.addCounters(CounterType.P1P1.createInstance((Integer) this.getValue("damageAmount")), source, game);
        }
        return true;
    }
}
