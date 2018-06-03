
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.InfectAbility;
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
 * @author ayratn
 */
public final class BurntheImpure extends CardImpl {

    public BurntheImpure(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");

        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BurntheImpureEffect());
    }

    public BurntheImpure(final BurntheImpure card) {
        super(card);
    }

    @Override
    public BurntheImpure copy() {
        return new BurntheImpure(this);
    }

}

class BurntheImpureEffect extends OneShotEffect {

    public BurntheImpureEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals 3 damage to target creature. If that creature has infect, {this} deals 3 damage to that creature's controller.";
    }

    public BurntheImpureEffect(final BurntheImpureEffect effect) {
        super(effect);
    }

    @Override
    public BurntheImpureEffect copy() {
        return new BurntheImpureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            permanent.damage(3, source.getSourceId(), game, false, true);
            if (permanent.getAbilities().contains(InfectAbility.getInstance())) {
                Player controller = game.getPlayer(permanent.getControllerId());
                if (controller != null) {
                    controller.damage(3, source.getSourceId(), game, false, true);
                }
            }
            return true;
        }
        return false;
    }
}
