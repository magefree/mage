package mage.abilities.effects;

import mage.*;
import mage.abilities.Ability;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.*;
import mage.counters.AbilityCounter;
import mage.counters.BoostCounter;
import mage.filter.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageObject> objects) {
        for (MageObject mageObject : objects) {
            if (!(mageObject instanceof Permanent)) {
                continue;
            }
            Permanent permanent = (Permanent) mageObject;
            if (layer == Layer.AbilityAddingRemovingEffects_6) {
                for (AbilityCounter counter : permanent.getCounters(game).getAbilityCounters()) {
                    permanent.addAbility(counter.getAbility(), source == null ? permanent.getId() : source.getSourceId(), game);
                }
                if (permanent.isSuspected()) {
                    permanent.addAbility(new MenaceAbility(false), source == null ? permanent.getId() : source.getSourceId(), game);
                }
            } else if (layer == Layer.PTChangingEffects_7 && sublayer == SubLayer.Counters_7d) {
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
    public List<MageObject> queryAffectedObjects(Layer layer, Ability source, Game game) {
        List<MageObject> objects = new ArrayList<>();
        if (layer == Layer.AbilityAddingRemovingEffects_6) {
            objects.addAll(game.getBattlefield().getAllActivePermanents());
        }
        else if (layer == Layer.PTChangingEffects_7) {
            objects.addAll(game.getBattlefield().getAllActivePermanents()
                    .stream()
                    .filter(permanent -> StaticFilters.FILTER_PERMANENT_CREATURE.match(permanent, game))
                    .collect(Collectors.toList()));
        }
        return objects;
    }

    @Override
    public ApplyStatusEffect copy() {
        return new ApplyStatusEffect(this);
    }
}
