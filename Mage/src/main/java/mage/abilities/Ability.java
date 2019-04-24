package mage.abilities;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.constants.AbilityType;
import mage.constants.AbilityWord;
import mage.constants.EffectType;
import mage.constants.Zone;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.Targets;
import mage.target.targetadjustment.TargetAdjuster;
import mage.watchers.Watcher;

/**
 * Practically everything in the game is started from an Ability. This interface
 * describes what an Ability is composed of at the highest level.
 */
public interface Ability extends Controllable, Serializable {

    /**
     * Gets the globally unique id of the ability contained within the game.
     *
     * @return A {@link java.util.UUID} which the game will use to store and
     * retrieve the exact instance of this ability.
     */
    @Override
    UUID getId();

    /**
     * Assigns a new {@link java.util.UUID}
     *
     * @see mage.players.PlayerImpl#playAbility(mage.abilities.ActivatedAbility,
     * mage.game.Game)
     * @see
     * mage.game.GameImpl#addTriggeredAbility(mage.abilities.TriggeredAbility)
     * @see
     * mage.game.GameImpl#addDelayedTriggeredAbility(mage.abilities.DelayedTriggeredAbility)
     */
    void newId();

    /**
     * Assigns a new {@link java.util.UUID}
     *
     * @see mage.players.PlayerImpl#playAbility(mage.abilities.ActivatedAbility,
     * mage.game.Game)
     * @see
     * mage.game.GameImpl#addTriggeredAbility(mage.abilities.TriggeredAbility)
     * @see
     * mage.game.GameImpl#addDelayedTriggeredAbility(mage.abilities.DelayedTriggeredAbility)
     */
    void newOriginalId();

    /**
     * Gets the {@link AbilityType} of this ability.
     *
     * @return The {@link AbilityType type} of this ability.
     */
    AbilityType getAbilityType();

    /**
     * Gets the id of the player in control of this ability.
     *
     * @return The {@link java.util.UUID} of the controlling player.
     */
    @Override
    UUID getControllerId();

    /**
     * Sets the id of the controller of this ability.
     *
     * @param controllerId The {@link java.util.UUID} of the controller.
     */
    void setControllerId(UUID controllerId);

    /**
     * Gets the id of the object which put this ability in motion.
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

    /**
     * Gets all the {@link ManaCosts} that must be paid before activating this
     * ability. These costs should be modified by any modification effects
     * currently present within the game state.
     *
     * @return All {@link ManaCosts} that must be paid.
     */
    ManaCosts<ManaCost> getManaCostsToPay();

    /**
     * Adds a {@link ManaCost} to this ability that must be paid before this
     * ability is activated.
     *
     * @param cost The {@link ManaCost} to add.
     */
    void addManaCost(ManaCost cost);

    /**
     * TODO Method is unused, keep it around?
     *
     * Gets all costs that are optional to this ability. These costs can be paid
     * in addition to other costs to have other effects put into place.
     *
     * @return All {@link Costs} that can be paid above and beyond other costs.
     */
    Costs<Cost> getOptionalCosts();

    /**
     * Adds a {@link Cost} that is optional to this ability.
     *
     * @param cost The {@link Cost} to add to the optional costs.
     */
    void addOptionalCost(Cost cost);

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
     * @param game
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
     * onto the stack.
     *
     * @return All {@link Targets} that must be satisfied before this ability is
     * put onto the stack.
     */
    Targets getTargets();

    /**
     * Retrieves the {@link Target} located at the 0th index in the
     * {@link Targets}. A call to the method is equivalent to
     * {@link #getTargets()}.get(0).getFirstTarget().
     *
     * @return The {@link java.util.UUID} of the first target within the targets
     * list.
     *
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
     *
     * @return
     */
    Zone getZone();

    /**
     * Retrieves whether or not this abilities activation will use the stack.
     *
     * @return
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
     * @return
     */
    String getRule(boolean all);

    /**
     * Retrieves the rule associated with the given source.
     *
     * @param source
     * @return
     */
    String getRule(String source);

    /**
     * Activates this ability prompting the controller to pay any mandatory
     *
     * @param game A reference the {@link Game} for which this ability should be
     * activated within.
     * @param noMana Whether or not {@link ManaCosts} have to be paid.
     * @return True if this ability was successfully activated.
     *
     * @see mage.players.PlayerImpl#cast(mage.abilities.SpellAbility,
     * mage.game.Game, boolean)
     * @see mage.players.PlayerImpl#playAbility(mage.abilities.ActivatedAbility,
     * mage.game.Game)
     * @see
     * mage.players.PlayerImpl#triggerAbility(mage.abilities.TriggeredAbility,
     * mage.game.Game)
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
     *
     * @see
     * mage.players.PlayerImpl#playManaAbility(mage.abilities.mana.ManaAbility,
     * mage.game.Game)
     * @see mage.players.PlayerImpl#specialAction(mage.abilities.SpecialAction,
     * mage.game.Game)
     */
    boolean resolve(Game game);

    /**
     * Used to reset the state of this ability.
     *
     * @param game
     */
    void reset(Game game);

    /**
     * Overridden by triggered abilities with intervening if clauses - rule
     * 20110715 - 603.4
     *
     * @param game
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

    boolean canChooseTarget(Game game);

    /**
     * Gets the list of sub-abilities associated with this ability.
     *
     * @return
     */
    List<Ability> getSubAbilities();

    /**
     * Adds a sub-ability to this ability.
     *
     * @param ability The {@link Ability} to add.
     */
    void addSubAbility(Ability ability);

    List<Watcher> getWatchers();

    void addWatcher(Watcher watcher);

    /**
     * Returns true if this abilities source is in the zone for the ability
     *
     * @param game
     * @param source
     * @param event
     * @return
     */
    boolean isInUseableZone(Game game, MageObject source, GameEvent event);

    /**
     * Returns true if the source object has currently the ability (e.g. The
     * object can have lost all or some abilities for some time (e.g. Turn to
     * Frog)
     *
     * @param game
     * @param source
     * @param event
     * @return
     */
    boolean hasSourceObjectAbility(Game game, MageObject source, GameEvent event);

    /**
     * Returns true if this ability has to be shown as topmost of all the rules
     * of the object
     *
     * @return
     */
    boolean getRuleAtTheTop();

    /**
     * Sets the value for the ruleAtTheTop attribute
     *
     * true = show the rule at the top position of the rules
     *
     * @param ruleAtTheTop
     * @return
     */
    Ability setRuleAtTheTop(boolean ruleAtTheTop);

    /**
     * Returns true if this ability has to work also with face down object (set
     * to not visible normally).
     *
     * @return
     */
    boolean getWorksFaceDown();

    /**
     * Sets the value for the worksFaceDown flag
     *
     * true = the ability works also if the object is face down
     *
     * @param worksFaceDown
     */
    void setWorksFaceDown(boolean worksFaceDown);

    /**
     * Returns true if this ability's rule is visible on the card tooltip
     *
     * @return
     */
    boolean getRuleVisible();

    /**
     * Sets the value for the ruleVisible attribute
     *
     * true = rule will be shown for the card / permanent false = rule won't be
     * shown
     *
     * @param ruleVisible
     */
    void setRuleVisible(boolean ruleVisible);

    /**
     * Returns true if the additional costs of the abilitiy should be visible on
     * the tooltip text
     *
     * @return
     */
    boolean getAdditionalCostsRuleVisible();

    /**
     * Sets the value for the additional costs rule attribute
     *
     * true = rule will be shown for the card / permanent false = rule won't be
     * shown
     *
     * @param ruleAdditionalCostsVisible
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
     *
     * --- Not usable yet for rule text generation of triggered abilities ---
     *
     * @param abilityWord
     */
    void setAbilityWord(AbilityWord abilityWord);

    /**
     * Creates the message about the ability casting/triggering/activating to
     * post in the game log before the ability resolves.
     *
     * @param game
     * @return
     */
    String getGameLogMessage(Game game);

    /**
     * Used to deactivate cost modification logic of ability activation for some
     * special handling (e.g. FlashbackAbility gets cost modifiaction twice
     * because of how it's handled now)
     *
     * @param active execute no cost modification
     */
    void setCostModificationActive(boolean active);

    boolean activateAlternateOrAdditionalCosts(MageObject sourceObject, boolean noMana, Player controller, Game game);

    /**
     * Sets the object that actually existed while a ability triggerd or an
     * ability was activated.
     *
     * @param mageObject
     * @param game
     */
    void setSourceObject(MageObject mageObject, Game game);

    /**
     * Returns the object that actually existed while a ability triggerd or an
     * ability was activated. If not set yet, the current object will be
     * retrieved from the game.
     *
     * @param game
     * @return
     */
    MageObject getSourceObject(Game game);

    int getSourceObjectZoneChangeCounter();

    /**
     * Returns the object that actually existed while a ability triggerd or an
     * ability was activated only if it has not changed zone meanwhile. If not
     * set yet, the current object will be retrieved from the game.
     *
     * @param game
     * @return
     */
    MageObject getSourceObjectIfItStillExists(Game game);

    /**
     * Returns the permanent that actually existed while the ability triggerd or
     * an ability was activated only if it has not changed zone meanwhile. If
     * not set yet, the current permanent if one exists will be retrieved from
     * the game and returned.
     *
     * @param game
     * @return
     */
    Permanent getSourcePermanentIfItStillExists(Game game);

    String getTargetDescription(Targets targets, Game game);

    void setCanFizzle(boolean canFizzle);

    boolean canFizzle();

    void setTargetAdjuster(TargetAdjuster targetAdjuster);

    TargetAdjuster getTargetAdjuster();

    void adjustTargets(Game game);
}
