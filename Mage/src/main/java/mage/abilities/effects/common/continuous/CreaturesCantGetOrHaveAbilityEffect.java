package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.DependencyType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class CreaturesCantGetOrHaveAbilityEffect extends ContinuousEffectImpl {

    private final Ability ability;
    private final FilterCreaturePermanent filter;

    public CreaturesCantGetOrHaveAbilityEffect(Ability ability, Duration duration) {
        this(ability, duration, StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES);
    }

    public CreaturesCantGetOrHaveAbilityEffect(Ability ability, Duration duration, FilterCreaturePermanent filter) {
        super(duration, Outcome.Detriment);
        this.ability = ability;
        this.filter = filter;
        staticText = filter.getMessage() + " lose " + ability.getRule() + " and can't have or gain " + ability.getRule();
        addDependedToType(DependencyType.AddingAbility);
    }

    public CreaturesCantGetOrHaveAbilityEffect(final CreaturesCantGetOrHaveAbilityEffect effect) {
        super(effect);
        this.ability = effect.ability;
        this.filter = effect.filter;
    }

    @Override
    public CreaturesCantGetOrHaveAbilityEffect copy() {
        return new CreaturesCantGetOrHaveAbilityEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                if (permanent != null) {
                    permanent.removeAbility(ability, source.getSourceId(), game);
                }
            }
            return true;
        }
        return false;

    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6;
    }
}
