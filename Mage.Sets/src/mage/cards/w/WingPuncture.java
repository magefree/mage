
package mage.cards.w;

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
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class WingPuncture extends CardImpl {

    public WingPuncture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Target creature you control deals damage equal to its power to target creature with flying.
        this.getSpellAbility().addEffect(new WingPunctureEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING));
    }

    private WingPuncture(final WingPuncture card) {
        super(card);
    }

    @Override
    public WingPuncture copy() {
        return new WingPuncture(this);
    }
}

class WingPunctureEffect extends OneShotEffect {

    WingPunctureEffect() {
        super(Outcome.Damage);
        staticText = "Target creature you control deals damage equal to its power to target creature with flying";
    }

    private WingPunctureEffect(final WingPunctureEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getFirstTarget());
        if (sourcePermanent == null) {
            sourcePermanent = (Permanent) game.getLastKnownInformation(source.getFirstTarget(), Zone.BATTLEFIELD);
        }

        Permanent targetPermanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (sourcePermanent != null && targetPermanent != null) {
            targetPermanent.damage(sourcePermanent.getPower().getValue(), sourcePermanent.getId(), source, game, false, true);
            return true;
        }
        return false;
    }

    @Override
    public WingPunctureEffect copy() {
        return new WingPunctureEffect(this);
    }

}
