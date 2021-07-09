package mage.abilities.effects;

import mage.abilities.Ability;
import mage.constants.*;
import mage.counters.AbilityCounter;
import mage.counters.BoostCounter;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ApplyCountersEffect extends ContinuousEffectImpl {

    ApplyCountersEffect() {
        super(Duration.EndOfGame, Outcome.BoostCreature);
    }

    private ApplyCountersEffect(ApplyCountersEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        if (layer == Layer.AbilityAddingRemovingEffects_6) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents()) {
                for (AbilityCounter counter : permanent.getCounters(game).getAbilityCounters()) {
                    permanent.addAbility(counter.getAbility(), source == null ? permanent.getId() : source.getSourceId(), game);
                }
            }
        }
        if (layer == Layer.PTChangingEffects_7 && sublayer == SubLayer.Counters_7d) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(CardType.CREATURE, game)) {
                for (BoostCounter counter : permanent.getCounters(game).getBoostCounters()) {
                    permanent.addPower(counter.getPower() * counter.getCount());
                    permanent.addToughness(counter.getToughness() * counter.getCount());
                }
            }
        }
        return true;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7 || layer == Layer.AbilityAddingRemovingEffects_6;
    }

    @Override
    public ApplyCountersEffect copy() {
        return new ApplyCountersEffect(this);
    }
}
