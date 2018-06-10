
package mage.abilities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.abilities.costs.OptionalAdditionalModeSourceCosts;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.Filter;
import mage.filter.FilterPlayer;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Modes extends LinkedHashMap<UUID, Mode> {

    private Mode currentMode; // the current mode of the selected modes
    private final List<UUID> selectedModes = new ArrayList<>();
    private int minModes;
    private int maxModes;
    private TargetController modeChooser;
    private boolean eachModeMoreThanOnce; // each mode can be selected multiple times during one choice
    private boolean eachModeOnlyOnce; // state if each mode can be chosen only once as long as the source object exists
    private final Map<UUID, Mode> duplicateModes = new LinkedHashMap<>();
    private OptionalAdditionalModeSourceCosts optionalAdditionalModeSourceCosts = null; // only set if costs have to be paid
    private Filter maxModesFilter = null; // calculates the max number of available modes

    public Modes() {
        this.currentMode = new Mode();
        this.put(currentMode.getId(), currentMode);
        this.minModes = 1;
        this.maxModes = 1;
        this.addSelectedMode(currentMode.getId());
        this.modeChooser = TargetController.YOU;
        this.eachModeOnlyOnce = false;
        this.eachModeMoreThanOnce = false;
    }

    public Modes(final Modes modes) {
        for (Map.Entry<UUID, Mode> entry : modes.entrySet()) {
            this.put(entry.getKey(), entry.getValue().copy());
        }
        for (Map.Entry<UUID, Mode> entry : modes.duplicateModes.entrySet()) {
            this.put(entry.getKey(), entry.getValue().copy());
        }
        this.minModes = modes.minModes;
        this.maxModes = modes.maxModes;
        this.selectedModes.addAll(modes.getSelectedModes());

        if (modes.getSelectedModes().isEmpty()) {
            this.currentMode = values().iterator().next();
        } else {
            this.currentMode = get(modes.getMode().getId());
        }
        this.modeChooser = modes.modeChooser;
        this.eachModeOnlyOnce = modes.eachModeOnlyOnce;
        this.eachModeMoreThanOnce = modes.eachModeMoreThanOnce;
        this.optionalAdditionalModeSourceCosts = modes.optionalAdditionalModeSourceCosts;
        this.maxModesFilter = modes.maxModesFilter; // can't change so no copy needed
    }

    public Modes copy() {
        return new Modes(this);
    }

    @Override
    public Mode get(Object key) {
        Mode modeToGet = super.get(key);
        if (modeToGet == null && eachModeMoreThanOnce) {
            modeToGet = duplicateModes.get(key);
        }
        return modeToGet;
    }

    public Mode getMode() {
        return currentMode;
    }

    /**
     * Returns the mode by index. For modal spells with eachModeMoreThanOnce,
     * the index returns the n selected mode
     *
     * @param index
     * @return
     */
    public UUID getModeId(int index) {
        int idx = 0;
        if (eachModeMoreThanOnce) {
            for (UUID modeId : this.selectedModes) {
                idx++;
                if (idx == index) {
                    return modeId;
                }
            }
        } else {
            for (Mode mode : this.values()) {
                idx++;
                if (idx == index) {
                    return mode.getId();
                }
            }
        }
        return null;
    }

    public List<UUID> getSelectedModes() {
        return selectedModes;
    }

    public void setMinModes(int minModes) {
        this.minModes = minModes;
    }

    public int getMinModes() {
        return this.minModes;
    }

    public void setMaxModes(int maxModes) {
        this.maxModes = maxModes;
    }

    public Filter getMaxModesFilter() {
        return maxModesFilter;
    }

    public void setMaxModesFilter(Filter maxModesFilter) {
        this.maxModesFilter = maxModesFilter;
    }

    public int getMaxModes() {
        return this.maxModes;
    }

    public void setModeChooser(TargetController modeChooser) {
        this.modeChooser = modeChooser;
    }

    public TargetController getModeChooser() {
        return this.modeChooser;
    }

    public void setActiveMode(Mode mode) {
        if (selectedModes.contains(mode.getId())) {
            this.currentMode = mode;
        }
    }

    public void setActiveMode(UUID modeId) {
        if (selectedModes.contains(modeId)) {
            this.currentMode = get(modeId);
        }
    }

    public void addMode(Mode mode) {
        this.put(mode.getId(), mode);
    }

    public boolean choose(Game game, Ability source) {
        if (this.size() > 1) {
            this.selectedModes.clear();
            this.duplicateModes.clear();
            // check if mode modifying abilities exist
            Card card = game.getCard(source.getSourceId());
            if (card != null) {
                for (Ability modeModifyingAbility : card.getAbilities()) {
                    if (modeModifyingAbility instanceof OptionalAdditionalModeSourceCosts) {
                        ((OptionalAdditionalModeSourceCosts) modeModifyingAbility).changeModes(source, game);
                    }
                }
            }
            // check if all modes can be activated automatically
            if (this.size() == this.getMinModes() && !isEachModeMoreThanOnce()) {
                Set<UUID> onceSelectedModes = null;
                if (isEachModeOnlyOnce()) {
                    onceSelectedModes = getAlreadySelectedModes(source, game);
                }
                for (Mode mode : this.values()) {
                    if ((!isEachModeOnlyOnce() || onceSelectedModes == null || !onceSelectedModes.contains(mode.getId()))
                            && mode.getTargets().canChoose(source.getSourceId(), source.getControllerId(), game)) {
                        this.addSelectedMode(mode.getId());
                    }
                }
                if (isEachModeOnlyOnce()) {
                    setAlreadySelectedModes(selectedModes, source, game);
                }
                return !selectedModes.isEmpty();
            }

            // 700.2d
            // Some spells and abilities specify that a player other than their controller chooses a mode for it.
            // In that case, the other player does so when the spell or ability's controller normally would do so.
            // If there is more than one other player who could make such a choice, the spell or ability's controller decides which of those players will make the choice.
            UUID playerId = null;
            if (modeChooser == TargetController.OPPONENT) {
                TargetOpponent targetOpponent = new TargetOpponent();
                if (targetOpponent.choose(Outcome.Benefit, source.getControllerId(), source.getSourceId(), game)) {
                    playerId = targetOpponent.getFirstTarget();
                }
            } else {
                playerId = source.getControllerId();
            }
            if (playerId == null) {
                return false;
            }
            Player player = game.getPlayer(playerId);

            // player chooses modes manually
            this.currentMode = null;
            int currentMaxModes = this.getMaxModes();
            if (getMaxModesFilter() != null) {
                if (maxModesFilter instanceof FilterPlayer) {
                    currentMaxModes = 0;
                    for (UUID targetPlayerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                        Player targetPlayer = game.getPlayer(targetPlayerId);
                        if (((FilterPlayer) maxModesFilter).match(targetPlayer, source.getSourceId(), source.getControllerId(), game)) {
                            currentMaxModes++;
                        }
                    }
                    if (currentMaxModes > this.getMaxModes()) {
                        currentMaxModes = this.getMaxModes();
                    }
                }
            }
            while (this.selectedModes.size() < currentMaxModes) {
                Mode choice = player.chooseMode(this, source, game);
                if (choice == null) {
                    if (isEachModeOnlyOnce()) {
                        setAlreadySelectedModes(selectedModes, source, game);
                    }
                    return this.selectedModes.size() >= this.getMinModes();
                }
                this.addSelectedMode(choice.getId());
                if (currentMode == null) {
                    currentMode = choice;
                }
            }
            if (isEachModeOnlyOnce()) {
                setAlreadySelectedModes(selectedModes, source, game);
            }
            return true;
        } else { // only one mode
            if (currentMode == null) {
                this.selectedModes.clear();
                Mode mode = this.values().iterator().next();
                this.addSelectedMode(mode.getId());
                this.setActiveMode(mode);
            }
            return true;
        }
    }

    /**
     * Saves the already selected modes to the state value
     *
     * @param selectedModes
     * @param source
     * @param game
     */
    private void setAlreadySelectedModes(List<UUID> selectedModes, Ability source, Game game) {
        for (UUID modeId : selectedModes) {
            String key = getKey(source, game, modeId);
            game.getState().setValue(key, true);
        }
    }

    /**
     * Adds a mode as selected. If the mode is already selected, it copies the
     * mode and adds it to the duplicate modes
     *
     * @param modeId
     */
    private void addSelectedMode(UUID modeId) {
        if (selectedModes.contains(modeId) && eachModeMoreThanOnce) {
            Mode duplicateMode = get(modeId).copy();
            duplicateMode.setRandomId();
            modeId = duplicateMode.getId();
            duplicateModes.put(modeId, duplicateMode);

        }
        this.selectedModes.add(modeId);
    }

    // The already once selected modes for a modal card are stored as a state value
    // That's important for modal abilities with modes that can only selected once while the object stays in its zone
    @SuppressWarnings("unchecked")
    private Set<UUID> getAlreadySelectedModes(Ability source, Game game) {
        Set<UUID> onceSelectedModes = new HashSet<>();
        for (UUID modeId : this.keySet()) {
            Object exist = game.getState().getValue(getKey(source, game, modeId));
            if (exist != null) {
                onceSelectedModes.add(modeId);
            }
        }
        return onceSelectedModes;
    }

    // creates the key the selected modes are saved with to the state values
    private String getKey(Ability source, Game game, UUID modeId) {
        return source.getSourceId().toString() + game.getState().getZoneChangeCounter(source.getSourceId()) + modeId.toString();
    }

    /**
     * Returns all (still) available modes of the ability
     *
     * @param source
     * @param game
     * @return
     */
    public List<Mode> getAvailableModes(Ability source, Game game) {
        List<Mode> availableModes = new ArrayList<>();
        Set<UUID> nonAvailableModes;
        if (isEachModeMoreThanOnce()) {
            nonAvailableModes = new HashSet<>();
        } else {
            nonAvailableModes = getAlreadySelectedModes(source, game);
        }
        for (Mode mode : this.values()) {
            if (isEachModeOnlyOnce() && nonAvailableModes != null && nonAvailableModes.contains(mode.getId())) {
                continue;
            }
            availableModes.add(mode);
        }
        return availableModes;
    }

    public String getText() {
        if (this.size() <= 1) {
            return this.getMode().getEffects().getText(this.getMode());
        }
        StringBuilder sb = new StringBuilder();
        if (this.getMaxModesFilter() != null) {
            sb.append("choose one or more. Each mode must target ").append(getMaxModesFilter().getMessage());
        } else if (this.getMinModes() == 1 && this.getMaxModes() == 3) {
            sb.append("choose one or more ");
        } else if (this.getMinModes() == 1 && this.getMaxModes() == 2) {
            sb.append("choose one or both ");
        } else if (this.getMinModes() == 2 && this.getMaxModes() == 2) {
            sb.append("choose two ");
        } else if (this.getMinModes() == 3 && this.getMaxModes() == 3) {
            sb.append("choose three ");
        } else {
            sb.append("choose one ");
        }
        if (isEachModeOnlyOnce()) {
            sb.append("that hasn't been chosen ");
        }
        if (isEachModeMoreThanOnce()) {
            sb.append(". You may choose the same mode more than once.<br>");
        } else {
            sb.append("&mdash;<br>");
        }
        for (Mode mode : this.values()) {
            sb.append("&bull  ");
            sb.append(mode.getEffects().getTextStartingUpperCase(mode));
            sb.append("<br>");
        }
        return sb.toString();
    }

    public String getText(String sourceName) {
        String text = getText();
        text = text.replace("{this}", sourceName);
        text = text.replace("{source}", sourceName);
        return text;
    }

    public boolean isEachModeOnlyOnce() {
        return eachModeOnlyOnce;
    }

    public void setEachModeOnlyOnce(boolean eachModeOnlyOnce) {
        this.eachModeOnlyOnce = eachModeOnlyOnce;
    }

    public boolean isEachModeMoreThanOnce() {
        return eachModeMoreThanOnce;
    }

    public void setEachModeMoreThanOnce(boolean eachModeMoreThanOnce) {
        this.eachModeMoreThanOnce = eachModeMoreThanOnce;
    }

    public OptionalAdditionalModeSourceCosts getAdditionalCost() {
        return optionalAdditionalModeSourceCosts;
    }

    public void setAdditionalCost(OptionalAdditionalModeSourceCosts optionalAdditionalModeSourceCosts) {
        this.optionalAdditionalModeSourceCosts = optionalAdditionalModeSourceCosts;
    }

}
