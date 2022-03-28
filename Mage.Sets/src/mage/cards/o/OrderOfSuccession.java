package mage.cards.o;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceLeftOrRight;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.players.PlayerList;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class OrderOfSuccession extends CardImpl {

    public OrderOfSuccession(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Choose left or right. Starting with you and proceeding in the chosen direction, each player chooses a creature controlled by the next player in that direction. Each player gains control of the creature they chose.
        this.getSpellAbility().addEffect(new OrderOfSuccessionEffect());
    }

    private OrderOfSuccession(final OrderOfSuccession card) {
        super(card);
    }

    @Override
    public OrderOfSuccession copy() {
        return new OrderOfSuccession(this);
    }
}

class OrderOfSuccessionEffect extends OneShotEffect {

    public OrderOfSuccessionEffect() {
        super(Outcome.Benefit);
        this.staticText = "Starting with you and proceeding in the chosen direction, each player chooses a creature controlled by the next player in that direction. Each player gains control of the creature they chose";
    }

    public OrderOfSuccessionEffect(final OrderOfSuccessionEffect effect) {
        super(effect);
    }

    @Override
    public OrderOfSuccessionEffect copy() {
        return new OrderOfSuccessionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Map<UUID, UUID> playerCreature = new HashMap<>(2);
            Choice choice = new ChoiceLeftOrRight();
            if (!controller.choose(Outcome.Neutral, choice, game)) {
                return false;
            }
            boolean left = choice.getChoice().equals("Left");
            PlayerList playerList = game.getState().getPlayerList().copy();
            // set playerlist to controller
            while (!playerList.get().equals(source.getControllerId()) && controller.canRespond()) {
                playerList.getNext();
            }
            Player currentPlayer = game.getPlayer(playerList.get());
            Player nextPlayer;
            UUID firstNextPlayer = null;

            while (!getNextPlayerInDirection(left, playerList, game).equals(firstNextPlayer) && controller.canRespond()) {
                nextPlayer = game.getPlayer(playerList.get());
                if (nextPlayer == null) {
                    return false;
                }
                // save first next player to check for iteration stop
                if (firstNextPlayer == null) {
                    firstNextPlayer = nextPlayer.getId();
                }
                if (!nextPlayer.canRespond()) {
                    continue;
                }
                // if player is in range they choose a creature to control
                if (currentPlayer != null && game.getState().getPlayersInRange(controller.getId(), game).contains(currentPlayer.getId())) {
                    FilterCreaturePermanent filter = new FilterCreaturePermanent("creature controlled by " + nextPlayer.getLogName());
                    filter.add(new ControllerIdPredicate(nextPlayer.getId()));
                    Target target = new TargetCreaturePermanent(filter);
                    target.setNotTarget(true);
                    if (target.canChoose(currentPlayer.getId(), source, game)) {
                        if (currentPlayer.chooseTarget(outcome, target, source, game)) {
                            playerCreature.put(currentPlayer.getId(), target.getFirstTarget());
                        }
                    }
                }
                currentPlayer = nextPlayer;
            }
            // change control of targets
            for (Map.Entry<UUID, UUID> entry : playerCreature.entrySet()) {
                Player player = game.getPlayer(entry.getKey());
                if (player != null) {
                    Permanent creature = game.getPermanent(entry.getValue());
                    if (creature != null) {
                        ContinuousEffect effect = new GainControlTargetEffect(Duration.EndOfGame, player.getId());
                        effect.setTargetPointer(new FixedTarget(creature.getId(), game));
                        game.addEffect(effect, source);
                        game.informPlayers(new StringBuilder(player.getLogName()).append(" gains control of ").append(creature.getName()).toString());
                    }
                }
            }
            return true;
        }
        return false;
    }

    private UUID getNextPlayerInDirection(boolean left, PlayerList playerList, Game game) {
        UUID nextPlayerId;
        if (left) {
            nextPlayerId = playerList.getNext();
        } else {
            nextPlayerId = playerList.getPrevious();
        }
        return nextPlayerId;
    }
}
