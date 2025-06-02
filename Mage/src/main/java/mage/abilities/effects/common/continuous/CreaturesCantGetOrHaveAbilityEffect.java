package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LevelX2
 */
public class CreaturesCantGetOrHaveAbilityEffect extends ContinuousEffectImpl {

    private final Ability ability;
    private final FilterCreaturePermanent filter;

    public CreaturesCantGetOrHaveAbilityEffect(Ability ability, Duration duration) {
        this(ability, duration, StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES);
    }

    public CreaturesCantGetOrHaveAbilityEffect(Ability ability, Duration duration, FilterCreaturePermanent filter) {
        super(duration, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.Detriment);
        this.ability = ability;
        this.filter = filter;
        staticText = filter.getMessage() + " lose " + ability.getRule() + " and can't have or gain " + ability.getRule();
        addDependedToType(DependencyType.AddingAbility);
    }

    protected CreaturesCantGetOrHaveAbilityEffect(final CreaturesCantGetOrHaveAbilityEffect effect) {
        super(effect);
        this.ability = effect.ability;
        this.filter = effect.filter;
    }

    @Override
    public CreaturesCantGetOrHaveAbilityEffect copy() {
        return new CreaturesCantGetOrHaveAbilityEffect(this);
    }

    @Override
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> objects) {
        if (objects.isEmpty()) {
            return false;
        }
        for (MageItem object : objects) {
            if (!(object instanceof Permanent)) {
                continue;
            }
            ((Permanent) object).removeAbility(ability, source.getSourceId(), game);
        }
        return true;
    }

    @Override
    public List<MageItem> queryAffectedObjects(Layer layer, Ability source, Game game) {
        return new ArrayList<>(game.getBattlefield()
                .getActivePermanents(filter, source.getControllerId(), source, game));
    }
}
