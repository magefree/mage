
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Alvin
 */
public final class CradleOfVitality extends CardImpl {

    public CradleOfVitality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{W}");


        // Whenever you gain life, you may pay {1}{W}. If you do, put a +1/+1 counter on target creature for each 1 life you gained.
        Ability ability = new CradleOfVitalityGainLifeTriggeredAbility();
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public CradleOfVitality(final CradleOfVitality card) {
        super(card);
    }

    @Override
    public CradleOfVitality copy() {
        return new CradleOfVitality(this);
    }
}


class CradleOfVitalityGainLifeTriggeredAbility extends TriggeredAbilityImpl {

    public CradleOfVitalityGainLifeTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CradleOfVitalityEffect(), false);
        addManaCost(new ManaCostsImpl("{1}{W}"));
    }

    public CradleOfVitalityGainLifeTriggeredAbility(final CradleOfVitalityGainLifeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CradleOfVitalityGainLifeTriggeredAbility copy() {
        return new CradleOfVitalityGainLifeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.GAINED_LIFE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.controllerId)) {
            this.getEffects().get(0).setValue("amount", event.getAmount());
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you gain life, you may pay {1}{W}. If you do, put a +1/+1 counter on target creature for each 1 life you gained";
    }
}

class CradleOfVitalityEffect extends OneShotEffect {

    public CradleOfVitalityEffect() {
        super(Outcome.Benefit);
        staticText = "Put a +1/+1 counter on target creature for each 1 life you gained";
    }

    public CradleOfVitalityEffect(final CradleOfVitalityEffect effect) {
        super(effect);
    }

    @Override
    public CradleOfVitalityEffect copy() {
        return new CradleOfVitalityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int affectedTargets = 0;
        Integer amount = (Integer) getValue("amount");
        for (UUID uuid : targetPointer.getTargets(game, source)) {
            Permanent permanent = game.getPermanent(uuid);
            permanent.addCounters(CounterType.P1P1.createInstance(amount), source, game);
            affectedTargets ++;
        }
        return affectedTargets > 0;
    }
}
