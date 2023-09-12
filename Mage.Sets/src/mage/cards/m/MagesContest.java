
package mage.cards.m;

import java.util.Objects;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.util.RandomUtil;

/**
 *
 * @author Quercitron & L_J
 */
public final class MagesContest extends CardImpl {

    public MagesContest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}{R}");

        // You and target spell's controller bid life. You start the bidding with a bid of 1. In turn order, each player may top the high bid. The bidding ends if the high bid stands. The high bidder loses life equal to the high bid. If you win the bidding, counter that spell.
        this.getSpellAbility().addEffect(new MagesContestEffect());
        this.getSpellAbility().addTarget(new TargetSpell());

    }

    private MagesContest(final MagesContest card) {
        super(card);
    }

    @Override
    public MagesContest copy() {
        return new MagesContest(this);
    }
}

class MagesContestEffect extends OneShotEffect {

    public MagesContestEffect() {
        super(Outcome.Detriment);
        this.staticText = "You and target spell's controller bid life. You start the bidding with a bid of 1. In turn order, each player may top the high bid. The bidding ends if the high bid stands. The high bidder loses life equal to the high bid. If you win the bidding, counter that spell";
    }

    private MagesContestEffect(final MagesContestEffect effect) {
        super(effect);
    }

    @Override
    public MagesContestEffect copy() {
        return new MagesContestEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(this.getTargetPointer().getFirst(game, source));
        if (spell != null) {
            Player you = game.getPlayer(source.getControllerId());
            Player spellController = game.getPlayer(spell.getControllerId());
            if (you != null && spellController != null) {
                int highBid = 1;
                game.informPlayers(you.getLogName() + " has bet " + highBid + " life");
                Player winner = you;
                Player currentPlayer = spellController;
                do {
                    if (currentPlayer.canRespond()) {
                        int newBid = 0;
                        if (currentPlayer.isComputer()) {
                            // AI hint
                            // make AI evaluate value of the spell to decide on bidding, should be reworked
                            int maxBid = Math.min(RandomUtil.nextInt(Math.max(currentPlayer.getLife(), 1)) + RandomUtil.nextInt(Math.max(spell.getManaValue(), 1)), currentPlayer.getLife());
                            if (highBid + 1 < maxBid) {
                                newBid = highBid + 1;
                            }
                        } else if (currentPlayer.chooseUse(Outcome.Benefit, winner.getLogName() + " has bet " + highBid + " life. Top the bid?", source, game)) {
                            // Human choose
                            newBid = currentPlayer.getAmount(highBid + 1, Integer.MAX_VALUE, "Choose bid", game);
                        }
                        if (newBid > highBid) {
                            highBid = newBid;
                            winner = currentPlayer;
                            game.informPlayers(currentPlayer.getLogName() + " has bet " + newBid + " life");
                            currentPlayer = (winner == you ? spellController : you);
                        } else {
                            break;
                        }
                    }
                } while (!Objects.equals(currentPlayer, winner));
                game.informPlayers(winner.getLogName() + " has won the contest with a high bid of " + highBid + " life");
                winner.loseLife(highBid, game, source, false);
                if (winner == you) {
                    game.getStack().counter(spell.getId(), source, game);
                }
                return true;
            }
        }
        return false;
    }
}
