
package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.Set;
import java.util.UUID;

/**
 * @author North
 */
public final class Mindcrank extends CardImpl {

    public Mindcrank(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Whenever an opponent loses life, that player puts that many cards from the top of their library into their graveyard.
        // (Damage dealt by sources without infect causes loss of life.)
        this.addAbility(new MindcrankTriggeredAbility());
    }

    private Mindcrank(final Mindcrank card) {
        super(card);
    }

    @Override
    public Mindcrank copy() {
        return new Mindcrank(this);
    }
}

class MindcrankTriggeredAbility extends TriggeredAbilityImpl {

    public MindcrankTriggeredAbility() {
        super(Zone.BATTLEFIELD, new MindcrankEffect(), false);
    }

    public MindcrankTriggeredAbility(final MindcrankTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MindcrankTriggeredAbility copy() {
        return new MindcrankTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOST_LIFE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Set<UUID> opponents = game.getOpponents(this.getControllerId());
        if (opponents.contains(event.getPlayerId())) {
            for (Effect effect : this.getEffects()) {
                effect.setValue("lostLife", event.getAmount());
                effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent loses life, that player mills that many cards.";
    }
}

class MindcrankEffect extends OneShotEffect {

    public MindcrankEffect() {
        super(Outcome.Detriment);
    }

    public MindcrankEffect(final MindcrankEffect effect) {
        super(effect);
    }

    @Override
    public MindcrankEffect copy() {
        return new MindcrankEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetPlayer != null) {
            Integer amount = (Integer) getValue("lostLife");
            if (amount == null) {
                amount = 0;
            }
            targetPlayer.millCards(amount, source, game);
        }
        return true;
    }
}
