package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.Set;
import java.util.UUID;

/**
 * @author fireshoes
 */
public final class Amnesia extends CardImpl {

    public Amnesia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}{U}{U}");

        // Target player reveals their hand and discards all nonland cards.
        this.getSpellAbility().addEffect(new AmnesiaEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private Amnesia(final Amnesia card) {
        super(card);
    }

    @Override
    public Amnesia copy() {
        return new Amnesia(this);
    }
}

class AmnesiaEffect extends OneShotEffect {

    public AmnesiaEffect() {
        super(Outcome.Discard);
        this.staticText = "Target player reveals their hand and discards all nonland cards";
    }

    private AmnesiaEffect(final AmnesiaEffect effect) {
        super(effect);
    }

    @Override
    public AmnesiaEffect copy() {
        return new AmnesiaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            Cards hand = player.getHand();
            player.revealCards(source, hand, game);
            Set<Card> cards = hand.getCards(game);
            cards.removeIf(card -> card.isLand(game));
            player.discard(new CardsImpl(cards), false, source, game);
            return true;
        }
        return false;
    }
}
