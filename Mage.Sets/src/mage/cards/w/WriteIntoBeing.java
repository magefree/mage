package mage.cards.w;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ManifestEffect;
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

/**
 *
 * @author LevelX2
 */
public final class WriteIntoBeing extends CardImpl {

    public WriteIntoBeing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Look at the top two cards of your library. Manifest one of those cards, then put the other on the top or bottom of your library.
        this.getSpellAbility().addEffect(new WriteIntoBeingEffect());
    }

    private WriteIntoBeing(final WriteIntoBeing card) {
        super(card);
    }

    @Override
    public WriteIntoBeing copy() {
        return new WriteIntoBeing(this);
    }
}

class WriteIntoBeingEffect extends OneShotEffect {

    public WriteIntoBeingEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Look at the top two cards of your library. Manifest one of those cards, then put the other on the top or bottom of your library. "
                + "<i>(To manifest a card, put it onto the battlefield face down as a 2/2 creature. Turn it face up any time for its mana cost if it's a creature card.)</i>";
    }

    public WriteIntoBeingEffect(final WriteIntoBeingEffect effect) {
        super(effect);
    }

    @Override
    public WriteIntoBeingEffect copy() {
        return new WriteIntoBeingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (sourceObject != null && controller != null) {
            Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 2));
            controller.lookAtCards(source, null, cards, game);
            Card cardToManifest = null;
            if (cards.size() > 1) {
                TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCard("card to manifest"));
                if (controller.chooseTarget(outcome, cards, target, source, game)) {
                    cardToManifest = cards.get(target.getFirstTarget(), game);
                }
            } else {
                cardToManifest = cards.getRandom(game);
            }
            if (!controller.getLibrary().getFromTop(game).equals(cardToManifest)) {
                Card cardToPutBack = controller.getLibrary().removeFromTop(game);
                cardToManifest = controller.getLibrary().removeFromTop(game);
                controller.getLibrary().putOnTop(cardToPutBack, game);
                controller.getLibrary().putOnTop(cardToManifest, game);
            }
            new ManifestEffect(1).apply(game, source);
            if (controller.getLibrary().hasCards()) {
                Card cardToPutBack = controller.getLibrary().getFromTop(game);
                if (controller.chooseUse(Outcome.Detriment, "Put " + cardToPutBack.getName() + " on bottom of library?", source, game)) {
                    controller.putCardsOnBottomOfLibrary(cardToPutBack, game, source, true);
                } else {
                    controller.putCardsOnTopOfLibrary(cardToPutBack, game, source, true);
                }
            }
            return true;
        }
        return false;
    }
}
