package mage.abilities.effects.common.continuous;

import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

/**
 * @author Loki
 */
public class GainAbilityAllEffect extends ContinuousEffectImpl {

    protected Ability ability;
    protected boolean excludeSource;
    protected FilterPermanent filter;
    protected boolean forceQuotes = false;

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

        this.generateGainAbilityDependencies(ability, filter);
    }

    public GainAbilityAllEffect(final GainAbilityAllEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
        ability.newId(); // This is needed if the effect is copied e.g. by a clone so the ability can be added multiple times to permanents
        this.filter = effect.filter.copy();
        this.excludeSource = effect.excludeSource;
        this.forceQuotes = effect.forceQuotes;
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
            for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext(); ) { // filter may not be used again, because object can have changed filter relevant attributes but still geets boost
                Permanent permanent = it.next().getPermanentOrLKIBattlefield(game); //LKI is neccessary for "dies triggered abilities" to work given to permanets  (e.g. Showstopper)
                if (permanent != null) {
                    permanent.addAbility(ability, source.getSourceId(), game);
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
                    perm.addAbility(ability, source.getSourceId(), game);
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

        boolean quotes = forceQuotes || (ability instanceof SimpleActivatedAbility) || (ability instanceof TriggeredAbility);
        boolean each = filter.getMessage().toLowerCase(Locale.ENGLISH).startsWith("each");
        if (excludeSource && !each) {
            sb.append("other ");
        }
        sb.append(filter.getMessage());
        if (duration == Duration.WhileOnBattlefield) {
            sb.append(each ? " has " : " have ");
        } else {
            sb.append(each ? " gains " : " gain ");
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

    /**
     * Add quotes to gains abilities (by default static abilities don't have it)
     */
    public GainAbilityAllEffect withForceQuotes() {
        this.forceQuotes = true;
        return this;
    }
}
