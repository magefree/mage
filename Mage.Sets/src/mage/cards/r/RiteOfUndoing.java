
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DelveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author fireshoes
 */
public final class RiteOfUndoing extends CardImpl {
    
    private static final FilterNonlandPermanent filterControlled = new FilterNonlandPermanent("nonland permanent you control");
    private static final FilterNonlandPermanent filterNotControlled = new FilterNonlandPermanent("nonland permanent you don't control");

    static {
        filterControlled.add(new ControllerPredicate(TargetController.YOU));
        filterNotControlled.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public RiteOfUndoing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{U}");

        // Delve
        this.addAbility(new DelveAbility());
        
        // Return target nonland permanent you control and target nonland permanent you don't control to their owners' hands.
        this.getSpellAbility().addEffect(new RiteOfUndoingEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent(filterControlled));
        this.getSpellAbility().addTarget(new TargetNonlandPermanent(filterNotControlled));
    }

    public RiteOfUndoing(final RiteOfUndoing card) {
        super(card);
    }

    @Override
    public RiteOfUndoing copy() {
        return new RiteOfUndoing(this);
    }
}

class RiteOfUndoingEffect extends OneShotEffect {

    public RiteOfUndoingEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return target nonland permanent you control and target nonland permanent you don't control to their owners' hands";
    }

    public RiteOfUndoingEffect(final RiteOfUndoingEffect effect) {
        super(effect);
    }

    @Override
    public RiteOfUndoingEffect copy() {
        return new RiteOfUndoingEffect(this);
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