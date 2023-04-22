package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.util.CardUtil;

import java.util.List;
import java.util.Set;

/**
 * @author Styxo
 */
public class WishEffect extends OneShotEffect {

    private final FilterCard filter;
    private final boolean reveal;
    private final boolean alsoFromExile;
    private final boolean topOfLibrary;
    private final String choiceText;

    public WishEffect() {
        this(false);
    }

    public WishEffect(boolean topOfLibrary) {
        super(Outcome.DrawCard);
        this.filter = StaticFilters.FILTER_CARD;
        this.reveal = false;
        this.alsoFromExile = false;
        this.topOfLibrary = topOfLibrary;
        choiceText = "Put a card you own from outside the game " +
                (topOfLibrary ? "on top of your library?" : "into your hand?");
        staticText = "You may " + Character.toLowerCase(choiceText.charAt(0)) + choiceText.substring(1, choiceText.length() - 1);
    }

    public WishEffect(FilterCard filter) {
        this(filter, false);
    }

    public WishEffect(FilterCard filter, boolean alsoFromExile) {
        this(filter, alsoFromExile, false);
    }

    public WishEffect(FilterCard filter, boolean alsoFromExile, boolean topOfLibrary) {
        super(Outcome.DrawCard);
        this.filter = filter;
        this.reveal = true;
        this.alsoFromExile = alsoFromExile;
        this.topOfLibrary = topOfLibrary;
        choiceText = "Reveal " + CardUtil.addArticle(filter.getMessage()) + " you own from outside the game "
                + (alsoFromExile ? "or choose " + makeExileText(filter) + " you own in exile. Put that card" : "and put it")
                + (topOfLibrary ? " on top of your library?" : " into your hand?");
        staticText = "You may " + Character.toLowerCase(choiceText.charAt(0)) + choiceText.substring(1, choiceText.length() - 1);
    }

    private static String makeExileText(FilterCard filter) {
        String s = filter.getMessage();
        if (s.startsWith("a ")) {
            return s.replace("a ", "a face-up ");
        } else if (s.startsWith("an ")) {
            return s.replace("an ", "a face-up ");
        }
        return "a face-up " + s;
    }

    public WishEffect(final WishEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.reveal = effect.reveal;
        this.alsoFromExile = effect.alsoFromExile;
        this.topOfLibrary = effect.topOfLibrary;
        this.choiceText = effect.choiceText;
    }

    @Override
    public WishEffect copy() {
        return new WishEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }
        if (!controller.chooseUse(Outcome.Benefit, choiceText, source, game)) {
            return false;
        }
        Cards cards = controller.getSideboard();
        List<Card> exile = game.getExile().getAllCards(game);
        boolean noTargets = cards.isEmpty() && (!alsoFromExile || exile.isEmpty());
        if (noTargets) {
            game.informPlayer(controller, "You have no cards outside the game" + (alsoFromExile ? " or in exile" : "") + '.');
            return true;
        }

        Set<Card> filtered = cards.getCards(filter, game);
        Cards filteredCards = new CardsImpl();
        for (Card card : filtered) {
            filteredCards.add(card.getId());
        }
        if (alsoFromExile) {
            for (Card exileCard : exile) {
                if (exileCard.isOwnedBy(source.getControllerId()) && filter.match(exileCard, game)) {
                    filteredCards.add(exileCard);
                }
            }
        }
        if (filteredCards.isEmpty()) {
            game.informPlayer(controller, "You don't have " + filter.getMessage() + " outside the game" + (alsoFromExile ? " or in exile" : "") + '.');
            return true;
        }

        TargetCard target = new TargetCard(Zone.ALL, filter);
        target.setNotTarget(true);
        if (controller.choose(Outcome.Benefit, filteredCards, target, source, game)) {
            Card card = controller.getSideboard().get(target.getFirstTarget(), game);
            if (card == null && alsoFromExile) {
                card = game.getCard(target.getFirstTarget());
            }
            if (card != null) {
                if (topOfLibrary) {
                    controller.putCardsOnTopOfLibrary(card, game, source, true);
                } else {
                    controller.moveCards(card, Zone.HAND, source, game);
                }
                if (reveal) {
                    Cards revealCard = new CardsImpl();
                    revealCard.add(card);
                    controller.revealCards(sourceObject.getIdName(), revealCard, game);
                }
            }
        }
        return true;
    }

}
