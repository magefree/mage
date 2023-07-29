package mage.abilities;

import mage.MageIdentifier;
import mage.MageObject;
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
import mage.constants.*;
import mage.game.Game;
import mage.game.command.Dungeon;
import mage.game.command.Emblem;
import mage.game.command.Plane;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackAbility;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.Targets;
import mage.target.common.TargetCardInLibrary;
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
    protected UUID originalId; // TODO: delete originalId???
    protected AbilityType abilityType;
    protected UUID controllerId;
    protected UUID sourceId;
    protected ManaCosts<ManaCost> manaCosts;
    protected ManaCosts<ManaCost> manaCostsToPay;
    protected Costs<Cost> costs;
    protected Modes modes; // access to it by GetModes only (it can be overridden by some abilities)
    protected Zone zone;
    protected String name;
    protected AbilityWord abilityWord;
    protected String flavorWord;
    protected boolean usesStack = true;
    protected boolean ruleAtTheTop = false;
    protected boolean ruleVisible = true;
    protected boolean ruleAdditionalCostsVisible = true;
    protected boolean activated = false;
    protected boolean worksFaceDown = false;
    protected int sourceObjectZoneChangeCounter;
    protected List<Watcher> watchers = new ArrayList<>(); // access to it by GetWatchers only (it can be overridden by some abilities)
    protected List<Ability> subAbilities = null;
    protected boolean canFizzle = true;
    protected TargetAdjuster targetAdjuster = null;
    protected CostAdjuster costAdjuster = null;
    protected List<Hint> hints = new ArrayList<>();
    protected List<CardIcon> icons = new ArrayList<>();
    protected Outcome customOutcome = null; // uses for AI decisions instead effects
    protected MageIdentifier identifier; // used to identify specific ability (e.g. to match with corresponding watcher)
    protected String appendToRule = null;
    protected int sourcePermanentTransformCount = 0;

    public AbilityImpl(AbilityType abilityType, Zone zone) {
        this.id = UUID.randomUUID();
        this.originalId = id;
        this.abilityType = abilityType;
        this.zone = zone;
        this.manaCosts = new ManaCostsImpl<>();
        this.manaCostsToPay = new ManaCostsImpl<>();
        this.costs = new CostsImpl<>();
        this.modes = new Modes();
    }

    public AbilityImpl(final AbilityImpl ability) {
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
        for (Watcher watcher : ability.getWatchers()) {
            watchers.add(watcher.copy());
        }

        if (ability.subAbilities != null) {
            this.subAbilities = new ArrayList<>();
            for (Ability subAbility : ability.subAbilities) {
                subAbilities.add(subAbility.copy());
            }
        }
        this.modes = ability.getModes().copy();
        this.ruleAtTheTop = ability.ruleAtTheTop;
        this.ruleVisible = ability.ruleVisible;
        this.ruleAdditionalCostsVisible = ability.ruleAdditionalCostsVisible;
        this.worksFaceDown = ability.worksFaceDown;
        this.abilityWord = ability.abilityWord;
        this.flavorWord = ability.flavorWord;
        this.sourceObjectZoneChangeCounter = ability.sourceObjectZoneChangeCounter;
        this.canFizzle = ability.canFizzle;
        this.targetAdjuster = ability.targetAdjuster;
        this.costAdjuster = ability.costAdjuster;
        for (Hint hint : ability.getHints()) {
            this.hints.add(hint.copy());
        }
        for (CardIcon icon : ability.getIcons()) {
            this.icons.add(icon.copy());
        }
        this.customOutcome = ability.customOutcome;
        this.identifier = ability.identifier;
        this.activated = ability.activated;
        this.appendToRule = ability.appendToRule;
        this.sourcePermanentTransformCount = ability.sourcePermanentTransformCount;
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
    public boolean resolve(Game game) {
        boolean result = true;
        //20100716 - 117.12
        if (checkIfClause(game)) {
            // Ability has started resolving. Fire event.
            // Used for abilities counting the number of resolutions like Ashling the Pilgrim.
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
                    if (this.getAbilityType() != AbilityType.MANA) {
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
            game.getState().processAction(game);
        }
        return result;
    }

    @Override
    public boolean activate(Game game, boolean noMana) {
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
                int xValue = this.getManaCostsToPay().getX();
                this.getManaCostsToPay().clear();
                VariableManaCost xCosts = new VariableManaCost(VariableCostType.ADDITIONAL);
                // no x events - rules from Unbound Flourishing:
                // - Spells with additional costs that include X won't be affected by Unbound Flourishing. X must be in the spell's mana cost.
                xCosts.setAmount(xValue, xValue, false);
                this.getManaCostsToPay().add(xCosts);
            } else {
                this.getManaCostsToPay().clear();
            }
        }

        // fused or spliced spells contain multiple abilities (e.g. fused, left, right)
        // optional costs and cost modification must be applied only to the first/main ability
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
        if (isMainPartAbility && !activateAlternateOrAdditionalCosts(sourceObject, noMana, controller, game)) {
            if (getAbilityType() == AbilityType.SPELL
                    && ((SpellAbility) this).getSpellAbilityType() == SpellAbilityType.FACE_DOWN_CREATURE) {
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

        // 20121001 - 601.2b
        // If the spell has a variable cost that will be paid as it's being cast (such as an {X} in
        // its mana cost; see rule 107.3), the player announces the value of that variable.
        VariableManaCost variableManaCost = handleManaXCosts(game, noMana, controller);
        String announceString = handleOtherXCosts(game, controller);

        handlePhyrexianManaCosts(game, controller);

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

            if (this.getAbilityType() != AbilityType.TRIGGERED) { // triggered abilities check this already in playerImpl.triggerAbility
                adjustTargets(game);
            }

            if (!getTargets().isEmpty()) {
                Outcome outcome = getEffects().getOutcome(this);

                // only activated abilities can be canceled by human user (not triggered)
                boolean canCancel = this instanceof ActivatedAbility && controller.isHuman();
                if (!getTargets().chooseTargets(outcome, this.controllerId, this, noMana, game, canCancel)) {
                    // was canceled during targer selection
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
        if (this instanceof ActivatedManaAbilityImpl && !costs.pay(this, game, this, controllerId, noMana, null)) {
            logger.debug("activate mana ability failed - non mana costs");
            return false;
        }

        //20101001 - 601.2e
        if (isMainPartAbility) {
            adjustCosts(game); // still needed for CostAdjuster objects (to handle some types of dynamic costs)
            game.getContinuousEffects().costModification(this, game);
        }

        UUID activatorId = controllerId;
        if ((this instanceof ActivatedAbilityImpl) && ((ActivatedAbilityImpl) this).getActivatorId() != null) {
            activatorId = ((ActivatedAbilityImpl) this).getActivatorId();
        }

        //20100716 - 601.2f  (noMana is not used here, because mana costs were cleared for this ability before adding additional costs and applying cost modification effects)
        if (!manaCostsToPay.pay(this, game, this, activatorId, false, null)) {
            return false; // cancel during mana payment
        }

        //20100716 - 601.2g
        if (!costs.pay(this, game, this, activatorId, noMana, null)) {
            logger.debug("activate failed - non mana costs");
            return false;
        }
        // inform about x costs now, so canceled announcements are not shown in the log
        if ((announceString != null) && (!announceString.equals(""))) {
            game.informPlayers(announceString);
        }
        if (variableManaCost != null) {
            int xValue = getManaCostsToPay().getX();
            game.informPlayers(controller.getLogName() + " announces a value of " + xValue + " for " + variableManaCost.getText());
        }
        activated = true;
        return true;
    }

    @Override
    public boolean isActivated() {
        return activated;
    }

    @Override
    public boolean activateAlternateOrAdditionalCosts(MageObject sourceObject, boolean noMana, Player controller, Game game) {
        boolean canUseAlternativeCost = true;
        boolean canUseAdditionalCost = true;

        if (this instanceof SpellAbility) {
            // A player can't apply two alternative methods of casting or two alternative costs to a single spell.
            switch (((SpellAbility) this).getSpellAbilityCastMode()) {

                case FLASHBACK:
                case MADNESS:
                case TRANSFORMED:
                case DISTURB:
                case MORE_THAN_MEETS_THE_EYE:
                    // from Snapcaster Mage:
                    // If you cast a spell from a graveyard using its flashback ability, you can't pay other alternative costs
                    // (such as that of Foil). (2018-12-07)
                    canUseAlternativeCost = false;
                    // You may pay any optional additional costs the spell has, such as kicker costs. You must pay any
                    // mandatory additional costs the spell has, such as that of Tormenting Voice. (2018-12-07)
                    canUseAdditionalCost = true;
                    break;
                case NORMAL:
                default:
                    canUseAlternativeCost = true;
                    canUseAdditionalCost = true;
                    break;
            }
        }

        boolean alternativeCostUsed = false;
        if (sourceObject != null && !(sourceObject instanceof Permanent)) {
            // it's important to apply alternative cost first
            // example: Omniscience gives free mana as alternative, but Entwine ability adds {2} as additional
            Abilities<Ability> abilities = CardUtil.getAbilities(sourceObject, game);

            // 1. ALTERNATIVE COSTS
            for (Ability ability : abilities) {
                // if cast for noMana no Alternative costs are allowed
                if (canUseAlternativeCost && !noMana && ability instanceof AlternativeSourceCosts) {
                    AlternativeSourceCosts alternativeSpellCosts = (AlternativeSourceCosts) ability;
                    if (alternativeSpellCosts.isAvailable(this, game)) {
                        if (alternativeSpellCosts.askToActivateAlternativeCosts(this, game)) {
                            // only one alternative costs may be activated
                            alternativeCostUsed = true;
                            break;
                        }
                    }
                }
            }
            // controller specific alternate spell costs
            if (canUseAlternativeCost && !noMana && !alternativeCostUsed) {
                if (this.getAbilityType() == AbilityType.SPELL
                        // 117.9a Only one alternative cost can be applied to any one spell as it's being cast.
                        // So an alternate spell ability can't be paid with Omniscience
                        && ((SpellAbility) this).getSpellAbilityType() != SpellAbilityType.BASE_ALTERNATE) {
                    for (AlternativeSourceCosts alternativeSourceCosts : controller.getAlternativeSourceCosts()) {
                        if (alternativeSourceCosts.isAvailable(this, game)) {
                            if (alternativeSourceCosts.askToActivateAlternativeCosts(this, game)) {
                                // only one alternative costs may be activated
                                alternativeCostUsed = true;
                                break;
                            }
                        }
                    }
                }
            }

            // 2. ADDITIONAL COST
            for (Ability ability : abilities) {
                if (canUseAdditionalCost && ability instanceof OptionalAdditionalSourceCosts) {
                    ((OptionalAdditionalSourceCosts) ability).addOptionalAdditionalCosts(this, game);
                }
            }
        }

        return alternativeCostUsed;
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
        for (VariableCost variableCost : this.costs.getVariableCosts()) {
            if (!(variableCost instanceof VariableManaCost) && !((Cost) variableCost).isPaid()) {
                int xValue = variableCost.announceXValue(this, game);
                Cost fixedCost = variableCost.getFixedCostsFromAnnouncedValue(xValue);
                if (fixedCost != null) {
                    costs.add(fixedCost);
                }
                // set the xcosts to paid
                // no x events - rules from Unbound Flourishing:
                // - Spells with additional costs that include X won't be affected by Unbound Flourishing. X must be in the spell's mana cost.
                variableCost.setAmount(xValue, xValue, false);
                ((Cost) variableCost).setPaid();
                String message = controller.getLogName() + " announces a value of " + xValue + " (" + variableCost.getActionText() + ')';
                announceString.append(message);
            }
        }
        return announceString.toString();
    }

    /**
     * 601.2b If a cost that will be paid as the spell is being cast includes
     * Phyrexian mana symbols, the player announces whether they intend to pay 2
     * life or the corresponding colored mana cost for each of those symbols.
     */
    private void handlePhyrexianManaCosts(Game game, Player controller) {
        Iterator<ManaCost> costIterator = manaCostsToPay.iterator();
        while (costIterator.hasNext()) {
            ManaCost cost = costIterator.next();

            if (!cost.isPhyrexian()) {
                continue;
            }
            PayLifeCost payLifeCost = new PayLifeCost(2);
            if (payLifeCost.canPay(this, this, controller.getId(), game)
                    && controller.chooseUse(Outcome.LoseLife, "Pay 2 life instead of " + cost.getText().replace("/P", "") + '?', this, game)) {
                costIterator.remove();
                costs.add(payLifeCost);
                manaCostsToPay.incrPhyrexianPaid();
            }
        }
    }

    public int handleManaXMultiplier(Game game, int value) {
        // some spells can change X value without new pays (Unbound Flourishing doubles X)
        GameEvent xEvent = GameEvent.getEvent(GameEvent.EventType.X_MANA_ANNOUNCE, this.getId(), this, getControllerId(), value);
        game.replaceEvent(xEvent, this);
        return xEvent.getAmount();
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
        for (ManaCost cost : manaCostsToPay) {
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
                int xValueMultiplier = handleManaXMultiplier(game, 1);
                if (!noMana || variableManaCost.getCostType().canUseAnnounceOnFreeCast()) {
                    xValue = controller.announceXMana(variableManaCost.getMinX(), variableManaCost.getMaxX(), xValueMultiplier,
                            "Announce the value for " + variableManaCost.getText(), game, this);
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
                    manaCostsToPay.add(new ManaCostsImpl<>(manaString.toString()));
                    manaCostsToPay.setX(xValue * xValueMultiplier, amountMana);
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
            if (!manaCosts.isEmpty()) {
                sbRule.append(manaCosts.getText());
            }
            if (!costs.isEmpty()) {
                if (sbRule.length() > 0) {
                    sbRule.append(", ");
                }
                sbRule.append(costs.getText());
            }
            if (sbRule.length() > 0) {
                sbRule.append(": ");
            }
        }

        String ruleStart = sbRule.toString();
        String text = getModes().getText();
        String rule;
        if (!text.isEmpty()) {
            if (ruleStart.length() > 1) {
                String end = ruleStart.substring(ruleStart.length() - 2).trim();
                if (end.isEmpty() || end.equals(":") || end.equals(".")) {
                    rule = ruleStart + CardUtil.getTextWithFirstCharUpperCase(text);
                } else {
                    rule = ruleStart + text;
                }
            } else {
                rule = ruleStart + text;
            }
        } else {
            rule = ruleStart;
        }
        String prefix;
        if (this instanceof TriggeredAbility || this instanceof EntersBattlefieldAbility) {
            prefix = null;
        } else if (abilityWord != null) {
            prefix = abilityWord.formatWord();
        } else if (flavorWord != null) {
            prefix = CardUtil.italicizeWithEmDash(flavorWord);
        } else {
            prefix = null;
        }
        if (prefix != null) {
            rule = prefix + CardUtil.getTextWithFirstCharUpperCase(rule);
        }
        if (appendToRule != null) {
            rule = rule.concat(appendToRule);
        }
        return rule;
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
                this.addManaCost((ManaCost) cost);
            } else {
                this.costs.add(cost);
            }
        }
    }

    @Override
    public void addManaCost(ManaCost cost) {
        if (cost != null) {
            this.manaCosts.add(cost);
            this.manaCostsToPay.add(cost);
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
        return new Targets();
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
        return res;
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
                if (modes.isEachModeMoreThanOnce()) {
                    return true;
                }
                if (found >= modes.getMinModes()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param game
     * @param source
     * @return
     */
    @Override
    public boolean isInUseableZone(Game game, MageObject source, GameEvent event) {
        if (!this.hasSourceObjectAbility(game, source, event)) {
            return false;
        }
        if (zone == Zone.COMMAND) {
            if (this.getSourceId() == null) { // commander effects
                return true;
            }
            MageObject object = game.getObject(this.getSourceId());
            // emblem/planes are always actual
            if (object instanceof Emblem || object instanceof Dungeon || object instanceof Plane) {
                return true;
            }
        }

        UUID parameterSourceId;
        // for singleton abilities like Flying we can't rely on abilities' source because it's only once in continuous effects
        // so will use the sourceId of the object itself that came as a parameter if it is not null
        if (this instanceof MageSingleton && source != null) {
            parameterSourceId = source.getId();
        } else {
            parameterSourceId = getSourceId();
        }
        // check against shortLKI for effects that move multiple object at the same time (e.g. destroy all)
        if (game.getShortLivingLKI(getSourceId(), getZone())) {
            return true;
        }
        // check against current state
        Zone test = game.getState().getZone(parameterSourceId);
        return zone.match(test);
    }

    @Override
    public boolean hasSourceObjectAbility(Game game, MageObject source, GameEvent event) {
        // if source object have this ability
        // uses for ability.isInUseableZone
        // replacement and other continues effects can be without source, but active (must return true)

        MageObject object = source;
        // for singleton abilities like Flying we can't rely on abilities' source because it's only once in continuous effects
        // so will use the sourceId of the object itself that came as a parameter if it is not null
        if (object == null) {
            object = game.getPermanentEntering(getSourceId());
            if (object == null) {
                object = game.getObject(getSourceId());
            }
        }
        if (object != null) {
            if (object instanceof Permanent) {
                return object.hasAbility(this, game) && ((Permanent) object).isPhasedIn();
            } else {
                // cards and other objects
                return object.hasAbility(this, game);
            }
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
        this.ruleAtTheTop = ruleAtTheTop;
        return this;
    }

    @Override
    public boolean getRuleVisible() {
        return ruleVisible;
    }

    @Override
    public void setRuleVisible(boolean ruleVisible) {
        if (!(this instanceof MageSingleton)) { // prevent to change singletons
            this.ruleVisible = ruleVisible;
        }
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
    public Ability withFirstModeFlavorWord(String flavorWord) {
        this.modes.getMode().withFlavorWord(flavorWord);
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
        return new StringBuilder(" activates: ")
                .append(object != null ? this.formatRule(getModes().getText(), object.getLogName()) : getModes().getText())
                .append(" from ")
                .append(getMessageText(game)).toString();
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
    public AbilityImpl setTargetAdjuster(TargetAdjuster targetAdjuster) {
        this.targetAdjuster = targetAdjuster;
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

    /**
     * Dynamic cost modification for ability.<br>
     * Example: if it need stack related info (like real targets) then must
     * check two states (game.inCheckPlayableState): <br>
     * 1. In playable state it must check all possible use cases (e.g. allow to
     * reduce on any available target and modes) <br>
     * 2. In real cast state it must check current use case (e.g. real selected
     * targets and modes)
     *
     * @param costAdjuster
     */
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
    public void adjustCosts(Game game) {
        if (costAdjuster != null) {
            costAdjuster.adjustCosts(this, game);
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
}
