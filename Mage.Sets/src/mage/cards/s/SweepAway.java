
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
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
 * @author fireshoes
 */
public final class SweepAway extends CardImpl {

    public SweepAway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");

        // Return target creature to its owner's hand. If that creature is attacking, you may put it on top of its owner's library instead.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new SweepAwayEffect());
    }

    private SweepAway(final SweepAway card) {
        super(card);
    }

    @Override
    public SweepAway copy() {
        return new SweepAway(this);
    }
}

class SweepAwayEffect extends OneShotEffect {

    public SweepAwayEffect() {
        super(Outcome.Benefit);
        staticText = "Return target creature to its owner's hand. If that creature is attacking, you may put it on top of its owner's library instead";
    }

    private SweepAwayEffect(final SweepAwayEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (permanent != null && controller != null) {
            if (permanent.isAttacking()) {
                if (controller.chooseUse(Outcome.Neutral, "Put " + permanent.getIdName() + " on top of its owner's library (otherwise return to hand)?", source, game)) {
                    new PutOnLibraryTargetEffect(true).apply(game, source);
                } else {
                    new ReturnToHandTargetEffect().apply(game, source);
                }
            } else {
                new ReturnToHandTargetEffect().apply(game, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public SweepAwayEffect copy() {
        return new SweepAwayEffect(this);
    }
}
