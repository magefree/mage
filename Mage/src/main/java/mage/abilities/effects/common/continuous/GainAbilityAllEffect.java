
package mage.abilities.effects.common.continuous;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.UUID;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.DependencyType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Loki
 */
public class GainAbilityAllEffect extends ContinuousEffectImpl {

    protected Ability ability;
    protected boolean excludeSource;
    protected FilterPermanent filter;

    public GainAbilityAllEffect(Ability ability, Duration duration) {
        this(ability, duration, new FilterPermanent());
    }

    public GainAbilityAllEffect(Ability ability, Duration duration, FilterPermanent filter) {
        this(ability, duration, filter, false);
    }

    public GainAbilityAllEffect(Ability ability, Duration duration, FilterPermanent filter, String text) {
        this(ability, duration, filter, false);
        this.staticText = text;
    }

    public GainAbilityAllEffect(Ability ability, Duration duration, FilterPermanent filter, boolean excludeSource) {
        this(ability, duration, filter, excludeSource, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA);
    }

    public GainAbilityAllEffect(Ability ability, Duration duration, FilterPermanent filter, boolean excludeSource, Layer layer, SubLayer subLayer) {
        super(duration, layer, subLayer, Outcome.AddAbility);
        this.ability = ability.copy();
        this.ability.newId();
        this.filter = filter;
        this.excludeSource = excludeSource;
        this.addDependencyType(DependencyType.AddingAbility);
    }

    public GainAbilityAllEffect(final GainAbilityAllEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
        ability.newId(); // This is needed if the effect is copied e.g. by a clone so the ability can be added multiple times to permanents
        this.filter = effect.filter.copy();
        this.excludeSource = effect.excludeSource;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        setRuntimeData(source, game);
        if (this.affectedObjectsSet) {
            for (Permanent perm : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                if (!(excludeSource && perm.getId().equals(source.getSourceId())) && selectedByRuntimeData(perm, source, game)) {
                    affectedObjectList.add(new MageObjectReference(perm, game));
                }
            }
        }
    }

    @Override
    public GainAbilityAllEffect copy() {
        return new GainAbilityAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (this.affectedObjectsSet) {
            for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext();) { // filter may not be used again, because object can have changed filter relevant attributes but still geets boost
                Permanent permanent = it.next().getPermanentOrLKIBattlefield(game); //LKI is neccessary for "dies triggered abilities" to work given to permanets  (e.g. Showstopper)
                if (permanent != null) {
                    permanent.addAbility(ability, source.getSourceId(), game, false);
                } else {
                    it.remove(); // no longer on the battlefield, remove reference to object
                    if (affectedObjectList.isEmpty()) {
                        discard();
                    }
                }
            }
        } else {
            setRuntimeData(source, game);
            for (Permanent perm : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                if (!(excludeSource && perm.getId().equals(source.getSourceId())) && selectedByRuntimeData(perm, source, game)) {
                    perm.addAbility(ability, source.getSourceId(), game, false);
                }
            }
            // still as long as the prev. permanent is known to the LKI (e.g. Mikaeus, the Unhallowed) so gained dies triggered ability will trigger
            HashMap<UUID, MageObject> LKIBattlefield = game.getLKI().get(Zone.BATTLEFIELD);
            if (LKIBattlefield != null) {
                for (MageObject mageObject : LKIBattlefield.values()) {
                    Permanent perm = (Permanent) mageObject;
                    if (!(excludeSource && perm.getId().equals(source.getSourceId())) && selectedByRuntimeData(perm, source, game)) {
                        if (filter.match(perm, source.getSourceId(), source.getControllerId(), game)) {
                            perm.addAbility(ability, source.getSourceId(), game, false);
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Overwrite this in effect that inherits from this
     *
     * @param source
     * @param game
     */
    protected void setRuntimeData(Ability source, Game game) {

    }

    /**
     * Overwrite this in effect that inherits from this
     *
     * @param permanent
     * @param source
     * @param game
     * @return
     */
    protected boolean selectedByRuntimeData(Permanent permanent, Ability source, Game game) {
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }

        StringBuilder sb = new StringBuilder();

        boolean quotes = (ability instanceof SimpleActivatedAbility) || (ability instanceof TriggeredAbility);
        if (excludeSource) {
            sb.append("Other ");
        }
        sb.append(filter.getMessage());
        if (duration == Duration.WhileOnBattlefield) {
            if (filter.getMessage().toLowerCase(Locale.ENGLISH).startsWith("each")) {
                sb.append(" has ");
            } else {
                sb.append(" have ");
            }
        } else if (filter.getMessage().toLowerCase(Locale.ENGLISH).startsWith("each")) {
            sb.append(" gains ");
        } else {
            sb.append(" gain ");
        }
        if (quotes) {
            sb.append('"');
        }
        sb.append(ability.getRule());
        if (quotes) {
            sb.append('"');
        }
        if (!duration.toString().isEmpty()) {
            sb.append(' ').append(duration.toString());
        }
        return sb.toString();
    }
}
