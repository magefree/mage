package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
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
public class ExileCardsFromTopOfLibraryTargetEffect extends OneShotEffect {

    int amount;
    String targetName;

    public ExileCardsFromTopOfLibraryTargetEffect(int amount) {
        this(amount, null);
    }

    public ExileCardsFromTopOfLibraryTargetEffect(int amount, String targetName) {
        super(Outcome.Exile);
        this.amount = amount;
        this.staticText = (targetName == null ? "that player" : targetName) + " exiles the top "
                + CardUtil.numberToText(amount, "")
                + (amount == 1 ? "card" : " cards") + " of their library";
    }

    public ExileCardsFromTopOfLibraryTargetEffect(final ExileCardsFromTopOfLibraryTargetEffect effect) {
        super(effect);
        this.amount = effect.amount;

    }

    @Override
    public ExileCardsFromTopOfLibraryTargetEffect copy() {
        return new ExileCardsFromTopOfLibraryTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetPlayer != null) {
            Cards cards = new CardsImpl();
            cards.addAllCards(targetPlayer.getLibrary().getTopCards(game, amount));
            return targetPlayer.moveCards(cards, Zone.EXILED, source, game);
        }
        return false;
    }
}
