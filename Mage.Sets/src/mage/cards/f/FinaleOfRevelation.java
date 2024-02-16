package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.UntapLandsEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FinaleOfRevelation extends CardImpl {

    public FinaleOfRevelation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}{U}");

        // Draw X cards. If X is 10 or more, instead shuffle your graveyard into your library, draw X cards, untap up to five lands, and you have no maximum hand size for the rest of the game.
        this.getSpellAbility().addEffect(new FinaleOfRevelationEffect());

        // Exile Finale of Revelation.
        this.getSpellAbility().addEffect(new ExileSpellEffect().concatBy("<br>"));
    }

    private FinaleOfRevelation(final FinaleOfRevelation card) {
        super(card);
    }

    @Override
    public FinaleOfRevelation copy() {
        return new FinaleOfRevelation(this);
    }
}

class FinaleOfRevelationEffect extends OneShotEffect {

    FinaleOfRevelationEffect() {
        super(Outcome.Benefit);
        staticText = "Draw X cards. If X is 10 or more, instead shuffle your graveyard into your library, " +
                "draw X cards, untap up to five lands, and you have no maximum hand size for the rest of the game";
    }

    private FinaleOfRevelationEffect(final FinaleOfRevelationEffect effect) {
        super(effect);
    }

    @Override
    public FinaleOfRevelationEffect copy() {
        return new FinaleOfRevelationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int xValue = source.getManaCostsToPay().getX();

        if (xValue < 10) {
            player.drawCards(xValue, source, game);
        } else {
            player.putCardsOnTopOfLibrary(player.getGraveyard(), game, source, false);
            player.shuffleLibrary(source, game);
            player.drawCards(xValue, source, game);
            new UntapLandsEffect(5).apply(game, source);
            game.addEffect(new MaximumHandSizeControllerEffect(
                    Integer.MAX_VALUE, Duration.EndOfGame,
                    MaximumHandSizeControllerEffect.HandSizeModification.SET
            ), source);
        }

        return true;
    }
}
