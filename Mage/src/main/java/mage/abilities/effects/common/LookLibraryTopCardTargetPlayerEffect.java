
package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class LookLibraryTopCardTargetPlayerEffect extends OneShotEffect {

    protected int amount;
    protected boolean putToGraveyard;
    protected boolean mayShuffleAfter; // for Visions

    public LookLibraryTopCardTargetPlayerEffect(int amount) {
        super(Outcome.Benefit);
        this.amount = amount;
        this.putToGraveyard = false;
        this.mayShuffleAfter = false;
        setText();
    }

    public LookLibraryTopCardTargetPlayerEffect(int amount, boolean putToGraveyard) {
        super(Outcome.Benefit);
        this.amount = amount;
        this.putToGraveyard = putToGraveyard;
        this.mayShuffleAfter = false;
        setText();
    }

    public LookLibraryTopCardTargetPlayerEffect(int amount, boolean putToGraveyard, boolean mayShuffleAfter) {
        super(Outcome.Benefit);
        this.amount = amount;
        this.putToGraveyard = putToGraveyard;
        this.mayShuffleAfter = mayShuffleAfter;
        setText();
    }

    public LookLibraryTopCardTargetPlayerEffect() {
        this(1);
    }

    public LookLibraryTopCardTargetPlayerEffect(final LookLibraryTopCardTargetPlayerEffect effect) {
        super(effect);
        amount = effect.amount;
        putToGraveyard = effect.putToGraveyard;
        mayShuffleAfter = effect.mayShuffleAfter;
    }

    @Override
    public LookLibraryTopCardTargetPlayerEffect copy() {
        return new LookLibraryTopCardTargetPlayerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        MageObject sourceObject = game.getObject(source);
        if (player != null && targetPlayer != null && sourceObject != null) {
            Cards cards = new CardsImpl();
            cards.addAllCards(targetPlayer.getLibrary().getTopCards(game, amount));
            player.lookAtCards(sourceObject.getIdName(), cards, game);
            if (putToGraveyard) {
                for (Card card : cards.getCards(game)) {
                    if (player.chooseUse(outcome, "Put that card into its owner's graveyard?", source, game)) {
                        player.moveCardToGraveyardWithInfo(card, source, game, Zone.LIBRARY);
                    } else {
                        game.informPlayers(player.getLogName() + " puts the card back on top of the library.");
                    }
                }
            }
            if (mayShuffleAfter) {
                if (player.chooseUse(Outcome.Benefit, (player == targetPlayer ? "Shuffle your library?" : "Have the chosen player shuffle?"), source, game)) {
                    targetPlayer.shuffleLibrary(source, game);
                }
            }
            return true;
        }
        return false;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder("look at the top ");
        if (amount > 1) {
            sb.append(CardUtil.numberToText(amount));
            sb.append(" cards ");
        } else {
            sb.append("card ");
        }
        sb.append("of target player's library");
        if (putToGraveyard) {
            sb.append(". You may put ");
            if (amount > 1) {
                sb.append("those cards");
            } else {
                sb.append("that card");
            }
            sb.append(" into their graveyard");
        }
        if (mayShuffleAfter) {
            sb.append(". You may then have that player shuffle that library");
        }
        this.staticText = sb.toString();
    }
}
