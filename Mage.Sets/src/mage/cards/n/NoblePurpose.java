
package mage.cards.n;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author Quercitron
 */
public final class NoblePurpose extends CardImpl {

    public NoblePurpose(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{W}{W}");


        // Whenever a creature you control deals combat damage, you gain that much life.
        this.addAbility(new NoblePurposeTriggeredAbility());
    }

    public NoblePurpose(final NoblePurpose card) {
        super(card);
    }

    @Override
    public NoblePurpose copy() {
        return new NoblePurpose(this);
    }
}

class NoblePurposeTriggeredAbility extends TriggeredAbilityImpl {

    public NoblePurposeTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
    }
    
    public NoblePurposeTriggeredAbility(final NoblePurposeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public NoblePurposeTriggeredAbility copy() {
        return new NoblePurposeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_CREATURE 
                || event.getType() == EventType.DAMAGED_PLAYER 
                || event.getType() == EventType.DAMAGED_PLANESWALKER;
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedEvent damageEvent = (DamagedEvent) event;
        if (damageEvent.isCombatDamage()) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            if (permanent != null && permanent.isCreature()
                    && permanent.isControlledBy(this.getControllerId())) {
                this.getEffects().clear();
                this.getEffects().add(new GainLifeEffect(damageEvent.getAmount()));
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String getRule() {
        return "Whenever a creature you control deals combat damage, you gain that much life.";
    }
    
}
