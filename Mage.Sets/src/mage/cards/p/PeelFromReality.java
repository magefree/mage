
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class PeelFromReality extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you don't control");

    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public PeelFromReality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");


        // Return target creature you control and target creature you don't control to their owners' hands.
        this.getSpellAbility().addEffect(new PeelFromRealityEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    public PeelFromReality(final PeelFromReality card) {
        super(card);
    }

    @Override
    public PeelFromReality copy() {
        return new PeelFromReality(this);
    }
}

class PeelFromRealityEffect extends OneShotEffect {

    public PeelFromRealityEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return target creature you control and target creature you don't control to their owners' hands";
    }

    public PeelFromRealityEffect(final PeelFromRealityEffect effect) {
        super(effect);
    }

    @Override
    public PeelFromRealityEffect copy() {
        return new PeelFromRealityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;

        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            result |= permanent.moveToZone(Zone.HAND, source.getSourceId(), game, false);
        }
        permanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (permanent != null) {
            result |= permanent.moveToZone(Zone.HAND, source.getSourceId(), game, false);
        }

        return result;
    }
}
