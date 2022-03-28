package mage.target;

import mage.MageObject;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.constants.AbilityType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.TargetEvent;
import mage.players.Player;
import mage.util.CardUtil;
import mage.util.RandomUtil;

import java.util.*;
import mage.game.permanent.Permanent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class TargetImpl implements Target {

    protected final Map<UUID, Integer> targets = new LinkedHashMap<>();
    protected final Map<UUID, Integer> zoneChangeCounters = new HashMap<>();

    protected String targetName;
    protected Zone zone; // all targets will be filtered by that zone, don't use "multi-zone" filter
    protected int maxNumberOfTargets;
    protected int minNumberOfTargets;
    protected boolean required = true;
    protected boolean requiredExplicitlySet = false;
    protected boolean chosen = false;
    // is the target handled as targeted spell/ability (notTarget = true is used for not targeted effects like e.g. sacrifice)
    protected boolean notTarget = false;
    protected boolean atRandom = false;
    protected UUID targetController = null; // if null the ability controller is the targetController
    protected UUID abilityController = null; // only used if target controller != ability controller

    protected int targetTag; // can be set if other target check is needed (AnotherTargetPredicate)
    protected String chooseHint = null; // UI choose hints after target name
    protected boolean shouldReportEvents = true;

    @Override
    public abstract TargetImpl copy();

    public TargetImpl() {
        this(false);
    }

    public TargetImpl(boolean notTarget) {
        this.notTarget = notTarget;
    }

    public TargetImpl(final TargetImpl target) {
        this.targetName = target.targetName;
        this.zone = target.zone;
        this.maxNumberOfTargets = target.maxNumberOfTargets;
        this.minNumberOfTargets = target.minNumberOfTargets;
        this.required = target.required;
        this.requiredExplicitlySet = target.requiredExplicitlySet;
        this.chosen = target.chosen;
        this.targets.putAll(target.targets);
        this.zoneChangeCounters.putAll(target.zoneChangeCounters);
        this.atRandom = target.atRandom;
        this.notTarget = target.notTarget;
        this.targetController = target.targetController;
        this.abilityController = target.abilityController;
        this.targetTag = target.targetTag;
        this.chooseHint = target.chooseHint;
        this.shouldReportEvents = target.shouldReportEvents;
    }

    @Override
    public int getNumberOfTargets() {
        return this.minNumberOfTargets;
    }

    @Override
    public int getMinNumberOfTargets() {
        return this.minNumberOfTargets;
    }

    @Override
    public int getMaxNumberOfTargets() {
        return this.maxNumberOfTargets;
    }

    @Override
    public void setMinNumberOfTargets(int minNumberOftargets) {
        this.minNumberOfTargets = minNumberOftargets;
    }

    @Override
    public void setMaxNumberOfTargets(int maxNumberOftargets) {
        this.maxNumberOfTargets = maxNumberOftargets;
    }

    @Override
    public String getMessage() {
        // UI choose message
        String suffix = "";
        if (this.chooseHint != null) {
            suffix = " (" + this.chooseHint + ")";
        }
        if (getMaxNumberOfTargets() != 1) {
            StringBuilder sb = new StringBuilder();
            sb.append("Select ").append(targetName);
            if (getMaxNumberOfTargets() > 0 && getMaxNumberOfTargets() != Integer.MAX_VALUE) {
                sb.append(" (selected ").append(targets.size()).append(" of ").append(getMaxNumberOfTargets()).append(')');
            } else {
                sb.append(" (selected ").append(targets.size()).append(')');
            }
            sb.append(suffix);
            return sb.toString();
        }
        if (getMinNumberOfTargets() == 0 && getMaxNumberOfTargets() == 1) {
            return "Select up to one " + targetName + suffix;
        }
        return "Select " + CardUtil.addArticle(targetName) + suffix;
    }

    @Override
    public boolean isNotTarget() {
        return notTarget;
    }

    @Override
    public String getTargetName() {
        return targetName + (isRandom() ? " chosen at random" : "");
    }

    @Override
    public void setTargetName(String name) {
        this.targetName = name;
    }

    @Override
    public Zone getZone() {
        return zone;
    }

    @Override
    public boolean isRequired(UUID sourceId, Game game) {
        MageObject object = game.getObject(sourceId);
        if (!requiredExplicitlySet && object instanceof Ability) {
            return isRequired((Ability) object);
        } else {
            return isRequired();
        }
    }

    @Override
    public boolean isRequired() {
        return required;
    }

    @Override
    public boolean isRequired(Ability ability) {
        return ability == null || ability.isActivated() || !(ability.getAbilityType() == AbilityType.SPELL || ability.getAbilityType() == AbilityType.ACTIVATED);
    }

    @Override
    public void setRequired(boolean required) {
        this.required = required;
        this.requiredExplicitlySet = true;
    }

    @Override
    public boolean isChosen() {
        if (getMaxNumberOfTargets() == 0 && getNumberOfTargets() == 0) {
            return true;
        }
        return getMaxNumberOfTargets() != 0 && targets.size() == getMaxNumberOfTargets() || chosen;
    }

    @Override
    public boolean doneChosing() {
        return getMaxNumberOfTargets() != 0 && targets.size() == getMaxNumberOfTargets();
    }

    @Override
    public void clearChosen() {
        targets.clear();
        zoneChangeCounters.clear();
        chosen = false;
    }

    @Override
    public void add(UUID id, Game game) {
        if (getMaxNumberOfTargets() == 0 || targets.size() < getMaxNumberOfTargets()) {
            if (!targets.containsKey(id)) {
                targets.put(id, 0);
                rememberZoneChangeCounter(id, game);
                chosen = targets.size() >= getNumberOfTargets();
            }
        }
    }

    @Override
    public void remove(UUID id) {
        if (targets.containsKey(id)) {
            targets.remove(id);
            zoneChangeCounters.remove(id);
        }
    }

    @Override
    public void addTarget(UUID id, Ability source, Game game) {
        addTarget(id, source, game, notTarget);
    }

    @Override
    public void addTarget(UUID id, Ability source, Game game, boolean skipEvent) {
        //20100423 - 113.3
        if (getMaxNumberOfTargets() == 0 || targets.size() < getMaxNumberOfTargets()) {
            if (!targets.containsKey(id)) {
                if (source != null && !skipEvent && shouldReportEvents) {
                    if (!game.replaceEvent(new TargetEvent(id, source))) {
                        targets.put(id, 0);
                        rememberZoneChangeCounter(id, game);
                        chosen = targets.size() >= getNumberOfTargets();
                        if (!skipEvent && shouldReportEvents) {
                            game.addSimultaneousEvent(GameEvent.getEvent(GameEvent.EventType.TARGETED, id, source, source.getControllerId()));
                        }
                    }
                } else {
                    targets.put(id, 0);
                }
            }
        }
    }

    @Override
    public void updateTarget(UUID id, Game game) {
        rememberZoneChangeCounter(id, game);
    }

    private void rememberZoneChangeCounter(UUID id, Game game) {
        Card card = game.getCard(id);
        if (card != null) {
            zoneChangeCounters.put(id, card.getZoneChangeCounter(game));
        }
    }

    @Override
    public void addTarget(UUID id, int amount, Ability source, Game game) {
        addTarget(id, amount, source, game, false);
    }

    @Override
    public void addTarget(UUID id, int amount, Ability source, Game game, boolean skipEvent) {
        if (targets.containsKey(id)) {
            amount += targets.get(id);
        }
        if (source != null && !skipEvent && shouldReportEvents) {
            if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.TARGET, id, source, source.getControllerId()))) {
                targets.put(id, amount);
                rememberZoneChangeCounter(id, game);
                chosen = targets.size() >= getNumberOfTargets();
                if (!skipEvent && shouldReportEvents) {
                    game.fireEvent(GameEvent.getEvent(GameEvent.EventType.TARGETED, id, source, source.getControllerId()));
                }
            }
        } else {
            targets.put(id, amount);
            rememberZoneChangeCounter(id, game);
        }
    }

    @Override
    public boolean choose(Outcome outcome, UUID playerId, UUID sourceId, Ability source, Game game) {
        Player targetController = getTargetController(game, playerId);
        if (targetController == null) {
            return false;
        }

        chosen = targets.size() >= getNumberOfTargets();
        do {
            if (!targetController.canRespond()) {
                return chosen;
            }
            if (!targetController.choose(outcome, this, source, game)) {
                return chosen;
            }
            chosen = targets.size() >= getNumberOfTargets();
        } while (!isChosen() && !doneChosing());
        return chosen;
    }

    @Override
    public boolean chooseTarget(Outcome outcome, UUID playerId, Ability source, Game game) {
        Player targetController = getTargetController(game, playerId);
        if (targetController == null) {
            return false;
        }

        List<UUID> possibleTargets = new ArrayList<>(possibleTargets(playerId, source, game));

        chosen = targets.size() >= getNumberOfTargets();
        do {
            if (!targetController.canRespond()) {
                return chosen;
            }
            if (isRandom()) {
                if (possibleTargets.isEmpty()) {
                    return chosen;
                }
                // find valid target
                while (!possibleTargets.isEmpty()) {
                    int index = RandomUtil.nextInt(possibleTargets.size());
                    if (this.canTarget(playerId, possibleTargets.get(index), source, game)) {
                        this.addTarget(possibleTargets.get(index), source, game);
                        possibleTargets.remove(index);
                        break;
                    } else {
                        possibleTargets.remove(index);
                    }
                }
            } else if (!targetController.chooseTarget(outcome, this, source, game)) {
                return chosen;
            }
            chosen = targets.size() >= getNumberOfTargets();
        } while (!isChosen() && !doneChosing());

        return chosen;
    }

    @Override
    public boolean isLegal(Ability source, Game game) {
        //20101001 - 608.2b
        Set<UUID> illegalTargets = new HashSet<>();
        for (UUID targetId : targets.keySet()) {
            Card card = game.getCard(targetId);
            if (card != null) {
                // if a permanent, verify it is phased in, otherwise it is illegal
                Permanent p = game.getPermanent(targetId);
                if (p != null
                        && !p.isPhasedIn()) {
                    illegalTargets.add(targetId);
                    continue; // it's not legal so continue to have a look at other targeted objects
                }
                // check if the card moved to another zone
                if (zoneChangeCounters.containsKey(targetId)
                        && zoneChangeCounters.get(targetId) != card.getZoneChangeCounter(game)) {
                    illegalTargets.add(targetId);
                    continue; // it's not legal so continue to have a look at other targeted objects
                }
            }
            if (!notTarget && game.replaceEvent(new TargetEvent(targetId, source))) {
//                replacedTargets++;
                illegalTargets.add(targetId);
                continue;
            }
            if (!stillLegalTarget(targetId, source, game)) {
                illegalTargets.add(targetId);
            }
        }
        // remove illegal targets, needed to handle if only a subset of targets was illegal
        for (UUID targetId : illegalTargets) {
            targets.remove(targetId);
        }
        if (targets.isEmpty()) {
            // If all targets that were set before are illegal now, the target is no longer legal
            if (!illegalTargets.isEmpty()) {
                return false;
            }
            // if no targets have to be set and no targets are set, that's legal
            if (getNumberOfTargets() == 0) {
                return true;
            }
        }

        return !targets.isEmpty();
    }

    /**
     * Returns all possible different target combinations
     *
     * @param source
     * @param game
     * @return
     */
    @Override
    public List<? extends TargetImpl> getTargetOptions(Ability source, Game game) {
        List<TargetImpl> options = new ArrayList<>();
        List<UUID> possibleTargets = new ArrayList<>();
        possibleTargets.addAll(possibleTargets(source.getControllerId(), source, game));
        possibleTargets.removeAll(getTargets());

        // get the length of the array
        // e.g. for {'A','B','C','D'} => N = 4
        int N = possibleTargets.size();
        // not enough targets, return no option
        if (N < getNumberOfTargets()) {
            return options;
        }
        // not target but that's allowed, return one empty option
        if (N == 0) {
            TargetImpl target = this.copy();
            options.add(target);
            return options;
        }
        int maxK = getMaxNumberOfTargets() - getTargets().size();
        if (maxK > 5) { // Prevent endless iteration with targets set to INTEGER.maxvalue
            maxK = 5;
            if (N > 10) { // not more than 252 combinations
                maxK = 4;
            }
            if (N > 20) { // not more than 4845 combinations
                maxK = 3;
            }
        }
        if (N < maxK) { // less possible targets than the maximum allowed so reduce the max
            maxK = N;
        }
        int minK = getNumberOfTargets();
        if (getNumberOfTargets() == 0) { // add option without targets if possible
            TargetImpl target = this.copy();
            options.add(target);
            minK = 1;
        }
        for (int K = minK; K <= maxK; K++) {
            // get the combination by index
            // e.g. 01 --> AB , 23 --> CD
            int[] combination = new int[K];

            // position of current index
            //  if (r = 1)              r*
            //  index ==>        0   |   1   |   2
            //  element ==>      A   |   B   |   C
            int r = 0;
            int index = 0;

            while (r >= 0) {
                // possible indexes for 1st position "r=0" are "0,1,2" --> "A,B,C"
                // possible indexes for 2nd position "r=1" are "1,2,3" --> "B,C,D"

                // for r = 0 ==> index < (4+ (0 - 2)) = 2
                if (index <= (N + (r - K))) {
                    combination[r] = index;

                    // if we are at the last position print and increase the index
                    if (r == K - 1) {
                        //add the new target option
                        TargetImpl target = this.copy();
                        for (int i = 0; i < combination.length; i++) {
                            target.addTarget(possibleTargets.get(combination[i]), source, game, true);
                        }
                        options.add(target);
                        index++;
                    } else {
                        // select index for next position
                        index = combination[r] + 1;
                        r++;
                    }
                } else {
                    r--;
                    if (r > 0) {
                        index = combination[r] + 1;
                    } else {
                        index = combination[0] + 1;
                    }
                }
            }
        }
        return options;
    }

    @Override
    public List<UUID> getTargets() {
        return new ArrayList<>(targets.keySet());
    }

    @Override
    public int getTargetAmount(UUID targetId) {
        if (targets.containsKey(targetId)) {
            return targets.get(targetId);
        }
        return 0;
    }

    @Override
    public UUID getFirstTarget() {
        if (!targets.isEmpty()) {
            return targets.keySet().iterator().next();
        }
        return null;
    }

    @Override
    public boolean stillLegalTarget(UUID id, Ability source, Game game) {
        return canTarget(id, source, game);
    }

    @Override
    public void setNotTarget(boolean notTarget) {
        this.notTarget = notTarget;
    }

    @Override
    public boolean isRandom() {
        return this.atRandom;
    }

    @Override
    public void setRandom(boolean atRandom) {
        this.atRandom = atRandom;
    }

    @Override
    public void setTargetController(UUID playerId) {
        this.targetController = playerId;
    }

    @Override
    public UUID getTargetController() {
        return targetController;
    }

    @Override
    public void setAbilityController(UUID playerId) {
        this.abilityController = playerId;
    }

    @Override
    public UUID getAbilityController() {
        return abilityController;
    }

    @Override
    public Player getTargetController(Game game, UUID playerId) {
        if (getTargetController() != null) {
            return game.getPlayer(getTargetController());
        } else {
            return game.getPlayer(playerId);
        }
    }

    @Override
    public boolean isRequiredExplicitlySet() {
        return requiredExplicitlySet;
    }

    @Override
    public int getTargetTag() {
        return targetTag;
    }

    /**
     * Is used to be able to check, that another target is selected within the
     * group of targets of the ability with a target tag > 0.
     *
     * @param targetTag
     */
    @Override
    public void setTargetTag(int targetTag) {
        this.targetTag = targetTag;
    }

    @Override
    public Target getOriginalTarget() {
        return this;
    }

    @Override
    public void setTargetAmount(UUID targetId, int amount, Game game) {
        targets.put(targetId, amount);
        rememberZoneChangeCounter(targetId, game);
    }

    @Override
    public Target withChooseHint(String chooseHint) {
        this.chooseHint = chooseHint;
        return this;
    }

    @Override
    public String getChooseHint() {
        return chooseHint;
    }

    @Override
    public void setEventReporting(boolean shouldReport) {
        this.shouldReportEvents = shouldReport;
    }

    @Override
    public int getSize() {
        return targets.size();
    }

    @Override
    public boolean contains(UUID targetId) {
        return targets.containsKey(targetId);
    }
}
