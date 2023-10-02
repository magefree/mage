
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class CertainDeath extends CardImpl {

    public CertainDeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{B}");

        // Destroy target creature. Its controller loses 2 life and you gain 2 life.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new CertainDeathEffect());
    }

    private CertainDeath(final CertainDeath card) {
        super(card);
    }

    @Override
    public CertainDeath copy() {
        return new CertainDeath(this);
    }
}

class CertainDeathEffect extends OneShotEffect {

    public CertainDeathEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target creature. Its controller loses 2 life and you gain 2 life";
    }

    private CertainDeathEffect(final CertainDeathEffect effect) {
        super(effect);
    }

    @Override
    public CertainDeathEffect copy() {
        return new CertainDeathEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (permanent != null && you != null) {
            permanent.destroy(source, game, false);
            Player permController = game.getPlayer(permanent.getControllerId());
            if (permController != null) {
                permController.loseLife(2, game, source, false);
                you.gainLife(2, game, source);
                return true;
            }
        }
        return false;
    }
}