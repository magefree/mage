
package mage.cards.p;

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
 * @author Scott-Crawford
 */
public final class PathOfPeace extends CardImpl {

    public PathOfPeace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}");

        // Destroy target creature. Its owner gains 4 life.
        this.getSpellAbility().addEffect(new PathOfPeaceEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private PathOfPeace(final PathOfPeace card) {
        super(card);
    }

    @Override
    public PathOfPeace copy() {
        return new PathOfPeace(this);
    }
}

class PathOfPeaceEffect extends OneShotEffect {

    public PathOfPeaceEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target creature. Its owner gains 4 life";
    }

    private PathOfPeaceEffect(final PathOfPeaceEffect effect) {
        super(effect);
    }

    @Override
    public PathOfPeaceEffect copy() {
        return new PathOfPeaceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent target = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller != null && target != null) {
            target.destroy(source, game, false);
            Player owner = game.getPlayer(target.getOwnerId());
            if (owner != null) {
                owner.gainLife(4, game, source);
            }
            return true;
        }
        return false;
    }
}
