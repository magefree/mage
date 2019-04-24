
package mage.target;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.Filter;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public interface Target extends Serializable {

    boolean isChosen();

    boolean doneChosing();

    void clearChosen();

    boolean isNotTarget();

    /**
     * controls if it will be checked, if the target can be targeted from source
     *
     * @param notTarget true = do not check for protection, false = check for
     * protection
     */
    void setNotTarget(boolean notTarget);

    // methods for targets
    boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game);

    Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game);

    boolean chooseTarget(Outcome outcome, UUID playerId, Ability source, Game game);

    void addTarget(UUID id, Ability source, Game game);

    void addTarget(UUID id, int amount, Ability source, Game game);

    void addTarget(UUID id, Ability source, Game game, boolean skipEvent);

    void addTarget(UUID id, int amount, Ability source, Game game, boolean skipEvent);

    boolean canTarget(UUID id, Game game);

    boolean canTarget(UUID id, Ability source, Game game);

    boolean canTarget(UUID playerId, UUID id, Ability source, Game game);

    boolean isLegal(Ability source, Game game);

    List<? extends Target> getTargetOptions(Ability source, Game game);

    //methods for non-targets
    boolean canChoose(UUID sourceControllerId, Game game);

    Set<UUID> possibleTargets(UUID sourceControllerId, Game game);

    boolean choose(Outcome outcome, UUID playerId, UUID sourceId, Game game);

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

    // some targets are choosen from players that are not the controller of the ability (e.g. Pandemonium)
    void setTargetController(UUID playerId);

    UUID getTargetController();

    void setAbilityController(UUID playerId);

    UUID getAbilityController();

    Player getTargetController(Game game, UUID playerId);

    int getTargetTag();

    void setTargetTag(int tag);

    Target getOriginalTarget();

    // used for cards like Spellskite
    void setTargetAmount(UUID targetId, int amount, Game game);

}
