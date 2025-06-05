package mage.abilities.effects;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.*;
import mage.counters.AbilityCounter;
import mage.counters.BoostCounter;
import mage.counters.Counters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Map;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 * <p>
 * Applies boost from boost counters and also adds abilities from ability counters and suspected mechanic
 */
public class ApplyStatusEffect extends ContinuousEffectImpl {

    ApplyStatusEffect() {
        super(Duration.EndOfGame, Outcome.BoostCreature);
    }

    private ApplyStatusEffect(ApplyStatusEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public Map<UUID, MageItem> queryAffectedObjects(Layer layer, Ability source, Game game) {
        if (layer == Layer.AbilityAddingRemovingEffects_6) {
            affectedObjectMap.clear();
        }
        if (!affectedObjectMap.isEmpty()) {
            return affectedObjectMap;
        }
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents()) {
            Counters counters = permanent.getCounters(game);
            if (!counters.getAbilityCounters().isEmpty() || !counters.getBoostCounters().isEmpty()) {
                affectedObjectMap.put(permanent.getId(), permanent);
            }
        }
        return affectedObjectMap;
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, Map<UUID, MageItem> objects) {
        if (layer == Layer.AbilityAddingRemovingEffects_6) {
            for (MageItem object : objects.values()) {
                Permanent permanent = (Permanent) object;
                for (AbilityCounter counter : permanent.getCounters(game).getAbilityCounters()) {
                    permanent.addAbility(counter.getAbility(), source == null ? permanent.getId() : source.getSourceId(), game);
                }
                if (permanent.isSuspected()) {
                    permanent.addAbility(new MenaceAbility(false), source == null ? permanent.getId() : source.getSourceId(), game);
                }
            }
        }
        if (layer == Layer.PTChangingEffects_7 && sublayer == SubLayer.Counters_7d) {
            for (MageItem object : objects.values()) {
                Permanent permanent = (Permanent) object;
                for (BoostCounter counter : permanent.getCounters(game).getBoostCounters()) {
                    permanent.addPower(counter.getPower() * counter.getCount());
                    permanent.addToughness(counter.getToughness() * counter.getCount());
                }
            }
        }
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        if (queryAffectedObjects(layer, source, game).isEmpty()) {
            return false;
        }
        applyToObjects(layer, sublayer, source, game, affectedObjectMap);
        return true;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7 || layer == Layer.AbilityAddingRemovingEffects_6;
    }

    @Override
    public ApplyStatusEffect copy() {
        return new ApplyStatusEffect(this);
    }
}
