package mage.abilities.effects.common.continuous;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class LoseAbilityAllEffect extends ContinuousEffectImpl {

    protected List<Ability> ability;
    protected boolean excludeSource;
    protected FilterPermanent filter;

    public LoseAbilityAllEffect(Ability ability, Duration duration) {
        this(ability, duration, StaticFilters.FILTER_PERMANENT);
    }

    public LoseAbilityAllEffect(Ability ability, Duration duration, FilterPermanent filter) {
        this(ability, duration, filter, false);
    }

    public LoseAbilityAllEffect(Ability ability, Duration duration, FilterPermanent filter, boolean excludeSource) {
        this(duration, filter, excludeSource, ability);
    }

    public LoseAbilityAllEffect(Duration duration, FilterPermanent filter, Ability... abilities) {
        this(duration, filter, false, abilities);
    }

    public LoseAbilityAllEffect(Duration duration, FilterPermanent filter, boolean excludeSource, Ability... abilities) {
        super(duration, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.LoseAbility);
        this.ability = Arrays.asList(abilities);
        this.filter = filter;
        this.excludeSource = excludeSource;
    }

    protected LoseAbilityAllEffect(final LoseAbilityAllEffect effect) {
        super(effect);
        this.ability = effect.ability;
        this.filter = effect.filter.copy();
        this.excludeSource = effect.excludeSource;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (getAffectedObjectsSet()) {
            for (Permanent perm : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                if (!(excludeSource && perm.getId().equals(source.getSourceId()))) {
                    affectedObjectList.add(new MageObjectReference(perm, game));
                }
            }
        }
    }

    @Override
    public LoseAbilityAllEffect copy() {
        return new LoseAbilityAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (getAffectedObjectsSet()) {
            for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext(); ) { // filter may not be used again, because object can have changed filter relevant attributes but still geets boost
                Permanent perm = it.next().getPermanentOrLKIBattlefield(game); //LKI is neccessary for "dies triggered abilities" to work given to permanets  (e.g. Showstopper)
                if (perm != null) {
                    perm.removeAbilities(ability, source.getSourceId(), game);
                } else {
                    it.remove();
                    if (affectedObjectList.isEmpty()) {
                        discard();
                    }
                }
            }
        } else {
            for (Permanent perm : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                if (!(excludeSource && perm.getId().equals(source.getSourceId()))) {
                    perm.removeAbilities(ability, source.getSourceId(), game);
                }
            }
        }
        return true;
    }

}
