package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.players.PlayerList;

import java.util.Objects;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class PainsReward extends CardImpl {

    public PainsReward(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Each player may bid life. You start the bidding with a bid of any number. In turn order, each player may top the high bid. The bidding ends if the high bid stands. The high bidder loses life equal to the high bid and draws four cards.
        this.getSpellAbility().addEffect(new PainsRewardEffect());
    }

    private PainsReward(final PainsReward card) {
        super(card);
    }

    @Override
    public PainsReward copy() {
        return new PainsReward(this);
    }
}


class PainsRewardEffect extends OneShotEffect {

    public PainsRewardEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Each player may bid life. You start the bidding with a bid of any number. In turn order, each player may top the high bid. The bidding ends if the high bid stands. The high bidder loses life equal to the high bid and draws four cards";
    }

    private PainsRewardEffect(final PainsRewardEffect effect) {
        super(effect);
    }

    @Override
    public PainsRewardEffect copy() {
        return new PainsRewardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            PlayerList playerList = game.getPlayerList().copy();
            playerList.setCurrent(controller.getId());
            Player winner = game.getPlayer(controller.getId());

            int highBid = chooseLifeAmountToBid(controller, -1, game); // -1 for start with 0 min big
            game.informPlayers(winner.getLogName() + " has bet " + highBid + " lifes");

            Player currentPlayer = playerList.getNextInRange(controller, game);
            while (currentPlayer != null && !Objects.equals(currentPlayer, winner)) {
                String text = winner.getLogName() + " has bet " + highBid + " life" + (highBid > 1 ? "s" : "") + ". Top the bid?";

                // AI hint
                int safeLifeToLost = Math.min(6, currentPlayer.getLife() / 2);
                Outcome aiOutcome = (highBid + 1 <= safeLifeToLost) ? Outcome.Benefit : Outcome.Detriment;

                if (currentPlayer.chooseUse(aiOutcome, text, source, game)) {
                    int newBid = chooseLifeAmountToBid(currentPlayer, highBid, game);
                    if (newBid > highBid) {
                        highBid = newBid;
                        winner = currentPlayer;
                        game.informPlayers(currentPlayer.getLogName() + " bet " + newBid + " life" + (newBid > 1 ? "s" : ""));
                    }
                }
                currentPlayer = playerList.getNextInRange(controller, game);
            }

            game.informPlayers(winner.getLogName() + " won the auction with a bid of " + highBid + " life" + (highBid > 1 ? "s" : ""));
            winner.loseLife(highBid, game, source, false);
            winner.drawCards(4, source, game);
            return true;
        }
        return false;
    }

    private int chooseLifeAmountToBid(Player player, int currentBig, Game game) {
        int newBid;
        if (player.isComputer()) {
            // AI choose
            newBid = currentBig + 1;
        } else {
            // Human choose
            newBid = player.getAmount(currentBig + 1, Integer.MAX_VALUE, "Choose amount of life to bid", game);
        }
        return newBid;
    }
}
