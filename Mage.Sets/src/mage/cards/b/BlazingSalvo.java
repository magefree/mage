
package mage.cards.b;

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
 * @author cbt33
 */
public final class BlazingSalvo extends CardImpl {

    public BlazingSalvo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");

        // Blazing Salvo deals 3 damage to target creature unless that creature's controller has Blazing Salvo deal 5 damage to him or her.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BlazingSalvoEffect());
    }

    public BlazingSalvo(final BlazingSalvo card) {
        super(card);
    }

    @Override
    public BlazingSalvo copy() {
        return new BlazingSalvo(this);
    }
}

class BlazingSalvoEffect extends OneShotEffect {

    public BlazingSalvoEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 3 damage to target creature unless that creature's controller has {this} deal 5 damage to him or her";
    }

    public BlazingSalvoEffect(final BlazingSalvoEffect effect) {
        super(effect);
    }

    @Override
    public BlazingSalvoEffect copy() {
        return new BlazingSalvoEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (permanent != null) {
            Player player = game.getPlayer(permanent.getControllerId());
            if (player != null) {
                String message = "Have Blazing Salvo do 5 damage to you?";
                if (player.chooseUse(Outcome.Damage, message, source, game)) {
                    player.damage(5, source.getSourceId(), game, false, true);
                } else {
                    permanent.damage(3, source.getSourceId(), game, false, true);
                }
                return true;
            }
        }
        return false;
    }

}
