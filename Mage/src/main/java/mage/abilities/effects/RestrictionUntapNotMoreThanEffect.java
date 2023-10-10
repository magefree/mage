

package mage.abilities.effects;

import mage.abilities.Ability;
import mage.constants.Duration;
import mage.constants.EffectType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;

/**
 * @author LevelX2
 */
public abstract class RestrictionUntapNotMoreThanEffect extends ContinuousEffectImpl {

    private final int number;
    private FilterControlledPermanent filter;

    public RestrictionUntapNotMoreThanEffect(Duration duration, int number, FilterControlledPermanent filter) {
        super(duration, Outcome.Detriment);
        this.effectType = EffectType.RESTRICTION_UNTAP_NOT_MORE_THAN;
        this.number = number;
        this.filter = filter;
    }

    protected RestrictionUntapNotMoreThanEffect(final RestrictionUntapNotMoreThanEffect effect) {
        super(effect);
        this.number = effect.number;
        if (effect.filter != null) {
            this.filter = effect.filter.copy();
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        throw new UnsupportedOperationException("Not supported.");
    }

    public abstract boolean applies(Player player, Ability source, Game game);

    public int getNumber() {
        return number;
    }

    public FilterControlledPermanent getFilter() {
        return filter;
    }

}
