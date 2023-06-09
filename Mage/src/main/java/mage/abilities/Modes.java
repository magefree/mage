package mage.abilities;

import mage.abilities.condition.Condition;
import mage.abilities.costs.OptionalAdditionalModeSourceCosts;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.Filter;
import mage.filter.FilterPlayer;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;
import mage.util.RandomUtil;

import java.util.*;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class Modes extends LinkedHashMap<UUID, Mode> {

    // choose ID for options in ability/mode picker dialogs
    public static final UUID CHOOSE_OPTION_DONE_ID = UUID.fromString("33e72ad6-17ae-4bfb-a097-6e7aa06b49e9");
    public static final UUID CHOOSE_OPTION_CANCEL_ID = UUID.fromString("0125bd0c-5610-4eba-bc80-fc6d0a7b9de6");

    private Mode currentMode; // the current mode of the selected modes
    private final List<UUID> selectedModes = new ArrayList<>(); // all selected modes (this + duplicate), use getSelectedModes all the time to keep modes order
    private final Map<UUID, Mode> selectedDuplicateModes = new LinkedHashMap<>(); // for 2x selects: copy mode and put it to duplicate list
    private final Map<UUID, UUID> selectedDuplicateToOriginalModeRefs = new LinkedHashMap<>(); // for 2x selects: stores ref from duplicate to original mode

    private int minModes;
    private int maxModes;
    private TargetController modeChooser;
    private boolean eachModeMoreThanOnce; // each mode can be selected multiple times during one choice
    private boolean eachModeOnlyOnce; // state if each mode can be chosen only once as long as the source object exists
    private Filter maxModesFilter = null; // calculates the max number of available modes
    private boolean isRandom = false;
    private String chooseText = null;
    private boolean resetEachTurn = false;
    private Condition moreCondition;
    private boolean mayChooseNone = false;

    public Modes() {
        this.currentMode = new Mode((Effect) null);
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
        for (Map.Entry<UUID, Mode> entry : modes.selectedDuplicateModes.entrySet()) {
            selectedDuplicateModes.put(entry.getKey(), entry.getValue().copy());
        }
        selectedDuplicateToOriginalModeRefs.putAll(modes.selectedDuplicateToOriginalModeRefs);

        this.minModes = modes.minModes;
        this.maxModes = modes.maxModes;
        this.selectedModes.addAll(modes.getSelectedModes());

        this.modeChooser = modes.modeChooser;
        this.eachModeOnlyOnce = modes.eachModeOnlyOnce;
        this.eachModeMoreThanOnce = modes.eachModeMoreThanOnce;
        this.maxModesFilter = modes.maxModesFilter; // can't change so no copy needed

        this.isRandom = modes.isRandom;
        this.chooseText = modes.chooseText;
        this.resetEachTurn = modes.resetEachTurn;
        if (modes.getSelectedModes().isEmpty()) {
            this.currentMode = values().iterator().next();
        } else {
            this.currentMode = get(modes.getMode().getId()); // need fix?
        }
        this.moreCondition = modes.moreCondition;
        this.mayChooseNone = modes.mayChooseNone;
    }

    public Modes copy() {
        return new Modes(this);
    }

    @Override
    public Mode get(Object key) {
        Mode modeToGet = super.get(key);
        if (modeToGet == null && eachModeMoreThanOnce) {
            modeToGet = selectedDuplicateModes.get(key);
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
            for (UUID modeId : this.getSelectedModes()) {
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
        // modes can be selected in any order by user, but execution must be in rule's order
        List<UUID> res = new ArrayList<>();
        for (Mode mode : this.values()) {
            for (UUID selectedId : this.selectedModes) {
                // selectedModes contains original mode and 2+ selected as duplicates (new modes)
                UUID selectedOriginalId = this.selectedDuplicateToOriginalModeRefs.get(selectedId);
                if (Objects.equals(mode.getId(), selectedId)
                        || Objects.equals(mode.getId(), selectedOriginalId)) {
                    res.add(selectedId);
                }
            }
        }
        return res;
    }

    public void clearSelectedModes() {
        this.selectedModes.clear();
        this.selectedDuplicateModes.clear();
        this.selectedDuplicateToOriginalModeRefs.clear();
    }

    public int getSelectedStats(UUID modeId) {
        int count = 0;
        if (this.selectedModes.contains(modeId)) {

            // single select
            count++;

            // multiple select (all 2x select generate new duplicate mode)
            UUID originalId;
            if (this.selectedDuplicateModes.containsKey(modeId)) {
                // modeId is duplicate
                originalId = this.selectedDuplicateToOriginalModeRefs.get(modeId);
            } else {
                // modeId is original
                originalId = modeId;
            }
            for (UUID id : this.selectedDuplicateToOriginalModeRefs.values()) {
                if (id.equals(originalId)) {
                    count++;
                }
            }
        }

        return count;
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

    /**
     * Return real affected max modes in current game. Use null params for default max modes value.
     *
     * @param game
     * @param source can be null for rules generation
     * @return
     */
    public int getMaxModes(Game game, Ability source) {
        int realMaxModes = this.maxModes;
        if (game == null || source == null) {
            return realMaxModes;
        }

        // use case: make two modes chooseable (all cards that use this currently go from one to two)
        if (moreCondition != null && moreCondition.apply(game, source)) {
            realMaxModes = 2;
        }

        // use case: limit max modes by opponents (wtf?!)
        if (getMaxModesFilter() != null) {
            if (this.maxModesFilter instanceof FilterPlayer) {
                realMaxModes = 0;
                for (UUID targetPlayerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                    Player targetPlayer = game.getPlayer(targetPlayerId);
                    if (((FilterPlayer) this.maxModesFilter).match(targetPlayer, source.getControllerId(), source, game)) {
                        realMaxModes++;
                    }
                }
                if (realMaxModes > this.maxModes) {
                    realMaxModes = this.maxModes;
                }
            }
        }

        return realMaxModes;
    }

    public void setModeChooser(TargetController modeChooser) {
        this.modeChooser = modeChooser;
    }

    public TargetController getModeChooser() {
        return this.modeChooser;
    }

    public void setActiveMode(Mode mode) {
        setActiveMode(mode.getId());
    }

    public void setActiveMode(UUID modeId) {
        if (selectedModes.contains(modeId)) {
            this.currentMode = get(modeId);
        }
    }

    public void addMode(Mode mode) {
        this.put(mode.getId(), mode);
    }

    public void setMoreCondition(Condition moreCondition) {
        this.moreCondition = moreCondition;
    }

    public boolean choose(Game game, Ability source) {
        if (this.isResetEachTurn()) {
            if (getTurnNum(game, source) != game.getTurnNum()) {
                this.clearAlreadySelectedModes(source, game);
                setTurnNum(game, source);
            }
        }
        if (this.size() > 1) {
            this.clearSelectedModes();
            if (this.isRandom) {
                List<Mode> modes = getAvailableModes(source, game);
                this.addSelectedMode(modes.get(RandomUtil.nextInt(modes.size())).getId());
                return true;
            }

            // check if mode modifying abilities exist
            Card card = game.getCard(source.getSourceId());
            if (card != null) {
                for (Ability modeModifyingAbility : card.getAbilities(game)) {
                    if (modeModifyingAbility instanceof OptionalAdditionalModeSourceCosts) {
                        // cost must check activation condition in changeModes
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
                            && mode.getTargets().canChoose(source.getControllerId(), source, game)) {
                        this.addSelectedMode(mode.getId());
                    }
                }
                if (isEachModeOnlyOnce()) {
                    setAlreadySelectedModes(source, game);
                }
                return !selectedModes.isEmpty();
            }

            // 700.2d
            // Some spells and abilities specify that a player other than their controller chooses a mode for it.
            // In that case, the other player does so when the spell or ability's controller normally would do so.
            // If there is more than one other player who could make such a choice, the spell or ability's controller decides which of those players will make the choice.
            UUID playerId;
            if (modeChooser == TargetController.OPPONENT) {
                TargetOpponent targetOpponent = new TargetOpponent();
                targetOpponent.choose(Outcome.Benefit, source.getControllerId(), source.getSourceId(), source, game);
                playerId = targetOpponent.getFirstTarget();
            } else {
                playerId = source.getControllerId();
            }
            Player player = game.getPlayer(playerId);
            if (player == null) {
                return false;
            }

            // player chooses modes manually
            this.currentMode = null;
            int currentMaxModes = this.getMaxModes(game, source);

            while (this.selectedModes.size() < currentMaxModes) {
                Mode choice = player.chooseMode(this, source, game);
                if (choice == null) {
                    if (isEachModeOnlyOnce()) {
                        setAlreadySelectedModes(source, game);
                    }
                    return this.selectedModes.size() >= this.getMinModes()
                            || (this.selectedModes.size() == 0 && mayChooseNone);
                }
                this.addSelectedMode(choice.getId());
                if (currentMode == null) {
                    currentMode = choice;
                }
            }
            if (isEachModeOnlyOnce()) {
                setAlreadySelectedModes(source, game);
            }
            if (modeChooser == TargetController.OPPONENT) {
                selectedModes
                        .stream()
                        .map(this::get)
                        .map(Mode::getEffects)
                        .forEach(effects -> effects.setValue("choosingPlayer", playerId));
            }
        } else {
            // only one mode available
            if (currentMode == null) {
                this.clearSelectedModes();
                Mode mode = this.values().iterator().next();
                this.addSelectedMode(mode.getId());
                this.setActiveMode(mode);
            }
        }
        return true;
    }

    /**
     * Saves the already selected modes to the state value
     *
     * @param source
     * @param game
     */
    private void setAlreadySelectedModes(Ability source, Game game) {
        for (UUID modeId : getSelectedModes()) {
            String key = getKey(source, game, modeId);
            game.getState().setValue(key, true);
        }
    }

    private void clearAlreadySelectedModes(Ability source, Game game) {
        for (UUID modeId : getAlreadySelectedModes(source, game)) {
            String key = getKey(source, game, modeId);
            game.getState().setValue(key, false);
        }
    }

    /**
     * Adds a mode as selected. If the mode is already selected, it copies the
     * mode and adds it to the duplicate modes
     *
     * @param modeId
     */
    public void addSelectedMode(UUID modeId) {
        if (!this.containsKey(modeId)) {
            throw new IllegalArgumentException("Unknown modeId to select");
        }

        if (selectedModes.contains(modeId) && eachModeMoreThanOnce) {
            Mode duplicateMode = get(modeId).copy();
            UUID originalId = modeId;
            duplicateMode.setRandomId();
            modeId = duplicateMode.getId();
            selectedDuplicateModes.put(modeId, duplicateMode);
            selectedDuplicateToOriginalModeRefs.put(duplicateMode.getId(), originalId);

        }
        this.selectedModes.add(modeId);
    }

    public void removeSelectedMode(UUID modeId) {
        this.selectedModes.remove(modeId);
        this.selectedDuplicateModes.remove(modeId);
        this.selectedDuplicateToOriginalModeRefs.remove(modeId);
    }

    // The already once selected modes for a modal card are stored as a state value
    // That's important for modal abilities with modes that can only selected once while the object stays in its zone
    @SuppressWarnings("unchecked")
    private Set<UUID> getAlreadySelectedModes(Ability source, Game game) {
        Set<UUID> onceSelectedModes = new HashSet<>();
        for (UUID modeId : this.keySet()) {
            Object exist = game.getState().getValue(getKey(source, game, modeId));
            if (exist == Boolean.TRUE) {
                onceSelectedModes.add(modeId);
            }
        }
        return onceSelectedModes;
    }

    // creates the key the selected modes are saved with to the state values
    private String getKey(Ability source, Game game, UUID modeId) {
        return source.getSourceId().toString() + game.getState().getZoneChangeCounter(source.getSourceId()) + modeId.toString();
    }

    private static int getTurnNum(Game game, Ability source) {
        String key = source.getSourceId().toString() + game.getState().getZoneChangeCounter(source.getSourceId()) + "turnNum";
        Object object = game.getState().getValue(key);
        if (object instanceof Integer) {
            return (Integer) object;
        }
        return 0;
    }

    private static void setTurnNum(Game game, Ability source) {
        String key = source.getSourceId().toString() + game.getState().getZoneChangeCounter(source.getSourceId()) + "turnNum";
        game.getState().setValue(key, game.getTurnNum());
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
            if (isEachModeOnlyOnce() && nonAvailableModes.contains(mode.getId())) {
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
        if (mayChooseNone) {
            sb.append("you may ");
        }
        if (this.chooseText == null) {
            if (modeChooser == TargetController.OPPONENT) {
                sb.append("an opponent chooses ");
            } else {
                sb.append("choose ");
            }
            if (this.getMinModes() == 0 && this.getMaxModes(null, null) == 1) {
                sb.append("up to one");
            } else if (this.getMinModes() == 0 && this.getMaxModes(null, null) > 2) {
                sb.append("any number");
            } else if (this.getMinModes() == 1 && this.getMaxModes(null, null) == 2) {
                sb.append("one or both");
            } else if (this.getMinModes() == 1 && this.getMaxModes(null, null) > 2) {
                sb.append("one or more");
            } else if (this.getMinModes() == this.getMaxModes(null, null)) {
                sb.append(CardUtil.numberToText(this.getMinModes()));
            } else {
                throw new UnsupportedOperationException(String.format("no text available for this selection of min and max modes (%d and %d)",
                        this.getMinModes(), this.getMaxModes(null, null)));
            }
        } else {
            sb.append(chooseText);
        }

        if (isEachModeOnlyOnce() && this.getMaxModesFilter() == null) {
            sb.append(" that hasn't been chosen");
        }
        if (isResetEachTurn()) {
            sb.append(" this turn");
        }

        if (this.getMaxModesFilter() != null) {
            sb.append(". Each mode must target ").append(getMaxModesFilter().getMessage()).append('.');
        } else if (isEachModeMoreThanOnce()) {
            sb.append(". You may choose the same mode more than once.");
        } else if (chooseText == null) {
            sb.append(" &mdash;");
        }

        sb.append("<br>");

        for (Mode mode : this.values()) {
            sb.append("&bull  ");
            sb.append(mode.getEffects().getTextStartingUpperCase(mode));
            sb.append("<br>");
        }
        return sb.toString();
    }

    public String getText(String sourceName) {
        return getText().replace("{this}", sourceName);
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

    public void setRandom(boolean isRandom) {
        this.isRandom = isRandom;
    }

    public boolean isResetEachTurn() {
        return resetEachTurn;
    }

    public void setResetEachTurn(boolean resetEachTurn) {
        this.resetEachTurn = resetEachTurn;
    }

    public void setChooseText(String chooseText) {
        this.chooseText = chooseText;
    }

    public void setMayChooseNone(boolean mayChooseNone) {
        this.mayChooseNone = mayChooseNone;
    }

    public boolean isMayChooseNone() {
        return mayChooseNone;
    }
}
