
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class Powerleech extends CardImpl {

    public Powerleech(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}{G}");

        // Whenever an artifact an opponent controls becomes tapped or an opponent activates an artifact's ability without {T} in its activation cost, you gain 1 life.
        this.addAbility(new PowerleechTriggeredAbility());
    }

    private Powerleech(final Powerleech card) {
        super(card);
    }

    @Override
    public Powerleech copy() {
        return new Powerleech(this);
    }
}

class PowerleechTriggeredAbility extends TriggeredAbilityImpl {

    PowerleechTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainLifeEffect(1));
    }

    private PowerleechTriggeredAbility(final PowerleechTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PowerleechTriggeredAbility copy() {
        return new PowerleechTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY
                || event.getType() == GameEvent.EventType.TAPPED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player player = game.getPlayer(controllerId);
        if (player == null) {
            return false;
        }
        if (event.getType() == GameEvent.EventType.ACTIVATED_ABILITY) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
            if (permanent == null || !permanent.isArtifact(game)) {
                return false;
            }
            // abilities with {T} trigger by the tapped event, mana abilities have no stack object (605.3b)
            StackObject stackObject = game.getStack().getStackObject(event.getTargetId());
            Ability ability = stackObject != null
                    ? stackObject.getStackAbility()
                    : game.getAbility(event.getTargetId(), event.getSourceId()).orElse(null);
            return ability != null
                    && !ability.hasTapCost()
                    && player.hasOpponent(permanent.getControllerId(), game);
        }
        if (event.getType() == GameEvent.EventType.TAPPED) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (permanent == null || !permanent.isArtifact(game)) {
                return false;
            }
            return player.hasOpponent(permanent.getControllerId(), game);
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an artifact an opponent controls becomes tapped or an opponent activates an artifact's ability without {T} in its activation cost, you gain 1 life.";
    }
}
