package mage.target;

import mage.abilities.Ability;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.game.Game;
import mage.players.Player;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public interface Target extends Serializable {

    boolean isChosen();

    boolean doneChoosing();

    void clearChosen();

    boolean isNotTarget();

    /**
     * controls if it will be checked, if the target can be targeted from source
     *
     * @param notTarget true = do not check for protection, false = check for
     *                  protection
     */
    void setNotTarget(boolean notTarget);

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

    boolean chooseTarget(Outcome outcome, UUID playerId, Ability source, Game game);

    void addTarget(UUID id, Ability source, Game game);

    void addTarget(UUID id, int amount, Ability source, Game game);

    void addTarget(UUID id, Ability source, Game game, boolean skipEvent);

    void addTarget(UUID id, int amount, Ability source, Game game, boolean skipEvent);

    boolean canTarget(UUID id, Game game);

    /**
     * @param id
     * @param source WARNING, it can be null for AI or other calls from events (TODO: introduce normal source in AI ComputerPlayer)
     * @param game
     * @return
     */
    boolean canTarget(UUID id, Ability source, Game game);

    boolean stillLegalTarget(UUID id, Ability source, Game game);

    boolean canTarget(UUID playerId, UUID id, Ability source, Game game);

    boolean isLegal(Ability source, Game game);

    List<? extends Target> getTargetOptions(Ability source, Game game);

    boolean canChoose(UUID sourceControllerId, Game game);

    Set<UUID> possibleTargets(UUID sourceControllerId, Game game);

    boolean choose(Outcome outcome, UUID playerId, UUID sourceId, Ability source, Game game);

    void add(UUID id, Game game);

    void remove(UUID targetId);

    void updateTarget(UUID targetId, Game game);

    String getMessage();

    String getTargetName();

    void setTargetName(String name);

    String getTargetedName(Game game);

    Zone getZone();

    int getTargetAmount(UUID targetId);

    int getNumberOfTargets();

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

    void setRandom(boolean atRandom);

    UUID getFirstTarget();

    Target copy();

    // some targets are chosen from players that are not the controller of the ability (e.g. Pandemonium)
    void setTargetController(UUID playerId);

    UUID getTargetController();

    void setAbilityController(UUID playerId);

    UUID getAbilityController();

    Player getTargetController(Game game, UUID playerId);

    int getTargetTag();

    Target setTargetTag(int tag);

    Target getOriginalTarget();

    // used for cards like Spellskite
    void setTargetAmount(UUID targetId, int amount, Game game);

    Target withChooseHint(String chooseHint);

    String getChooseHint();

    void setEventReporting(boolean shouldReport);

    int getSize();

    boolean contains(UUID targetId);
}
