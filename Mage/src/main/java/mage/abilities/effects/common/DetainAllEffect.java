package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTargets;

import java.util.List;

/**
 * @author LevelX2
 */
public class DetainAllEffect extends OneShotEffect {

    private FilterPermanent filter = new FilterPermanent();

    public DetainAllEffect(FilterPermanent filter) {
        super(Outcome.Benefit);
        this.filter = filter;
        this.staticText = "detain " + filter.getMessage();
    }

    protected DetainAllEffect(final DetainAllEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public DetainAllEffect copy() {
        return new DetainAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> detainedPermanents = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game);
        if (!game.isSimulation()) {
            for (Permanent permanent : detainedPermanents) {
                game.informPlayers("Detained permanent: " + permanent.getName());
            }
        }
        ContinuousEffect effect = new DetainRestrictionEffect();
        effect.setTargetPointer(new FixedTargets(detainedPermanents, game));
        game.addEffect(effect, source);
        return true;
    }
}
