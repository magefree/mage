package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class PeelFromReality extends CardImpl {

    public PeelFromReality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Return target creature you control and target creature you don't control to their owners' hands.
        this.getSpellAbility().addEffect(new PeelFromRealityEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
    }

    private PeelFromReality(final PeelFromReality card) {
        super(card);
    }

    @Override
    public PeelFromReality copy() {
        return new PeelFromReality(this);
    }
}

class PeelFromRealityEffect extends OneShotEffect {

    PeelFromRealityEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return target creature you control and target creature you don't control to their owners' hands";
    }

    private PeelFromRealityEffect(final PeelFromRealityEffect effect) {
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
