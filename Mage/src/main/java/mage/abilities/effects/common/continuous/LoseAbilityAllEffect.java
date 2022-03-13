package mage.abilities.effects.common.continuous;

import java.util.Iterator;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.CompoundAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class LoseAbilityAllEffect extends ContinuousEffectImpl {

    protected CompoundAbility ability;
    protected boolean excludeSource;
    protected FilterPermanent filter;

    public LoseAbilityAllEffect(Ability ability, Duration duration) {
        this(ability, duration, StaticFilters.FILTER_PERMANENT);
    }

    public LoseAbilityAllEffect(CompoundAbility ability, Duration duration) {
        this(ability, duration, StaticFilters.FILTER_PERMANENT);
    }

    public LoseAbilityAllEffect(Ability ability, Duration duration, FilterPermanent filter) {
        this(ability, duration, filter, false);
    }

    public LoseAbilityAllEffect(CompoundAbility ability, Duration duration, FilterPermanent filter) {
        this(ability, duration, filter, false);
    }

    public LoseAbilityAllEffect(Ability ability, Duration duration, FilterPermanent filter, boolean excludeSource) {
        this(new CompoundAbility(ability), duration, filter, excludeSource);
    }

    public LoseAbilityAllEffect(CompoundAbility ability, Duration duration, FilterPermanent filter, boolean excludeSource) {
        super(duration, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.LoseAbility);
        this.ability = ability;
        this.filter = filter;
        this.excludeSource = excludeSource;
    }

    public LoseAbilityAllEffect(final LoseAbilityAllEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
        this.filter = effect.filter.copy();
        this.excludeSource = effect.excludeSource;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (this.affectedObjectsSet) {
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
        if (this.affectedObjectsSet) {
            for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext();) { // filter may not be used again, because object can have changed filter relevant attributes but still geets boost
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
                    System.out.println(game.getTurn() + ", " + game.getPhase() + ": " + "remove from size " + perm.getAbilities().size());
                    perm.removeAbilities(ability, source.getSourceId(), game);
                }
            }
        }
        return true;
    }

}
