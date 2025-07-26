package mage.abilities.effects.common.continuous;

import mage.MageItem;
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
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            ((Permanent) object).removeAbility(ability, source.getSourceId(), game);
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        affectedObjects.addAll(game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game));
        return !affectedObjects.isEmpty();
    }
}
