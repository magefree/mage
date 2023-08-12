package mage.cards.e;

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
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public final class EbonyOwlNetsuke extends CardImpl {

    public EbonyOwlNetsuke(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // At the beginning of each opponent's upkeep, if that player has seven or more cards in hand, Ebony Owl Netsuke deals 4 damage to that player.
        this.addAbility(new EbonyOwlNetsukeTriggeredAbility());
    }

    private EbonyOwlNetsuke(final EbonyOwlNetsuke card) {
        super(card);
    }

    @Override
    public EbonyOwlNetsuke copy() {
        return new EbonyOwlNetsuke(this);
    }
}

class EbonyOwlNetsukeTriggeredAbility extends TriggeredAbilityImpl {

    EbonyOwlNetsukeTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(4), false);
    }

    EbonyOwlNetsukeTriggeredAbility(final EbonyOwlNetsukeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EbonyOwlNetsukeTriggeredAbility copy() {
        return new EbonyOwlNetsukeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(controllerId).contains(event.getPlayerId())) {
            Player player = game.getPlayer(event.getPlayerId());
            if (player != null) {
                for (Effect effect : getEffects()) {
                    effect.setTargetPointer(new FixedTarget(player.getId(), game));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        Player player = game.getPlayer(game.getActivePlayerId());
        return player != null && player.getHand().size() >= 7;
    }

    @Override
    public String getRule() {
        return "At the beginning of each opponent's upkeep, if that player has seven or more cards in hand, {this} deals 4 damage to that player.";
    }
}
