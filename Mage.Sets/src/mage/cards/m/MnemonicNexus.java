package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class MnemonicNexus extends CardImpl {

    public MnemonicNexus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Each player shuffles their graveyard into their library.
        this.getSpellAbility().addEffect(new MnemonicNexusEffect());
    }

    private MnemonicNexus(final MnemonicNexus card) {
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

    private MnemonicNexusEffect(final MnemonicNexusEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player sourcePlayer = game.getPlayer(source.getControllerId());
        if (sourcePlayer == null) {
            return false;
        }
        for (UUID playerId : game.getState().getPlayersInRange(sourcePlayer.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.shuffleCardsToLibrary(player.getGraveyard(), game, source);
            }
        }
        return true;
    }

    @Override
    public MnemonicNexusEffect copy() {
        return new MnemonicNexusEffect(this);
    }

}
