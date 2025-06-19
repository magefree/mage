package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.RabbitToken;
import mage.game.permanent.token.Token;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Grath
 */
public final class TemptWithBunnies extends CardImpl {

    public TemptWithBunnies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}");

        // Tempting offer -- Draw a card and create a 1/1 white Rabbit creature token. Then each opponent may draw a
        // card and create a 1/1 white Rabbit creature token. For each opponent who does, you draw a card and you
        // create a 1/1 white Rabbit creature token.
        this.getSpellAbility().addEffect(new TemptWithBunniesEffect());
        this.getSpellAbility().withFlavorWord("Tempting Offer");
    }

    private TemptWithBunnies(final TemptWithBunnies card) {
        super(card);
    }

    @Override
    public TemptWithBunnies copy() {
        return new TemptWithBunnies(this);
    }
}

class TemptWithBunniesEffect extends OneShotEffect {

    TemptWithBunniesEffect() {
        super(Outcome.PutLandInPlay);
        this.staticText = "draw a card and create a 1/1 white Rabbit creature token. Then each opponent may draw a card and create a 1/1 white Rabbit creature token. For each opponent who does, you draw a card and you create a 1/1 white Rabbit creature token.";
    }

    private TemptWithBunniesEffect(final TemptWithBunniesEffect effect) {
        super(effect);
    }

    @Override
    public TemptWithBunniesEffect copy() {
        return new TemptWithBunniesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {

            controller.drawCards(1, source, game);
            Token tokenCopy = new RabbitToken();
            tokenCopy.putOntoBattlefield(1, game, source, source.getControllerId(), false, false);

            int opponentsAddedTokens = 0;
            for (UUID playerId : game.getOpponents(controller.getId())) {
                Player opponent = game.getPlayer(playerId);
                if (opponent != null) {
                    if (opponent.chooseUse(outcome, "Draw a card and create a Rabbit token?", source, game)) {
                        opponent.drawCards(1, source, game);
                        opponentsAddedTokens++;
                        tokenCopy.putOntoBattlefield(1, game, source, playerId, false, false);
                    }
                }
            }
            if (opponentsAddedTokens > 0) {
                controller.drawCards(opponentsAddedTokens, source, game);
                tokenCopy.putOntoBattlefield(opponentsAddedTokens, game, source, source.getControllerId(), false, false);
            }
            return true;
        }

        return false;
    }
}
