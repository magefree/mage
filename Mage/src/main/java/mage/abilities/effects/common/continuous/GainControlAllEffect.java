package mage.abilities.effects.common.continuous;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fenhl
 */
public class GainControlAllEffect extends OneShotEffect {

    private final FilterPermanent filter;
    private final UUID controllingPlayerId;
    private final Duration duration;

    public GainControlAllEffect(Duration duration, FilterPermanent filter) {
        this(duration, filter, null);
    }

    public GainControlAllEffect(Duration duration, FilterPermanent filter, UUID controllingPlayerId) {
        super(Outcome.GainControl);
        this.filter = filter;
        this.duration = duration;
        this.controllingPlayerId = controllingPlayerId;
        this.staticText = "gain control of " + filter.getMessage();
    }

    public GainControlAllEffect(final GainControlAllEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
        this.duration = effect.duration;
        this.controllingPlayerId = effect.controllingPlayerId;
    }

    @Override
    public GainControlAllEffect copy() {
        return new GainControlAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield()
                .getActivePermanents(filter,
                        source.getControllerId(), source, game)) {
            ContinuousEffect effect = new GainControlTargetEffect(Duration.Custom, controllingPlayerId);
            effect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(effect, source);
        }
        return true;
    }
}
