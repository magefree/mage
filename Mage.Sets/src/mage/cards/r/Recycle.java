
package mage.cards.r;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SkipDrawStepEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect.HandSizeModification;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author emerald000
 */
public final class Recycle extends CardImpl {

    public Recycle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{G}{G}");

        // Skip your draw step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SkipDrawStepEffect())); 
        
        // Whenever you play a card, draw a card.
        this.addAbility(new RecycleTriggeredAbility());
        
        // Your maximum hand size is two.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MaximumHandSizeControllerEffect(2, Duration.WhileOnBattlefield, HandSizeModification.SET)));
    }

    private Recycle(final Recycle card) {
        super(card);
    }

    @Override
    public Recycle copy() {
        return new Recycle(this);
    }
}

class RecycleTriggeredAbility extends TriggeredAbilityImpl {
    
    RecycleTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), false);
    }
    
    RecycleTriggeredAbility(final RecycleTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public RecycleTriggeredAbility copy() {
        return new RecycleTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST || event.getType() == GameEvent.EventType.LAND_PLAYED;
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId());
    }
    
    @Override
    public String getRule() {
        return "Whenever you play a card, draw a card.";
    }
}
