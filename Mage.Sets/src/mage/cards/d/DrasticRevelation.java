package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class DrasticRevelation extends CardImpl {

    public DrasticRevelation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{B}{R}");

        // Discard your hand. Draw seven cards, then discard three cards at random.
        this.getSpellAbility().addEffect(new DrasticRevelationEffect());
    }

    private DrasticRevelation(final DrasticRevelation card) {
        super(card);
    }

    @Override
    public DrasticRevelation copy() {
        return new DrasticRevelation(this);
    }
}

class DrasticRevelationEffect extends OneShotEffect {

    DrasticRevelationEffect() {
        super(Outcome.DrawCard);
        staticText = "Discard your hand. Draw seven cards, then discard three cards at random";
    }

    private DrasticRevelationEffect(final DrasticRevelationEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        if (you == null) {
            return false;
        }
        you.discard(you.getHand(), false, source, game);
        you.drawCards(7, source, game);
        you.discard(3, true, false, source, game);
        return false;
    }

    @Override
    public DrasticRevelationEffect copy() {
        return new DrasticRevelationEffect(this);
    }
}
