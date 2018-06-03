
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author CountAndromalius
 */
public final class SerumTank extends CardImpl {

    public SerumTank(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // Whenever {this} or another artifact comes into play, put a charge counter on {this}.
        Effect effect = new AddCountersSourceEffect(CounterType.CHARGE.createInstance());
        effect.setText("put a charge counter on {this}");
        this.addAbility(new SerumTankTriggeredAbility(effect));

        // {3}, {tap}, Remove a charge counter from {this}: Draw a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new ManaCostsImpl("{3}"));
        ability.addCost(new RemoveCountersSourceCost(CounterType.CHARGE.createInstance(1)));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public SerumTank(final SerumTank card) {
        super(card);
    }

    @Override
    public SerumTank copy() {
        return new SerumTank(this);
    }
}

class SerumTankTriggeredAbility extends TriggeredAbilityImpl {

    SerumTankTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
    }

    SerumTankTriggeredAbility(final SerumTankTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SerumTankTriggeredAbility copy() {
        return new SerumTankTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        UUID targetId = event.getTargetId();
        Permanent permanent = game.getPermanent(targetId);
        if (permanent.isArtifact()) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(permanent, game));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} or another artifact enters the battlefield, put a charge counter on {this}.";
    }
}
