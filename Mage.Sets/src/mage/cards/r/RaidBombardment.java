
package mage.cards.r;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class RaidBombardment extends CardImpl {

    public RaidBombardment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // Whenever a creature you control with power 2 or less attacks, Raid Bombardment deals 1 damage to defending player.
        this.addAbility(new RaidBombardmentTriggeredAbility());
    }

    private RaidBombardment(final RaidBombardment card) {
        super(card);
    }

    @Override
    public RaidBombardment copy() {
        return new RaidBombardment(this);
    }
}

class RaidBombardmentTriggeredAbility extends TriggeredAbilityImpl {

    public RaidBombardmentTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(1));
    }

    private RaidBombardmentTriggeredAbility(final RaidBombardmentTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RaidBombardmentTriggeredAbility copy() {
        return new RaidBombardmentTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.isActivePlayer(this.controllerId)) {
            Permanent attacker = game.getPermanent(event.getSourceId());
            if (attacker != null) {
                if (attacker.getPower().getValue() <= 2) {
                    UUID defendingPlayerId = game.getCombat().getDefenderId(attacker.getId());
                    this.getEffects().get(0).setTargetPointer(new FixedTarget(defendingPlayerId));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control with power 2 or less attacks, {this} deals 1 damage to the player or planeswalker that creature is attacking.";
    }

}
