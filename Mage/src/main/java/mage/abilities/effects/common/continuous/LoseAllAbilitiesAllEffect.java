
package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class LoseAllAbilitiesAllEffect extends ContinuousEffectImpl {

    private final FilterPermanent filter;

    public LoseAllAbilitiesAllEffect(FilterPermanent filter, Duration duration) {
        super(duration, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.LoseAbility);
        this.filter = filter;
    }

    public LoseAllAbilitiesAllEffect(final LoseAllAbilitiesAllEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
    }

    @Override
    public LoseAllAbilitiesAllEffect copy() {
        return new LoseAllAbilitiesAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            if (permanent != null) {
                permanent.removeAllAbilities(source.getSourceId(), game);
            }
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        if (duration == Duration.EndOfTurn) {
            sb.append(duration.toString()).append(", ");
        }
        sb.append(filter.getMessage()).append(" lose all abilities.");
        return sb.toString();
    }

}
