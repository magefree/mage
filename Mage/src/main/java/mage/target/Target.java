package mage.target;

import mage.MageObject;
import mage.abilities.Ability;
import mage.cards.Cards;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.game.Game;
import mage.game.permanent.Permanent;
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
    @Deprecated
    // TODO: replace with UUID abilityControllerId, Ability source, Game game
    boolean isChosen(Game game);

    /**
     * Checking target complete and nothing to choose (X=0, all selected, all possible selected, etc)
     *
     * @param fromCards can be null for non cards selection
     */
    boolean isChoiceCompleted(UUID abilityControllerId, Ability source, Game game, Cards fromCards);

    /**
     * Tests and AI related for "up to" targets (mark target that it was skipped on selection, so new choose dialog will be called)
     * Example: AI sim possible target options
     */
    boolean isSkipChoice();

    void setSkipChoice(boolean isSkipChoice);

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

    /**
     * Checks if there are enough targets the player can choose from among them
     * or if they are autochosen since there are fewer than the minimum number.
     * <p>
     * Implement as return canChooseFromPossibleTargets(sourceControllerId, source, game);
     * TODO: remove after all canChoose replaced with default
     *
     * @param sourceControllerId - controller of the target event source
     * @param source             - can be null
     * @param game
     * @return - true if enough valid choices exist
     */
    boolean canChoose(UUID sourceControllerId, Ability source, Game game);

    /**
     * Make sure target can be fully selected or already selected, e.g. by AI sims
     * <p>
     * Do not override
     */
    default boolean canChooseOrAlreadyChosen(UUID sourceControllerId, Ability source, Game game) {
        return this.isChosen(game) || this.canChoose(sourceControllerId, source, game);
    }

    default boolean canChooseFromPossibleTargets(UUID sourceControllerId, Ability source, Game game) {
        // TODO: replace all canChoose override methods by that code call
        if (getMinNumberOfTargets() == 0) {
            return true;
        }

        int selectedCount = getSize();
        int moreSelectCount = possibleTargets(sourceControllerId, source, game).size();

        if (selectedCount >= getMaxNumberOfTargets()) {
            return false;
        }

        return moreSelectCount > 0 && selectedCount + moreSelectCount >= getMinNumberOfTargets();
    }

    /**
     * Returns a set of all possible targets that match the criteria of the implemented Target class.
     * WARNING, it must filter already selected targets by keepValidPossibleTargets call at the end
     *
     * @param source - can be null
     */
    Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game);

    default Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game, Set<UUID> cards) {
        // do not override
        return possibleTargets(sourceControllerId, source, game).stream()
                .filter(id -> cards == null || cards.contains(id))
                .collect(Collectors.toSet());
    }

    /**
     * Keep only valid and not selected targets - must be used inside any possibleTargets implementation
     */
    default Set<UUID> keepValidPossibleTargets(Set<UUID> possibleTargets, UUID sourceControllerId, Ability source, Game game) {
        // TODO: check target amount in human dialogs - is it allow to select it again
        // do not override
        // keep only valid and not selected targets list
        return possibleTargets.stream()
                .filter(this::notContains)
                .filter(targetId -> {
                    // non-target allow any
                    if (source == null || source.getSourceId() == null || isNotTarget()) {
                        return true;
                    }
                    MageObject sourceObject = game.getObject(source);
                    if (sourceObject == null) {
                        return true;
                    }

                    // target allow non-protected
                    Player targetPlayer = game.getPlayer(targetId);
                    if (targetPlayer != null) {
                        return !targetPlayer.hasLeft()
                                && canTarget(sourceControllerId, targetId, source, game)
                                && targetPlayer.canBeTargetedBy(sourceObject, sourceControllerId, source, game);
                    }
                    Permanent targetPermanent = game.getPermanent(targetId);
                    if (targetPermanent != null) {
                        return canTarget(sourceControllerId, targetId, source, game)
                                && targetPermanent.canBeTargetedBy(sourceObject, sourceControllerId, source, game);
                    }
                    return true;
                })
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

    default boolean canChoose(UUID sourceControllerId, Game game) {
        return canChoose(sourceControllerId, null, game);
    }

    @Deprecated
        // TODO: need replace to source only version?
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

    default boolean notContains(UUID targetId) {
        // for better usage in streams
        return !contains(targetId);
    }

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
