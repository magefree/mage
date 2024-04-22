
package mage.cards.d;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class DeathPitsOfRath extends CardImpl {

    public DeathPitsOfRath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");

        // Whenever a creature is dealt damage, destroy it. It can't be regenerated.
        this.addAbility(new DeathPitsOfRathTriggeredAbility());
    }

    private DeathPitsOfRath(final DeathPitsOfRath card) {
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

    private DeathPitsOfRathTriggeredAbility(final DeathPitsOfRathTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public DeathPitsOfRathTriggeredAbility copy() {
        return new DeathPitsOfRathTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null || !permanent.isCreature(game)) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever a creature is dealt damage, destroy it. It can't be regenerated.";
    }
}
