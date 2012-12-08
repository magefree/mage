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

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import mage.Constants.Zone;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.mana.ManaAbility;
import mage.game.Game;

/**
 * Represents a collection of {@link Ability Abilities}.  This is the top most
 * interface for this.
 * 
 * @param <T> The ability type this collection will hold.
 * 
 * @see mage.abilities.AbilitiesImpl
 * @see mage.abilities.DelayedTriggeredAbilities
 * @see mage.abilities.SpecialActions
 * @see mage.abilities.TriggeredAbilities
 */
public interface Abilities<T extends Ability> extends List<T>, Serializable {

    /**
     * Retrieves a {@link List}&lt;{@link String}&gt; of ability texts for the
     * given source.
     * 
     * @param source The source to retrieve ability texts.
     * @return the {@link List}&lt;{@link String}&gt; of ability texts.
     * 
     * @see mage.cards.CardImpl#getRules()
     * @see mage.abilities.keyword.LevelAbility#getRule()
     */
    public List<String> getRules(String source);

    /**
     * Retrieves all activated abilities for the given {@link Zone}.
     * 
     * @param zone The {@link Zone} for which abilities should be retrieved.
     * @return All abilities for the given {@link Zone}
     * 
     * @see mage.cards.CardImpl#getSpellAbility()
     */
    public Abilities<ActivatedAbility> getActivatedAbilities(Zone zone);

    /**
     * Retrieves all {@link ManaAbility mana abilities} in the given {@link Zone}.
     * 
     * @param zone The {@link Zone} to search for {@link ManaAbility mana abilities}.
     * @return All {@link ManaAbility mana abilities} for the given {@link Zone}.
     * 
     * @see mage.cards.CardImpl#getMana()
     * @see mage.players.PlayerImpl#getManaAvailable(mage.game.Game)
     * @see mage.players.PlayerImpl#getAvailableManaProducers(mage.game.Game)
     */
    public Abilities<ManaAbility> getManaAbilities(Zone zone);

    /**
     * Retrieves all {@link ManaAbility mana abilities} in the given {@link Zone} that can be used.
     * 
     * @param zone The {@link Zone} to search for {@link ManaAbility mana abilities}.
     * @return All {@link ManaAbility mana abilities} for the given {@link Zone} that can be used.
     * 
     * @see mage.cards.CardImpl#getMana()
     * @see mage.players.PlayerImpl#getManaAvailable(mage.game.Game)
     * @see mage.players.PlayerImpl#getAvailableManaProducers(mage.game.Game)
     */
    public Abilities<ManaAbility> getAvailableManaAbilities(Zone zone, Game game);

    /**
     * Retrieves all {@link StaticAbility static abilities} in the given {@link Zone}.
     * 
     * @param zone The {@link Zone} to search for {@link StaticAbility}
     * @return All {@link StaticAbility static abilities} in the given {@link Zone}
     * 
     * @see mage.abilities.effects.ContinuousEffects#getLayeredEffects(mage.game.Game)
     * @see mage.abilities.effects.ContinuousEffects#getApplicableRequirementEffects(mage.game.permanent.Permanent, mage.game.Game)
     * @see mage.abilities.effects.ContinuousEffects#getApplicableRestrictionEffects(mage.game.permanent.Permanent, mage.game.Game)
     * @see mage.abilities.effects.ContinuousEffects#getApplicableReplacementEffects(mage.game.events.GameEvent, mage.game.Game)
     * @see mage.abilities.effects.ContinuousEffects#asThough(java.util.UUID, mage.Constants.AsThoughEffectType, mage.game.Game)
     * @see mage.abilities.effects.ContinuousEffects#costModification(mage.abilities.Ability, mage.game.Game)
     */
    public Abilities<StaticAbility> getStaticAbilities(Zone zone);

    /**
     * Retrieves all {@link EvasionAbility evasion abilities}.
     * 
     * @return The {@link EvasionAbility evasion abilities}.
     */
    public Abilities<EvasionAbility> getEvasionAbilities();

    /**
     * Retrieves all {@link TriggeredAbility triggered abilities} for the given
     * {@link Zone}.
     * 
     * @param zone The {@link Zone} to search for {@link TriggeredAbility triggered abilities}
     * @return All found {@link TriggeredAbility triggered abilities}.
     * 
     * @see mage.cards.CardImpl#checkTriggers(mage.Constants.Zone, mage.game.events.GameEvent, mage.game.Game)
     * @see mage.game.permanent.PermanentImpl#checkTriggers(mage.game.events.GameEvent, mage.game.Game)
     * @see mage.game.permanent.PermanentCard#checkPermanentOnlyTriggers(mage.game.events.ZoneChangeEvent, mage.game.Game)
     */
    public Abilities<TriggeredAbility> getTriggeredAbilities(Zone zone);

    /**
     * Retrieves all {@link ProtectionAbility protection abilities}.
     * 
     * @return All found {@link ProtectionAbility protection abilities}.
     * 
     * @see mage.game.permanent.PermanentImpl#hasProtectionFrom(mage.MageObject)
     * @see mage.players.PlayerImpl#hasProtectionFrom(mage.MageObject)
     * @see mage.players.PlayerImpl#canDamage(mage.MageObject)
     */
    public Abilities<ProtectionAbility> getProtectionAbilities();

    /**
     * TODO Method is unused, keep it around?
     * 
     * The only implementation seems to want to use this for totally a set of
     * abilities by some arbitrary numeral value.  Possibly a good method to be
     * used by the AI's?
     * 
     * @return A numeral value representing the 'strength' or effectiveness of the abilities?
     */
    public int getOutcomeTotal();

    /**
     * Sets the controller of this set of abilities.
     * 
     * @param controllerId
     * 
     * @see mage.cards.CardImpl#setControllerId(java.util.UUID)
     * @see mage.cards.CardImpl#setOwnerId(java.util.UUID)
     * @see mage.game.permanent.PermanentImpl#changeControllerId(java.util.UUID, mage.game.Game)
     * @see mage.game.permanent.PermanentCard#copyFromCard(mage.cards.Card)
     */
    public void setControllerId(UUID controllerId);

    /**
     * Sets the source of this set of abilities.
     * 
     * @param sourceId
     * 
     * @see mage.cards.CardImpl#assignNewId()
     */
    public void setSourceId(UUID sourceId);

    /**
     * Assigns a new {@link java.util.UUID}
     */
    public void newId();

    /**
     * Assigns a new {@link java.util.UUID}
     */
    public void newOriginalId();

    /**
     * Searches this set of abilities to see if the ability represented by the abilityId
     * is contained within.  Can be used to find usages of singleton abilities.
     * 
     * @param abilityId
     * @return 
     */
    public boolean containsKey(UUID abilityId);

    /**
     * TODO Method is unused, keep it around?
     * 
     * Gets the ability represented by the given abilityId.
     * 
     * @param abilityId
     * @return 
     */
    public T get(UUID abilityId);

    /**
     * TODO The usage of this method seems redundant to that of {@link #containsKey(java.util.UUID)}
     * minus the fact that it searches for exact instances instead of id's of
     * singleton Abilities.
     * 
     * Searches for the exact instance of the passed in ability.
     * 
     * @param ability
     * @return 
     */
    public boolean contains(T ability);

    /**
     * Searches this set of abilities for the existence of each of the passed in
     * set of abilities.
     * 
     * @param abilities
     * @return True if the passed in set of abilities is also in this set of abilities.
     */
    public boolean containsAll(Abilities<T> abilities);

    /**
     * Copies this set of abilities.  This copy should be new instances of all
     * the contained abilities.
     * 
     * @return 
     */
    public Abilities<T> copy();

    public String getValue();
}
