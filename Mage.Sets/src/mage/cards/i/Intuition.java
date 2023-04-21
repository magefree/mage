
package mage.cards.i;

import java.util.List;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.SearchEffect;
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
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;

/**
 * @author Plopman
 */
public final class Intuition extends CardImpl {

    public Intuition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");


        // Search your library for three cards and reveal them. Target opponent chooses one. Put that card into your hand and the rest into your graveyard. Then shuffle your library.
        this.getSpellAbility().addEffect(new IntuitionEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private Intuition(final Intuition card) {
        super(card);
    }

    @Override
    public Intuition copy() {
        return new Intuition(this);
    }
}

class IntuitionEffect extends SearchEffect {


    public IntuitionEffect() {
        super(new TargetCardInLibrary(3, new FilterCard()), Outcome.Benefit);
        staticText = "Search your library for three cards and reveal them. Target opponent chooses one. Put that card into your hand and the rest into your graveyard. Then shuffle";
    }


    public IntuitionEffect(final IntuitionEffect effect) {
        super(effect);
    }

    @Override
    public IntuitionEffect copy() {
        return new IntuitionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (controller == null || opponent == null)
            return false;

        if (controller.getLibrary().size() >= 3 && controller.searchLibrary(target, source, game)) {

            if (target.getTargets().size() == 3) {
                Cards cards = new CardsImpl();
                for (UUID cardId : target.getTargets()) {
                    Card card = controller.getLibrary().getCard(cardId, game);
                    if (card != null) {
                        cards.add(card);
                    }
                }
                controller.revealCards("Reveal", cards, game);

                TargetCard targetCard = new TargetCard(Zone.LIBRARY, new FilterCard());

                while (!opponent.choose(Outcome.Neutral, cards, targetCard, source, game)) {
                    if (!opponent.canRespond()) {
                        return false;
                    }
                }
                Card card = cards.get(targetCard.getFirstTarget(), game);
                if (card != null) {
                    cards.remove(card);
                    controller.moveCards(card, Zone.HAND, source, game);
                }
                controller.moveCards(cards, Zone.GRAVEYARD, source, game);
            }
            controller.shuffleLibrary(source, game);
            return true;
        }

        controller.shuffleLibrary(source, game);
        return false;
    }

    public List<UUID> getTargets() {
        return target.getTargets();
    }

}