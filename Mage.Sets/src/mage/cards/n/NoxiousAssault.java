package mage.cards.n;

import java.util.UUID;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.counter.AddPoisonCounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author AhmadYProjects
 */
public final class NoxiousAssault extends CardImpl {

    public NoxiousAssault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}{G}");
        

        // Creatures you control get +2/+2 until end of turn. Whenever a creature blocks this turn, its controller gets a poison counter.
        this.getSpellAbility().addEffect(new BoostControlledEffect(2,2, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(
                new NoxiousAssaultDelayedTriggerAbility()));
    }

    private NoxiousAssault(final NoxiousAssault card) {
        super(card);
    }

    @Override
    public NoxiousAssault copy() {
        return new NoxiousAssault(this);
    }
}

class NoxiousAssaultDelayedTriggerAbility extends DelayedTriggeredAbility {
    NoxiousAssaultDelayedTriggerAbility() {
        super(new AddPoisonCounterTargetEffect(1),Duration.EndOfTurn,false);
    }

    private NoxiousAssaultDelayedTriggerAbility(final NoxiousAssaultDelayedTriggerAbility ability){
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATURE_BLOCKS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(permanent.getControllerId()));
        return true;
    }

    @Override
    public NoxiousAssaultDelayedTriggerAbility copy(){
        return new NoxiousAssaultDelayedTriggerAbility();
    }

    @Override
    public String getRule(){
        return "Whenever a creature blocks this turn, it's controller gets a poison counter.";
    }

}
