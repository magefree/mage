package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
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
 * @author TheElk801
 */
public final class ElvenFarsight extends CardImpl {

    public ElvenFarsight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Scry 3, then you may reveal the top card of your library. If it's a creature card, draw a card.
        this.getSpellAbility().addEffect(new ElvenFarsightEffect());
    }

    private ElvenFarsight(final ElvenFarsight card) {
        super(card);
    }

    @Override
    public ElvenFarsight copy() {
        return new ElvenFarsight(this);
    }
}

class ElvenFarsightEffect extends OneShotEffect {

    ElvenFarsightEffect() {
        super(Outcome.Benefit);
        staticText = "scry 3, then you may reveal the top card of your library. If it's a creature card, draw a card";
    }

    private ElvenFarsightEffect(final ElvenFarsightEffect effect) {
        super(effect);
    }

    @Override
    public ElvenFarsightEffect copy() {
        return new ElvenFarsightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.scry(3, source, game);
        Card card = player.getLibrary().getFromTop(game);
        if (card == null || !player.chooseUse(outcome, "Reveal " + card.getName() + '?', source, game)) {
            return true;
        }
        player.revealCards(source, new CardsImpl(card), game);
        if (card.isCreature(game)) {
            player.drawCards(1, source, game);
        }
        return true;
    }
}
