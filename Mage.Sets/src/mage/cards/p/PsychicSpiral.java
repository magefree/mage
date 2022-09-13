package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class PsychicSpiral extends CardImpl {

    public PsychicSpiral(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}");

        // Shuffle all cards from your graveyard into your library. Target player puts that many cards from the top of their library into their graveyard.
        this.getSpellAbility().addEffect(new PsychicSpiralEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private PsychicSpiral(final PsychicSpiral card) {
        super(card);
    }

    @Override
    public PsychicSpiral copy() {
        return new PsychicSpiral(this);
    }
}

class PsychicSpiralEffect extends OneShotEffect {

    public PsychicSpiralEffect() {
        super(Outcome.GainLife);
        staticText = "Shuffle all cards from your graveyard into your library. Target player mills that many cards";
    }

    public PsychicSpiralEffect(final PsychicSpiralEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (controller != null && targetPlayer != null) {
            int cardsInGraveyard = controller.getGraveyard().size();
            controller.shuffleCardsToLibrary(controller.getGraveyard(), game, source);
            if (cardsInGraveyard > 0) {
                targetPlayer.millCards(cardsInGraveyard, source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public PsychicSpiralEffect copy() {
        return new PsychicSpiralEffect(this);
    }
}
