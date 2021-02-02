
package mage.cards.c;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public final class CloudCover extends CardImpl {

    public CloudCover(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}{U}");

        // Whenever another permanent you control becomes the target of a spell or ability an opponent controls, you may return that permanent to its owner's hand.
        this.addAbility(new CloudCoverAbility());
    }

    private CloudCover(final CloudCover card) {
        super(card);
    }

    @Override
    public CloudCover copy() {
        return new CloudCover(this);
    }
}

class CloudCoverAbility extends TriggeredAbilityImpl {

    public CloudCoverAbility() {
        super(Zone.BATTLEFIELD, new ReturnToHandTargetEffect(), true);
    }

    public CloudCoverAbility(final CloudCoverAbility ability) {
        super(ability);
    }

    @Override
    public CloudCoverAbility copy() {
        return new CloudCoverAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        Player controller = game.getPlayer(this.getControllerId());
        if (permanent != null
                && permanent.isControlledBy(getControllerId())
                && !permanent.getId().equals(this.getSourceId())
                && controller != null
                && controller.hasOpponent(event.getPlayerId(), game)) {
            getEffects().get(0).setTargetPointer(new FixedTarget(permanent, game));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever another permanent you control becomes the target of a spell or ability an opponent controls, you may return that permanent to its owner's hand.";
    }
}
