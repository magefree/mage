
package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.players.PlayerList;
import mage.target.Target;
import mage.target.common.TargetCardInHand;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author spjspj
 */
public final class KynaiosAndTiroOfMeletis extends CardImpl {

    public KynaiosAndTiroOfMeletis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}{W}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(8);

        // At the beginning of your end step, draw a card. Each player may put a land card from their hand onto the battlefield, then each opponent who didn't draws a card.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new KynaiosAndTirosEffect(), TargetController.YOU, false));
    }

    private KynaiosAndTiroOfMeletis(final KynaiosAndTiroOfMeletis card) {
        super(card);
    }

    @Override
    public KynaiosAndTiroOfMeletis copy() {
        return new KynaiosAndTiroOfMeletis(this);
    }
}

class KynaiosAndTirosEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("a land card");

    static {
        filter.add(CardType.LAND.getPredicate());
    }

    public KynaiosAndTirosEffect() {
        super(Outcome.DrawCard);
        staticText = "draw a card. Each player may put a land card from their hand onto the battlefield, then each opponent who didn't draws a card";
    }

    public KynaiosAndTirosEffect(final KynaiosAndTirosEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.drawCards(1, source, game);
            PlayerList playerList = game.getState().getPlayerList().copy();

            while (!playerList.get().equals(source.getControllerId()) && controller.canRespond()) {
                playerList.getNext();
            }
            Player currentPlayer = game.getPlayer(playerList.get());
            UUID firstInactivePlayer = null;
            Target target = new TargetCardInHand(filter);
            List<UUID> noLandPlayers = new ArrayList<>();

            while (controller.canRespond()) {
                if (currentPlayer != null && currentPlayer.canRespond() && game.getState().getPlayersInRange(controller.getId(), game).contains(currentPlayer.getId())) {
                    if (firstInactivePlayer == null) {
                        firstInactivePlayer = currentPlayer.getId();
                    }
                    target.clearChosen();
                    boolean playedLand = false;
                    if (target.canChoose(currentPlayer.getId(), source, game) && currentPlayer.chooseUse(outcome, "Put a land card from your hand onto the battlefield?", source, game)) {
                        if (target.chooseTarget(outcome, currentPlayer.getId(), source, game)) {
                            Card card = game.getCard(target.getFirstTarget());
                            if (card != null) {
                                currentPlayer.moveCards(card, Zone.BATTLEFIELD, source, game);
                                playedLand = true;
                            }
                        }
                    }
                    if (!playedLand && !Objects.equals(currentPlayer, controller)) {
                        noLandPlayers.add(currentPlayer.getId());
                    }
                }
                // get next player
                playerList.getNext();
                currentPlayer = game.getPlayer(playerList.get());
                // if all player since this player didn't put permanent in play finish the process
                if (currentPlayer.getId().equals(firstInactivePlayer)) {
                    break;
                }
            }

            for (UUID playerId : noLandPlayers) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    player.drawCards(1, source, game);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public KynaiosAndTirosEffect copy() {
        return new KynaiosAndTirosEffect(this);
    }
}
