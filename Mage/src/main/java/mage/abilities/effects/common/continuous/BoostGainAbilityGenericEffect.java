package mage.abilities.effects.common.continuous;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.LinkedEffectIdStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Targets;
import mage.target.targetpointer.FilterAllPermanentsTargetPointer;
import mage.target.targetpointer.TargetPointer;
import mage.util.CardUtil;

import java.util.*;

/**
 * Boosts power/toughness and/or grants abilities to whatever the target pointer points at,
 * generating the whole "gets +1/+0 and gains first strike until end of turn" sentence.
 * <p>
 * Either half may be absent: without abilities this is a pure boost ({@link BoostTargetEffect}),
 * without a boost a pure ability grant ({@link GainAbilityTargetEffect}). Cards that need both use
 * this class directly and install their own target pointer, which also supplies the description --
 * so pass a filter that names itself, such as StaticFilters.FILTER_CONTROLLED_CREATURES.
 *
 * @author notgreat
 */
public class BoostGainAbilityGenericEffect extends ContinuousEffectImpl {

    private final List<Ability> abilities = new ArrayList<>();
    // shall a card gain the ability (otherwise a permanent)
    private final boolean useOnCard; // only one card per ability supported
    protected String targetObjectName = null;
    protected boolean durationRuleAtStart = false; // put duration rule to the start of the rules instead end
    protected boolean abilitiesFirst = false; // "gains trample and gets +2/+0" instead of "gets +2/+0 and gains trample"
    private DynamicValue power; // null when this effect only grants abilities
    private DynamicValue toughness;
    private boolean waitingCardPermanent = false; // wait the permanent from card's resolve (for inner usage only)
    private String abilitiesRuleText = null; // overrides the generated list of granted abilities

    public BoostGainAbilityGenericEffect(int power, int toughness, Duration duration, Ability... abilities) {
        this(StaticValue.get(power), StaticValue.get(toughness), duration, abilities);
    }

    public BoostGainAbilityGenericEffect(DynamicValue power, DynamicValue toughness, Duration duration, Ability... abilities) {
        this(power, toughness, duration, null, false, abilities);
    }

    /**
     * Grant abilities without boosting.
     */
    public BoostGainAbilityGenericEffect(Duration duration, Ability... abilities) {
        this(null, null, duration, null, false, abilities);
    }

    public BoostGainAbilityGenericEffect(DynamicValue power, DynamicValue toughness, Duration duration,
                                         String rule, boolean useOnCard, Ability... abilities) {
        super(duration, outcomeOf(power, toughness));
        this.power = power;
        this.toughness = toughness;
        copyInto(this.abilities, Arrays.asList(abilities));
        this.staticText = rule; // null, so that setText("") can suppress the generated text
        this.useOnCard = useOnCard;
        if (!this.abilities.isEmpty()) {
            this.generateGainAbilityDependencies(this.abilities, null);
        }
    }

    protected BoostGainAbilityGenericEffect(final BoostGainAbilityGenericEffect effect) {
        super(effect);
        this.power = effect.power == null ? null : effect.power.copy();
        this.toughness = effect.toughness == null ? null : effect.toughness.copy();
        copyInto(this.abilities, effect.abilities); // see copyInto, ability.copy() is not enough
        this.useOnCard = effect.useOnCard;
        this.waitingCardPermanent = effect.waitingCardPermanent;
        this.targetObjectName = effect.targetObjectName;
        this.durationRuleAtStart = effect.durationRuleAtStart;
        this.abilitiesFirst = effect.abilitiesFirst;
        this.abilitiesRuleText = effect.abilitiesRuleText;
    }

    private static Outcome outcomeOf(DynamicValue power, DynamicValue toughness) {
        return power == null ? Outcome.AddAbility : CardUtil.getBoostOutcome(power, toughness);
    }

    /**
     * Copying the ability and providing ability is needed in a few situations. The copy in order to
     * have internal fields be proper to that ability in particular. Id must be different for the copy,
     * for a few things like the abilities gained by a clone, or in the case of an activated ability
     * called multiple times on the same target, and thus gained multiple times.
     */
    private static void copyInto(List<Ability> target, Collection<Ability> source) {
        for (Ability ability : source) {
            Ability abilityToCopy = ability.copy();
            abilityToCopy.newId();
            if (abilityToCopy instanceof LinkedEffectIdStaticAbility) {
                ((LinkedEffectIdStaticAbility) abilityToCopy).setEffectIdManually();
            }
            target.add(abilityToCopy);
        }
    }

    /**
     * The "other " prefix for an effect that excludes its own source. A message that already names
     * a single permanent ("each ...") or a whole group ("all ...") reads correctly without it.
     */
    protected static String withOtherPrefix(String message, boolean excludeSource) {
        String lower = message.toLowerCase(Locale.ENGLISH);
        if (!excludeSource || lower.startsWith("each") || lower.startsWith("all ")) {
            return message;
        }
        return "other " + message;
    }

    /**
     * Names the controller for an effect that only acts on permanents you control. A filter whose
     * message already says so anywhere -- "creatures you control with flying", or Death Baron's
     * "Skeletons you control and other Zombies you control" -- is left alone.
     */
    protected static String withYouControl(String message) {
        return message.contains("you control") ? message : message + " you control";
    }

    /**
     * Whether the described targets take plural verbs. A filter whose message starts with "each"
     * describes one permanent at a time, so it stays singular ("each creature gets", not "get").
     */
    public static boolean isPluralTarget(TargetPointer targetPointer, Targets targets) {
        if (targetPointer instanceof FilterAllPermanentsTargetPointer) {
            String description = targetPointer.getTargetDescription();
            return description == null || !description.toLowerCase(Locale.ENGLISH).startsWith("each");
        }
        return targetPointer.isPlural(targets);
    }

    @Override
    public BoostGainAbilityGenericEffect copy() {
        return new BoostGainAbilityGenericEffect(this);
    }

    protected boolean hasBoost() {
        return power != null;
    }

    protected boolean hasAbilities() {
        return !abilities.isEmpty();
    }

    /**
     * Either half, both, and so possibly two layers at once -- which is why this effect declares no
     * single layer to the superclass. That field is a convenience for effects that live in exactly
     * one layer and do not override this method; nothing reads it once this method is overridden.
     */
    @Override
    public boolean hasLayer(Layer layer) {
        return (hasBoost() && layer == Layer.PTChangingEffects_7)
                || (hasAbilities() && layer == Layer.AbilityAddingRemovingEffects_6);
    }

    protected List<Ability> getGrantedAbilities() {
        return abilities;
    }

    /**
     * The abilities to grant on this application, which the effect never keeps: the list it was
     * built with stays untouched, so the rules text always describes the same abilities no matter
     * what an override decides here.
     * <p>
     * Override to build abilities that only exist at runtime (example: Grothama, All-Devouring), or
     * to settle a detail that is only known once the game is running (example: which color a
     * granted protection ability names). Return an empty list to grant nothing this time round.
     */
    protected List<Ability> getAbilitiesToGrant(Game game, Ability source) {
        return abilities;
    }

    /**
     * Fresh copies of the abilities this effect was built with, for an override of
     * {@link #getAbilitiesToGrant} that wants to adjust them rather than replace them.
     */
    protected List<Ability> copyOfGrantedAbilities() {
        List<Ability> copies = new ArrayList<>();
        copyInto(copies, abilities);
        return copies;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);

        // 611.2c: the source ability decides whether the affected set is locked in, and this is the
        // moment the effect begins
        if (!getAffectedObjectsSetAtInit(source)) {
            return;
        }
        getTargetPointer().fixTargets(game, source);

        if (hasBoost()) {
            // a dynamic boost is locked in at resolution
            power = StaticValue.get(power.calculate(game, source, this));
            toughness = StaticValue.get(toughness.calculate(game, source, this));
        }

        // Only the card hand-off needs a snapshot: the pointer locks permanents in by itself, but
        // the card it points at becomes a different object once it resolves.
        if (hasAbilities() && this.useOnCard) {
            getTargetPointer().getTargets(game, source)
                    .stream()
                    .map(game::getPermanent)
                    .filter(Objects::nonNull)
                    .forEach(permanent -> this.affectedObjectList.add(new MageObjectReference(permanent, game)));
            getTargetPointer().getTargets(game, source)
                    .stream()
                    .map(game::getCard)
                    .filter(Objects::nonNull)
                    .forEach(card -> this.affectedObjectList.add(new MageObjectReference(card, game)));
            waitingCardPermanent = true;
            if (this.affectedObjectList.size() > 1) {
                throw new IllegalArgumentException("Gain ability can't target a multiple cards (unsupported)");
            }
        }
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        if (hasBoost() && layer == Layer.PTChangingEffects_7 && sublayer == SubLayer.ModifyPT_7c) {
            return applyBoost(game, source);
        }
        if (hasAbilities() && layer == Layer.AbilityAddingRemovingEffects_6 && sublayer == SubLayer.NA) {
            return applyAbilities(game, source);
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    private boolean applyBoost(Game game, Ability source) {
        int affectedTargets = 0;
        for (UUID permanentId : getTargetPointer().getTargets(game, source)) {
            Permanent target = game.getPermanent(permanentId);
            if (target != null && target.isCreature(game)) {
                target.addPower(power.calculate(game, source, this));
                target.addToughness(toughness.calculate(game, source, this));
                affectedTargets++;
            }
        }
        return affectedTargets > 0;
    }

    private boolean applyAbilities(Game game, Ability source) {
        List<Ability> abilitiesToGrant = getAbilitiesToGrant(game, source);
        if (abilitiesToGrant.isEmpty()) {
            return false;
        }
        if (this.useOnCard && getAffectedObjectsSet()) {
            return applyToWaitingCard(game, source, abilitiesToGrant);
        }

        int affectedTargets = 0;
        for (UUID objectId : getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(objectId);
            if (permanent != null) {
                gainAll(game, source, permanent, abilitiesToGrant);
                affectedTargets++;
                continue;
            }
            if (this.useOnCard) {
                Card card = game.getCard(objectId);
                if (card != null) {
                    addToCard(game, card, abilitiesToGrant);
                    affectedTargets++;
                }
            }
        }

        // a locked-in set that has run dry is gone for good, while a dynamic one may fill again
        if (affectedTargets == 0 && getAffectedObjectsSet()) {
            discard();
        }
        return affectedTargets > 0;
    }

    /**
     * A grant made to a card that is about to become a permanent (example: Tyvar Kell's emblem).
     * The card and the permanent it resolves into are different objects, so the effect has to follow
     * it across that change rather than re-asking the target pointer.
     */
    private boolean applyToWaitingCard(Game game, Ability source, List<Ability> abilitiesToGrant) {
        int affectedTargets = 0;
        List<MageObjectReference> newWaitingPermanents = new ArrayList<>();
        for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext(); ) {
            MageObjectReference mor = it.next();

            Permanent permanent = mor.getPermanent(game);
            if (permanent != null) {
                this.waitingCardPermanent = false;
                gainAll(game, source, permanent, abilitiesToGrant);
                affectedTargets++;
                continue;
            }

            Card card = mor.getCard(game);
            if (card != null) {
                addToCard(game, card, abilitiesToGrant);
                affectedTargets++;
                continue;
            }

            Permanent perm = game.getPermanent(mor.getSourceId());
            if (perm != null) {
                gainAll(game, source, perm, abilitiesToGrant);
                affectedTargets++;
                newWaitingPermanents.add(new MageObjectReference(perm, game));
                this.waitingCardPermanent = false;
            }
            it.remove();
        }

        if (!newWaitingPermanents.isEmpty()) {
            this.affectedObjectList.addAll(newWaitingPermanents);
            return affectedTargets > 0;
        }

        if (this.affectedObjectList.isEmpty()) {
            discard();
        }

        // the card was countered, so no permanent is coming
        if (duration == Duration.Custom && affectedTargets == 0 && !this.waitingCardPermanent) {
            discard();
        }
        return affectedTargets > 0;
    }

    private void addToCard(Game game, Card card, List<Ability> abilitiesToGrant) {
        for (Ability ability : abilitiesToGrant) {
            game.getState().addOtherAbility(card, ability);
        }
    }

    private void gainAll(Game game, Ability source, Permanent permanent, List<Ability> abilitiesToGrant) {
        for (Ability ability : abilitiesToGrant) {
            permanent.addAbility(ability, source.getSourceId(), game);
        }
    }

    public BoostGainAbilityGenericEffect withDurationRuleAtStart(boolean durationRuleAtStart) {
        this.durationRuleAtStart = durationRuleAtStart;
        return this;
    }

    // ---------------------------------------------------------------- rules text

    /**
     * Name the object a granted ability refers to, so that "{this}" in its text reads as
     * "this creature" rather than resolving to the source that granted it.
     */
    public BoostGainAbilityGenericEffect withTargetObjectName(String targetObjectName) {
        this.targetObjectName = targetObjectName;
        return this;
    }

    /**
     * Print the granted abilities before the boost, as "gains trample and gets +2/+0".
     */
    public BoostGainAbilityGenericEffect withAbilitiesFirst(boolean abilitiesFirst) {
        this.abilitiesFirst = abilitiesFirst;
        return this;
    }

    /**
     * Overwrite the generated list of granted abilities in the rules text.
     */
    public BoostGainAbilityGenericEffect withAbilitiesRuleText(String abilitiesRuleText) {
        this.abilitiesRuleText = abilitiesRuleText;
        return this;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null) {
            return staticText;
        }
        boolean showDuration = !duration.toString().isEmpty() && duration != Duration.EndOfGame;

        StringBuilder sb = new StringBuilder();
        if (durationRuleAtStart && showDuration) {
            sb.append(duration).append(", ");
        }
        String describedTargets = getTargetPointer().describeTargets(mode.getTargets(), "it");
        sb.append(describedTargets);

        String first = abilitiesFirst ? abilitiesPart(mode) : boostPart(mode);
        String second = abilitiesFirst ? boostPart(mode) : abilitiesPart(mode);
        if (first == null) {
            first = second;
            second = null;
        }
        sb.append(describedTargets.isEmpty() ? first.substring(1) : first);
        if (second != null) {
            sb.append(" and").append(second);
        }

        if (!durationRuleAtStart && showDuration) {
            sb.append(' ').append(duration);
        }
        if (hasBoost()) {
            // the "for each ..." / ", where X is ..." tail of a dynamic boost always comes last
            String message = power.getMessage();
            if (message.isEmpty()) {
                message = toughness.getMessage();
            }
            if (!message.isEmpty()) {
                sb.append(CardUtil.getBoostCountAsStr(power, toughness).contains("X") ? ", where X is " : " for each ");
                sb.append(message);
            }
        }
        return sb.toString();
    }

    private String boostPart(Mode mode) {
        if (!hasBoost()) {
            return null;
        }
        String verb;
        if (!isPluralTarget(getTargetPointer(), mode.getTargets())) {
            verb = " gets ";
        } else if (getTargetPointer() instanceof FilterAllPermanentsTargetPointer) {
            verb = " get ";
        } else {
            verb = " each get ";
        }
        return verb + CardUtil.getBoostCountAsStr(power, toughness);
    }

    /**
     * The "gains a, b, and c" clause. Multi-word abilities are quoted and capitalised, single
     * keywords are lower-cased.
     */
    private String abilitiesPart(Mode mode) {
        if (!hasAbilities()) {
            return null;
        }
        boolean plural = isPluralTarget(getTargetPointer(), mode.getTargets());
        String verb;
        if (duration == Duration.WhileOnBattlefield || duration == Duration.EndOfGame) {
            verb = plural ? " have " : " has ";
        } else {
            verb = plural ? " gain " : " gains ";
        }
        if (abilitiesRuleText != null) {
            return verb + abilitiesRuleText;
        }

        List<String> rules = new ArrayList<>();
        for (Ability ability : abilities) {
            String rule = targetObjectName == null
                    ? CardUtil.stripReminderText(ability.getRule())
                    : CardUtil.stripReminderText(ability.getRule("this " + targetObjectName));
            // three words or more reads as a sentence of rules text, so it gets quoted
            if (rule.length() - rule.replace(" ", "").length() >= 2 && !rule.startsWith("protection from")) {
                rules.add('"' + CardUtil.getTextWithFirstCharUpperCase(rule) + '"');
            } else {
                rules.add(CardUtil.getTextWithFirstCharLowerCase(rule));
            }
        }
        return verb + CardUtil.concatWithAnd(rules);
    }
}
