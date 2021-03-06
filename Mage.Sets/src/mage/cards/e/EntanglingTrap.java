package mage.cards.e;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class EntanglingTrap extends CardImpl {

    public EntanglingTrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // Whenever you clash, tap target creature an opponent controls. If you won, that creature doesn't untap during its controller's next untap step.
        this.addAbility(new EntanglingTrapTriggeredAbility());
    }

    private EntanglingTrap(final EntanglingTrap card) {
        super(card);
    }

    @Override
    public EntanglingTrap copy() {
        return new EntanglingTrap(this);
    }
}

class EntanglingTrapTriggeredAbility extends TriggeredAbilityImpl {

    EntanglingTrapTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
        this.addTarget(new TargetOpponentsCreaturePermanent());
    }

    private EntanglingTrapTriggeredAbility(final EntanglingTrapTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EntanglingTrapTriggeredAbility copy() {
        return new EntanglingTrapTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CLASHED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId())) {
            return false;
        }
        this.getEffects().clear();
        this.addEffect(new TapTargetEffect());
        if (event.getFlag()) {
            this.addEffect(new DontUntapInControllersNextUntapStepTargetEffect());
        }
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever you clash, tap target creature an opponent controls. " +
                "If you won, that creature doesn't untap during its controller's next untap step.";
    }
}
