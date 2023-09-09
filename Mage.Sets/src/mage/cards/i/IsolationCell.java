package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.ManaUtil;

import java.util.UUID;

/**
 * @author North
 */
public final class IsolationCell extends CardImpl {

    public IsolationCell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // Whenever an opponent casts a creature spell, that player loses 2 life unless they pay {2}.
        this.addAbility(new IsolationCellTriggeredAbility());
    }

    private IsolationCell(final IsolationCell card) {
        super(card);
    }

    @Override
    public IsolationCell copy() {
        return new IsolationCell(this);
    }
}

class IsolationCellTriggeredAbility extends TriggeredAbilityImpl {

    public IsolationCellTriggeredAbility() {
        super(Zone.BATTLEFIELD, new IsolationCellEffect(), false);
    }

    private IsolationCellTriggeredAbility(final IsolationCellTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public IsolationCellTriggeredAbility copy() {
        return new IsolationCellTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(controllerId).contains(event.getPlayerId())) {
            Card card = game.getCard(event.getSourceId());
            if (card != null
                    && card.isCreature(game)) {
                this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getPlayerId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent casts a creature spell, that player loses 2 life unless they pay {2}.";
    }
}

class IsolationCellEffect extends OneShotEffect {

    public IsolationCellEffect() {
        super(Outcome.Neutral);
        this.staticText = "that player loses 2 life unless they pay {2}";
    }

    private IsolationCellEffect(final IsolationCellEffect effect) {
        super(effect);
    }

    @Override
    public IsolationCellEffect copy() {
        return new IsolationCellEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            Cost cost = ManaUtil.createManaCost(2, false);
            if (!cost.pay(source, game, source, player.getId(), false)) {
                player.loseLife(2, game, source, false);
            }
            return true;
        }
        return false;
    }
}
