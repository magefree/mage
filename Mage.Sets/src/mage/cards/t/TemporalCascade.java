
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ShuffleHandGraveyardAllEffect;
import mage.abilities.keyword.EntwineAbility;
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
public final class TemporalCascade extends CardImpl {

    public TemporalCascade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{U}{U}");

        // Choose one - Each player shuffles their hand and graveyard into their library;
        this.getSpellAbility().addEffect(new ShuffleHandGraveyardAllEffect());

        // or each player draws seven cards.
        Mode mode = new Mode(new TemporalCascadeDrawEffect());
        this.getSpellAbility().getModes().addMode(mode);

        // Entwine {2}
        this.addAbility(new EntwineAbility("{2}"));
    }

    private TemporalCascade(final TemporalCascade card) {
        super(card);
    }

    @Override
    public TemporalCascade copy() {
        return new TemporalCascade(this);
    }
}

class TemporalCascadeDrawEffect extends OneShotEffect {

    public TemporalCascadeDrawEffect() {
        super(Outcome.Neutral);
        staticText = "Each player draws seven cards";
    }

    public TemporalCascadeDrawEffect(final TemporalCascadeDrawEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player sourcePlayer = game.getPlayer(source.getControllerId());
        game.getState().handleSimultaneousEvent(game); // needed here so state based triggered effects 
        for (UUID playerId : game.getState().getPlayersInRange(sourcePlayer.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.drawCards(7, source, game);
            }
        }
        return true;
    }

    @Override
    public TemporalCascadeDrawEffect copy() {
        return new TemporalCascadeDrawEffect(this);
    }
}
