
package mage.cards.b;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
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
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author TheElk801
 */
public final class BoneyardParley extends CardImpl {

    public BoneyardParley(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{B}{B}");

        // Exile up to five target creature cards from graveyards. An opponent separates those cards into two piles. Put all cards from the pile of your choice onto the battlefield under your control and the rest into their owners' graveyards.
        this.getSpellAbility().addEffect(new BoneyardParleyEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(0, 5, new FilterCard("cards from graveyards")));
    }

    private BoneyardParley(final BoneyardParley card) {
        super(card);
    }

    @Override
    public BoneyardParley copy() {
        return new BoneyardParley(this);
    }
}

class BoneyardParleyEffect extends OneShotEffect {

    BoneyardParleyEffect() {
        super(Outcome.Benefit);
        this.staticText = "Exile up to five target creature cards from graveyards. "
                + "An opponent separates those cards into two piles. "
                + "Put all cards from the pile of your choice onto the battlefield under your control "
                + "and the rest into their owners' graveyards";
    }

    BoneyardParleyEffect(final BoneyardParleyEffect effect) {
        super(effect);
    }

    @Override
    public BoneyardParleyEffect copy() {
        return new BoneyardParleyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Cards cards = new CardsImpl();
            for (Target target : source.getTargets()) {
                for (UUID cardId : target.getTargets()) {
                    cards.add(cardId);
                }
            }
            if (!cards.isEmpty() && player.moveCards(cards, Zone.EXILED, source, game)) {
                TargetOpponent targetOpponent = new TargetOpponent(true);
                if (player.choose(Outcome.Neutral, targetOpponent, source, game)) {
                    Player opponent = game.getPlayer(targetOpponent.getFirstTarget());
                    if (opponent != null) {
                        TargetCard targetCards = new TargetCard(0, cards.size(), Zone.EXILED, new FilterCard("cards to put in the first pile"));
                        List<Card> pile1 = new ArrayList<>();
                        if (opponent.choose(Outcome.Neutral, cards, targetCards, game)) {
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
                        boolean choice = player.choosePile(outcome, "Choose a pile to put onto the battlefield.", pile1, pile2, game);

                        Zone pile1Zone = Zone.GRAVEYARD;
                        Zone pile2Zone = Zone.BATTLEFIELD;
                        if (choice) {
                            pile1Zone = Zone.BATTLEFIELD;
                            pile2Zone = Zone.GRAVEYARD;
                        }
                        Set<Card> pile1Set = new HashSet<>();
                        Set<Card> pile2Set = new HashSet<>();
                        pile1Set.addAll(pile1);
                        pile2Set.addAll(pile2);

//                        Cards toBattlefield = new CardsImpl();
//                        Cards toGraveyard = new CardsImpl();
//
//                        if (pile1Zone == Zone.BATTLEFIELD) {
//                            toBattlefield.addAll(pile1);
//                            toGraveyard.addAll(pile2);
//                        } else {
//                            toBattlefield.addAll(pile2);
//                            toGraveyard.addAll(pile1);
//                        }
                        player.moveCards(pile1Set, pile1Zone, source, game, false, false, false, null);
                        player.moveCards(pile2Set, pile2Zone, source, game, false, false, false, null);

//                        StringBuilder sb = new StringBuilder("Pile 1, going to ").append(pile1Zone == Zone.BATTLEFIELD ? "Battlefield" : "Graveyard").append(": ");
//                        game.informPlayers(sb.toString());
//                        sb = new StringBuilder("Pile 2, going to ").append(pile2Zone == Zone.BATTLEFIELD ? "Battlefield" : "Graveyard").append(':');
//                        game.informPlayers(sb.toString());
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
