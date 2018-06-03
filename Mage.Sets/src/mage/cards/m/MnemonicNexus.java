
package mage.cards.m;

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

/**
 *
 * @author fireshoes
 */
public final class MnemonicNexus extends CardImpl {

    public MnemonicNexus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}");

        // Each player shuffles their graveyard into their library.
        this.getSpellAbility().addEffect(new MnemonicNexusEffect());
    }

    public MnemonicNexus(final MnemonicNexus card) {
        super(card);
    }

    @Override
    public MnemonicNexus copy() {
        return new MnemonicNexus(this);
    }
}

class MnemonicNexusEffect extends OneShotEffect {

    public MnemonicNexusEffect() {
        super(Outcome.Neutral);
        staticText = "Each player shuffles their graveyard into their library";
    }

    public MnemonicNexusEffect(final MnemonicNexusEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player sourcePlayer = game.getPlayer(source.getControllerId());
        for (UUID playerId: game.getState().getPlayersInRange(sourcePlayer.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                for (Card card: player.getGraveyard().getCards(game)) {
                    card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                }                               
                player.shuffleLibrary(source, game);
            }
        }
        return true;
    }

    @Override
    public MnemonicNexusEffect copy() {
        return new MnemonicNexusEffect(this);
    }

}