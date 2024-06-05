package mage.abilities;

import mage.MageIdentifier;
import mage.MageObject;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.Costs;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.hint.Hint;
import mage.abilities.icon.CardIcon;
import mage.cards.Card;
import mage.constants.*;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.Targets;
import mage.target.targetadjustment.TargetAdjuster;
import mage.watchers.Watcher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Practically everything in the game is started from an Ability. This interface
 * describes what an Ability is composed of at the highest level.
 */
public interface Ability extends Controllable, Serializable {

    /**
     * Assigns a new {@link java.util.UUID}
     */
    void newId();

    /**
     * Assigns a new {@link java.util.UUID}
     */
    void newOriginalId(); // TODO: delete newOriginalId???

    /**
     * Gets the {@link AbilityType} of this ability.
     *
     * @return The {@link AbilityType type} of this ability.
     */
    AbilityType getAbilityType();

    /**
     * If this ability is an activated one (mana included).
     */
    boolean isActivatedAbility();

    /**
     * If this ability is a triggered one (mana included).
     */
    boolean isTriggeredAbility();

    /**
     * If this ability is an activated one, excluding mana.
     */
    boolean isNonManaActivatedAbility();

    /**
     * If this ability is a mana activated one.
     */
    boolean isManaActivatedAbility();

    /**
     * If this ability is a mana ability, (both triggered and activated can be mana abilities).
     */
    boolean isManaAbility();

    /**
     * Sets the id of the controller of this ability.
     *
     * @param controllerId The {@link java.util.UUID} of the controller.
     */
    void setControllerId(UUID controllerId);

    /**
     * Gets the id of the object which put this ability in motion.
     * <p>
     * WARNING, MageSingleton abilities contains dirty data here, so you can't use sourceId with it
     *
     * @return The {@link java.util.UUID} of the object this ability is
     * associated with.
     */
    UUID getSourceId();

    /**
     * Sets the id of the object which this ability orignates from.
     *
     * @param sourceID {@link java.util.UUID} the source id to set.
     */
    void setSourceId(UUID sourceID);

    default void clearCosts() {
        getCosts().clear();
    }

    default void clearManaCosts() {
        getManaCosts().clear();
    }

    default void clearManaCostsToPay() {
        getManaCostsToPay().clear();
    }

    /**
     * Gets all {@link Costs} associated with this ability.
     *
     * @return All {@link Costs} associated with this ability.
     */
    Costs<Cost> getCosts();

    /**
     * Adds a {@link Cost} to this ability that must be paid before this ability
     * is activated.
     *
     * @param cost The {@link Cost} to add.
     */
    void addCost(Cost cost);

    /**
     * Gets all {@link ManaCosts} associated with this ability. These returned
     * costs should never be modified as they represent the base costs before
     * any modifications.
     *
     * @return All {@link ManaCosts} that must be paid.
     */
    ManaCosts<ManaCost> getManaCosts();

    default List<String> getManaCostSymbols() {
        List<String> symbols = new ArrayList<>();
        for (ManaCost cost : getManaCosts()) {
            symbols.add(cost.getText());
        }
        return symbols;
    }

    /**
     * Gets all the {@link ManaCosts} that must be paid before activating this
     * ability. These costs should be modified by any modification effects
     * currently present within the game state.
     *
     * @return All {@link ManaCosts} that must be paid.
     */
    ManaCosts<ManaCost> getManaCostsToPay();

    void addManaCostsToPay(ManaCost manaCost);

    /**
     * Gets a map of the cost tags (set while casting/activating) of this ability, can be null if no tags have been set yet.
     * Does NOT return the source permanent's tags.
     * You should not be using this function in implementation of cards,
     * this is a backing data structure used for internal storage.
     * Use CardUtil {@link mage.util.CardUtil#getSourceCostsTag getSourceCostsTag} or {@link mage.util.CardUtil#checkSourceCostsTagExists checkSourceCostsTagExists} instead
     *
     * @return The map of tags and corresponding objects
     */
    Map<String, Object> getCostsTagMap();

    /**
     * Set tag for this ability to the value, initializes this ability's tags map if needed.
     * Should only be used from an {@link ActivatedAbility} (including {@link SpellAbility})
     */
    void setCostsTag(String tag, Object value);

    /**
     * Retrieves the effects that are put into the place by the resolution of
     * this ability.
     *
     * @return All {@link Effects} that will be put into place by the resolution
     * of this ability.
     */
    Effects getEffects();

    /**
     * Retrieves all effects of an ability. That means effects from all modes
     * event those modes that are not seleced (yet).
     *
     * @return All {@link Effects} of this ability.
     */
    Effects getAllEffects();

    /**
     * Retrieves the effects of the specified {@link EffectType type} that are
     * put into place by the resolution of this ability.
     *
     * @param effectType The {@link EffectType type} to search for.
     * @return All {@link Effects} of the given {@link EffectType}.
     */
    Effects getEffects(Game game, EffectType effectType);

    /**
     * Adds an effect to this ability.
     *
     * @param effect The {@link Effect} to add.
     */
    void addEffect(Effect effect);

    /**
     * Retrieves all targets that must be satisfied before this ability is put
     * onto the stack. Warning, return targets from first/current mode only.
     *
     * @return All {@link Targets} that must be satisfied before this ability is
     * put onto the stack.
     */
    Targets getTargets();

    /**
     * Retrieves all selected targets, read only. Multi-modes return different targets.
     * Works on stack only (after real cast/activate)
     */
    Targets getAllSelectedTargets();

    /**
     * Retrieves the {@link Target} located at the 0th index in the
     * {@link Targets}. A call to the method is equivalent to
     * {@link #getTargets()}.get(0).getFirstTarget().
     * <p>
     * Warning, if you effect uses target pointers then it must search getTargetPointer too
     *
     * @return The {@link java.util.UUID} of the first target within the targets
     * list.
     * @see mage.target.Target
     */
    UUID getFirstTarget();

    /**
     * Adds a target to this ability that must be satisfied before this ability
     * is put onto the stack.
     *
     * @param target The {@link Target} to add.
     */
    void addTarget(Target target);

    /**
     * Retrieves the {@link Zone} that this ability is active within.
     */
    Zone getZone();

    /**
     * Retrieves whether or not this abilities activation will use the stack.
     */
    boolean isUsesStack();

    /**
     * Retrieves a human readable string representing what the ability states it
     * accomplishes. This call is equivalent to
     * {@link #getRule(boolean) getRule(false)}
     *
     * @return A human readable string representing what the ability states it
     * accomplishes
     */
    String getRule();

    /**
     * Retrieves a human readable string including any costs associated with
     * this ability if the all parameter is true, and just the abilities rule
     * text if the all parameter is false.
     *
     * @param all True if costs are desired in the output, false otherwise.
     */
    String getRule(boolean all);

    /**
     * Retrieves the rule associated with the given source.
     */
    String getRule(String source);

    /**
     * Activates this ability prompting the controller to pay any mandatory
     *
     * @param game   A reference the {@link Game} for which this ability should be
     *               activated within.
     * @param noMana Whether or not {@link ManaCosts} have to be paid.
     * @return True if this ability was successfully activated.
     */
    boolean activate(Game game, boolean noMana);

    boolean isActivated();

    /**
     * Resolves this ability and puts any effects it produces into play. This
     * method should only be called if the
     * {@link #activate(mage.game.Game, boolean)} method returned true.
     *
     * @param game The {@link Game} for which this ability resolves within.
     * @return Whether or not this ability successfully resolved.
     */
    boolean resolve(Game game);

    /**
     * Used to reset the state of this ability.
     */
    void reset(Game game);

    /**
     * Overridden by triggered abilities with intervening if clauses - rule
     * 20110715 - 603.4
     *
     * @return Whether or not the intervening if clause is satisfied
     */
    boolean checkIfClause(Game game);

    /**
     * Creates a fresh copy of this ability.
     *
     * @return A new copy of this ability.
     */
    Ability copy();

    boolean isModal();

    void addMode(Mode mode);

    Modes getModes();

    boolean canChooseTarget(Game game, UUID playerId);

    /**
     * Gets the list of sub-abilities associated with this ability.
     * When copying, subabilities are copied separately and thus the list is desynced.
     * Do not interact with the subabilities list during a game!
     */
    List<Ability> getSubAbilities();

    /**
     * Adds a sub-ability to this ability.
     *
     * @param ability The {@link Ability} to add.
     */
    void addSubAbility(Ability ability);

    List<Watcher> getWatchers();

    /**
     * Add watcher blueprint (real watcher will be created on card/ability init)
     */
    void addWatcher(Watcher watcher);

    /**
     * Returns true if this abilities source is in the zone for the ability
     */
    boolean isInUseableZone(Game game, MageObject source, GameEvent event);

    /**
     * Returns true if the source object has currently the ability (e.g. The
     * object can have lost all or some abilities for some time (e.g. Turn to
     * Frog)
     */
    boolean hasSourceObjectAbility(Game game, MageObject source, GameEvent event);

    /**
     * Returns true if the ability has a tap itself in their costs
     */
    default boolean hasTapCost() {
        for (Cost cost : this.getCosts()) {
            if (cost instanceof TapSourceCost) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if this ability has to be shown as topmost of all the rules
     * of the object
     */
    boolean getRuleAtTheTop();

    /**
     * Sets the value for the ruleAtTheTop attribute
     * <p>
     * true = show the rule at the top position of the rules
     */
    Ability setRuleAtTheTop(boolean ruleAtTheTop);

    /**
     * Returns true if this ability has to work also with face down object (set
     * to not visible normally).
     */
    boolean getWorksFaceDown();

    /**
     * Sets the value for the worksFaceDown flag
     * <p>
     * true = the ability works also if the object is face down
     */
    void setWorksFaceDown(boolean worksFaceDown);

    /**
     * Returns true if this ability has to work also with phased out object.
     */
    boolean getWorksPhasedOut();

    /**
     * Sets the value for the worksPhasedOut flag
     * <p>
     * true = the ability works also if the object is phased out
     */
    void setWorksPhasedOut(boolean worksPhasedOut);

    /**
     * Returns true if this ability's rule is visible on the card tooltip
     */
    boolean getRuleVisible();

    /**
     * Sets the value for the ruleVisible attribute
     * <p>
     * true = rule will be shown for the card / permanent false = rule won't be shown
     */
    Ability setRuleVisible(boolean ruleVisible);

    /**
     * Returns true if the additional costs of the ability should be visible on
     * the tooltip text
     */
    boolean getAdditionalCostsRuleVisible();

    /**
     * Sets the value for the additional costs rule attribute
     * <p>
     * true = rule will be shown for the card / permanent false = rule won't be shown
     */
    void setAdditionalCostsRuleVisible(boolean ruleAdditionalCostsVisible);

    /**
     * Get the originalId of the ability
     *
     * @return originalId
     */
    UUID getOriginalId();

    /**
     * Sets the ability word for the given ability. An ability word is a word
     * that, in essence, groups, and reminds players of, cards that have a
     * common functionality and does not imply any particular rules.
     * <p>
     * --- Not usable yet for rule text generation of triggered abilities ---
     */
    Ability setAbilityWord(AbilityWord abilityWord);

    /**
     * Sets flavor word for whole ability
     */
    Ability withFlavorWord(String flavorWord);

    /**
     * Sets flavor word for first mode
     */
    Ability withFirstModeFlavorWord(String flavorWord);

    /**
     * Sets cost word for first mode
     */
    Ability withFirstModeCost(Cost cost);

    /**
     * Creates the message about the ability casting/triggering/activating to
     * post in the game log before the ability resolves.
     */
    String getGameLogMessage(Game game);

    boolean activateAlternateOrAdditionalCosts(MageObject sourceObject, boolean noMana, Player controller, Game game);

    /**
     * Finds the source object regardless of its zcc. Can be LKI from battlefield in some cases.
     */
    MageObject getSourceObject(Game game);

    void setSourceObjectZoneChangeCounter(int zoneChangeCounter);

    int getSourceObjectZoneChangeCounter();

    /**
     * Finds the source object (Permanent, StackObject, Card, etc.) as long as its zcc has not changed, otherwise null
     * - for not activated ability - returns exists object
     * - for activated ability - returns exists object or LKI (if it triggers from non battlefield, e.g. sacrifice cost);
     */
    MageObject getSourceObjectIfItStillExists(Game game);

    /**
     * Finds the source object as long as it is a Card (can also be Permanent, Spell, etc.) and its zcc has not changed
     */
    Card getSourceCardIfItStillExists(Game game);

    /**
     * Finds the source object as long as it is a Permanent and its zcc has not changed
     */
    Permanent getSourcePermanentIfItStillExists(Game game);

    /**
     * Returns source permanent info (actual if it exists, otherwise from LKI)
     */
    Permanent getSourcePermanentOrLKI(Game game);

    void setSourcePermanentTransformCount(Game game);

    boolean checkTransformCount(Permanent permanent, Game game);

    String getTargetDescription(Targets targets, Game game);

    void setCanFizzle(boolean canFizzle);

    boolean canFizzle();

    Ability setTargetAdjuster(TargetAdjuster targetAdjuster);

    TargetAdjuster getTargetAdjuster();

    void adjustTargets(Game game);

    Ability setCostAdjuster(CostAdjuster costAdjuster);

    CostAdjuster getCostAdjuster();

    void adjustCosts(Game game);

    List<Hint> getHints();

    Ability addHint(Hint hint);

    /**
     * Tag the current mode to be retrieved elsewhere thanks to the tag.
     */
    void setModeTag(String tag);

    /**
     * For abilities with static icons
     */
    List<CardIcon> getIcons();

    /**
     * For abilities with dynamic icons
     *
     * @param game can be null for static calls like copies
     */
    List<CardIcon> getIcons(Game game);

    Ability addIcon(CardIcon cardIcon);

    Ability addCustomOutcome(Outcome customOutcome);

    Outcome getCustomOutcome();

    /**
     * For mtg's instances search, see rules example in 112.10b
     */
    boolean isSameInstance(Ability ability);

    MageIdentifier getIdentifier();

    AbilityImpl setIdentifier(MageIdentifier mageIdentifier);
}
