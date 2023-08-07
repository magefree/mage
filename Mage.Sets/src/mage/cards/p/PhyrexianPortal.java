  
package mage.cards.p;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
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
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 * 
 * @author L_J
 */
public final class PhyrexianPortal extends CardImpl {

    public PhyrexianPortal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {3}: If your library has ten or more cards in it, target opponent looks at the top ten cards of your library and separates them into two face-down piles. Exile one of those piles. Search the other pile for a card, put it into your hand, then shuffle the rest of that pile into your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PhyrexianPortalEffect(), new ManaCostsImpl<>("{3}"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private PhyrexianPortal(final PhyrexianPortal card) {
        super(card);
    }

    @Override
    public PhyrexianPortal copy() {
        return new PhyrexianPortal(this);
    }
}

class PhyrexianPortalEffect extends OneShotEffect {

    public PhyrexianPortalEffect() {
        super(Outcome.Benefit);
        this.staticText = "If your library has ten or more cards in it, target opponent looks at the top ten cards of your library and separates them into two face-down piles. Exile one of those piles. Search the other pile for a card, put it into your hand, then shuffle the rest of that pile into your library";
    }

    public PhyrexianPortalEffect(final PhyrexianPortalEffect effect) {
        super(effect);
    }

    @Override
    public PhyrexianPortalEffect copy() {
        return new PhyrexianPortalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (controller != null && opponent != null) {
            if (controller.getLibrary().size() >= 10) {
                Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 10));
                
                TargetCard target = new TargetCard(0, cards.size(), Zone.LIBRARY, new FilterCard("cards to put in the first pile"));
                List<Card> pile1 = new ArrayList<>();
                if (opponent.choose(Outcome.Neutral, cards, target, source, game)) {
                    List<UUID> targets = target.getTargets();
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

                game.informPlayers(opponent.getLogName() + " separated the top 10 cards of " + controller.getLogName() + "'s library into two face-down piles ("
                        + pile1.size() + " cards and " + pile2.size() + " cards)");
                // it's not viable to turn cards face down here for choosePile (since they're still library cards), this is a workaround
                boolean choice = controller.chooseUse(outcome, "Choose pile to search for a card (the other will be exiled):",
                    source.getSourceObject(game).getLogName(), "Pile 1 (" + pile1.size() + " cards)", "Pile 2 (" + pile2.size() + " cards)", source, game);

                game.informPlayers(controller.getLogName() + " chooses to search the " + (choice ? "first" : "second") + " pile");
                Cards pileToExile = new CardsImpl();
                pileToExile.addAllCards(choice ? pile2 : pile1);
                controller.moveCardsToExile(pileToExile.getCards(game), source, game, true, null, "");
                Cards chosenPile = new CardsImpl();
                chosenPile.addAllCards(choice ? pile1 : pile2);
                
                TargetCard target2 = new TargetCard(Zone.HAND, new FilterCard("card to put into your hand"));
                if (controller.choose(outcome, chosenPile, target2, source, game)) {
                    Card card = chosenPile.get(target2.getFirstTarget(), game);
                    if (card != null) {
                        controller.moveCards(card, Zone.HAND, source, game);
                    }
                }
                controller.shuffleLibrary(source, game);
            }
            return true;
        }
        return false;
    }
}
