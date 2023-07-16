package mage.abilities.effects.common.continuous;

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

import java.util.Iterator;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class GainAbilityControlledEffect extends ContinuousEffectImpl {

    protected CompoundAbility ability;
    protected boolean excludeSource;
    protected FilterPermanent filter;
    protected boolean forceQuotes = false;

    public GainAbilityControlledEffect(Ability ability, Duration duration) {
        this(ability, duration, StaticFilters.FILTER_PERMANENTS);
    }

    public GainAbilityControlledEffect(CompoundAbility ability, Duration duration) {
        this(ability, duration, StaticFilters.FILTER_PERMANENTS);
    }

    public GainAbilityControlledEffect(Ability ability, Duration duration, FilterPermanent filter) {
        this(ability, duration, filter, false);
    }

    public GainAbilityControlledEffect(CompoundAbility ability, Duration duration, FilterPermanent filter) {
        this(ability, duration, filter, false);
    }

    public GainAbilityControlledEffect(Ability ability, Duration duration, FilterPermanent filter, boolean excludeSource) {
        this(new CompoundAbility(ability), duration, filter, excludeSource);
    }

    public GainAbilityControlledEffect(CompoundAbility ability, Duration duration, FilterPermanent filter, boolean excludeSource) {
        super(duration, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.ability = ability;
        this.filter = filter;
        this.excludeSource = excludeSource;
        setText();

        this.generateGainAbilityDependencies(ability, filter);
    }

    public GainAbilityControlledEffect(final GainAbilityControlledEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
        this.filter = effect.filter.copy();
        this.excludeSource = effect.excludeSource;
        this.forceQuotes = effect.forceQuotes;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (this.affectedObjectsSet) {
            for (Permanent perm : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                if (perm.isControlledBy(source.getControllerId())
                        && !(excludeSource && perm.getId().equals(source.getSourceId()))) {
                    affectedObjectList.add(new MageObjectReference(perm, game));
                }
            }
        }
    }

    @Override
    public GainAbilityControlledEffect copy() {
        return new GainAbilityControlledEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (this.affectedObjectsSet) {
            for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext(); ) { // filter may not be used again, because object can have changed filter relevant attributes but still geets boost
                Permanent perm = it.next().getPermanentOrLKIBattlefield(game); //LKI is neccessary for "dies triggered abilities" to work given to permanets  (e.g. Showstopper)
                if (perm != null) {
                    for (Ability abilityToAdd : ability) {
                        perm.addAbility(abilityToAdd, source.getSourceId(), game);
                    }
                } else {
                    it.remove();
                    if (affectedObjectList.isEmpty()) {
                        discard();
                    }
                }
            }
        } else {
            for (Permanent perm : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                if (perm.isControlledBy(source.getControllerId())
                        && !(excludeSource && perm.getId().equals(source.getSourceId()))) {
                    for (Ability abilityToAdd : ability) {
                        perm.addAbility(abilityToAdd, source.getSourceId(), game);
                    }
                }
            }
        }
        return true;
    }

    public void setAbility(Ability ability) {
        this.ability = new CompoundAbility(ability);
    }

    public Ability getFirstAbility() {
        return ability.get(0);
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        if (excludeSource) {
            sb.append("other ");
        }
        String gainedAbility = ability.getRule();
        sb.append(filter.getMessage()).append(" you control ");
        if (duration == Duration.WhileOnBattlefield || duration == Duration.EndOfGame) {
            sb.append("have ");
            if (forceQuotes || gainedAbility.startsWith("When") || gainedAbility.startsWith("{T}")) {
                gainedAbility = '"' + gainedAbility + '"';
            }
        } else {
            sb.append("gain ");
        }
        sb.append(gainedAbility);
        if (!duration.toString().isEmpty() && duration != Duration.EndOfGame) {
            sb.append(' ').append(duration.toString());
        }
        staticText = sb.toString();
    }

    /**
     * Add quotes to gains abilities (by default static abilities don't have it)
     *
     * @return
     */
    public GainAbilityControlledEffect withForceQuotes() {
        this.forceQuotes = true;
        setText();
        return this;
    }
}
