
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author North
 */
public final class IntoTheMawOfHell extends CardImpl {

    public IntoTheMawOfHell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{R}{R}");


        // Destroy target land. Into the Maw of Hell deals 13 damage to target creature.
        this.getSpellAbility().addTarget(new TargetLandPermanent());
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new IntoTheMawOfHellEffect());
    }

    private IntoTheMawOfHell(final IntoTheMawOfHell card) {
        super(card);
    }

    @Override
    public IntoTheMawOfHell copy() {
        return new IntoTheMawOfHell(this);
    }
}

class IntoTheMawOfHellEffect extends OneShotEffect {

    public IntoTheMawOfHellEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 13 damage to target creature";
    }

    private IntoTheMawOfHellEffect(final IntoTheMawOfHellEffect effect) {
        super(effect);
    }

    @Override
    public IntoTheMawOfHellEffect copy() {
        return new IntoTheMawOfHellEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (permanent != null) {
            permanent.damage(13, source.getSourceId(), source, game, false, true);
            return true;
        }
        return false;
    }
}
