
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class WrackWithMadness extends CardImpl {

    public WrackWithMadness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}");


        // Target creature deals damage to itself equal to its power.
        this.getSpellAbility().addEffect(new WrackWithMadnessEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private WrackWithMadness(final WrackWithMadness card) {
        super(card);
    }

    @Override
    public WrackWithMadness copy() {
        return new WrackWithMadness(this);
    }
}

class WrackWithMadnessEffect extends OneShotEffect {

    public WrackWithMadnessEffect() {
        super(Outcome.Damage);
        this.staticText = "Target creature deals damage to itself equal to its power";
    }

    private WrackWithMadnessEffect(final WrackWithMadnessEffect effect) {
        super(effect);
    }

    @Override
    public WrackWithMadnessEffect copy() {
        return new WrackWithMadnessEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (permanent != null) {
            permanent.damage(permanent.getPower().getValue(), permanent.getId(), source, game, false, true);
            return true;
        }
        return false;
    }
}
