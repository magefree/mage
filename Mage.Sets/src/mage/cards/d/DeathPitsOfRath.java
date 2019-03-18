
package mage.cards.d;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Plopman
 */
public final class DeathPitsOfRath extends CardImpl {

    public DeathPitsOfRath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{B}{B}");

        // Whenever a creature is dealt damage, destroy it. It can't be regenerated.
        this.addAbility(new DeathPitsOfRathTriggeredAbility());
    }

    public DeathPitsOfRath(final DeathPitsOfRath card) {
        super(card);
    }

    @Override
    public DeathPitsOfRath copy() {
        return new DeathPitsOfRath(this);
    }
}

class DeathPitsOfRathTriggeredAbility extends TriggeredAbilityImpl {

    public DeathPitsOfRathTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect(true));
    }

    public DeathPitsOfRathTriggeredAbility(final DeathPitsOfRathTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public DeathPitsOfRathTriggeredAbility copy() {
        return new DeathPitsOfRathTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_CREATURE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        for (Effect effect : this.getEffects()) {
            effect.setTargetPointer(new FixedTarget(event.getTargetId()));
        }
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever a creature is dealt damage, destroy it. It can't be regenerated.";
    }
}
