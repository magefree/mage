
package mage.cards.d;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
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
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author L_J
 */
public final class DeathOrGlory extends CardImpl {

    public DeathOrGlory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}");

        // Separate all creature cards in your graveyard into two piles. Exile the pile of an opponent’s choice and return the other to the battlefield.
        this.getSpellAbility().addEffect(new DeathOrGloryEffect());
    }

    public DeathOrGlory(final DeathOrGlory card) {
        super(card);
    }

    @Override
    public DeathOrGlory copy() {
        return new DeathOrGlory(this);
    }
}

class DeathOrGloryEffect extends OneShotEffect {

    DeathOrGloryEffect() {
        super(Outcome.Benefit);
        this.staticText = "Separate all creature cards in your graveyard into two piles. Exile the pile of an opponent’s choice and return the other to the battlefield";
    }

    DeathOrGloryEffect(final DeathOrGloryEffect effect) {
        super(effect);
    }

    @Override
    public DeathOrGloryEffect copy() {
        return new DeathOrGloryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cards = new CardsImpl(controller.getGraveyard().getCards(new FilterCreatureCard(), game));
            if (!cards.isEmpty()) {
                TargetCard targetCards = new TargetCard(0, cards.size(), Zone.EXILED, new FilterCard("cards to put in the first pile"));
                List<Card> pile1 = new ArrayList<>();
                if (controller.choose(Outcome.Neutral, cards, targetCards, game)) {
                    List<UUID> targets = targetCards.getTargets();
                    for (UUID targetId : targets) {
                        Card card = cards.get(targetId, game);
                        if (card != null) {
                            pile1.add(card);
                            cards.remove(card);
                        }
                    }
                }
                List<Card> pile2 = new ArrayList<>();
                pile2.addAll(cards.getCards(game));

                StringBuilder sb = new StringBuilder("First pile of ").append(controller.getLogName()).append(": ");
                sb.append(pile1.stream().map(Card::getLogName).collect(Collectors.joining(", ")));
                game.informPlayers(sb.toString());
                
                sb = new StringBuilder("Second pile of ").append(controller.getLogName()).append(": ");
                sb.append(pile2.stream().map(Card::getLogName).collect(Collectors.joining(", ")));
                game.informPlayers(sb.toString());

                Set<UUID> opponents = game.getOpponents(source.getControllerId());
                if (!opponents.isEmpty()) {
                    Player opponent = game.getPlayer(opponents.iterator().next());
                    if (opponents.size() > 1) {
                        Target targetOpponent = new TargetOpponent(true);
                        if (controller.chooseTarget(Outcome.Neutral, targetOpponent, source, game)) {
                            opponent = game.getPlayer(targetOpponent.getFirstTarget());
                            game.informPlayers(controller.getLogName() + " chose " + opponent.getLogName() + " to choose his pile");
                        }
                    }
                    if (opponent != null) {
                        boolean choice = opponent.choosePile(outcome, "Choose a pile to put onto the battlefield.", pile1, pile2, game);

                        Zone pile1Zone = Zone.EXILED;
                        Zone pile2Zone = Zone.BATTLEFIELD;
                        if (choice) {
                            pile1Zone = Zone.BATTLEFIELD;
                            pile2Zone = Zone.EXILED;
                        }
                        Set<Card> pile1Set = new HashSet<>();
                        Set<Card> pile2Set = new HashSet<>();
                        pile1Set.addAll(pile1);
                        pile2Set.addAll(pile2);
                        controller.moveCards(pile1Set, pile1Zone, source, game, false, false, false, null);
                        controller.moveCards(pile2Set, pile2Zone, source, game, false, false, false, null);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
