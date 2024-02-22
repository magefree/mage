package mage.cards.i;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Thopter00ColorlessToken;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author DominionSpy
 */
public final class IntrudeOnTheMind extends CardImpl {

    public IntrudeOnTheMind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}{U}");

        // Reveal the top five cards of your library and separate them into two piles. An opponent chooses one of those piles. Put that pile into your hand and the other into your graveyard.
        // Create a 0/0 colorless Thopter artifact creature token with flying, then put a +1/+1 counter on it for each card put into your graveyard this way.
        this.getSpellAbility().addEffect(new IntrudeOnTheMindEffect());
    }

    private IntrudeOnTheMind(final IntrudeOnTheMind card) {
        super(card);
    }

    @Override
    public IntrudeOnTheMind copy() {
        return new IntrudeOnTheMind(this);
    }
}

class IntrudeOnTheMindEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("cards to put in the first pile");

    IntrudeOnTheMindEffect() {
        super(Outcome.DrawCard);
        staticText = "Reveal the top five cards of your library and separate them into two piles. " +
                "An opponent chooses one of those piles. Put that pile into your hand and the other into your graveyard. " +
                "Create a 0/0 colorless Thopter artifact creature token with flying, " +
                "then put a +1/+1 counter on it for each card put into your graveyard this way.";
    }

    private IntrudeOnTheMindEffect(final IntrudeOnTheMindEffect effect) {
        super(effect);
    }

    @Override
    public IntrudeOnTheMindEffect copy() {
        return new IntrudeOnTheMindEffect(this);
    }

    /**
     * Pile-choosing logic based on {@link mage.abilities.effects.common.RevealAndSeparatePilesEffect}.
     */
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Cards allCards = new CardsImpl(controller.getLibrary().getTopCards(game, 5));
        Cards cards = allCards.copy();
        controller.revealCards(source, cards, game);

        TargetCard target = new TargetCard(0, cards.size(), Zone.LIBRARY, filter);
        List<Card> pile1 = new ArrayList<>();
        controller.choose(Outcome.Neutral, cards, target, source, game);
        target.getTargets()
                .stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .forEach(pile1::add);
        cards.removeIf(target.getTargets()::contains);
        List<Card> pile2 = new ArrayList<>();
        pile2.addAll(cards.getCards(game));

        Player opponent = getOpponent(controller, game, source);
        if (opponent == null) {
            return false;
        }
        boolean choice = opponent.choosePile(outcome, "Choose a pile to put into hand.", pile1, pile2, game);

        Zone pile1Zone = choice ? Zone.HAND : Zone.GRAVEYARD;
        Zone pile2Zone = choice ? Zone.GRAVEYARD : Zone.HAND;

        game.informPlayers("Pile 1, going to " + pile1Zone + ": " + (pile1.isEmpty() ? " (none)" :
                pile1.stream().map(MageObject::getName).collect(Collectors.joining(", "))));
        cards.clear();
        cards.addAllCards(pile1);
        controller.moveCards(cards, pile1Zone, source, game);

        game.informPlayers("Pile 2, going to " + pile2Zone + ": " + (pile2.isEmpty() ? " (none)" :
                pile2.stream().map(MageObject::getName).collect(Collectors.joining(", "))));
        cards.clear();
        cards.addAllCards(pile2);
        controller.moveCards(cards, pile2Zone, source, game);

        Token token = new Thopter00ColorlessToken();
        token.putOntoBattlefield(1, game, source);

        allCards.retainZone(Zone.GRAVEYARD, game);
        int count = allCards.size();
        if (count <= 0) {
            return true;
        }
        for (UUID tokenId : token.getLastAddedTokenIds()) {
            Permanent permanent = game.getPermanent(tokenId);
            if (permanent == null) {
                continue;
            }
            permanent.addCounters(CounterType.P1P1.createInstance(count), source.getControllerId(), source, game);
        }
        return true;
    }

    private static Player getOpponent(Player controller, Game game, Ability source) {
        Player opponent;
        Set<UUID> opponents = game.getOpponents(source.getControllerId());
        if (opponents.isEmpty()) {
            return null;
        }
        if (opponents.size() == 1) {
            opponent = game.getPlayer(opponents.iterator().next());
        } else {
            Target targetOpponent = new TargetOpponent(true);
            controller.chooseTarget(Outcome.Neutral, targetOpponent, source, game);
            opponent = game.getPlayer(targetOpponent.getFirstTarget());
            if (opponent == null) {
                return null;
            }
            game.informPlayers(controller.getLogName() + " chose " + opponent.getLogName() + " to choose the piles");
        }
        return opponent;
    }
}
