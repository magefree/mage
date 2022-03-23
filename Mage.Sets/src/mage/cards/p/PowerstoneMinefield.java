
package mage.cards.p;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author vereena42
 */
public final class PowerstoneMinefield extends CardImpl {

    public PowerstoneMinefield(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{W}");

        // Whenever a creature attacks or blocks, Powerstone Minefield deals 2 damage to it.
        this.addAbility(new PowerstoneMinefieldTriggeredAbility());
    }

    private PowerstoneMinefield(final PowerstoneMinefield card) {
        super(card);
    }

    @Override
    public PowerstoneMinefield copy() {
        return new PowerstoneMinefield(this);
    }
}

class PowerstoneMinefieldTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public PowerstoneMinefieldTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(2), false);
    }

    public PowerstoneMinefieldTriggeredAbility(PowerstoneMinefieldTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED || event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (filter.match(permanent, getControllerId(), this, game)) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(permanent, game));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature attacks or blocks, {this} deals 2 damage to it.";
    }

    @Override
    public PowerstoneMinefieldTriggeredAbility copy() {
        return new PowerstoneMinefieldTriggeredAbility(this);
    }
}
