package mage.cards.d;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Quercitron
 */
public final class DingusEgg extends CardImpl {

    public DingusEgg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // Whenever a land is put into a graveyard from the battlefield, Dingus Egg deals 2 damage to that land's controller.
        this.addAbility(new DingusEggTriggeredAbility());
    }

    private DingusEgg(final DingusEgg card) {
        super(card);
    }

    @Override
    public DingusEgg copy() {
        return new DingusEgg(this);
    }
}

class DingusEggTriggeredAbility extends TriggeredAbilityImpl {

    public DingusEggTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(2), false);
    }

    public DingusEggTriggeredAbility(final DingusEggTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.isDiesEvent()
                && zEvent.getTarget().isLand(game)) {
            if (getTargets().isEmpty()) {
                UUID targetControllerId = zEvent.getTarget().getControllerId();
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(targetControllerId));
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a land is put into a graveyard from the battlefield, {this} deals 2 damage to that land's controller";
    }

    @Override
    public DingusEggTriggeredAbility copy() {
        return new DingusEggTriggeredAbility(this);
    }
}
