package mage.cards.t;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author arcox
 */
public final class TrackDown extends CardImpl {

    public TrackDown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Scry 3, then reveal the top card of your library. If it's a creature or land card, draw a card.
        this.getSpellAbility().addEffect(new ScryEffect(3, false));
        this.getSpellAbility().addEffect(new TrackDownEffect().concatBy(", then"));
    }

    private TrackDown(final TrackDown card) {
        super(card);
    }

    @Override
    public TrackDown copy() {
        return new TrackDown(this);
    }
}

class TrackDownEffect extends OneShotEffect {

    public TrackDownEffect() {
        super(Outcome.DrawCard);
        this.staticText = "reveal the top card of your library. If it's a creature or land card, draw a card";
    }

    public TrackDownEffect(final TrackDownEffect effect) {
        super(effect);
    }

    @Override
    public TrackDownEffect copy() {
        return new TrackDownEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);

        if (sourceObject == null || controller == null) {
            return false;
        }

        if (!controller.getLibrary().hasCards()) {
            return false;
        }

        Card card = controller.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }

        CardsImpl cards = new CardsImpl();
        cards.add(card);
        controller.revealCards(sourceObject.getName(), cards, game);
        if (card.isLand(game) || card.isCreature(game)) {
            controller.drawCards(1, source, game);
        }

        return true;
    }
}
