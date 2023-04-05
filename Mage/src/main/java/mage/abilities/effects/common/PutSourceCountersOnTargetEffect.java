package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801
 */
public class PutSourceCountersOnTargetEffect extends OneShotEffect {

    public PutSourceCountersOnTargetEffect() {
        super(Outcome.Benefit);
    }

    private PutSourceCountersOnTargetEffect(final PutSourceCountersOnTargetEffect effect) {
        super(effect);
    }

    @Override
    public PutSourceCountersOnTargetEffect copy() {
        return new PutSourceCountersOnTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = (Permanent) getValue("permanentLeftBattlefield");
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (sourcePermanent == null || permanent == null) {
            return false;
        }
        sourcePermanent
                .getCounters(game)
                .values()
                .stream()
                .forEach(counter -> permanent.addCounters(counter, source.getControllerId(), source, game));
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "put its counters on target " + mode.getTargets().get(0).getTargetName();
    }
}
