package mage.cards.m;

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
 * @author TheElk801
 */
public final class MisersCage extends CardImpl {

    public MisersCage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // At the beginning of each opponent's upkeep, if that player has five or more cards in hand, Misers' Cage deals 2 damage to that player.
        this.addAbility(new MisersCageTriggeredAbility());
    }

    private MisersCage(final MisersCage card) {
        super(card);
    }

    @Override
    public MisersCage copy() {
        return new MisersCage(this);
    }
}

class MisersCageTriggeredAbility extends TriggeredAbilityImpl {

    MisersCageTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(2), false);
    }

    MisersCageTriggeredAbility(final MisersCageTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MisersCageTriggeredAbility copy() {
        return new MisersCageTriggeredAbility(this);
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
        return player != null && player.getHand().size() >= 5;
    }

    @Override
    public String getRule() {
        return "at the beginning of each opponent's upkeep, if that player has five or more cards in hand, {this} deals 2 damage to that player.";
    }
}
