package mage.cards.r;

import static java.lang.Integer.min;
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
import mage.target.TargetCard;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public final class Ransack extends CardImpl {

    public Ransack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Look at the top five cards of target player's library. 
        //Put any number of them on the bottom of that library in any order 
        //and the rest on top of the library in any order.
        this.getSpellAbility().addEffect(new RansackEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

    }

    private Ransack(final Ransack card) {
        super(card);
    }

    @Override
    public Ransack copy() {
        return new Ransack(this);
    }
}

class RansackEffect extends OneShotEffect {

    public RansackEffect() {
        super(Outcome.Detriment);
        this.staticText = "Look at the top five cards of target player's library. "
                + "Put any number of them on the bottom of that library in any order "
                + "and the rest on top of the library in any order";
    }

    public RansackEffect(final RansackEffect effect) {
        super(effect);
    }

    @Override
    public RansackEffect copy() {
        return new RansackEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        FilterCard filter = new FilterCard("cards to put on the bottom of your library");
        if (player != null) {
            int number = min(player.getLibrary().size(), 5);
            Set<Card> cards = player.getLibrary().getTopCards(game, number);
            Cards cardsRemaining = new CardsImpl();
            cardsRemaining.addAllCards(cards);
            TargetCard target = new TargetCard(0, number, Zone.LIBRARY, filter);
            if (player.choose(Outcome.DrawCard, cardsRemaining, target, source, game)) {
                Cards pickedCards = new CardsImpl(target.getTargets());
                cardsRemaining.removeAll(pickedCards);
                player.putCardsOnBottomOfLibrary(pickedCards, game, source, true);
                player.putCardsOnTopOfLibrary(cardsRemaining, game, source, true);
                return true;
            }
        }
        return false;
    }
}
