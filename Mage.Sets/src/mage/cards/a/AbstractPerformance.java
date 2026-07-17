package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MayCastTargetCardEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author muz
 */
public final class AbstractPerformance extends CardImpl {

    public AbstractPerformance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{U}");

        // Exile the top four cards of your library in a face-down pile, then exile the top four cards of your library in a face-up pile.
        // An opponent chooses one of those piles. Put that pile into your graveyard. Look at the cards in the other pile.
        // You may cast a spell from among them without paying its mana cost. Put the rest into your hand.
        this.getSpellAbility().addEffect(new AbstractPerformanceEffect());
    }

    private AbstractPerformance(final AbstractPerformance card) {
        super(card);
    }

    @Override
    public AbstractPerformance copy() {
        return new AbstractPerformance(this);
    }
}

class AbstractPerformanceEffect extends OneShotEffect {

    AbstractPerformanceEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Exile the top four cards of your library in a face-down pile, then exile the top four cards "
            + "of your library in a face-up pile. An opponent chooses one of those piles. Put that pile into your "
            + "graveyard. Look at the cards in the other pile. You may cast a spell from among them without paying "
            + "its mana cost. Put the rest into your hand";
    }

    private AbstractPerformanceEffect(final AbstractPerformanceEffect effect) {
        super(effect);
    }

    @Override
    public AbstractPerformanceEffect copy() {
        return new AbstractPerformanceEffect(this);
    }

    private Cards exileTopFour(Player controller, Ability source, Game game, boolean reveal) {
        Set<Card> toExile = controller.getLibrary().getTopCards(game, 4);
        controller.moveCardsToExile(toExile, source, game, reveal, null, "");
        Cards exiled = new CardsImpl();
        for (Card card : toExile) {
            if (game.getState().getZone(card.getId()) == Zone.EXILED) {
                if (!reveal) {
                    card.setFaceDown(true, game);
                }
                exiled.add(card.getId());
            }
        }
        return exiled;
    }

    private static void turnFaceUp(Cards cards, Game game) {
        for (UUID cardId : cards) {
            Card card = game.getCard(cardId);
            if (card != null && card.isFaceDown(game)) {
                card.setFaceDown(false, game);
            }
        }
    }

    private static Player selectOpponent(Player controller, Ability source, Game game) {
        Set<UUID> opponents = game.getOpponents(source.getControllerId());
        if (opponents.isEmpty()) {
            return null;
        }
        if (opponents.size() == 1) {
            return game.getPlayer(opponents.iterator().next());
        }
        TargetOpponent targetOpponent = new TargetOpponent(true);
        controller.chooseTarget(Outcome.Neutral, targetOpponent, source, game);
        return game.getPlayer(targetOpponent.getFirstTarget());
    }

    private static void mayCastSpellForFree(Player controller, Cards remainingPile, Ability source, Game game) {
        Cards spellsInPile = new CardsImpl();

        for (UUID cardId : remainingPile) {
            Card card = game.getCard(cardId);
            if (card != null && !card.isLand(game) && game.getState().getZone(cardId) == Zone.EXILED) {
                spellsInPile.add(cardId);
            }
        }
        if (spellsInPile.isEmpty()) {
            return;
        }

        TargetCard target = new TargetCard(0, 1, Zone.EXILED, StaticFilters.FILTER_CARD_NON_LAND);
        controller.choose(Outcome.PlayForFree, spellsInPile, target, source, game);
        if (target.getFirstTarget() == null) {
            return;
        }

        Effect effect = new MayCastTargetCardEffect(CastManaAdjustment.WITHOUT_PAYING_MANA_COST);
        effect.setTargetPointer(new FixedTarget(target.getFirstTarget()));
        effect.apply(game, source);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Player opponent = selectOpponent(controller, source, game);
        if (opponent == null) {
            return false;
        }

        // Exile the top four cards of your library in a face-down pile
        Cards pile1 = exileTopFour(controller, source, game, false);

        // then exile the top four cards of your library in a face-up pile.
        Cards pile2 = exileTopFour(controller, source, game, true);
        controller.revealCards(source, "Face-up pile", pile2, game);

        // An opponent chooses one of those piles. Put that pile into your graveyard.
        boolean chosePile1 = opponent.choosePile(
            Outcome.Neutral,
            "Choose a pile to put into your opponent's graveyard.",
            new ArrayList<>(pile1.getCards(game)),
            new ArrayList<>(pile2.getCards(game)),
            game
        );

        Cards graveyardPile;
        Cards remainingPile;
        if (chosePile1) {
            graveyardPile = pile1;
            remainingPile = pile2;
        } else {
            graveyardPile = pile2;
            remainingPile = pile1;
        }

        turnFaceUp(graveyardPile, game);
        controller.moveCards(graveyardPile, Zone.GRAVEYARD, source, game);

        // Look at the cards in the other pile.
        turnFaceUp(remainingPile, game);
        controller.lookAtCards(source, "Other pile", remainingPile, game);

        // You may cast a spell from among them without paying its mana cost.
        mayCastSpellForFree(controller, remainingPile, source, game);

        // Put the rest into your hand.
        remainingPile.retainZone(Zone.EXILED, game);
        controller.moveCards(remainingPile, Zone.HAND, source, game);

        return true;
    }
}
