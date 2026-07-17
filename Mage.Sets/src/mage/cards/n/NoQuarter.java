package mage.cards.n;

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
 * @author xenohedron
 */
public final class NoQuarter extends CardImpl {

    public NoQuarter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        // Whenever a creature becomes blocked by a creature with lesser power, destroy the blocking creature.
        this.addAbility(new NoQuarterTriggeredAbility(true));

        // Whenever a creature blocks a creature with lesser power, destroy the attacking creature.
        this.addAbility(new NoQuarterTriggeredAbility(false));

    }

    private NoQuarter(final NoQuarter card) {
        super(card);
    }

    @Override
    public NoQuarter copy() {
        return new NoQuarter(this);
    }
}

class NoQuarterTriggeredAbility extends TriggeredAbilityImpl {

    private final boolean destroyBlockingCreature;

    NoQuarterTriggeredAbility(boolean destroyBlockingCreature) {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect().setText(
                destroyBlockingCreature ? "destroy the blocking creature" : "destroy the attacking creature"
        ), false);
        setTriggerPhrase("Whenever a creature "
                + (destroyBlockingCreature ? "becomes blocked by" : "blocks")
                + " a creature with lesser power, ");
        this.destroyBlockingCreature = destroyBlockingCreature;
    }

    private NoQuarterTriggeredAbility(final NoQuarterTriggeredAbility ability) {
        super(ability);
        this.destroyBlockingCreature = ability.destroyBlockingCreature;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent attacker = game.getPermanent(event.getTargetId());
        Permanent blocker = game.getPermanent(event.getSourceId());
        if (attacker == null || blocker == null) {
            return false;
        }
        if (destroyBlockingCreature) {
            if (attacker.getPower().getValue() > blocker.getPower().getValue()) {
                getEffects().setTargetPointer(new FixedTarget(event.getSourceId(), game));
                return true;
            }
        } else {
            if (attacker.getPower().getValue() < blocker.getPower().getValue()) {
                getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
                return true;
            }
        }
        return false;
    }

    @Override
    public NoQuarterTriggeredAbility copy() {
        return new NoQuarterTriggeredAbility(this);
    }
}
