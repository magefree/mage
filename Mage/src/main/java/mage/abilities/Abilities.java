
package mage.abilities;

import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.constants.Zone;
import mage.game.Game;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

/**
 * Represents a collection of {@link Ability Abilities}. This is the top most
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
    List<String> getRules(String source);

    List<String> getRules(String source, boolean capitalize);

    /**
     * Retrieves all activated abilities for the given {@link Zone}.
     *
     * @param zone The {@link Zone} for which abilities should be retrieved.
     * @return All abilities for the given {@link Zone}
     *
     * @see mage.cards.CardImpl#getSpellAbility()
     */
    Abilities<ActivatedAbility> getActivatedAbilities(Zone zone);

    /**
     * Retrieves all playable abilities for the given {@link Zone}.
     *
     * @param zone The {@link Zone} for which abilities should be retrieved.
     * @return All abilities for the given {@link Zone}
     *
     * @see mage.cards.CardImpl#getSpellAbility()
     */
    Abilities<ActivatedAbility> getPlayableAbilities(Zone zone);

    /**
     * Retrieves all {@link ActivatedManaAbilityImpl mana abilities} in the
     * given {@link Zone}.
     *
     * @param zone The {@link Zone} to search for
     * {@link ActivatedManaAbilityImpl mana abilities}.
     * @return All {@link ActivatedManaAbilityImpl mana abilities} for the given
     * {@link Zone}.
     *
     * @see mage.cards.CardImpl#getMana()
     * @see mage.players.PlayerImpl#getManaAvailable(mage.game.Game)
     * @see mage.players.PlayerImpl#getAvailableManaProducers(mage.game.Game)
     */
    Abilities<ActivatedManaAbilityImpl> getActivatedManaAbilities(Zone zone);


    /**
     * Retrieves all {@link ActivatedManaAbilityImpl mana abilities} in the
     * given {@link Zone} that can be used.
     *
     * @param zone The {@link Zone} to search for
     * {@link ActivatedManaAbilityImpl mana abilities}.
     * @param playerId The id of the player to check availability for
     * @return All {@link ActivatedManaAbilityImpl mana abilities} for the given
     * {@link Zone} that can be used.
     *
     * @see mage.cards.CardImpl#getMana()
     * @see mage.players.PlayerImpl#getManaAvailable(mage.game.Game)
     * @see mage.players.PlayerImpl#getAvailableManaProducers(mage.game.Game)
     */
    Abilities<ActivatedManaAbilityImpl> getAvailableActivatedManaAbilities(Zone zone, UUID playerId, Game game);

    /**
     * Retrieves all {@link StaticAbility static abilities} in the given
     * {@link Zone}.
     *
     * @param zone The {@link Zone} to search for {@link StaticAbility}
     * @return All {@link StaticAbility static abilities} in the given
     * {@link Zone}
     *
     * @see
     * mage.abilities.effects.ContinuousEffects#getLayeredEffects(mage.game.Game)
     * @see
     * mage.abilities.effects.ContinuousEffects#getApplicableRequirementEffects(mage.game.permanent.Permanent,
     * mage.game.Game)
     * @see
     * mage.abilities.effects.ContinuousEffects#getApplicableRestrictionEffects(mage.game.permanent.Permanent,
     * mage.game.Game)
     * @see
     * mage.abilities.effects.ContinuousEffects#getApplicableReplacementEffects(mage.game.events.GameEvent,
     * mage.game.Game)
     * @see mage.abilities.effects.ContinuousEffects#asThough(java.util.UUID,
     * mage.constants.AsThoughEffectType, mage.game.Game)
     * @see
     * mage.abilities.effects.ContinuousEffects#costModification(mage.abilities.Ability,
     * mage.game.Game)
     */
    Abilities<StaticAbility> getStaticAbilities(Zone zone);

    /**
     * Retrieves all {@link EvasionAbility evasion abilities}.
     *
     * @return The {@link EvasionAbility evasion abilities}.
     */
    Abilities<EvasionAbility> getEvasionAbilities();

    /**
     * Retrieves all {@link TriggeredAbility triggered abilities} for the given
     * {@link Zone}.
     *
     * @param zone The {@link Zone} to search for
     * {@link TriggeredAbility triggered abilities}
     * @return All found {@link TriggeredAbility triggered abilities}.
     *
     * @see mage.cards.CardImpl#checkTriggers(mage.constants.Zone,
     * mage.game.events.GameEvent, mage.game.Game)
     * @see
     * mage.game.permanent.PermanentImpl#checkTriggers(mage.game.events.GameEvent,
     * mage.game.Game)
     * @see
     * mage.game.permanent.PermanentCard#checkPermanentOnlyTriggers(mage.game.events.ZoneChangeEvent,
     * mage.game.Game)
     */
    Abilities<TriggeredAbility> getTriggeredAbilities(Zone zone);

    /**
     * Retrieves all {@link ProtectionAbility protection abilities}.
     *
     * @return All found {@link ProtectionAbility protection abilities}.
     *
     * @see mage.game.permanent.PermanentImpl#hasProtectionFrom(mage.MageObject)
     * @see mage.players.PlayerImpl#hasProtectionFrom(mage.MageObject)
     * @see mage.players.PlayerImpl#canDamage(mage.MageObject)
     */
    Abilities<ProtectionAbility> getProtectionAbilities();

    /**
     * TODO Method is unused, keep it around?
     *
     * The only implementation seems to want to use this for totally a set of
     * abilities by some arbitrary numeral value. Possibly a good method to be
     * used by the AI's?
     *
     * @return A numeral value representing the 'strength' or effectiveness of
     * the abilities?
     */
    int getOutcomeTotal();

    /**
     * Sets the controller of this set of abilities.
     *
     * @param controllerId
     *
     * @see mage.cards.CardImpl#setControllerId(java.util.UUID)
     * @see mage.cards.CardImpl#setOwnerId(java.util.UUID)
     * @see mage.game.permanent.PermanentImpl#changeControllerId(java.util.UUID,
     * mage.game.Game)
     * @see mage.game.permanent.PermanentCard#copyFromCard(mage.cards.Card)
     */
    void setControllerId(UUID controllerId);

    /**
     * Sets the source of this set of abilities.
     *
     * @param sourceId
     *
     * @see mage.cards.CardImpl#assignNewId()
     */
    void setSourceId(UUID sourceId);

    /**
     * Assigns a new {@link java.util.UUID}
     */
    void newId();

    /**
     * Assigns a new {@link java.util.UUID}
     */
    void newOriginalId();

    /**
     * Searches this set of abilities to see if the ability represented by the
     * abilityId is contained within. Can be used to find usages of singleton
     * abilities.
     *
     * @param abilityId
     * @return
     */
    boolean containsKey(UUID abilityId);

    /**
     * TODO Method is unused, keep it around?
     *
     * Gets the ability represented by the given abilityId.
     *
     * @param abilityId
     * @return
     */
    Optional<T> get(UUID abilityId);

    /**
     * TODO The usage of this method seems redundant to that of
     * {@link #containsKey(java.util.UUID)} minus the fact that it searches for
     * exact instances instead of id's of singleton Abilities.
     *
     * Searches for the exact instance of the passed in ability.
     *
     * @param ability
     * @return
     */
    boolean contains(T ability);

    /**
     * Searches an ability with the same rule text as the passed in ability.
     *
     * @param ability
     * @return
     */
    boolean containsRule(T ability);

    /**
     * Searches this set of abilities for the existence of each of the passed in
     * set of abilities.
     *
     * @param abilities
     * @return True if the passed in set of abilities is also in this set of
     * abilities.
     */
    boolean containsAll(Abilities<T> abilities);

    /**
     * Searches this set of abilities for the existence of the given class
     * Warning, it doesn't work with inherited classes (e.g. it's not equal to instanceOf command)
     *
     * @param classObject
     * @return True if the passed in class is also in this set of abilities.
     */
    boolean containsClass(Class classObject);

    /**
     * Returns true if one or more of the abilities are activated mana abilities with the pollDependant flag set to true.
     * @return 
     */
    boolean hasPoolDependantAbilities();
    
    /**
     * Copies this set of abilities. This copy should be new instances of all
     * the contained abilities.
     *
     * @return
     */
    Abilities<T> copy();

    String getValue();

    @Deprecated // use permanent.removeAbility instead
    boolean remove(Object o);

    @Deprecated // use permanent.removeAbility instead
    boolean removeAll(Collection<?> c);

    @Deprecated // use permanent.removeAbility instead
    boolean removeIf(Predicate<? super T> filter);
}
