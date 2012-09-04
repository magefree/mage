/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.abilities;

import mage.Constants.AbilityType;
import mage.Constants.EffectType;
import mage.Constants.Zone;
import mage.abilities.costs.AlternativeCost;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.choices.Choice;
import mage.choices.Choices;
import mage.game.Controllable;
import mage.game.Game;
import mage.target.Target;
import mage.target.Targets;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Practically everything in the game is started from an Ability.  This
 * interface describes what an Ability is composed of at the highest level.
 */
public interface Ability extends Controllable, Serializable {

    /**
     * Gets the globally unique id of the ability contained within the game.
     * 
     * @return A {@link java.util.UUID} which the game will use to store and retrieve
     * the exact instance of this ability.
     */
    public UUID getId();

    /**
     * Assigns a new {@link java.util.UUID}
     * 
     * @see mage.players.PlayerImpl#playAbility(mage.abilities.ActivatedAbility, mage.game.Game)
     * @see mage.game.GameImpl#addTriggeredAbility(mage.abilities.TriggeredAbility)
     * @see mage.game.GameImpl#addDelayedTriggeredAbility(mage.abilities.DelayedTriggeredAbility)
     */
    public void newId();

    /**
     * Assigns a new {@link java.util.UUID}
     * 
     * @see mage.players.PlayerImpl#playAbility(mage.abilities.ActivatedAbility, mage.game.Game)
     * @see mage.game.GameImpl#addTriggeredAbility(mage.abilities.TriggeredAbility)
     * @see mage.game.GameImpl#addDelayedTriggeredAbility(mage.abilities.DelayedTriggeredAbility)
     */
    public void newOriginalId();

    /**
     * Gets the {@link AbilityType} of this ability.
     * 
     * @return The {@link AbilityType type} of this ability.
     */
    public AbilityType getAbilityType();

    /**
     * Gets the id of the player in control of this ability.
     * 
     * @return The {@link java.util.UUID} of the controlling player.
     */
    @Override
    public UUID getControllerId();

    /**
     * Sets the id of the controller of this ability.
     * 
     * @param controllerId The {@link java.util.UUID} of the controller.
     */
    public void setControllerId(UUID controllerId);

    /**
     * Gets the id of the object which put this ability in motion.
     * 
     * @return The {@link java.util.UUID} of the object this ability is associated with.
     */
    public UUID getSourceId();

    /**
     * Sets the id of the object which this ability orignates from.
     * 
     * @param sourceID {@link java.util.UUID} the source id to set.
     */
    public void setSourceId(UUID sourceID);

    /**
     * Gets all {@link Costs} associated with this ability.
     * 
     * @return All {@link Costs} associated with this ability.
     */
    public Costs<Cost> getCosts();

    /**
     * Adds a {@link Cost} to this ability that must be paid before this ability
     * is activated.
     * 
     * @param cost The {@link Cost} to add.
     */
    public void addCost(Cost cost);

    /**
     * Gets all {@link ManaCosts} associated with this ability.  These returned
     * costs should never be modified as they represent the base costs before
     * any modifications.
     * 
     * @return All {@link ManaCosts} that must be paid.
     */
    public ManaCosts<ManaCost> getManaCosts();

    /**
     * Gets all the {@link ManaCosts} that must be paid before activating this
     * ability.  These costs should be modified by any modification effects currently
     * present within the game state.
     * 
     * @return All {@link ManaCosts} that must be paid.
     */
    public ManaCosts<ManaCost> getManaCostsToPay();

    /**
     * Adds a {@link ManaCost} to this ability that must be paid before this
     * ability is activated.
     * 
     * @param cost The {@link ManaCost} to add.
     */
    public void addManaCost(ManaCost cost);

    /**
     * Gets all {@link AlternativeCost} associated with this ability.
     * 
     * @return All {@link AlternativeCost}'s that can be paid instead of the {@link ManaCosts}
     */
    public List<AlternativeCost> getAlternativeCosts();

    /**
     * Adds an {@link AlternativeCost} this ability that may be paid instead of
     * any other cost.
     * 
     * @param cost The {@link AlternativeCost} to add.
     */
    public void addAlternativeCost(AlternativeCost cost);

    /**
     * TODO Method is unused, keep it around?
     * 
     * Gets all costs that are optional to this ability.  These costs can be paid
     * in addition to other costs to have other effects put into place.
     * 
     * @return All {@link Costs} that can be paid above and beyond other costs.
     */
    public Costs<Cost> getOptionalCosts();

    /**
     * Adds a {@link Cost} that is optional to this ability.
     * 
     * @param cost The {@link Cost} to add to the optional costs.
     */
    public void addOptionalCost(Cost cost);

    /**
     * Retrieves the effects that are put into the place by the resolution of this
     * ability.
     * 
     * @return All {@link Effects} that will be put into place by the resolution
     * of this ability.
     */
    public Effects getEffects();

    /**
     * Retrieves the effects of the specified {@link EffectType type} that are
     * put into place by the resolution of this ability.
     *
     * @param game
     * @param effectType The {@link EffectType type} to search for.
     * @return All {@link Effects} of the given {@link EffectType}.
     */
    public Effects getEffects(Game game, EffectType effectType);

    /**
     * Adds an effect to this ability.
     * 
     * @param effect The {@link Effect} to add.
     */
    public void addEffect(Effect effect);

    /**
     * Retrieves all targets that must be satisfied before this ability is
     * put onto the stack.
     * 
     * @return All {@link Targets} that must be satisfied before this ability is put onto
     * the stack.
     */
    public Targets getTargets();

    /**
     * Retrieves the {@link Target} located at the 0th index in the {@link Targets}.
     * A call to the method is equivalent to {@link #getTargets()}.get(0).getFirstTarget().
     * 
     * @return The {@link java.util.UUID} of the first target within the targets list.
     * 
     * @see mage.target.Target
     */
    public UUID getFirstTarget();

    /**
     * Adds a target to this ability that must be satisfied before this ability
     * is put onto the stack.
     * 
     * @param target The {@link Target} to add.
     */
    public void addTarget(Target target);

    /**
     * Choices
     * 
     * @return 
     */
    public Choices getChoices();

    /**
     * TODO: Javadoc me
     * 
     * @param choice 
     */
    public void addChoice(Choice choice);

    /**
     * Retrieves the {@link Zone} that this ability is active within.
     * 
     * @return 
     */
    public Zone getZone();

    /**
     * Retrieves whether or not this abilities activation will use the stack.
     * 
     * @return 
     */
    public boolean isUsesStack();

    /**
     * Retrieves a human readable string representing what the ability states it
     * accomplishes.  This call is equivalent to {@link #getRule(boolean) getRule(false)}
     * 
     * @return A human readable string representing what the ability states it
     * accomplishes
     */
    public String getRule();

    /**
     * Retrieves a human readable string including any costs associated with this
     * ability if the all parameter is true, and just the abilities rule text if
     * the all parameter is false.
     * 
     * @param all True if costs are desired in the output, false otherwise.
     * @return
     */
    public String getRule(boolean all);

    /**
     * Retrieves the rule associated with the given source.
     * 
     * @param source
     * @return 
     */
    public String getRule(String source);

    /**
     * Activates this ability prompting the controller to pay any mandatory
     * {@link Costs} or {@link AlternativeCost} associated with this ability.
     * 
     * @param game A reference the {@link Game} for which this ability should be activated within.
     * @param noMana Whether or not {@link ManaCosts} have to be paid.
     * @return True if this ability was successfully activated.
     * 
     * @see mage.players.PlayerImpl#cast(mage.abilities.SpellAbility, mage.game.Game, boolean)
     * @see mage.players.PlayerImpl#playAbility(mage.abilities.ActivatedAbility, mage.game.Game)
     * @see mage.players.PlayerImpl#triggerAbility(mage.abilities.TriggeredAbility, mage.game.Game)
     */
    public boolean activate(Game game, boolean noMana);

    /**
     * Resolves this ability and puts any effects it produces into play.  This
     * method should only be called if the {@link #activate(mage.game.Game, boolean)}
     * method returned true.
     * 
     * @param game The {@link Game} for which this ability resolves within.
     * @return Whether or not this ability successfully resolved.
     * 
     * @see mage.players.PlayerImpl#playManaAbility(mage.abilities.mana.ManaAbility, mage.game.Game)
     * @see mage.players.PlayerImpl#specialAction(mage.abilities.SpecialAction, mage.game.Game)
     */
    public boolean resolve(Game game);

    /**
     * Used to reset the state of this ability.
     * 
     * @param game 
     */
    public void reset(Game game);

    /**
     * Overridden by triggered abilities with intervening if clauses - rule 20110715 - 603.4
     * 
     * @param game
     * @return Whether or not the intervening if clause is satisfied
     */
    public boolean checkIfClause(Game game);

    /**
     * Creates a fresh copy of this ability.
     * 
     * @return A new copy of this ability.
     */
    public Ability copy();

    public boolean isModal();

    public void addMode(Mode mode);

    public Modes getModes();

    public boolean canChooseTarget(Game game);

    /**
     * Returns true if this abilities source is in the zone for the ability
     * 
     * @param game
     * @param checkLKI
     * @return 
     */
    public boolean isInUseableZone(Game game, boolean checkLKI);

}
