
package mage.cards.r;

import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.token.ElementalToken;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author magenoxx
 */
public final class ResearchDevelopment extends SplitCard {

    public ResearchDevelopment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}{U}", "{3}{U}{R}", SpellAbilityType.SPLIT);

        // Choose up to four cards you own from outside the game and shuffle them into your library.
        getLeftHalfCard().getSpellAbility().addEffect(new ResearchEffect());

        // Create a 3/1 red Elemental creature token unless any opponent has you draw a card. Repeat this process two more times.
        getRightHalfCard().getSpellAbility().addEffect(new DevelopmentEffect());
    }

    public ResearchDevelopment(final ResearchDevelopment card) {
        super(card);
    }

    @Override
    public ResearchDevelopment copy() {
        return new ResearchDevelopment(this);
    }
}

class ResearchEffect extends OneShotEffect {

    private static final String choiceText = "Choose a card you own from outside the game";

    private static final FilterCard filter = new FilterCard("card");

    public ResearchEffect() {
        super(Outcome.Benefit);
        this.staticText = "Choose up to four cards you own from outside the game and shuffle them into your library";
    }

    public ResearchEffect(final ResearchEffect effect) {
        super(effect);
    }

    @Override
    public ResearchEffect copy() {
        return new ResearchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            StringBuilder textToAsk = new StringBuilder(choiceText);
            textToAsk.append(" (0)");
            int count = 0;
            while (player.chooseUse(Outcome.Benefit, textToAsk.toString(), source, game)) {
                Cards cards = player.getSideboard();
                if (cards.isEmpty()) {
                    game.informPlayer(player, "You have no cards outside the game.");
                    break;
                }

                Set<Card> filtered = cards.getCards(filter, game);
                if (filtered.isEmpty()) {
                    game.informPlayer(player, "You have no " + filter.getMessage() + " outside the game.");
                    break;
                }

                Cards filteredCards = new CardsImpl();
                for (Card card : filtered) {
                    filteredCards.add(card.getId());
                }

                TargetCard target = new TargetCard(Zone.OUTSIDE, filter);
                if (player.choose(Outcome.Benefit, filteredCards, target, game)) {
                    Card card = player.getSideboard().get(target.getFirstTarget(), game);
                    if (card != null) {
                        card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, false);
                        count++;
                        textToAsk = new StringBuilder(choiceText);
                        textToAsk.append(" (");
                        textToAsk.append(count);
                        textToAsk.append(')');
                    }
                }

                if (count == 4) {
                    break;
                }
            }

            game.informPlayers(player.getLogName() + " has chosen " + count + " card(s) to shuffle into their library.");

            if (count > 0) {
                player.shuffleLibrary(source, game);
            }

            return true;
        }

        return false;
    }
}

class DevelopmentEffect extends OneShotEffect {

    public DevelopmentEffect() {
        super(Outcome.Benefit);
        staticText = "Create a 3/1 red Elemental creature token unless any opponent has you draw a card. Repeat this process two more times.";
    }

    DevelopmentEffect(final DevelopmentEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            for (int i = 0; i < 3; i++) {
                Set<UUID> opponents = game.getOpponents(source.getControllerId());
                boolean putToken = true;
                for (UUID opponentUuid : opponents) {
                    Player opponent = game.getPlayer(opponentUuid);
                    if (opponent != null && opponent.chooseUse(Outcome.Detriment,
                            "Allow " + player.getLogName() + " to draw a card instead? (" + Integer.toString(i + 1) + ')', source, game)) {
                        game.informPlayers(opponent.getLogName() + " had chosen to let " + player.getLogName() + " draw a card.");
                        player.drawCards(1, game);
                        putToken = false;
                        break;
                    }
                }
                if (putToken) {
                    new CreateTokenEffect(new ElementalToken("DIS", 1)).apply(game, source);
                }
            }

            return true;
        }
        return false;
    }

    @Override
    public DevelopmentEffect copy() {
        return new DevelopmentEffect(this);
    }

}
