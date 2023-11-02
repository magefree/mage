
package mage.cards.o;

import java.util.UUID;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.AwakenAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class OnduRising extends CardImpl {

    public OnduRising(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{W}");

        // Whenever a creature attacks this turn, it gains lifelink until end of turn.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new OnduRisingTriggeredAbility()));

        // Awaken 4—{4}{W}
        this.addAbility(new AwakenAbility(this, 4, "{4}{W}"));
    }

    private OnduRising(final OnduRising card) {
        super(card);
    }

    @Override
    public OnduRising copy() {
        return new OnduRising(this);
    }
}

class OnduRisingTriggeredAbility extends DelayedTriggeredAbility {

    public OnduRisingTriggeredAbility() {
        super(new GainAbilityTargetEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn), Duration.EndOfTurn, false);
    }

    private OnduRisingTriggeredAbility(final OnduRisingTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (permanent != null) {
            for (Effect effect : getEffects()) {
                effect.setTargetPointer(new FixedTarget(permanent, game));
            }
            return true;
        }
        return false;
    }

    @Override
    public OnduRisingTriggeredAbility copy() {
        return new OnduRisingTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a creature attacks this turn, it gains lifelink until end of turn.";
    }
}
