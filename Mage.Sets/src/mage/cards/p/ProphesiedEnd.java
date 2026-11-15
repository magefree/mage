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
 * @author muz
 */
public final class ProphesiedEnd extends CardImpl {

    public ProphesiedEnd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Destroy target creature. If it wasn't attacking, its controller draws a card.
        this.getSpellAbility().addEffect(new ProphesiedEndEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ProphesiedEnd(final ProphesiedEnd card) {
        super(card);
    }

    @Override
    public ProphesiedEnd copy() {
        return new ProphesiedEnd(this);
    }
}


class ProphesiedEndEffect extends OneShotEffect {

    ProphesiedEndEffect() {
        super(Outcome.Benefit);
        staticText = "destroy target creature. If it wasn't attacking, its controller draws a card.";
    }

    private ProphesiedEndEffect(final ProphesiedEndEffect effect) {
        super(effect);
    }

    @Override
    public ProphesiedEndEffect copy() {
        return new ProphesiedEndEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        boolean attacking = permanent.isAttacking();

        permanent.destroy(source, game, false);
        if (player != null && !attacking) {
            player.drawCards(1, source, game);
        }
        return true;
    }
}
