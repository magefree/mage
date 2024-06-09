
package mage.cards.p;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author ilcartographer
 */
public final class PainMagnification extends CardImpl {

    public PainMagnification(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}{R}");

        // Whenever an opponent is dealt 3 or more damage by a single source, that player discards a card.
        this.addAbility(new PainMagnificationTriggeredAbility());
    }

    private PainMagnification(final PainMagnification card) {
        super(card);
    }

    @Override
    public PainMagnification copy() {
        return new PainMagnification(this);
    }
}

class PainMagnificationTriggeredAbility extends TriggeredAbilityImpl {
    
    public PainMagnificationTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DiscardTargetEffect(1), false);
    }
    
    private PainMagnificationTriggeredAbility(final PainMagnificationTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public PainMagnificationTriggeredAbility copy() {
        return new PainMagnificationTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // If the damaged player is an opponent
        if (game.getOpponents(getControllerId()).contains(event.getPlayerId())) {
            if(event.getAmount() >= 3) {
                // If at least 3 damage is dealt, set the opponent as the Discard target
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                }
                return true;
            }                
        }
        return false;
    }
    
    @Override
    public String getRule() {
        return "Whenever an opponent is dealt 3 or more damage by a single source, that player discards a card.";
    }
}
