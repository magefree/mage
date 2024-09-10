
package mage.cards.f;

import java.util.UUID;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public final class FalseCure extends CardImpl {

    public FalseCure(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}{B}");


        // Until end of turn, whenever a player gains life, that player loses 2 life for each 1 life they gained.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new FalseCureTriggeredAbility()));
    }

    private FalseCure(final FalseCure card) {
        super(card);
    }

    @Override
    public FalseCure copy() {
        return new FalseCure(this);
    }
}

class FalseCureTriggeredAbility extends DelayedTriggeredAbility {
    
    FalseCureTriggeredAbility() {
        super(new LoseLifeTargetEffect(0), Duration.EndOfTurn, false);
    }
    
    private FalseCureTriggeredAbility(final FalseCureTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public FalseCureTriggeredAbility copy() {
        return new FalseCureTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.GAINED_LIFE;
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        this.getEffects().clear();
        Effect effect = new LoseLifeTargetEffect(2 * event.getAmount());
        effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
        this.addEffect(effect);
        return true;
    }
    
    @Override
    public String getRule() {
        return "Until end of turn, whenever a player gains life, that player loses 2 life for each 1 life they gained.";
    }
}
