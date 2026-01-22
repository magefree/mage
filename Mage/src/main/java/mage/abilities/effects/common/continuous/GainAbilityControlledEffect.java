package mage.abilities.effects.common.continuous;

import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.CompoundAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.*;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class GainAbilityControlledEffect extends ContinuousEffectImpl {

    protected CompoundAbility abilities;
    protected boolean excludeSource;
    protected FilterPermanent filter;
    protected boolean forceQuotes = false;
    protected boolean durationRuleAtStart = false; // put duration rule to the start of the rules instead end
    protected Map<MageObjectReference, List<UUID>> originalIds = new HashMap<>(); // keep consistent individual originalId of gained ability for each affected permanent.
    protected UUID lastSourceOriginalId; // remember the original id for the source giving the ability. If it changes, originalIds need to be fresh.
    protected int lastSourceZcc; // remember the source zcc giving the ability. If it changes, originalIds need to be fresh.

    public GainAbilityControlledEffect(Ability ability, Duration duration, FilterPermanent filter) {
        this(ability, duration, filter, false);
    }

    public GainAbilityControlledEffect(CompoundAbility abilities, Duration duration, FilterPermanent filter) {
        this(abilities, duration, filter, false);
    }

    public GainAbilityControlledEffect(Ability ability, Duration duration, FilterPermanent filter, boolean excludeSource) {
        this(new CompoundAbility(ability), duration, filter, excludeSource);
    }

    public GainAbilityControlledEffect(CompoundAbility abilities, Duration duration, FilterPermanent filter, boolean excludeSource) {
        super(duration, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.abilities = abilities;
        this.filter = filter;
        this.excludeSource = excludeSource;
        setText();

        this.generateGainAbilityDependencies(abilities, filter);
    }

    protected GainAbilityControlledEffect(final GainAbilityControlledEffect effect) {
        super(effect);
        this.abilities = effect.abilities.copy();
        this.filter = effect.filter.copy();
        this.excludeSource = effect.excludeSource;
        this.forceQuotes = effect.forceQuotes;
        this.durationRuleAtStart = effect.durationRuleAtStart;
        for (MageObjectReference mor : effect.originalIds.keySet()) {
            List<UUID> array = new ArrayList<>();
            array.addAll(effect.originalIds.get(mor));
            this.originalIds.put(mor, array);
        }
        this.lastSourceOriginalId = effect.lastSourceOriginalId;
        this.lastSourceZcc = effect.lastSourceZcc;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (getAffectedObjectsSet()) {
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

    /**
     * OriginalIds for the copied abilities for a given permanent need to stay consistent each time the effect apply.
     * This method attempts to retrieved stored originalIds, and if not found, create new ones.
     */
    private List<UUID> getOriginalIds(MageObjectReference permMOR, Ability source, Game game) {
        UUID sourceOriginalId = source.getOriginalId();
        MageObject sourceObject = source.getSourceObject(game);
        int sourceZcc = sourceObject == null ? -1 : sourceObject.getZoneChangeCounter(game);
        //System.out.println(sourceOriginalId + " " + sourceZcc);
        if (!sourceOriginalId.equals(lastSourceOriginalId) || sourceZcc != lastSourceZcc) {
            // The source of the ability has changed, discarding outdated originalIds
            originalIds.clear();
            lastSourceOriginalId = sourceOriginalId;
            lastSourceZcc = sourceZcc;
        }
        if (originalIds.containsKey(permMOR)) {
            return originalIds.get(permMOR);
        }
        List<UUID> newOriginalIds = new ArrayList<>();
        for (int i = 0; i < abilities.size(); ++i) {
            newOriginalIds.add(UUID.randomUUID());
        }
        originalIds.put(permMOR, newOriginalIds);
        return newOriginalIds;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (getAffectedObjectsSet()) {
            for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext(); ) { // filter may not be used again, because object can have changed filter relevant attributes but still geets boost
                MageObjectReference mor = it.next();
                Permanent perm = mor.getPermanentOrLKIBattlefield(game); //LKI is necessary for "dies triggered abilities" to work given to permanets  (e.g. Showstopper)
                if (perm != null) {
                    List<UUID> originalIds = getOriginalIds(mor, source, game);
                    for (int i = 0; i < abilities.size(); ++i) {
                        Ability abilityToAdd = abilities.get(i);
                        UUID originalId = originalIds.get(i);
                        perm.addAbility(abilityToAdd, originalId, source.getSourceId(), game);
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
                    List<UUID> originalIds = getOriginalIds(new MageObjectReference(perm, game), source, game);
                    for (int i = 0; i < abilities.size(); ++i) {
                        Ability abilityToAdd = abilities.get(i);
                        UUID originalId = originalIds.get(i);
                        perm.addAbility(abilityToAdd, originalId, source.getSourceId(), game);
                    }
                }
            }
        }
        return true;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        if (durationRuleAtStart && !duration.toString().isEmpty() && duration != Duration.EndOfGame) {
            sb.append(duration.toString()).append(", ");
        }
        if (excludeSource) {
            sb.append("other ");
        }
        String gainedAbility = CardUtil.stripReminderText(abilities.getRule());
        sb.append(filter.getMessage());
        if (!filter.getMessage().contains("you control")) {
            sb.append(" you control");
        }
        boolean singular = filter.getMessage().toLowerCase().startsWith("each");
        if (duration == Duration.WhileOnBattlefield || duration == Duration.EndOfGame) {
            sb.append(singular ? " has " : " have ");
        } else {
            sb.append(singular ? " gains " : " gain ");
        }
        if (forceQuotes || gainedAbility.startsWith("When") || gainedAbility.startsWith("{T}")) {
            sb.append('"');
            sb.append(CardUtil.getTextWithFirstCharUpperCase(gainedAbility));
            sb.append('"');
        } else {
            sb.append(CardUtil.getTextWithFirstCharLowerCase(gainedAbility));
        }
        if (!durationRuleAtStart && !duration.toString().isEmpty() && duration != Duration.EndOfGame) {
            sb.append(' ');
            sb.append(duration);
        }
        staticText = sb.toString();
    }

    /**
     * Add quotes to gains abilities (by default static abilities don't have it)
     */
    public GainAbilityControlledEffect withForceQuotes() {
        this.forceQuotes = true;
        setText();
        return this;
    }

    public GainAbilityControlledEffect withDurationRuleAtStart(boolean durationRuleAtStart) {
        this.durationRuleAtStart = durationRuleAtStart;
        setText();
        return this;
    }
}
