

package mage.abilities.effects;

import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.abilities.Ability;
import mage.counters.BoostCounter;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ApplyCountersEffect extends ContinuousEffectImpl {

    public ApplyCountersEffect() {
        super(Duration.EndOfGame, Layer.PTChangingEffects_7, SubLayer.Counters_7d, Outcome.BoostCreature);
    }

    public ApplyCountersEffect(ApplyCountersEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent: game.getBattlefield().getAllActivePermanents(CardType.CREATURE)) {
            for (BoostCounter counter: permanent.getCounters(game).getBoostCounters()) {
                permanent.addPower(counter.getPower() * counter.getCount());
                permanent.addToughness(counter.getToughness() * counter.getCount());
            }
        }
        return true;
    }

    @Override
    public ApplyCountersEffect copy() {
        return new ApplyCountersEffect(this);
    }

}
