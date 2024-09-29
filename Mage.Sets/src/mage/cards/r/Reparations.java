
package mage.cards.r;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
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
 * @author jeffwadsworth
 */
public final class Reparations extends CardImpl {

    public Reparations(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{U}");

        // Whenever an opponent casts a spell that targets you or a creature you control, you may draw a card.
        this.addAbility(new ReparationsTriggeredAbility());

    }

    private Reparations(final Reparations card) {
        super(card);
    }

    @Override
    public Reparations copy() {
        return new Reparations(this);
    }
}

class ReparationsTriggeredAbility extends TriggeredAbilityImpl {

    public ReparationsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), true);
    }

    private ReparationsTriggeredAbility(final ReparationsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ReparationsTriggeredAbility copy() {
        return new ReparationsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        UUID stackObjectId = event.getTargetId(); // spell cast
        UUID casterId = event.getPlayerId();  // spell caster
        StackObject stackObject = game.getStack().getStackObject(stackObjectId);
        if (stackObject != null
                && game.getOpponents(controllerId).contains(casterId)) {
            Player targetPlayer = game.getPlayer(stackObject.getStackAbility().getFirstTarget());
            Permanent targetPermanent = game.getPermanent(stackObject.getStackAbility().getFirstTarget());
            if (targetPlayer != null
                    && targetPlayer.getId().equals(controllerId)) {
                return true;
            }
            if (targetPermanent != null
                    && targetPermanent.isCreature(game)
                    && targetPermanent.isControlledBy(controllerId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent casts a spell that targets you or a creature you control, you may draw a card.";
    }
}
