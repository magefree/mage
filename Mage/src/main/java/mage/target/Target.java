package mage.target;

import mage.abilities.Ability;
import mage.cards.Cards;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.game.Game;
import mage.players.Player;
import mage.util.Copyable;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author BetaSteward_at_googlemail.com
 */
public interface Target extends Copyable<Target>, Serializable {

    /**
     * All targets selected by a player
     * <p>
     * Warning, for "up to" targets it will return true all the time, so make sure your dialog
     * use do-while logic and call "choose" one time min or use isChoiceCompleted
     */
    @Deprecated // TODO: replace with UUID abilityControllerId, Ability source, Game game
    boolean isChosen(Game game);

    boolean isChoiceCompleted(UUID abilityControllerId, Ability source, Game game);

    void clearChosen();

    boolean isChoiceSelected();

    boolean isNotTarget();

    /**
     * Mark it as non target (e.g. card's rules do not contain a "target" word)
     * <p>
     * Non targeted abilities are unaffected by protection/hexproof and other target related effects
     * Non targeted spells can't be fizzled on resolve with invalid targets
     * Non targeted spells chooses targets on resolve, targeted spells chooses targets on activate
     * All costs must be non targeted
     *
     * @param notTarget
     * @return
     */
    Target withNotTarget(boolean notTarget);

    // methods for targets
    boolean canChoose(UUID sourceControllerId, Ability source, Game game);

    /**
     * Returns a set of all possible targets that match the criteria of the implemented Target class.
     *
     * @param sourceControllerId UUID of the ability's controller
     * @param source             Ability which requires the targets
     * @param game               Current game
     * @return Set of the UUIDs of possible targets
     */
    Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game);

    default Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game, Set<UUID> cards) {
        // do not override
        return possibleTargets(sourceControllerId, source, game).stream()
                .filter(id -> cards == null || cards.contains(id))
                .collect(Collectors.toSet());
    }

    /**
     * Priority method to make a choice from cards and other places, not a player.chooseXXX
     */
    boolean chooseTarget(Outcome outcome, UUID playerId, Ability source, Game game);

    /**
     * Add target from targeting methods like chooseTarget (will check and generate target events and effects)
     */
    void addTarget(UUID id, Ability source, Game game);

    void addTarget(UUID id, int amount, Ability source, Game game);

    void addTarget(UUID id, Ability source, Game game, boolean skipEvent);

    void addTarget(UUID id, int amount, Ability source, Game game, boolean skipEvent);

    boolean canTarget(UUID id, Game game);

    /**
     * @param id
     * @param source WARNING, it can be null for AI or other calls from events
     * @param game
     * @return
     */
    boolean canTarget(UUID id, Ability source, Game game);

    boolean stillLegalTarget(UUID playerId, UUID id, Ability source, Game game);

    boolean canTarget(UUID playerId, UUID id, Ability source, Game game);

    boolean isLegal(Ability source, Game game);

    /**
     * AI related code. Returns all possible different target combinations
     */
    List<? extends Target> getTargetOptions(Ability source, Game game);

    boolean canChoose(UUID sourceControllerId, Game game);

    Set<UUID> possibleTargets(UUID sourceControllerId, Game game);

    @Deprecated // TODO: need replace to source only version?
    boolean choose(Outcome outcome, UUID playerId, UUID sourceId, Ability source, Game game);

    /**
     * Priority method to make a choice from cards and other places, not a player.chooseXXX
     */
    default boolean choose(Outcome outcome, UUID playerId, Ability source, Game game) {
        return choose(outcome, playerId, source == null ? null : source.getSourceId(), source, game);
    }

    /**
     * Add target from non targeting methods like choose
     * TODO: need usage research, looks like there are wrong usage of addTarget, e.g. in choose method (must be add)
     */
    void add(UUID id, Game game);

    void remove(UUID targetId);

    void updateTarget(UUID targetId, Game game);

    /**
     * @return full description with target name, amount, etc (uses in abilities/rules/cost)
     */
    String getDescription();

    /**
     * @return message displayed on choosing targets (can be dynamically changed on more target selected)
     */
    String getMessage(Game game);

    /**
     * @return single target name
     */
    String getTargetName();

    /**
     * Overwrites the name automatically generated from the filter text.
     * If you want to add additional info for usability, use `withChooseHint` instead.
     */
    Target withTargetName(String name);

    String getTargetedName(Game game);

    Zone getZone();

    int getTargetAmount(UUID targetId);

    int getMinNumberOfTargets();

    int getMaxNumberOfTargets();

    void setMinNumberOfTargets(int minNumberofTargets);

    void setMaxNumberOfTargets(int maxNumberofTargets);

    List<UUID> getTargets();

    Filter getFilter();

    boolean isRequired();

    boolean isRequired(UUID sourceId, Game game);

    boolean isRequired(Ability ability);

    void setRequired(boolean required);

    boolean isRequiredExplicitlySet();

    boolean isRandom();

    /**
     * WARNING, if you need random choice then call it by target's choose method, not player's choose
     * see https://github.com/magefree/mage/issues/11933
     */
    void setRandom(boolean atRandom);

    UUID getFirstTarget();

    @Override
    Target copy();

    // some targets are chosen from players that are not the controller of the ability (e.g. Pandemonium)
    // TODO: research usage of setTargetController and setAbilityController - target adjusters must set it both, example: Necrotic Plague
    //   replace by shared method like setAbilityAndTargetControllers()
    @Deprecated
    void setTargetController(UUID playerId);

    UUID getTargetController();

    // TODO: research usage of setTargetController and setAbilityController - target adjusters must set it both, example: Necrotic Plague
    //   replace by shared method like setAbilityAndTargetControllers()
    @Deprecated
    void setAbilityController(UUID playerId);

    UUID getAbilityController();

    UUID getAffectedAbilityControllerId(UUID choosingPlayerId);

    Player getTargetController(Game game, UUID playerId);

    int getTargetTag();

    Target setTargetTag(int tag);

    Target getOriginalTarget();

    // used for cards like Spellskite
    void setTargetAmount(UUID targetId, int amount, Game game);

    /**
     * Adds a clarification during target selection (in parentheses).
     * Useful for abilities that have multiple targets and different effects.
     */
    Target withChooseHint(String chooseHint);

    String getChooseHint();

    void setEventReporting(boolean shouldReport);

    int getSize();

    boolean contains(UUID targetId);

    /**
     * This function tries to auto-choose the next target.
     * <p>
     * It will NOT add it to the list of targets, it will ony choose the next target
     * <p>
     * Use this version when the targets is selected from targets.getTargets.
     * <p>
     * It will auto-choosen if all of the following criteria are met:
     * - The minimum and maximum number of targets is the same (i.e. effect does not have "up to" in its name)
     * - The number of valid targets is equal to the number of targets still left to be specified
     *
     * @param abilityControllerId
     * @param source
     * @param game
     * @return The UUID of the chosen option, or null if one could not be chosen
     */
    UUID tryToAutoChoose(UUID abilityControllerId, Ability source, Game game);

    /**
     * Use this version when the target is chosen from a specified collection.
     * E.g. {@link Player#chooseTarget(Outcome outcome, Cards cards, TargetCard target, Ability source, Game game)}
     *
     * @param abilityControllerId
     * @param source
     * @param game
     * @param possibleTargets
     * @return
     */
    UUID tryToAutoChoose(UUID abilityControllerId, Ability source, Game game, Collection<UUID> possibleTargets);
}
