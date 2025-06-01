package mage.abilities;

import mage.MageIdentifier;
import mage.MageObject;
import mage.Mana;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.*;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.hint.Hint;
import mage.abilities.icon.CardIcon;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.Card;
import mage.choices.Choice;
import mage.choices.ChoiceHintType;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.filter.FilterMana;
import mage.game.Game;
import mage.game.command.Dungeon;
import mage.game.command.Emblem;
import mage.game.command.Plane;
import mage.game.events.BatchEvent;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackAbility;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.Targets;
import mage.target.common.TargetCardInLibrary;
import mage.target.targetadjustment.GenericTargetAdjuster;
import mage.target.targetadjustment.TargetAdjuster;
import mage.util.CardUtil;
import mage.util.GameLog;
import mage.util.ThreadLocalStringBuilder;
import mage.watchers.Watcher;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class AbilityImpl implements Ability {

    private static final Logger logger = Logger.getLogger(AbilityImpl.class);
    private static final ThreadLocalStringBuilder threadLocalBuilder = new ThreadLocalStringBuilder(100);
    private static final List<Ability> emptyAbilities = new ArrayList<>();

    protected UUID id;
    private UUID originalId; // TODO: delete originalId???
    protected AbilityType abilityType;
    protected UUID controllerId;
    protected UUID sourceId;
    private final ManaCosts<ManaCost> manaCosts;
    private final ManaCosts<ManaCost> manaCostsToPay;
    private final Costs<Cost> costs;
    private final Modes modes; // access to it by GetModes only (it can be overridden by some abilities)
    protected Zone zone;
    protected String name;
    protected AbilityWord abilityWord;
    protected String flavorWord;
    protected boolean usesStack = true;
    private boolean ruleAtTheTop = false;
    private boolean ruleVisible = true;
    private boolean ruleAdditionalCostsVisible = true;
    protected boolean activated = false;
    private boolean worksFaceDown = false;
    private boolean worksPhasedOut = false;
    private int sourceObjectZoneChangeCounter;
    private List<Watcher> watchers = new ArrayList<>(); // access to it by GetWatchers only (it can be overridden by some abilities)
    private List<Ability> subAbilities = null;
    private boolean canFizzle = true; // for Gilded Drake
    private boolean canBeCopied = true;
    private TargetAdjuster targetAdjuster = null;
    private CostAdjuster costAdjuster = null;
    private List<Hint> hints = new ArrayList<>();
    protected List<CardIcon> icons = new ArrayList<>();
    private Outcome customOutcome = null; // uses for AI decisions instead effects
    private MageIdentifier identifier = MageIdentifier.Default; // used to identify specific ability (e.g. to match with corresponding watcher)
    private String appendToRule = null;
    private int sourcePermanentTransformCount = 0;
    private Map<String, Object> costsTagMap = null;

    protected AbilityImpl(AbilityType abilityType, Zone zone) {
        this.id = UUID.randomUUID();
        this.originalId = id;
        this.abilityType = abilityType;
        this.zone = zone;
        this.manaCosts = new ManaCostsImpl<>();
        this.manaCostsToPay = new ManaCostsImpl<>();
        this.costs = new CostsImpl<>();
        this.modes = new Modes();
    }

    protected AbilityImpl(final AbilityImpl ability) {
        this.id = ability.id;
        this.originalId = ability.originalId;
        this.abilityType = ability.abilityType;
        this.controllerId = ability.controllerId;
        this.sourceId = ability.sourceId;
        this.zone = ability.zone;
        this.name = ability.name;
        this.usesStack = ability.usesStack;
        this.manaCosts = ability.manaCosts.copy();
        this.manaCostsToPay = ability.manaCostsToPay.copy();
        this.costs = ability.costs.copy();
        this.watchers = CardUtil.deepCopyObject(ability.watchers);

        this.subAbilities = CardUtil.deepCopyObject(ability.subAbilities);
        this.modes = ability.getModes().copy();
        this.ruleAtTheTop = ability.ruleAtTheTop;
        this.ruleVisible = ability.ruleVisible;
        this.ruleAdditionalCostsVisible = ability.ruleAdditionalCostsVisible;
        this.worksFaceDown = ability.worksFaceDown;
        this.worksPhasedOut = ability.worksPhasedOut;
        this.abilityWord = ability.abilityWord;
        this.flavorWord = ability.flavorWord;
        this.sourceObjectZoneChangeCounter = ability.sourceObjectZoneChangeCounter;
        this.canFizzle = ability.canFizzle;
        this.canBeCopied = ability.canBeCopied;
        this.targetAdjuster = ability.targetAdjuster;
        this.costAdjuster = ability.costAdjuster;
        this.hints = CardUtil.deepCopyObject(ability.hints);
        this.icons = CardUtil.deepCopyObject(ability.icons);
        this.customOutcome = ability.customOutcome;
        this.identifier = ability.identifier;
        this.activated = ability.activated;
        this.appendToRule = ability.appendToRule;
        this.sourcePermanentTransformCount = ability.sourcePermanentTransformCount;
        this.costsTagMap = CardUtil.deepCopyObject(ability.costsTagMap);
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void newId() {
        if (!(this instanceof MageSingleton)) {
            this.id = UUID.randomUUID();
        }
        getEffects().newId();

        for (Ability sub : getSubAbilities()) {
            sub.newId();
        }
    }

    @Override
    public void newOriginalId() {
        this.id = UUID.randomUUID();
        this.originalId = id;
        getEffects().newId();
    }

    @Override
    public AbilityType getAbilityType() {
        return this.abilityType;
    }

    @Override
    public boolean isActivatedAbility() {
        return this.abilityType.isActivatedAbility();
    }

    @Override
    public boolean isTriggeredAbility() {
        return this.abilityType.isTriggeredAbility();
    }

    @Override
    public boolean isNonManaActivatedAbility() {
        return this.abilityType.isNonManaActivatedAbility();
    }

    @Override
    public boolean isManaActivatedAbility() {
        return this.abilityType.isManaActivatedAbility();
    }

    @Override
    public boolean isManaAbility() {
        return this.abilityType.isManaAbility();
    }

    @Override
    public boolean resolve(Game game) {
        boolean result = true;
        //20100716 - 117.12
        if (checkIfClause(game)) {
            // Ability has started resolving. Fire event.
            // Used for abilities counting the number of resolutions like Ashling the Pilgrim.
            // TODO: called for mana abilities too, must be removed to safe place someday (see old place like StackAbility::resolve)
            game.fireEvent(new GameEvent(GameEvent.EventType.RESOLVING_ABILITY, this.getOriginalId(), this, this.getControllerId()));
            if (this instanceof TriggeredAbility) {
                for (UUID modeId : this.getModes().getSelectedModes()) {
                    this.getModes().setActiveMode(modeId);
                    result = resolveMode(game);
                }
            } else {
                result = resolveMode(game);
            }
        }
        return result;
    }

    private boolean resolveMode(Game game) {
        boolean result = true;
        for (Effect effect : getEffects()) {
            if (game.inCheckPlayableState() && !(effect instanceof ManaEffect)) {
                continue; // Ignored non mana effects - see GameEvent.TAPPED_FOR_MANA
            }
            if (effect instanceof OneShotEffect) {
                boolean effectResult = effect.apply(game, this);
                result &= effectResult;
                if (logger.isDebugEnabled()) {
                    if (!this.isManaAbility()) {
                        if (!effectResult) {
                            if (this.getSourceId() != null) {
                                MageObject mageObject = game.getObject(this.getSourceId());
                                if (mageObject != null) {
                                    logger.debug("AbilityImpl.resolve: object: " + mageObject.getName());
                                }
                            }
                            logger.debug("AbilityImpl.resolve: effect returned false -" + effect.getText(this.getModes().getMode()));
                        }
                    }
                }
            } else {
                game.addEffect((ContinuousEffect) effect, this);
            }

            /**
             * All restrained trigger events are fired now. To restrain the
             * events is mainly neccessary because of the movement of multiple
             * object at once. If the event is fired directly as one object
             * moved, other objects are not already in the correct zone to check
             * for their effects. (e.g. Valakut, the Molten Pinnacle)
             * <p>
             * game.applyEffects() has to be done at least for every effect that
             * moves cards/permanent between zones, or changes control of
             * objects so Static effects work as intended if dependant from the
             * moved objects zone it is in Otherwise for example were static
             * abilities with replacement effects deactivated too late Example:
             * {@link org.mage.test.cards.replacement.DryadMilitantTest#testDiesByDestroy testDiesByDestroy}
             */
            game.processAction();
        }
        return result;
    }

    @Override
    public boolean activate(Game game, Set<MageIdentifier> allowedIdentifiers, boolean noMana) {
        Player controller = game.getPlayer(this.getControllerId());
        if (controller == null) {
            return false;
        }
        game.applyEffects();

        MageObject sourceObject = getSourceObject(game);
        if (getSourceObjectZoneChangeCounter() == 0) {
            setSourceObjectZoneChangeCounter(game.getState().getZoneChangeCounter(getSourceId()));
        }
        setSourcePermanentTransformCount(game);

        // if ability can be cast for no mana, clear the mana costs now, because additional mana costs must be paid.
        // For Flashback ability can be set X before, so the X costs have to be restored for the flashbacked ability
        if (noMana) {
            if (!this.getManaCostsToPay().getVariableCosts().isEmpty()) {
                int xValue = CardUtil.getSourceCostsTag(game, this, "X", 0);
                this.clearManaCostsToPay();
                VariableManaCost xCosts = new VariableManaCost(VariableCostType.ADDITIONAL);
                xCosts.setAmount(xValue, xValue, false);
                addManaCostsToPay(xCosts);
            } else {
                this.clearManaCostsToPay();
            }
        }

        // fused or spliced spells contain multiple abilities (e.g. fused, left, right)
        // optional costs and cost modification must be applied only to the first/main ability
        // TODO: need tests with X announced costs, cost modification effects, CostAdjuster, early cost target, etc
        //  can be bugged due multiple calls (not all code parts below use isMainPartAbility)
        boolean isMainPartAbility = !CardUtil.isFusedPartAbility(this, game);

        /* 20220908 - 601.2b
         * If the player wishes to splice any cards onto the spell (see rule 702.45), they
         * reveal those cards in their hand.
         */
        if (isMainPartAbility && this.abilityType == AbilityType.SPELL) {
            game.getContinuousEffects().applySpliceEffects(this, game);
        }

        // 20130201 - 601.2b
        // If the spell has alternative or additional costs that will be paid as it's being cast such
        // as buyback, kicker, or convoke costs (see rules 117.8 and 117.9), the player announces his
        // or her intentions to pay any or all of those costs (see rule 601.2e).
        // A player can't apply two alternative methods of casting or two alternative costs to a single spell.
        if (isMainPartAbility) {
            if (!activateAlternateOrAdditionalCosts(sourceObject, allowedIdentifiers, noMana, controller, game)) {
                return false;
            }
        }

        // 117.6. Some mana costs contain no mana symbols. This represents an unpayable cost. An ability can
        // also have an unpayable cost if its cost is based on the mana cost of an object with no mana cost.
        // Attempting to cast a spell or activate an ability that has an unpayable cost is a legal action.
        // However, attempting to pay an unpayable cost is an illegal action.
        //
        // We apply this now, *AFTER* the user has made the choice to pay an alternative cost for the
        // spell. You can also still cast a spell with an unplayable cost by... not paying it's mana cost.
        //if (getAbilityType() == AbilityType.SPELL && getManaCostsToPay().isEmpty() && !noMana) {
        //    return false;
        //}
        if (getAbilityType() == AbilityType.SPELL && (getManaCostsToPay().isEmpty() && getCosts().isEmpty()) && !noMana) {
            return false;
        }

        // 20241022 - 601.2b
        // Choose targets for costs that have to be chosen early
        // Not yet included in 601.2b but this is where it will be
        handleChooseCostTargets(game, controller);

        // prepare dynamic costs (must be called before any x announce)
        if (isMainPartAbility) {
            adjustX(game);
        }

        // 20121001 - 601.2b
        // If the spell has a variable cost that will be paid as it's being cast (such as an {X} in
        // its mana cost; see rule 107.3), the player announces the value of that variable.
        VariableManaCost variableManaCost = handleManaXCosts(game, noMana, controller);
        String announceString = handleOtherXCosts(game, controller);

        // 601.2b If a cost that will be paid as the spell is being cast includes
        // Phyrexian mana symbols, the player announces whether they intend to pay 2
        // life or the corresponding colored mana cost for each of those symbols.
        AbilityImpl.handlePhyrexianCosts(game, this, this, this.getManaCostsToPay());

        // 20241022 - 601.2b
        // Not yet included in 601.2b but this is where it will be
        handleChooseCostTargets(game, controller);

        /* 20130201 - 601.2b
         * If the spell is modal the player announces the mode choice (see rule 700.2).
         */
        // rules:
        // You kick a spell as you cast it. You declare whether you're going to pay a kicker cost at the same
        // time you'd choose a spell's mode, and then you actually pay it at the same time you pay the spell's mana cost.
        // Kicking a spell is always optional.
        if (!getModes().choose(game, this)) {
            return false;
        }

        // apply mode costs if they have them
        for (UUID modeId : this.getModes().getSelectedModes()) {
            Cost cost = this.getModes().get(modeId).getCost();
            if (cost instanceof ManaCost) {
                this.addManaCostsToPay((ManaCost) cost.copy());
            } else if (cost != null) {
                this.costs.add(cost.copy());
            }
        }

        // unit tests only: it allows to add targets/choices by two ways:
        // 1. From cast/activate command params (process it here)
        // 2. From single addTarget/setChoice, it's a preffered method for tests (process it in normal choose dialogs like human player)
        if (controller.isTestsMode()) {
            if (!controller.addTargets(this, game)) {
                return false;
            }
        }

        for (UUID modeId : this.getModes().getSelectedModes()) {
            this.getModes().setActiveMode(modeId);

            // 20121001 - 601.2c
            // 601.2c The player announces their choice of an appropriate player, object, or zone for
            // each target the spell requires. A spell may require some targets only if an alternative or
            // additional cost (such as a buyback or kicker cost), or a particular mode, was chosen for it;
            // otherwise, the spell is cast as though it did not require those targets. If the spell has a
            // variable number of targets, the player announces how many targets they will choose before
            // they announce those targets. The same target can't be chosen multiple times for any one
            // instance of the word "target" on the spell. However, if the spell uses the word "target" in
            // multiple places, the same object, player, or zone can be chosen once for each instance of the
            // word "target" (as long as it fits the targeting criteria). If any effects say that an object
            // or player must be chosen as a target, the player chooses targets so that they obey the
            // maximum possible number of such effects without violating any rules or effects that say that
            // an object or player can't be chosen as a target. The chosen players, objects, and/or zones
            // each become a target of that spell. (Any abilities that trigger when those players, objects,
            // and/or zones become the target of a spell trigger at this point; they'll wait to be put on
            // the stack until the spell has finished being cast.)

            if (!this.getAbilityType().isTriggeredAbility()) { // triggered abilities check this already in playerImpl.triggerAbility
                adjustTargets(game);
            }

            if (!getTargets().isEmpty()) {
                Outcome outcome = getEffects().getOutcome(this);

                // only activated abilities can be canceled by human user (not triggered)
                // Note: ActivatedAbility does include SpellAbility & PlayLandAbility, but those should be able to be canceled too.
                boolean canCancel = this instanceof ActivatedAbility && controller.isHuman();
                if (!getTargets().chooseTargets(outcome, this.controllerId, this, noMana, game, canCancel)) {
                    // was canceled during target selection
                    return false;
                }
            }
        } // end modes

        // 20220908 - 601.2e
        // 601.2e The game checks to see if the proposed spell can legally be cast. If the proposed spell
        // is illegal, the game returns to the moment before the casting of that spell was proposed
        // (see rule 727, "Handling Illegal Actions").
        if (this.getAbilityType() == AbilityType.SPELL) {
            GameEvent castEvent = GameEvent.getEvent(GameEvent.EventType.CAST_SPELL_LATE, this.getId(), this, getControllerId());
            castEvent.setZone(game.getState().getZone(CardUtil.getMainCardId(game, sourceId)));
            if (game.replaceEvent(castEvent, this)) {
                return false;
            }
        }

        // this is a hack to prevent mana abilities with mana costs from causing endless loops - pay other costs first
        if (this instanceof ActivatedManaAbilityImpl && !getCosts().pay(this, game, this, controllerId, noMana, null)) {
            logger.debug("activate mana ability failed - non mana costs");
            return false;
        }

        //20101001 - 601.2e
        if (isMainPartAbility) {
            // adjustX already called before any announces
            game.getContinuousEffects().costModification(this, game);
        }

        UUID activatorId = controllerId;
        if ((this instanceof ActivatedAbilityImpl) && ((ActivatedAbilityImpl) this).getActivatorId() != null) {
            activatorId = ((ActivatedAbilityImpl) this).getActivatorId();
        }

        //20100716 - 601.2f  (noMana is not used here, because mana costs were cleared for this ability before adding additional costs and applying cost modification effects)
        if (!getManaCostsToPay().pay(this, game, this, activatorId, false, null)) {
            return false; // cancel during mana payment
        }

        //20100716 - 601.2g
        if (!getCosts().pay(this, game, this, activatorId, noMana, null)) {
            logger.debug("activate failed - non mana costs");
            return false;
        }
        // inform about x costs now, so canceled announcements are not shown in the log
        if ((announceString != null) && (!announceString.equals(""))) {
            game.informPlayers(announceString);
        }
        if (variableManaCost != null) {
            int xValue = CardUtil.getSourceCostsTag(game, this, "X", 0);
            game.informPlayers(controller.getLogName() + " announces a value of " + xValue + " for " + variableManaCost.getText()
                    + CardUtil.getSourceLogName(game, this));
        }
        activated = true;
        return true;
    }

    @Override
    public boolean isActivated() {
        return activated;
    }

    /**
     * @return false to stop activation process, e.g. on wrong data/choices
     */
    @Override
    public boolean activateAlternateOrAdditionalCosts(MageObject sourceObject, Set<MageIdentifier> allowedIdentifiers, boolean noMana, Player controller, Game game) {
        // alternative or additional costs supported for spells or activated abilities only
        if (!this.getAbilityType().isActivatedAbility()
                && !this.getAbilityType().isPlayCardAbility()) {
            return true;
        }

        boolean canUseAlternativeCost = true;
        boolean canUseAdditionalCost = true;

        if (this instanceof SpellAbility) {
            // A player can't apply two alternative methods of casting or two alternative costs to a single spell.
            switch (((SpellAbility) this).getSpellAbilityCastMode()) {
                case FLASHBACK:
                case HARMONIZE:
                case MADNESS:
                case TRANSFORMED:
                case DISTURB:
                case MORE_THAN_MEETS_THE_EYE:
                case BESTOW:
                case MORPH:
                case DISGUISE:
                case PLOT:
                    // from Snapcaster Mage:
                    // If you cast a spell from a graveyard using its flashback ability, you can't pay other alternative costs
                    // (such as that of Foil). (2018-12-07)
                    canUseAlternativeCost = false;
                    // You may pay any optional additional costs the spell has, such as kicker costs. You must pay any
                    // mandatory additional costs the spell has, such as that of Tormenting Voice. (2018-12-07)
                    canUseAdditionalCost = true;
                    break;
                case PROTOTYPE:
                    // Notably, casting a spell as a prototype does not count as paying an alternative cost.
                    // https://magic.wizards.com/en/news/feature/comprehensive-rules-changes
                case NORMAL:
                    canUseAlternativeCost = true;
                    canUseAdditionalCost = true;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown ability cast mode: " + ((SpellAbility) this).getSpellAbilityCastMode());
            }
        }
        if (this.getAbilityType() == AbilityType.SPELL && this instanceof SpellAbility
                // 117.9a Only one alternative cost can be applied to any one spell as it's being cast.
                // So an alternate spell ability can't be paid with Omniscience
                && ((SpellAbility) this).getSpellAbilityType() == SpellAbilityType.BASE_ALTERNATE) {
            canUseAlternativeCost = false;
        }

        // TODO: Why the check for permanent?
        if (sourceObject == null || sourceObject instanceof Permanent) {
            return true;
        }

        // it's important to apply alternative cost first
        // example: Omniscience gives free mana as alternative, but Entwine ability adds {2} as additional
        Abilities<Ability> abilities = CardUtil.getAbilities(sourceObject, game);

        // 1. ALTERNATIVE COSTS
        // Collect all possible alternatives costs:
        List<AlternativeSourceCosts> possibleAlternatives = new ArrayList<>();
        for (Ability ability : abilities) {
            // if cast for noMana no Alternative costs are allowed
            if (canUseAlternativeCost && !noMana && ability instanceof AlternativeSourceCosts) {
                AlternativeSourceCosts alternativeSpellCosts = (AlternativeSourceCosts) ability;
                if (alternativeSpellCosts.isAvailable(this, game)
                        && alternativeSpellCosts.canActivateAlternativeCostsNow(this, game)
                        && (allowedIdentifiers.contains(MageIdentifier.Default) || allowedIdentifiers.contains(ability.getIdentifier()))) {
                    possibleAlternatives.add(alternativeSpellCosts);
                }
            }
        }
        // controller specific alternate spell costs
        if (canUseAlternativeCost && !noMana) {
            for (AlternativeSourceCosts alternativeSourceCosts : controller.getAlternativeSourceCosts()) {
                if (alternativeSourceCosts.isAvailable(this, game)
                        && alternativeSourceCosts.canActivateAlternativeCostsNow(this, game)
                        && (allowedIdentifiers.contains(MageIdentifier.Default) || allowedIdentifiers.contains(alternativeSourceCosts.getIdentifier()))) {
                    possibleAlternatives.add(alternativeSourceCosts);
                }
            }
        }
        Player player = game.getPlayer(getControllerId());
        if (player == null) {
            // No controller to activate.
            return false;
        }
        Choice choice = new ChoiceImpl(false); // not required, cancelling will cancel the cast (as you could do once in the pay mana mode).
        choice.setSubMessage("for casting " + CardUtil.getSourceLogName(game, "", this, "", ""));
        AlternativeSourceCosts alternativeChosen = null;
        if (!possibleAlternatives.isEmpty()) {
            // At least one alternative cost is available.
            // We open a menu for the player to choose up to one.
            boolean mustChooseAlternative = !(allowedIdentifiers.contains(MageIdentifier.Default) || allowedIdentifiers.contains(getIdentifier()));
            choice.setMessage(
                    mustChooseAlternative
                            ? "Choose an alternative cost"
                            : "You may choose an alternative cost"
            );
            Map<String, Integer> sort = new LinkedHashMap<>();
            int i;
            for (i = 0; i < possibleAlternatives.size(); i++) {
                String key = Integer.toString(i + 1);
                sort.put(key, i);
                AlternativeSourceCosts alternative = possibleAlternatives.get(i);
                MageObject object = alternative.getSourceObject(game);
                choice.withItem(
                        key,
                        possibleAlternatives.get(i).getAlternativeCostText(this, game),
                        i,
                        object != null ? ChoiceHintType.GAME_OBJECT : null,
                        object != null ? object.getId().toString() : null
                );
            }
            if (!mustChooseAlternative) {
                // add the non-alternative cast as the last option.
                String key = Integer.toString(i + 1);
                sort.put(key, i);
                choice.withItem(
                        key,
                        "Cast with no alternative cost: " + this.getManaCosts().getText(),
                        i,
                        ChoiceHintType.GAME_OBJECT,
                        sourceObject.getId().toString()
                );
            }
            if (!player.choose(Outcome.Benefit, choice, game)) {
                return false;
            }
            String choiceKey = choice.getChoiceKey();
            if (sort.containsKey(choiceKey)) {
                int choiceNumber = sort.get(choiceKey);
                if (choiceNumber < possibleAlternatives.size()) {
                    alternativeChosen = possibleAlternatives.get(choiceNumber);
                }
            }
        }
        if (alternativeChosen != null) {
            alternativeChosen.activateAlternativeCosts(this, game);
        }
        // 2. ADDITIONAL COST
        for (Ability ability : abilities) {
            if (canUseAdditionalCost && ability instanceof OptionalAdditionalSourceCosts) {
                ((OptionalAdditionalSourceCosts) ability).addOptionalAdditionalCosts(this, game);
            }
        }
        return true;
    }

    /**
     * Handles the setting of non mana X costs
     *
     * @param controller
     * @param game
     * @return announce message
     */
    protected String handleOtherXCosts(Game game, Player controller) {
        StringBuilder announceString = new StringBuilder();
        for (VariableCost variableCost : this.getCosts().getVariableCosts()) {
            if (!(variableCost instanceof VariableManaCost) && !((Cost) variableCost).isPaid()) {
                int xValue = variableCost.announceXValue(this, game);
                Cost fixedCost = variableCost.getFixedCostsFromAnnouncedValue(xValue);
                addCost(fixedCost);
                // set the xcosts to paid
                variableCost.setAmount(xValue, xValue, false);
                ((Cost) variableCost).setPaid();
                String message = controller.getLogName() + " announces a value of " + xValue + " (" + variableCost.getActionText() + ')'
                        + CardUtil.getSourceLogName(game, this);
                announceString.append(message);
                setCostsTag("X", xValue);
            }
        }
        return announceString.toString();
    }

    /**
     * Prepare Phyrexian costs (choose life to pay instead mana)
     * Must be called on cast announce before any cost modifications
     *
     * @param abilityToPay   paying ability (will receive life cost)
     * @param manaCostsToPay paying cost (will remove P and replace it by mana or nothing)
     */
    public static void handlePhyrexianCosts(Game game, Ability source, Ability abilityToPay, ManaCosts manaCostsToPay) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return;
        }

        Iterator<ManaCost> costIterator = manaCostsToPay.iterator();
        while (costIterator.hasNext()) {
            ManaCost cost = costIterator.next();
            if (!cost.isPhyrexian()) {
                continue;
            }
            PayLifeCost payLifeCost = new PayLifeCost(2);
            if (payLifeCost.canPay(abilityToPay, source, controller.getId(), game)
                    && controller.chooseUse(Outcome.LoseLife, "Pay 2 life instead of " + cost.getText().replace("/P", "")
                    + " (phyrexian cost)?", source, game)) {
                costIterator.remove();
                abilityToPay.addCost(payLifeCost);
                manaCostsToPay.incrPhyrexianPaid(); // mark it as real phyrexian pay, e.g. for planeswalkers with Compleated ability
            }
        }
    }

    /**
     * Prepare and pay Phyrexian style effects like replace mana by life
     * Must be called after original Phyrexian mana processing and after cost modifications, e.g. on payment
     *
     * @param abilityToPay   paying ability (will receive life cost)
     * @param manaCostsToPay paying cost (will replace mana by nothing)
     */
    public static void handlePhyrexianLikeEffects(Game game, Ability source, Ability abilityToPay, ManaCosts manaCostsToPay) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return;
        }

        // If a cost contains a mana symbol that may be paid in multiple ways, such as {B/R}, {B/P}, or {2/B},
        // you choose how you'll pay it before you do so. If you choose to pay {B} this way, K'rrik's ability allows
        // you to pay life rather than pay that mana.
        // (2019-08-23)
        FilterMana phyrexianColors = controller.getPhyrexianColors();
        if (controller.getPhyrexianColors() == null) {
            return;
        }
        Iterator<ManaCost> costIterator = manaCostsToPay.iterator();
        while (costIterator.hasNext()) {
            ManaCost cost = costIterator.next();
            Mana mana = cost.getMana();
            if ((!phyrexianColors.isWhite() || mana.getWhite() <= 0)
                    && (!phyrexianColors.isBlue() || mana.getBlue() <= 0)
                    && (!phyrexianColors.isBlack() || mana.getBlack() <= 0)
                    && (!phyrexianColors.isRed() || mana.getRed() <= 0)
                    && (!phyrexianColors.isGreen() || mana.getGreen() <= 0)) {
                continue;
            }
            PayLifeCost payLifeCost = new PayLifeCost(2);
            if (payLifeCost.canPay(abilityToPay, source, controller.getId(), game)
                    && controller.chooseUse(Outcome.LoseLife, "Pay 2 life instead of " + cost.getText().replace("/P", "")
                    + " (pay life cost)?", source, game)) {
                if (payLifeCost.pay(abilityToPay, game, source, controller.getId(), false, null)) {
                    costIterator.remove();
                    abilityToPay.addCost(payLifeCost);
                }
            }
        }
    }

    /**
     * 601.2b Choose targets for costs that have to be chosen early.
     */
    private void handleChooseCostTargets(Game game, Player controller) {
        for (Cost cost : getCosts()) {
            if (cost instanceof EarlyTargetCost && cost.getTargets().isEmpty()) {
                ((EarlyTargetCost) cost).chooseTarget(game, this, controller);
            }
        }
        for (ManaCost cost : getManaCostsToPay()) {
            if (cost instanceof EarlyTargetCost && cost.getTargets().isEmpty()) {
                ((EarlyTargetCost) cost).chooseTarget(game, this, controller);
            }
        }
    }

    /**
     * Handles X mana costs and sets manaCostsToPay.
     *
     * @param game
     * @param noMana
     * @param controller
     * @return variableManaCost for posting to log later
     */
    protected VariableManaCost handleManaXCosts(Game game, boolean noMana, Player controller) {
        // 20210723 - 601.2b
        // If the spell has alternative or additional costs that will
        // be paid as it's being cast such as buyback or kicker costs (see rules 118.8 and 118.9),
        // the player announces their intentions to pay any or all of those costs (see rule 601.2f).
        // A player can't apply two alternative methods of casting or two alternative costs to a
        // single spell. If the spell has a variable cost that will be paid as it's being cast
        // (such as an {X} in its mana cost; see rule 107.3), the player announces the value of that
        // variable. If the value of that variable is defined in the text of the spell by a choice
        // that player would make later in the announcement or resolution of the spell, that player
        // makes that choice at this time instead of that later time.

        // TODO: Handle announcing other variable costs here like: RemoveVariableCountersSourceCost
        VariableManaCost variableManaCost = null;
        for (ManaCost cost : getManaCostsToPay()) {
            if (cost instanceof VariableManaCost) {
                if (variableManaCost == null) {
                    variableManaCost = (VariableManaCost) cost;
                } else {
                    // only one VariableManCost per spell (or is it possible to have more?)
                    logger.error("Variable mana cost allowes only in one instance per ability: " + this);
                }
            }
        }
        if (variableManaCost != null) {
            if (!variableManaCost.isPaid()) { // should only happen for human players
                int xValue;
                if (!noMana || variableManaCost.getCostType().canUseAnnounceOnFreeCast()) {
                    if (variableManaCost.wasAnnounced()) {
                        // announce by rules
                        xValue = variableManaCost.getAmount();
                    } else {
                        // announce by player
                        xValue = controller.announceX(variableManaCost.getMinX(), variableManaCost.getMaxX(),
                                "Announce the value for " + variableManaCost.getText(), game, this, true);
                    }

                    int amountMana = xValue * variableManaCost.getXInstancesCount();
                    StringBuilder manaString = threadLocalBuilder.get();
                    if (variableManaCost.getFilter() == null || variableManaCost.getFilter().isGeneric()) {
                        manaString.append('{').append(amountMana).append('}');
                    } else {
                        String manaSymbol = null;
                        if (variableManaCost.getFilter().isBlack()) {
                            if (variableManaCost.getFilter().isRed()) {
                                manaSymbol = "B/R";
                            } else {
                                manaSymbol = "B";
                            }
                        } else if (variableManaCost.getFilter().isRed()) {
                            manaSymbol = "R";
                        } else if (variableManaCost.getFilter().isBlue()) {
                            manaSymbol = "U";
                        } else if (variableManaCost.getFilter().isGreen()) {
                            manaSymbol = "G";
                        } else if (variableManaCost.getFilter().isWhite()) {
                            manaSymbol = "W";
                        }
                        if (manaSymbol == null) {
                            throw new UnsupportedOperationException("ManaFilter is not supported: " + this);
                        }
                        for (int i = 0; i < amountMana; i++) {
                            manaString.append('{').append(manaSymbol).append('}');
                        }
                    }
                    addManaCostsToPay(new ManaCostsImpl<>(manaString.toString()));
                    getManaCostsToPay().setX(xValue, amountMana);
                    setCostsTag("X", xValue);
                }
                variableManaCost.setPaid();
            }
        }

        return variableManaCost;
    }

    // called at end of turn for each Permanent
    @Override
    public void reset(Game game) {
    }

    @Override
    public boolean checkIfClause(Game game) {
        return true;
    }

    @Override
    public UUID getControllerId() {
        return controllerId;
    }

    @Override
    public UUID getControllerOrOwnerId() {
        return getControllerId();
    }

    @Override
    public void setControllerId(UUID controllerId) {
        this.controllerId = controllerId;
        for (Watcher watcher : getWatchers()) {
            watcher.setControllerId(controllerId);
        }

        if (subAbilities != null) {
            for (Ability subAbility : subAbilities) {
                subAbility.setControllerId(controllerId);
            }
        }
    }

    @Override
    public UUID getSourceId() {
        return sourceId;
    }

    @Override
    public void setSourceId(UUID sourceId) {
        if (this.sourceId == null) {
            this.sourceId = sourceId;
        } else if (!(this instanceof MageSingleton)) {
            this.sourceId = sourceId;
        }
        if (subAbilities != null) {
            for (Ability subAbility : subAbilities) {
                subAbility.setSourceId(sourceId);
            }
        }
        for (Watcher watcher : getWatchers()) {
            watcher.setSourceId(sourceId);
        }

    }

    @Override
    public Costs<Cost> getCosts() {
        return costs;
    }

    @Override
    public ManaCosts<ManaCost> getManaCosts() {
        return manaCosts;
    }

    /**
     * Should be used by
     * {@link mage.abilities.effects.CostModificationEffect cost modification effects}
     * to manipulate what is actually paid before resolution.
     *
     * @return
     */
    @Override
    public ManaCosts<ManaCost> getManaCostsToPay() {
        return manaCostsToPay;
    }

    @Override
    public Map<String, Object> getCostsTagMap() {
        return costsTagMap;
    }

    public void setCostsTag(String tag, Object value) {
        if (costsTagMap == null) {
            costsTagMap = new HashMap<>();
        }
        costsTagMap.put(tag, value);
    }

    @Override
    public Effects getEffects() {
        return getModes().getMode().getEffects();
    }

    @Override
    public Effects getAllEffects() {
        Effects allEffects = new Effects();
        for (Mode mode : getModes().values()) {
            allEffects.addAll(mode.getEffects());
        }
        return allEffects;
    }

    @Override
    public Effects getEffects(Game game, EffectType effectType) {
        Effects typedEffects = new Effects();
        for (Effect effect : getEffects()) {
            if (effect.getEffectType() == effectType) {
                typedEffects.add(effect);
            }
        }
        return typedEffects;
    }

    @Override
    public Zone getZone() {
        return zone;
    }

    @Override
    public List<Watcher> getWatchers() {
        return watchers;
    }

    @Override
    public void addWatcher(Watcher watcher) {
        watcher.setSourceId(this.sourceId);
        watcher.setControllerId(this.controllerId);
        getWatchers().add(watcher);
    }

    @Override
    public List<Ability> getSubAbilities() {
        if (subAbilities != null) {
            return subAbilities;
        } else {
            return emptyAbilities;
        }
    }

    @Override
    public void addSubAbility(Ability ability) {
        if (subAbilities == null) {
            subAbilities = new ArrayList<>();
        }
        ability.setSourceId(this.sourceId);
        ability.setControllerId(this.controllerId);
        subAbilities.add(ability);
    }

    @Override
    public boolean isUsesStack() {
        return usesStack;
    }

    public void appendToRule(String appendToRule) {
        this.appendToRule = appendToRule;
    }

    @Override
    public String getRule() {
        return getRule(false);
    }

    @Override
    public String getRule(boolean all) {
        StringBuilder sbRule = threadLocalBuilder.get();
        if (all || this.abilityType != AbilityType.SPELL) { // TODO: Why the override for non-spells?
            if (!getManaCosts().isEmpty()) {
                sbRule.append(getManaCosts().getText());
            }
            if (!getCosts().isEmpty()) {
                if (sbRule.length() > 0) {
                    sbRule.append(", ");
                }
                sbRule.append(getCosts().getText());
            }
            if (sbRule.length() > 0) {
                sbRule.append(": ");
            }
        }

        String ruleStart = sbRule.toString();
        String text = getModes().getText();
        StringBuilder rule = new StringBuilder();
        if (!text.isEmpty()) {
            if (ruleStart.length() > 1) {
                String end = ruleStart.substring(ruleStart.length() - 2).trim();
                if (end.isEmpty() || end.equals(":") || end.equals(".")) {
                    rule.append(ruleStart + CardUtil.getTextWithFirstCharUpperCase(text));
                } else {
                    rule.append(ruleStart + text);
                }
            } else {
                rule.append(ruleStart + text);
            }
        } else {
            rule.append(ruleStart);
        }
        if (appendToRule != null) {
            rule.append(appendToRule);
        }
        if (this instanceof TriggeredAbility || this instanceof EntersBattlefieldAbility) {
            return rule.toString();
        }
        return addRulePrefix(rule.toString());
    }

    @Override
    public String getRule(String source) {
        return formatRule(getRule(), source);
    }

    protected String formatRule(String rule, String source) {
        String replace = rule;
        if (rule != null && source != null && !source.isEmpty()) {
            replace = rule.replace("{this}", source);
        }
        return replace;
    }

    @Override
    public void addCost(Cost cost) {
        if (cost == null) {
            return;
        }
        if (cost instanceof Costs) {
            // as list of costs
            Costs<Cost> list = (Costs<Cost>) cost;
            for (Cost single : list) {
                addCost(single);
            }
        } else {
            // as single cost
            if (cost instanceof ManaCost) {
                manaCosts.add((ManaCost) cost);
                manaCostsToPay.add((ManaCost) cost);
            } else {
                costs.add(cost);
            }
        }
    }

    @Override
    public void addManaCostsToPay(ManaCost manaCost) {
        if (manaCost == null) {
            return;
        }
        if (manaCost instanceof ManaCosts) {
            manaCostsToPay.addAll((ManaCosts) manaCost);
        } else {
            manaCostsToPay.add(manaCost);
        }
    }

    @Override
    public void setVariableCostsMinMax(int min, int max) {
        // modify all values (mtg rules allow only one type of X, so min/max must be shared between all X instances)

        // base cost
        for (ManaCost cost : getManaCosts()) {
            if (cost instanceof MinMaxVariableCost) {
                MinMaxVariableCost minMaxCost = (MinMaxVariableCost) cost;
                minMaxCost.setMinX(min);
                minMaxCost.setMaxX(max);
            }
        }

        // prepared cost
        for (ManaCost cost : getManaCostsToPay()) {
            if (cost instanceof MinMaxVariableCost) {
                MinMaxVariableCost minMaxCost = (MinMaxVariableCost) cost;
                minMaxCost.setMinX(min);
                minMaxCost.setMaxX(max);
            }
        }
    }

    @Override
    public void setVariableCostsValue(int xValue) {
        // only mana cost supported

        // base cost
        boolean foundBaseCost = false;
        for (ManaCost cost : getManaCosts()) {
            if (cost instanceof VariableManaCost) {
                foundBaseCost = true;
                ((VariableManaCost) cost).setMinX(xValue);
                ((VariableManaCost) cost).setMaxX(xValue);
                ((VariableManaCost) cost).setAmount(xValue, xValue, false);
            }
        }

        // prepared cost
        boolean foundPreparedCost = false;
        for (ManaCost cost : getManaCostsToPay()) {
            if (cost instanceof VariableManaCost) {
                foundPreparedCost = true;
                ((VariableManaCost) cost).setMinX(xValue);
                ((VariableManaCost) cost).setMaxX(xValue);
                ((VariableManaCost) cost).setAmount(xValue, xValue, false);
            }
        }

        if (!foundPreparedCost || !foundBaseCost) {
            throw new IllegalArgumentException("Wrong code usage: auto-announced X values allowed in mana costs only");
        }
    }

    @Override
    public void addEffect(Effect effect) {
        if (effect != null) {
            getEffects().add(effect);
        }
    }

    @Override
    public void addTarget(Target target) {
        // verify check
        if (target instanceof TargetCardInLibrary
                || (target instanceof TargetCard && target.getZone().equals(Zone.LIBRARY))) {
            throw new IllegalArgumentException("Wrong usage of TargetCardInLibrary - you must use it with SearchLibrary only");
        }

        if (target != null) {
            getTargets().add(target);
        }
    }

    @Override
    public Targets getTargets() {
        if (getModes().getMode() != null) {
            return getModes().getMode().getTargets();
        }
        return new Targets().withReadOnly();
    }

    @Override
    public Targets getAllSelectedTargets() {
        Targets res = new Targets();
        for (UUID modeId : this.getModes().getSelectedModes()) {
            Mode mode = this.getModes().get(modeId);
            if (mode != null) {
                res.addAll(mode.getTargets());
            }
        }
        return res.withReadOnly();
    }

    @Override
    public UUID getFirstTarget() {
        return getTargets().getFirstTarget();
    }

    @Override
    public boolean isModal() {
        return getModes().size() > 1;
    }

    @Override
    public void addMode(Mode mode) {
        getModes().addMode(mode);

        // runtime check: modes must have good settings
        int currentMin = getModes().getMinModes();
        int currentMax = getModes().getMaxModes(null, null);
        boolean isFine = true;
        if (currentMin < 0 || currentMax < 0) {
            isFine = false;
        }
        if (currentMin > 0 && currentMin > currentMax) {
            isFine = false;
        }
        if (!isFine) {
            throw new IllegalArgumentException(String.format("Wrong code usage: you must setup correct min and max modes (%d, %d) for %s",
                    currentMin, currentMax, this));
        }
    }

    @Override
    public Modes getModes() {
        return modes;
    }

    @Override
    public boolean canChooseTarget(Game game, UUID playerId) {
        return canChooseTargetAbility(this, getModes(), game, playerId);
    }

    protected static boolean canChooseTargetAbility(Ability ability, Modes modes, Game game, UUID controllerId) {
        int found = 0;
        for (Mode mode : modes.values()) {
            boolean validTargets = true;
            for (Target target : mode.getTargets()) {
                UUID abilityControllerId = controllerId;
                if (target.getTargetController() != null) {
                    abilityControllerId = target.getTargetController();
                }
                if (!target.canChoose(abilityControllerId, ability, game)) {
                    validTargets = false;
                    break;
                }
            }

            if (validTargets) {
                found++;
                if (modes.isMayChooseSameModeMoreThanOnce()) {
                    return true;
                }
                if (found >= modes.getMinModes()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject sourceObject, GameEvent event) {
        if (!this.hasSourceObjectAbility(game, sourceObject, event)) {
            return false;
        }

        // workaround for singleton abilities like Flying
        UUID affectedSourceId = getRealSourceObjectId(this, sourceObject);
        MageObject affectedSourceObject = game.getObject(affectedSourceId);

        // global game effects (works all the time and don't have sourceId, example: FinalityCounterEffect)
        if (affectedSourceId == null) {
            return true;
        }

        // emblems/dungeons/planes effects (works all the time, store in command zone)
        if (zone == Zone.COMMAND) {
            if (affectedSourceObject instanceof Emblem || affectedSourceObject instanceof Dungeon || affectedSourceObject instanceof Plane) {
                return true;
            }
        }

        // on entering permanents must use static abilities like it already on battlefield
        // example: Tatterkite enters without counters from Mikaeus, the Unhallowed
        if (game.getPermanentEntering(affectedSourceId) != null && zone == Zone.BATTLEFIELD) {
            return true;
        }

        // 603.10.
        // Normally, objects that exist immediately after an event are checked to see if the event matched
        // any trigger conditions, and continuous effects that exist at that time are used to determine what the
        // trigger conditions are and what the objects involved in the event look like.
        // ...
        Zone affectedObjectZone = game.getState().getZone(affectedSourceId);

        // 603.10.
        // ...
        // However, some triggered abilities are exceptions to this rule; the game “looks back in time” to determine
        // if those abilities trigger, using the existence of those abilities and the appearance of objects
        // immediately prior to the event. The list of exceptions is as follows:

        // 603.10a
        // Some zone-change triggers look back in time. These are leaves-the-battlefield abilities,
        // abilities that trigger when a card leaves a graveyard, and abilities that trigger when an object that all
        // players can see is put into a hand or library.

        // TODO: research use cases and implement shared logic with "looking zone" instead LKI only
        //  in most use cases it's already supported by event (example: saved permanent object in event's target)
        // [x] 603.10a leaves-the-battlefield abilities and other
        // [ ] 603.10b Abilities that trigger when a permanent phases out look back in time.
        // [ ] 603.10c Abilities that trigger specifically when an object becomes unattached look back in time.
        // [ ] 603.10d Abilities that trigger when a player loses control of an object look back in time.
        // [ ] 603.10e Abilities that trigger when a spell is countered look back in time.
        // [ ] 603.10f Abilities that trigger when a player loses the game look back in time.
        // [ ] 603.10g Abilities that trigger when a player planeswalks away from a plane look back in time.

        if (event == null) {
            // state base triggers - use only actual state
        } else {
            // event triggers and continues effects - can look back in time
            if (isAbilityCanLookBackInTime(this) && isEventCanLookBackInTime(event)) {
                // 603.10a leaves-the-battlefield
                if (game.checkShortLivingLKI(affectedSourceId, Zone.BATTLEFIELD)) {
                    affectedObjectZone = Zone.BATTLEFIELD;
                }
                // 603.10a leaves a graveyard
                // TODO: need tests
                if (game.checkShortLivingLKI(affectedSourceId, Zone.GRAVEYARD)) {
                    affectedObjectZone = Zone.GRAVEYARD;
                }
                // 603.10a put into a hand or library
                // TODO: need tests and implementation?
            }
        }

        return zone.match(affectedObjectZone);
    }

    public static boolean isAbilityCanLookBackInTime(Ability ability) {
        if (ability instanceof StaticAbility) {
            return true;
        }
        if (ability instanceof TriggeredAbility) {
            return ((TriggeredAbility) ability).isLeavesTheBattlefieldTrigger();
        }
        return false;
    }

    public static boolean isEventCanLookBackInTime(GameEvent event) {
        if (event == null) {
            return false;
        }

        List<GameEvent> allEvents = new ArrayList<>();
        if (event instanceof BatchEvent) {
            allEvents.addAll(((BatchEvent) event).getEvents());
        } else {
            allEvents.add(event);
        }

        return allEvents.stream().anyMatch(e -> {
            // TODO: need sync code with TriggeredAbilityImpl.isInUseableZone
            // TODO: add more events with zone change logic (or make it event's param)?
            //   need research: is it ability's or event's task?
            //   - ability's task: code like ability.setLookBackInTime
            //   - event's task: code like current switch
            // TODO: alternative solution: replace check by source.isLeavesTheBattlefieldTrigger?
            switch (e.getType()) {
                case DESTROYED_PERMANENT:
                case EXPLOITED_CREATURE:
                case SACRIFICED_PERMANENT:
                    return true;
                case ZONE_CHANGE:
                    return ((ZoneChangeEvent) e).getFromZone() == Zone.BATTLEFIELD;
                default:
                    return false;
            }
        });
    }

    /**
     * Find real source object id from any ability (real and singleton)
     */
    protected static UUID getRealSourceObjectId(Ability sourceAbility, MageObject sourceObject) {
        // In singleton abilities like Flying we can't rely on ability's source because it's init only once in continuous effects
        // so will use the sourceId of the object itself that came as a parameter if it is not null
        if (sourceAbility instanceof MageSingleton && sourceObject != null) {
            return sourceObject.getId();
        } else {
            return sourceAbility.getSourceId();
        }
    }

    @Override
    public final boolean hasSourceObjectAbility(Game game, MageObject sourceObject, GameEvent event) {
        MageObject object = sourceObject;
        if (object == null) {
            object = game.getPermanentEntering(getSourceId());
            if (object == null) {
                object = game.getObject(getSourceId());
            }
        }

        if (object == null) {
            // global replacement and other continues effects can be without source, but active (must return true all time)
            return true;
        }

        if (!object.hasAbility(this, game)) {
            return false;
        }

        // phase in/out support
        if (object instanceof Permanent) {
            return ((Permanent) object).isPhasedIn() || this.getWorksPhasedOut();
        }

        return true;
    }

    @Override
    public String toString() {
        return getRule();
    }

    @Override
    public boolean getRuleAtTheTop() {
        return ruleAtTheTop;
    }

    @Override
    public Ability setRuleAtTheTop(boolean ruleAtTheTop) {
        if (!(this instanceof MageSingleton)) {
            this.ruleAtTheTop = ruleAtTheTop;
        }
        return this;
    }

    @Override
    public boolean getRuleVisible() {
        return ruleVisible;
    }

    @Override
    public Ability setRuleVisible(boolean ruleVisible) {
        if (!(this instanceof MageSingleton)) { // prevent to change singletons
            this.ruleVisible = ruleVisible;
        }
        return this;
    }

    @Override
    public boolean getAdditionalCostsRuleVisible() {
        return ruleAdditionalCostsVisible;
    }

    @Override
    public void setAdditionalCostsRuleVisible(boolean ruleAdditionalCostsVisible) {
        this.ruleAdditionalCostsVisible = ruleAdditionalCostsVisible;
    }

    @Override
    public UUID getOriginalId() {
        return this.originalId;
    }

    @Override
    public Ability setAbilityWord(AbilityWord abilityWord) {
        this.abilityWord = abilityWord;
        return this;
    }

    @Override
    public Ability withFlavorWord(String flavorWord) {
        this.flavorWord = flavorWord;
        return this;
    }

    @Override
    public String addRulePrefix(String rule) {
        if (abilityWord != null) {
            return abilityWord.formatWord() + CardUtil.getTextWithFirstCharUpperCase(rule);
        } else if (flavorWord != null) {
            return CardUtil.italicizeWithEmDash(flavorWord) + CardUtil.getTextWithFirstCharUpperCase(rule);
        } else {
            return rule;
        }
    }

    @Override
    public Ability withFirstModeFlavorWord(String flavorWord) {
        this.modes.getMode().withFlavorWord(flavorWord);
        return this;
    }

    @Override
    public Ability withFirstModeCost(Cost cost) {
        this.modes.getMode().withCost(cost);
        return this;
    }

    @Override
    public String getGameLogMessage(Game game) {
        if (game.isSimulation()) {
            return "";
        }
        MageObject object = game.getObject(this.sourceId);
        if (object == null) { // e.g. sacrificed token
            logger.warn("Could get no object: " + this);
        }
        return " activates: " +
                (object != null ? this.formatRule(getModes().getText(), object.getLogName()) : getModes().getText()) +
                " from " +
                getMessageText(game);
    }

    protected String getMessageText(Game game) {
        StringBuilder sb = threadLocalBuilder.get();
        MageObject object = game.getObject(this.sourceId);
        if (object != null) {
            if (object instanceof StackAbility) {
                Card card = game.getCard(((StackAbility) object).getSourceId());
                if (card != null) {
                    sb.append(GameLog.getColoredObjectIdName(card));
                } else {
                    sb.append(GameLog.getColoredObjectIdName(object));
                }
            } else if (object instanceof Spell) {
                Spell spell = (Spell) object;
                String castText = spell.getSpellCastText(game);
                sb.append((castText.startsWith("Cast ") ? castText.substring(5) : castText));
                if (spell.getFromZone() == Zone.GRAVEYARD) {
                    sb.append(" from graveyard");
                }
                sb.append(getOptionalTextSuffix(game, spell));
            } else {
                sb.append(GameLog.getColoredObjectIdName(object));
            }
        } else {
            sb.append("unknown");
        }
        if (object instanceof Spell && ((Spell) object).getSpellAbilities().size() > 1) {
            if (((Spell) object).getSpellAbility().getSpellAbilityType() == SpellAbilityType.SPLIT_FUSED) {
                Spell spell = (Spell) object;
                int i = 0;
                for (SpellAbility spellAbility : spell.getSpellAbilities()) {
                    i++;
                    String half;
                    if (i == 1) {
                        half = " left";
                    } else {
                        half = " right";
                    }
                    if (!spellAbility.getTargets().isEmpty()) {
                        sb.append(half).append(" half targeting ");
                        for (Target target : spellAbility.getTargets()) {
                            sb.append(target.getTargetedName(game));
                        }
                    }
                }
            } else {
                Spell spell = (Spell) object;
                int i = 0;
                for (SpellAbility spellAbility : spell.getSpellAbilities()) {
                    i++;
                    if (i > 1) {
                        sb.append(" splicing ");
                        if (spellAbility.name.length() > 5 && spellAbility.name.startsWith("Cast ")) {
                            sb.append(spellAbility.name.substring(5));
                        } else {
                            sb.append(spellAbility.name);
                        }
                    }
                    sb.append(getTargetDescriptionForLog(spellAbility.getTargets(), game));
                }
            }
        } else if (object instanceof Spell && ((Spell) object).getSpellAbility().getModes().size() > 1) {
            Modes spellModes = ((Spell) object).getSpellAbility().getModes();
            for (UUID selectedModeId : spellModes.getSelectedModes()) {
                Mode selectedMode = spellModes.get(selectedModeId);
                int item = 0;
                for (Mode mode : spellModes.values()) {
                    item++;
                    if (mode.getId().equals(selectedMode.getId())) {
                        sb.append(" (mode ").append(item).append(')');
                        sb.append(getTargetDescriptionForLog(selectedMode.getTargets(), game));
                        break;
                    }
                }
            }
        } else {
            sb.append(getTargetDescriptionForLog(getTargets(), game));
        }
        return sb.toString();
    }

    @Override
    public String getTargetDescription(Targets targets, Game game) {
        return getTargetDescriptionForLog(targets, game);
    }

    protected String getTargetDescriptionForLog(Targets targets, Game game) {
        StringBuilder sb = new StringBuilder(); // threadLocal StringBuilder can't be used because calling method already uses it
        if (!targets.isEmpty()) {
            String usedVerb = null;
            boolean isFirstTarget = true;
            for (Target target : targets) {
                if (!target.getTargets().isEmpty()) {
                    String targetHintInfo = target.getChooseHint() == null ? "" : " (" + target.getChooseHint() + ")";
                    if (!target.isNotTarget()) {
                        if (usedVerb == null || usedVerb.equals(" choosing ")) {
                            usedVerb = " targeting ";
                            sb.append(usedVerb);
                        }
                    } else if (target.isNotTarget() && (usedVerb == null || usedVerb.equals(" targeting "))) {
                        usedVerb = " choosing ";
                        sb.append(usedVerb);
                    }

                    if (!isFirstTarget) {
                        sb.append(", ");
                    }
                    isFirstTarget = false;

                    sb.append(target.getTargetedName(game));
                    sb.append(targetHintInfo);
                }
            }
        }
        return sb.toString();
    }

    private String getOptionalTextSuffix(Game game, Spell spell) {
        StringBuilder sb = new StringBuilder();
        for (Ability ability : spell.getAbilities()) {
            if (ability instanceof OptionalAdditionalSourceCosts) {
                sb.append(((OptionalAdditionalSourceCosts) ability).getCastMessageSuffix());
            }
            if (ability instanceof AlternativeSourceCosts && ((AlternativeSourceCosts) ability).isActivated(this, game)) {
                sb.append(((AlternativeSourceCosts) ability).getCastMessageSuffix(game));
            }
        }
        return sb.toString();
    }

    @Override
    public boolean getWorksFaceDown() {
        return worksFaceDown;
    }

    @Override
    public void setWorksFaceDown(boolean worksFaceDown) {
        this.worksFaceDown = worksFaceDown;
    }

    @Override
    public boolean getWorksPhasedOut() {
        return worksPhasedOut;
    }

    @Override
    public void setWorksPhasedOut(boolean worksPhasedOut) {
        this.worksPhasedOut = worksPhasedOut;
    }

    @Override
    public MageObject getSourceObject(Game game) {
        return game.getObject(getSourceId());
    }

    @Override
    public MageObject getSourceObjectIfItStillExists(Game game) {
        if (getSourceObjectZoneChangeCounter() == 0
                || getSourceObjectZoneChangeCounter() == game.getState().getZoneChangeCounter(getSourceId())) {
            // exists or lki from battlefield
            return game.getObject(getSourceId());
        }
        return null;
    }

    @Override
    public Card getSourceCardIfItStillExists(Game game) {
        MageObject mageObject = getSourceObjectIfItStillExists(game);
        if (mageObject instanceof Card) {
            return (Card) mageObject;
        }
        return null;
    }

    @Override
    public Permanent getSourcePermanentIfItStillExists(Game game) {
        MageObject mageObject = getSourceObjectIfItStillExists(game);
        if (mageObject instanceof Permanent) {
            return (Permanent) mageObject;
        }
        return null;
    }

    @Override
    public Permanent getSourcePermanentOrLKI(Game game) {
        Permanent permanent = getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            permanent = (Permanent) game.getLastKnownInformation(getSourceId(), Zone.BATTLEFIELD, getSourceObjectZoneChangeCounter());
        }
        return permanent;
    }

    @Override
    public void setSourceObjectZoneChangeCounter(int sourceObjectZoneChangeCounter) {
        this.sourceObjectZoneChangeCounter = sourceObjectZoneChangeCounter;
    }

    @Override
    public int getSourceObjectZoneChangeCounter() {
        return sourceObjectZoneChangeCounter;
    }

    @Override
    public void setSourcePermanentTransformCount(Game game) {
        Permanent permanent = getSourcePermanentOrLKI(game);
        if (permanent != null) {
            this.sourcePermanentTransformCount = permanent.getTransformCount();
        }
    }

    @Override
    public boolean checkTransformCount(Permanent permanent, Game game) {
        if (permanent == null
                || !permanent.getId().equals(sourceId)
                || permanent.getZoneChangeCounter(game) != sourceObjectZoneChangeCounter) {
            return true;
        }
        return permanent.getTransformCount() == sourcePermanentTransformCount;
    }

    @Override
    public boolean canFizzle() {
        return canFizzle;
    }

    @Override
    public void setCanFizzle(boolean canFizzle) {
        this.canFizzle = canFizzle;
    }

    @Override
    public boolean canBeCopied() {
        return canBeCopied;
    }

    @Override
    public Ability withCanBeCopied(boolean canBeCopied) {
        this.canBeCopied = canBeCopied;
        return this;
    }

    @Override
    public AbilityImpl setTargetAdjuster(TargetAdjuster targetAdjuster) {
        if (targetAdjuster instanceof GenericTargetAdjuster && this.getTargets().isEmpty()) {
            throw new IllegalStateException("Target adjuster being added but no targets are set!");
        }
        this.targetAdjuster = targetAdjuster;
        this.targetAdjuster.addDefaultTargets(this);
        return this;
    }

    @Override
    public TargetAdjuster getTargetAdjuster() {
        return targetAdjuster;
    }

    @Override
    public void adjustTargets(Game game) {
        if (targetAdjuster != null) {
            targetAdjuster.adjustTargets(this, game);
        }
    }

    @Override
    public AbilityImpl setCostAdjuster(CostAdjuster costAdjuster) {
        this.costAdjuster = costAdjuster;
        return this;
    }

    @Override
    public CostAdjuster getCostAdjuster() {
        return costAdjuster;
    }

    @Override
    public void adjustX(Game game) {
        if (costAdjuster != null) {
            costAdjuster.prepareX(this, game);
        }
    }

    @Override
    public void adjustCostsPrepare(Game game) {
        if (costAdjuster != null) {
            costAdjuster.prepareCost(this, game);
        }
    }

    @Override
    public void adjustCostsModify(Game game, CostModificationType costModificationType) {
        if (costAdjuster != null) {
            costAdjuster.modifyCost(this, game, costModificationType);
        }
    }

    @Override
    public List<Hint> getHints() {
        return this.hints;
    }

    @Override
    public Ability addHint(Hint hint) {
        this.hints.add(hint);
        return this;
    }

    /**
     * sets the mode tag for the current mode.
     */
    @Override
    public void setModeTag(String tag) {
        if (getModes().getMode() != null) {
            getModes().getMode().setModeTag(tag);
        }
    }

    @Override
    public final List<CardIcon> getIcons() {
        return getIcons(null);
    }

    @Override
    public List<CardIcon> getIcons(Game game) {
        return this.icons;
    }

    @Override
    public Ability addIcon(CardIcon cardIcon) {
        this.icons.add(cardIcon);
        return this;
    }

    @Override
    public Ability addCustomOutcome(Outcome customOutcome) {
        this.customOutcome = customOutcome;
        return this;
    }

    @Override
    public Outcome getCustomOutcome() {
        return this.customOutcome;
    }

    @Override
    public boolean isSameInstance(Ability ability) {
        // same instance (by mtg rules) = same object, ID or class+text (you can't check class only cause it can be different by params/text)
        if (ability == null) {
            return false;
        }

        return (this == ability)
                || (this.getId().equals(ability.getId()))
                || (this.getOriginalId().equals(ability.getOriginalId()))
                || (this.getClass() == ability.getClass() && this.getRule(true).equals(ability.getRule(true)));
    }

    @Override
    public MageIdentifier getIdentifier() {
        return identifier;
    }

    @Override
    public AbilityImpl setIdentifier(MageIdentifier identifier) {
        this.identifier = identifier;
        return this;
    }

    /**
     * Needed for disabling auto-mana payments for things like Sunburst.
     *
     * @return true if the ability is impacted by the color of the mana used to pay for it.
     */
    public boolean caresAboutManaColor() {
        return this.getEffects().stream()
                .filter(Objects::nonNull)
                .map(Effect::getCondition)
                .filter(Objects::nonNull)
                .anyMatch(Condition::caresAboutManaColor);
    }

    public AbilityImpl copyWithZone(Zone zone) {
        if (this instanceof MageSingleton) {
            // not safe to change zone for singletons
            // in theory there could be some sort of wrapper to effectively change
            // the zone here, but currently no use of copyWithZone actually needs
            // to change the zone of any existing singleton abilities
            return this;
        }
        AbilityImpl copy = ((AbilityImpl) this.copy());
        copy.zone = zone;
        copy.newId();
        return copy;
    }
}
