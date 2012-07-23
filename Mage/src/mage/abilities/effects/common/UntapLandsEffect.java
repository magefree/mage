package mage.abilities.effects.common;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * "Untap up to X lands" effect
 */
public class UntapLandsEffect extends OneShotEffect<UntapLandsEffect> {
    private int amount;

    public UntapLandsEffect(int amount) {
        super(Constants.Outcome.Untap);
        this.amount = amount;
        staticText = "Untap up to " + amount + " lands";
    }

    public UntapLandsEffect(final UntapLandsEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        TargetLandPermanent target = new TargetLandPermanent(0, amount, new FilterLandPermanent(), true);
        if (target.canChoose(source.getControllerId(), game)) {
            if (target.choose(Constants.Outcome.Untap, source.getControllerId(), source.getSourceId(), game)) {
                for (Object targetId : target.getTargets()) {
                    Permanent p = game.getPermanent((UUID) targetId);
                    if (p.isTapped())
                        p.untap(game);
                }
            }
        }
        return false;
    }

    @Override
    public UntapLandsEffect copy() {
        return new UntapLandsEffect(this);
    }
}
