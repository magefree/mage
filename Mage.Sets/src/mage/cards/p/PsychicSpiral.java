
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class PsychicSpiral extends CardImpl {

    public PsychicSpiral(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{U}");


        // Shuffle all cards from your graveyard into your library. Target player puts that many cards from the top of their library into their graveyard.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new PsychicSpiralEffect());
    }

    public PsychicSpiral(final PsychicSpiral card) {
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
        staticText = "Shuffle all cards from your graveyard into your library. Target player puts that many cards from the top of their library into their graveyard";
    }

    public PsychicSpiralEffect(final PsychicSpiralEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int cardsInGraveyard = player.getGraveyard().size();
            for (Card card: player.getGraveyard().getCards(game)) {
                card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
            }                           
            player.shuffleLibrary(source, game);

            if (cardsInGraveyard > 0) {
                Player targetPlayer = game.getPlayer(source.getFirstTarget());
                if (targetPlayer != null) {
                    targetPlayer.moveCards(targetPlayer.getLibrary().getTopCards(game, cardsInGraveyard), Zone.GRAVEYARD, source, game);
                }
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