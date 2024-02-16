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
import mage.util.Copyable;
import mage.util.RandomUtil;

import java.util.*;
import java.util.stream.Stream;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class Modes extends LinkedHashMap<UUID, Mode> implements Copyable<Modes> {

    // choose ID for options in ability/mode picker dialogs
    public static final UUID CHOOSE_OPTION_DONE_ID = UUID.fromString("33e72ad6-17ae-4bfb-a097-6e7aa06b49e9");
    public static final UUID CHOOSE_OPTION_CANCEL_ID = UUID.fromString("0125bd0c-5610-4eba-bc80-fc6d0a7b9de6");

    private Mode currentMode; // current active mode for resolving

    private final List<UUID> selectedModes = new ArrayList<>(); // all selected modes (this + duplicate), use getSelectedModes all the time to keep modes order
    private final Map<UUID, Mode> selectedDuplicateModes = new LinkedHashMap<>(); // for 2x selects: additional selected modes
    private final Map<UUID, UUID> selectedDuplicateToOriginalModeRefs = new LinkedHashMap<>(); // for 2x selects: stores ref from duplicate to original mode

    private int minModes;
    private int maxModes;
    private Filter maxModesFilter; // calculates the max number of available modes
    private Condition moreCondition; // allows multiple modes choose (example: choose one... if condition, you may choose both)

    private boolean limitUsageByOnce = false; // limit mode selection to once per game
    private boolean limitUsageResetOnNewTurn = false; // reset once per game limit on new turn, example: Galadriel, Light of Valinor

    private String chooseText = null;
    private TargetController chooseController;
    private boolean mayChooseSameModeMoreThanOnce = false; // example: choose three... you may choose the same mode more than once
    private boolean mayChooseNone = false;
    private boolean isRandom = false; // random from available modes, not modes TODO: research rules of Cult of Skaro after WHO release (is it random from all modes or from available/valid)

    public Modes() {
        // add default mode
        this.currentMode = new Mode((Effect) null);
        this.put(currentMode.getId(), currentMode);
        this.minModes = 1;
        this.maxModes = 1;
        this.addSelectedMode(currentMode.getId());
        this.chooseController = TargetController.YOU;
    }

    protected Modes(final Modes modes) {
        for (Map.Entry<UUID, Mode> entry : modes.entrySet()) {
            this.put(entry.getKey(), entry.getValue().copy());
        }
        for (Map.Entry<UUID, Mode> entry : modes.selectedDuplicateModes.entrySet()) {
            selectedDuplicateModes.put(entry.getKey(), entry.getValue().copy());
        }
        selectedDuplicateToOriginalModeRefs.putAll(modes.selectedDuplicateToOriginalModeRefs);

        this.minModes = modes.minModes;
        this.maxModes = modes.maxModes;
        this.maxModesFilter = modes.maxModesFilter; // can't change so no copy needed
        this.moreCondition = modes.moreCondition;

        this.limitUsageByOnce = modes.limitUsageByOnce;
        this.limitUsageResetOnNewTurn = modes.limitUsageResetOnNewTurn;

        this.chooseText = modes.chooseText;
        this.chooseController = modes.chooseController;
        this.mayChooseSameModeMoreThanOnce = modes.mayChooseSameModeMoreThanOnce;
        this.mayChooseNone = modes.mayChooseNone;
        this.isRandom = modes.isRandom;

        // current mode must be "copied" at the end
        this.selectedModes.addAll(modes.getSelectedModes()); // TODO: bugged - can lost multi selects here?
        if (modes.getSelectedModes().isEmpty()) {
            this.currentMode = values().iterator().next();
        } else {
            this.currentMode = get(modes.getMode().getId()); // TODO: bugged - can lost multi selects here?
        }
    }

    @Override
    public Modes copy() {
        return new Modes(this);
    }

    @Override
    public Mode get(Object key) {
        Mode modeToGet = super.get(key);
        if (modeToGet == null && mayChooseSameModeMoreThanOnce) {
            modeToGet = selectedDuplicateModes.get(key);
        }
        return modeToGet;
    }

    public Stream<Mode> streamAlreadySelectedModes(Ability source, Game game) {
        Set<UUID> selected = getAlreadySelectedModes(source, game, true);
        return super.values().stream().filter(m -> selected.contains(m.getId()));
    }

    /**
     * For card constructor: returns first/default mode
     * For game: returns current resolving mode
     */
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
        if (mayChooseSameModeMoreThanOnce) {
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

    /**
     * Return full list of selected modes in default/rules order (without multi selects)
     */
    public List<UUID> getSelectedModes() {
        // modes can be selected in any order by user, but execution must be in rule's order
        List<UUID> res = new ArrayList<>(this.size());
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
        // verify check
        if (maxModesFilter != null && !(maxModesFilter instanceof FilterPlayer)) {
            throw new IllegalArgumentException("Wrong code usage: max modes filter support only FilterPlayer");
        }

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

        // use case: limit max modes by opponents (example: choose one or more... each mode must target a different player)
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

    public void setChooseController(TargetController chooseController) {
        this.chooseController = chooseController;
    }

    public TargetController getChooseController() {
        return this.chooseController;
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

    private boolean isAlreadySelectedModesOutdated(Game game, Ability source) {
        return this.isLimitUsageResetOnNewTurn()
                && getOnceTurnNum(game, source) != game.getTurnNum();
    }

    private boolean isSelectedValid(Ability source, Game game) {
        if (isLimitUsageByOnce()) {
            setOnceSelectedModes(source, game);
        }
        return this.selectedModes.size() >= this.getMinModes()
                || (this.selectedModes.size() == 0 && mayChooseNone);
    }

    public boolean choose(Game game, Ability source) {
        if (isAlreadySelectedModesOutdated(game, source)) {
            this.clearAlreadySelectedModes(source, game);
        }
        this.clearSelectedModes();

        // runtime check
        if (this.isRandom && limitUsageByOnce) {
            // non-tested use case, if you catch this error then disable and manually test, if fine then that check can be removed
            throw new IllegalStateException("Wrong code usage: random modes are not support with once usage");
        }

        // 700.2b
        // The controller of a modal triggered ability chooses the mode(s) as part of putting that ability
        // on the stack. If one of the modes would be illegal (due to an inability to choose legal targets, for
        // example), that mode can’t be chosen. If no mode is chosen, the ability is removed from the
        // stack. (See rule 603.3c.)
        List<Mode> availableModes = getAvailableModes(source, game);
        if (availableModes.size() == 0) {
            return isSelectedValid(source, game);
        }

        // modal spells must show choose dialog even for 1 option, so check this.size instead evailableModes.size here
        if (this.size() > 1) {
            // multiple modes

            // modes modifications, e.g. choose max modes instead single
            Card card = game.getCard(source.getSourceId());
            if (card != null) {
                for (Ability modeModifyingAbility : card.getAbilities(game)) {
                    if (modeModifyingAbility instanceof OptionalAdditionalModeSourceCosts) {
                        // cost must check activation condition in changeModes
                        ((OptionalAdditionalModeSourceCosts) modeModifyingAbility).changeModes(source, game);
                    }
                }
            }

            // choose random
            if (this.isRandom) {
                // TODO: research rules of Cult of Skaro after WHO release (is it random from all modes or from available/valid)
                this.addSelectedMode(availableModes.get(RandomUtil.nextInt(availableModes.size())).getId());
                return isSelectedValid(source, game);
            }

            // UX: check if all modes can be activated automatically
            if (this.size() == this.getMinModes() && !isMayChooseSameModeMoreThanOnce()) {
                Set<UUID> onceSelectedModes = null;
                if (isLimitUsageByOnce()) {
                    onceSelectedModes = getAlreadySelectedModes(source, game, true);
                }
                for (Mode mode : this.values()) {
                    if ((!isLimitUsageByOnce() || onceSelectedModes == null || !onceSelectedModes.contains(mode.getId()))
                            && mode.getTargets().canChoose(source.getControllerId(), source, game)) {
                        this.addSelectedMode(mode.getId());
                    }
                }
                return isSelectedValid(source, game);
            }

            // 700.2d
            // Some spells and abilities specify that a player other than their controller chooses a mode for it.
            // In that case, the other player does so when the spell or ability's controller normally would do so.
            // If there is more than one other player who could make such a choice, the spell or ability's controller decides which of those players will make the choice.
            UUID playerId;
            if (chooseController == TargetController.OPPONENT) {
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
                    // user press cancel/stop in choose dialog or nothing to choose
                    return isSelectedValid(source, game);
                }
                this.addSelectedMode(choice.getId());
                if (currentMode == null) {
                    currentMode = choice;
                }
            }
            // effects helper (keep real choosing player)
            if (chooseController == TargetController.OPPONENT) {
                selectedModes
                        .stream()
                        .map(this::get)
                        .map(Mode::getEffects)
                        .forEach(effects -> effects.setValue("choosingPlayer", playerId));
            }
        } else {
            // only one mode
            Mode mode = this.values().iterator().next();
            this.addSelectedMode(mode.getId());
            this.setActiveMode(mode);
        }

        return isSelectedValid(source, game);
    }

    /**
     * Saves the already selected modes to the state value
     *
     * @param source
     * @param game
     */
    private void setOnceSelectedModes(Ability source, Game game) {
        for (UUID modeId : getSelectedModes()) {
            String key = getSelectedModesKey(source, game, modeId);
            game.getState().setValue(key, true);
        }
    }

    private void clearAlreadySelectedModes(Ability source, Game game) {
        // need full list to clear outdated data
        for (UUID modeId : getAlreadySelectedModes(source, game, false)) {
            String key = getSelectedModesKey(source, game, modeId);
            game.getState().setValue(key, false);
        }
        setOnceTurnNum(game, source);
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

        if (selectedModes.contains(modeId) && mayChooseSameModeMoreThanOnce) {
            Mode duplicateMode = get(modeId).copy();
            UUID originalId = modeId;
            duplicateMode.setRandomId();
            modeId = duplicateMode.getId();
            selectedDuplicateModes.put(modeId, duplicateMode);
            selectedDuplicateToOriginalModeRefs.put(duplicateMode.getId(), originalId);
        }
        // TODO: bugged and allows to choose same mode multiple times without mayChooseSameModeMoreThanOnce?
        this.selectedModes.add(modeId);
    }

    public void removeSelectedMode(UUID modeId) {
        this.selectedModes.remove(modeId);
        this.selectedDuplicateModes.remove(modeId);
        this.selectedDuplicateToOriginalModeRefs.remove(modeId);
    }

    /**
     * Return already selected modes, used for GUI and modal cards check
     * Can be outdated if each turn reset enabled
     * <p>
     * Warning, works with limitUsageByOnce only, other cards will not contain that info
     *
     * @param source
     * @param game
     * @param ignoreOutdatedData if true then return full selected modes (used in clear code on new turn)
     * @return
     */
    private Set<UUID> getAlreadySelectedModes(Ability source, Game game, boolean ignoreOutdatedData) {
        Set<UUID> res = new HashSet<>();

        // if selected modes is not for current turn, so we ignore any value that may be there
        if (ignoreOutdatedData && isAlreadySelectedModesOutdated(game, source)) {
            return res;
        }

        for (UUID modeId : this.keySet()) {
            Object exist = game.getState().getValue(getSelectedModesKey(source, game, modeId));
            if (exist == Boolean.TRUE) {
                res.add(modeId);
            }
        }
        return res;
    }

    private String getKeyPrefix(Game game, Ability source) {
        return source == null || source.getSourceId() == null ? "" : source.getSourceId().toString() + game.getState().getZoneChangeCounter(source.getSourceId());
    }

    private String getSelectedModesKey(Ability source, Game game, UUID modeId) {
        return getKeyPrefix(game, source) + modeId.toString();
    }

    private String getOnceTurnNumKey(Ability source, Game game) {
        return getKeyPrefix(game, source) + "turnNum";
    }

    private int getOnceTurnNum(Game game, Ability source) {
        Object object = game.getState().getValue(getOnceTurnNumKey(source, game));
        if (object instanceof Integer) {
            return (Integer) object;
        }
        return 0;
    }

    private void setOnceTurnNum(Game game, Ability source) {
        game.getState().setValue(getOnceTurnNumKey(source, game), game.getTurnNum());
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
        if (isMayChooseSameModeMoreThanOnce()) {
            nonAvailableModes = new HashSet<>();
        } else {
            nonAvailableModes = getAlreadySelectedModes(source, game, true);
        }
        for (Mode mode : this.values()) {
            if (isLimitUsageByOnce() && nonAvailableModes.contains(mode.getId())) {
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
            if (chooseController == TargetController.OPPONENT) {
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
            if (isRandom) {
                sb.append(" at random");
            }
        } else {
            sb.append(chooseText);
        }

        if (isLimitUsageByOnce() && this.getMaxModesFilter() == null) {
            sb.append(" that hasn't been chosen");
        }
        if (isLimitUsageResetOnNewTurn()) {
            sb.append(" this turn");
        }

        if (this.getMaxModesFilter() != null) {
            sb.append(". Each mode must target ").append(getMaxModesFilter().getMessage()).append('.');
        } else if (isMayChooseSameModeMoreThanOnce()) {
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

    public boolean isLimitUsageByOnce() {
        return limitUsageByOnce;
    }

    /**
     * Limit modes usage to once per game or once per turn
     */
    public void setLimitUsageByOnce(boolean resetOnNewTurn) {
        this.limitUsageByOnce = true;
        this.limitUsageResetOnNewTurn = resetOnNewTurn;
    }

    public boolean isMayChooseSameModeMoreThanOnce() {
        return mayChooseSameModeMoreThanOnce;
    }

    public void setMayChooseSameModeMoreThanOnce(boolean mayChooseSameModeMoreThanOnce) {
        this.mayChooseSameModeMoreThanOnce = mayChooseSameModeMoreThanOnce;
    }

    public void setRandom(boolean isRandom) {
        this.isRandom = isRandom;
    }

    public boolean isLimitUsageResetOnNewTurn() {
        return limitUsageResetOnNewTurn;
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
