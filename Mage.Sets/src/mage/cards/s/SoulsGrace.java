
package mage.cards.s;

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
 * @author North
 */
public final class SoulsGrace extends CardImpl {

    public SoulsGrace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");


        // You gain life equal to target creature's power.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new SoulsGraceEffect());
    }

    private SoulsGrace(final SoulsGrace card) {
        super(card);
    }

    @Override
    public SoulsGrace copy() {
        return new SoulsGrace(this);
    }
}

class SoulsGraceEffect extends OneShotEffect {

    public SoulsGraceEffect() {
        super(Outcome.GainLife);
        this.staticText = "You gain life equal to target creature's power";
    }

    private SoulsGraceEffect(final SoulsGraceEffect effect) {
        super(effect);
    }

    @Override
    public SoulsGraceEffect copy() {
        return new SoulsGraceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        Player player = game.getPlayer(source.getControllerId());
        if (permanent != null && player != null) {
            int amount = permanent.getPower().getValue();
            if (amount > 0) {
                player.gainLife(amount, game, source);
                return true;
            }
        }
        return false;
    }
}
