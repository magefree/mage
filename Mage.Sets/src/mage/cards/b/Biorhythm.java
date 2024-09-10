
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LoneFox
 */
public final class Biorhythm extends CardImpl {

    public Biorhythm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{6}{G}{G}");

        // Each player's life total becomes the number of creatures they control.
        this.getSpellAbility().addEffect(new BiorhythmEffect());
    }

    private Biorhythm(final Biorhythm card) {
        super(card);
    }

    @Override
    public Biorhythm copy() {
        return new Biorhythm(this);
    }
}

class BiorhythmEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public BiorhythmEffect() {
        super(Outcome.Neutral);
        this.staticText = "Each player's life total becomes the number of creatures they control";
    }

    private BiorhythmEffect(final BiorhythmEffect effect) {
        super(effect);
    }

    @Override
    public BiorhythmEffect copy() {
        return new BiorhythmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for(UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if(player != null) {
                int diff = player.getLife() - game.getBattlefield().countAll(filter, playerId, game);
                if(diff > 0) {
                    player.loseLife(diff, game, source, false);
                }
                if(diff < 0) {
                    player.gainLife(-diff, game, source);
                }
            }
        }
        return true;
    }
}
