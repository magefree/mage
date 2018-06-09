package mage.abilities.effects.common.continuous;

import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.CompoundAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.DependencyType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class GainAbilityControlledEffect extends ContinuousEffectImpl {

    protected CompoundAbility ability;
    protected boolean excludeSource;
    protected FilterPermanent filter;

    public GainAbilityControlledEffect(Ability ability, Duration duration) {
        this(ability, duration, StaticFilters.FILTER_PERMANENT);
    }

    public GainAbilityControlledEffect(CompoundAbility ability, Duration duration) {
        this(ability, duration, StaticFilters.FILTER_PERMANENT);
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
        this.addDependencyType(DependencyType.AddingAbility);
        setText();
    }

    public GainAbilityControlledEffect(final GainAbilityControlledEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
        this.filter = effect.filter.copy();
        this.excludeSource = effect.excludeSource;

    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (this.affectedObjectsSet) {
            for (Permanent perm : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
                if (!(excludeSource && perm.getId().equals(source.getSourceId()))) {
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
            for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext();) { // filter may not be used again, because object can have changed filter relevant attributes but still geets boost
                Permanent perm = it.next().getPermanentOrLKIBattlefield(game); //LKI is neccessary for "dies triggered abilities" to work given to permanets  (e.g. Showstopper)
                if (perm != null) {
                    for (Ability abilityToAdd : ability) {
                        perm.addAbility(abilityToAdd, source.getSourceId(), game, false);
                    }
                } else {
                    it.remove();
                    if (affectedObjectList.isEmpty()) {
                        discard();
                    }
                }
            }
        } else {
            for (Permanent perm : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
                if (!(excludeSource && perm.getId().equals(source.getSourceId()))) {
                    for (Ability abilityToAdd : ability) {
                        perm.addAbility(abilityToAdd, source.getSourceId(), game, false);
                    }
                }
            }
            // still as long as the prev. permanent is known to the LKI (e.g. Mikaeus, the Unhallowed) so gained dies triggered ability will trigger
            HashMap<UUID, MageObject> LKIBattlefield = game.getLKI().get(Zone.BATTLEFIELD);
            if (LKIBattlefield != null) {
                for (MageObject mageObject : LKIBattlefield.values()) {
                    Permanent perm = (Permanent) mageObject;
                    if (!(excludeSource && perm.getId().equals(source.getSourceId()))) {
                        if (filter.match(perm, source.getSourceId(), source.getControllerId(), game)) {
                            for (Ability abilityToAdd : ability) {
                                perm.addAbility(abilityToAdd, source.getSourceId(), game, false);
                            }
                        }
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
            sb.append("Other ");
        }
        String gainedAbility = ability.getRule();
        sb.append(filter.getMessage()).append(" you control ");
        if (duration == Duration.WhileOnBattlefield || duration == Duration.EndOfGame) {
            sb.append("have ");
            if (gainedAbility.startsWith("Whenever ") || gainedAbility.startsWith("{T}")) {
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

}
