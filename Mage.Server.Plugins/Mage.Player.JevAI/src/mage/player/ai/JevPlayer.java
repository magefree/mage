package mage.player.ai;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.Mode;
import mage.abilities.Modes;
import mage.abilities.TriggeredAbility;
import mage.cards.Card;
import mage.cards.Cards;
import mage.choices.Choice;
import mage.constants.Outcome;
import mage.constants.RangeOfInfluence;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.player.ai.jev.JevClient;
import mage.player.ai.jev.JevOptions;
import mage.player.ai.jev.JevQuestion;
import mage.player.ai.jev.JevAnswer;
import mage.player.ai.jev.JevState;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * An AI that does not search the game tree at all: every decision is a question
 * put to Jev, TypeSafe's System One model, over a plain description of the
 * current game. Code only collects the legal options and applies the answer.
 * <p>
 * The model is reached through OpenRouter, see {@link JevClient} for the
 * configuration. Without a key, or when a call fails, each decision falls back
 * to {@link ComputerPlayer}'s default so a game never stalls.
 */
public class JevPlayer extends ComputerPlayer {

    private static final Logger logger = Logger.getLogger(JevPlayer.class);

    private static final String PASS = "pass";
    private static final String DONE = "done";
    private static final String NO_ATTACK = "do not attack";
    private static final String NO_BLOCK = "do not block";
    private static final int MAX_AMOUNT_OPTIONS = 11;

    private static volatile boolean clientLoaded;
    private static volatile JevClient client;

    public JevPlayer(String name, RangeOfInfluence range, int skill) {
        super(name, range);
    }

    protected JevPlayer(UUID id) {
        super(id);
    }

    public JevPlayer(final JevPlayer player) {
        super(player);
    }

    @Override
    public JevPlayer copy() {
        return new JevPlayer(this);
    }

    /**
     * Shared client, built once from the environment. Null means no key is
     * configured and every decision uses the fallback.
     */
    private static JevClient jev() {
        if (!clientLoaded) {
            synchronized (JevPlayer.class) {
                if (!clientLoaded) {
                    client = JevClient.fromEnvironment();
                    clientLoaded = true;
                }
            }
        }
        return client;
    }

    /**
     * One choice question over the current game.
     *
     * @return the chosen key, or null when there is no answer to act on
     */
    private String pick(Game game, String instructions, JevOptions<?> options) {
        JevClient jevClient = jev();
        if (jevClient == null || options.isEmpty()) {
            return null;
        }
        String picked = jevClient.choose(JevState.of(game, getId()), instructions, options.criteria());
        if (picked == null) {
            return null;
        }
        if (!options.contains(picked)) {
            logger.warn("Jev: unknown option '" + picked + "' for: " + instructions);
            return null;
        }
        logger.debug("Jev (" + getName() + ") picked '" + picked + "' for: " + instructions);
        return picked;
    }

    /**
     * One yes/no question over the current game.
     */
    private Boolean ask(Game game, String instructions) {
        JevClient jevClient = jev();
        if (jevClient == null) {
            return null;
        }
        double probability = jevClient.noul(JevState.of(game, getId()), instructions, -1.0);
        if (probability < 0) {
            return null;
        }
        logger.debug("Jev (" + getName() + ") answered " + probability + " for: " + instructions);
        return probability >= 0.5;
    }

    private String who() {
        return "You are playing Magic: The Gathering as " + getName() + ", described in `me`. ";
    }

    // --- mulligan ---

    @Override
    public boolean chooseMulligan(Game game) {
        Boolean answer = ask(game, who()
                + "Judge the opening hand in `me.hand`: it has " + getHand().size() + " cards. "
                + "Should this hand be mulliganed away for a new one, one card smaller?");
        return answer == null ? super.chooseMulligan(game) : answer;
    }

    // --- priority: which action to take ---

    @Override
    public boolean priority(Game game) {
        List<ActivatedAbility> playable = getPlayable(game, true);
        if (jev() == null || playable.isEmpty()) {
            pass(game);
            return false;
        }

        JevOptions<ActivatedAbility> options = new JevOptions<>();
        for (ActivatedAbility ability : playable) {
            // mana abilities are not moves, the engine taps them when a cost is paid
            if (ability.isManaAbility()) {
                continue;
            }
            options.add(label(game, ability), description(game, ability), ability);
        }
        if (options.isEmpty()) {
            pass(game);
            return false;
        }
        options.add(PASS, "Take no action right now and pass priority. "
                + "Only right when every other option is unaffordable or actively bad.", null);

        String picked = pick(game, who()
                + "It is turn " + game.getTurnNum() + ", step " + game.getTurnStepType()
                + (getId().equals(game.getActivePlayerId()) ? ", your turn" : ", the opponent's turn") + ". "
                + "The options below are only the ones you can afford right now, so cost is not a reason to pass. "
                + "You win by reducing an opponent to 0 life, and you cannot do that without permanents on the "
                + "battlefield: play a land while `me.land_drop_still_available` is true, then cast the strongest "
                + "thing your mana allows, preferring creatures that can attack later. "
                + "Which single action do you take?", options);

        ActivatedAbility chosen = picked == null ? null : options.get(picked);
        if (chosen == null) {
            pass(game);
            return false;
        }

        game.getState().setPriorityPlayerId(getId());
        game.firePriorityEvent(getId());
        logger.info("Jev (" + getName() + ") plays: " + picked);
        if (activateAbility(chosen, game)) {
            return true;
        }
        pass(game);
        return false;
    }

    private String label(Game game, Ability ability) {
        MageObject source = game.getObject(ability.getSourceId());
        String rule = JevState.rule(ability.getRule());
        String name = source == null ? "" : source.getName();
        if (name == null || name.isEmpty()) {
            return rule;
        }
        return rule.isEmpty() ? name : name + " - " + rule;
    }

    private String description(Game game, Ability ability) {
        StringBuilder text = new StringBuilder(label(game, ability));
        String manaCost = ability.getManaCostsToPay().getText();
        if (manaCost != null && !manaCost.isEmpty()) {
            text.append(" Mana cost: ").append(manaCost).append('.');
        }
        String otherCosts = ability.getCosts().getText();
        if (otherCosts != null && !otherCosts.isEmpty()) {
            text.append(" Other cost: ").append(otherCosts).append('.');
        }
        text.append(symmetryWarning(game, ability));
        return text.toString();
    }

    /**
     * Sweepers and sacrifice costs hit the caster too, and the rules text does not
     * say who ends up worse off. Counting both boards is a fact code can supply.
     */
    private String symmetryWarning(Game game, Ability ability) {
        String rule = (label(game, ability) + ' ' + ability.getRule()).toLowerCase();
        boolean sweeper = rule.contains("each creature") || rule.contains("all creatures")
                || rule.contains("each player") || rule.contains("destroy all");
        boolean sacrifice = rule.contains("sacrifice a") || rule.contains("sacrifice another")
                || rule.contains("sacrifice two");
        if (!sweeper && !sacrifice) {
            return "";
        }
        int mine = countCreatures(game, getId());
        int theirs = 0;
        for (UUID opponentId : game.getOpponents(getId(), true)) {
            theirs += countCreatures(game, opponentId);
        }
        return " WARNING: this hits your own side too. You control " + mine
                + " creatures, your opponents control " + theirs
                + ". Taking it while you have as many creatures as them, or more, loses you the board.";
    }

    private int countCreatures(Game game, UUID playerId) {
        int count = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
            if (permanent.isCreature(game)) {
                count++;
            }
        }
        return count;
    }

    // --- combat ---

    @Override
    public void selectAttackers(Game game, UUID attackingPlayerId) {
        JevClient jevClient = jev();
        if (jevClient == null) {
            return;
        }
        List<Permanent> attackers = getAvailableAttackers(game);
        if (attackers.isEmpty()) {
            return;
        }
        Set<UUID> defenderIds = game.getCombat().getDefenders();
        if (defenderIds.isEmpty()) {
            return;
        }

        // what their board could do back, the other half of every attack decision
        int theirPower = 0;
        int theirBlockers = 0;
        for (UUID defenderId : defenderIds) {
            Player defender = game.getPlayer(defenderId);
            if (defender == null) {
                continue;
            }
            for (Permanent permanent : defender.getAvailableBlockers(game)) {
                theirBlockers++;
                theirPower += permanent.getPower().getValue();
            }
        }
        String threat = "You are at " + getLife() + " life. Your opponents have " + theirBlockers
                + " creatures that can block (" + theirPower + " total power), and those same creatures "
                + "can attack you back next turn.";

        // every creature is judged on the same state, so ask about all of them at once
        Map<String, JevQuestion> questions = new LinkedHashMap<>();
        Map<String, JevOptions<UUID>> optionsByQuestion = new LinkedHashMap<>();
        Map<String, Permanent> attackerByQuestion = new LinkedHashMap<>();
        int index = 0;
        for (Permanent attacker : attackers) {
            JevOptions<UUID> options = new JevOptions<>();
            for (UUID defenderId : defenderIds) {
                if (attacker.canAttack(defenderId, game)) {
                    options.add(defenderName(game, defenderId), "Attack " + defenderName(game, defenderId) + '.', defenderId);
                }
            }
            if (options.isEmpty()) {
                continue;
            }
            options.add(NO_ATTACK, "Keep this creature back, untapped and able to block.", null);

            String id = "attacker_" + index++;
            questions.put(id, JevQuestion.choice(who()
                    + threat + " Your creature " + attacker.getName() + " ("
                    + attacker.getPower().getValue() + '/' + attacker.getToughness().getValue()
                    + ", rules: " + JevState.rule(String.join(" ", attacker.getRules(game)))
                    + ") can attack this turn. An attacking creature is tapped and cannot block on the next turn, "
                    + "so weigh the damage it deals against what their counterattack would do to you. "
                    + "Where should it attack, if at all?", options.criteria()));
            optionsByQuestion.put(id, options);
            attackerByQuestion.put(id, attacker);
        }
        if (questions.isEmpty()) {
            return;
        }

        Map<String, JevAnswer> answers = jevClient.ask(JevState.of(game, getId()), questions);
        for (Map.Entry<String, JevAnswer> entry : answers.entrySet()) {
            JevOptions<UUID> options = optionsByQuestion.get(entry.getKey());
            Permanent attacker = attackerByQuestion.get(entry.getKey());
            if (options == null || attacker == null) {
                continue;
            }
            UUID defenderId = options.get(entry.getValue().getChoice());
            if (defenderId != null) {
                logger.info("Jev (" + getName() + ") attacks with " + attacker.getName()
                        + " into " + defenderName(game, defenderId));
                this.declareAttacker(attacker.getId(), defenderId, game, false);
            }
        }
    }

    @Override
    public void selectBlockers(Ability source, Game game, UUID defendingPlayerId) {
        JevClient jevClient = jev();
        if (jevClient == null) {
            return;
        }
        List<Permanent> blockers = getAvailableBlockers(game);
        List<CombatGroup> groups = game.getCombat().getGroups();
        if (blockers.isEmpty() || groups.isEmpty()) {
            return;
        }

        // the two numbers the decision actually turns on
        int damage = 0;
        for (CombatGroup group : groups) {
            for (UUID attackerId : group.getAttackers()) {
                Permanent attacker = game.getPermanent(attackerId);
                if (attacker != null) {
                    damage += attacker.getPower().getValue();
                }
            }
        }
        int life = getLife();
        String incoming = "Unblocked, the attackers deal " + damage + " damage and you are at " + life
                + " life, which leaves you at " + (life - damage) + (damage >= life ? " - you lose the game." : ".");

        Map<String, JevQuestion> questions = new LinkedHashMap<>();
        Map<String, JevOptions<UUID>> optionsByQuestion = new LinkedHashMap<>();
        Map<String, Permanent> blockerByQuestion = new LinkedHashMap<>();
        int index = 0;
        for (Permanent blocker : blockers) {
            JevOptions<UUID> options = new JevOptions<>();
            for (CombatGroup group : groups) {
                for (UUID attackerId : group.getAttackers()) {
                    Permanent attacker = game.getPermanent(attackerId);
                    if (attacker == null || !blocker.canBlock(attackerId, game)) {
                        continue;
                    }
                    options.add(attacker.getName(), "Block " + attacker.getName() + " ("
                            + attacker.getPower().getValue() + '/' + attacker.getToughness().getValue()
                            + ", rules: " + JevState.rule(String.join(" ", attacker.getRules(game))) + ").", attackerId);
                }
            }
            if (options.isEmpty()) {
                continue;
            }
            options.add(NO_BLOCK, "Do not block, let the damage through and keep this creature alive.", null);

            String id = "blocker_" + index++;
            questions.put(id, JevQuestion.choice(who()
                    + "You are being attacked. " + incoming + " Your creature " + blocker.getName() + " ("
                    + blocker.getPower().getValue() + '/' + blocker.getToughness().getValue()
                    + ", rules: " + JevState.rule(String.join(" ", blocker.getRules(game)))
                    + ") can block. Weigh the life you would lose against losing the creature: "
                    + "a creature is worth less than the game, so chump block when the damage would kill you. "
                    + "Which attacker should it block, if any?", options.criteria()));
            optionsByQuestion.put(id, options);
            blockerByQuestion.put(id, blocker);
        }
        if (questions.isEmpty()) {
            return;
        }

        Map<String, JevAnswer> answers = jevClient.ask(JevState.of(game, getId()), questions);
        for (Map.Entry<String, JevAnswer> entry : answers.entrySet()) {
            JevOptions<UUID> options = optionsByQuestion.get(entry.getKey());
            Permanent blocker = blockerByQuestion.get(entry.getKey());
            if (options == null || blocker == null) {
                continue;
            }
            UUID attackerId = options.get(entry.getValue().getChoice());
            if (attackerId != null) {
                Permanent attacker = game.getPermanent(attackerId);
                logger.info("Jev (" + getName() + ") blocks " + (attacker == null ? attackerId : attacker.getName())
                        + " with " + blocker.getName());
                this.declareBlocker(getId(), blocker.getId(), attackerId, game);
            }
        }
    }

    // --- yes/no prompts ---

    @Override
    public boolean chooseUse(Outcome outcome, String message, String secondMessage, String trueText, String falseText,
                             Ability source, Game game) {
        if (outcome == Outcome.AIDontUseIt) {
            return false;
        }
        if (payManaMode) {
            return super.chooseUse(outcome, message, secondMessage, trueText, falseText, source, game);
        }
        StringBuilder instructions = new StringBuilder(who())
                .append(sourceName(game, source)).append(" asks: \"").append(JevState.rule(message)).append("\" ");
        if (secondMessage != null && !secondMessage.isEmpty()) {
            instructions.append('(').append(JevState.rule(secondMessage)).append(") ");
        }
        if (trueText != null && falseText != null) {
            instructions.append("Yes means \"").append(trueText).append("\", no means \"").append(falseText).append("\". ");
        }
        if (source != null) {
            instructions.append("It comes from this card: \"").append(JevState.rule(source.getRule())).append("\" ");
        }
        instructions.append("Saying yes costs you nothing beyond what that text states. ")
                .append("Is taking it better for you than declining?");

        JevClient jevClient = jev();
        if (jevClient == null) {
            return super.chooseUse(outcome, message, secondMessage, trueText, falseText, source, game);
        }
        double probability = jevClient.noul(JevState.of(game, getId()), instructions.toString(), -1.0);
        if (probability < 0) {
            return super.chooseUse(outcome, message, secondMessage, trueText, falseText, source, game);
        }
        // the engine already classifies the effect, so the bar to clear depends on it:
        // free upside needs only a weak yes, a bad outcome needs a strong one
        double threshold = outcome.isGood() ? 0.35 : 0.65;
        logger.debug("Jev (" + getName() + ") answered " + probability + " (needs " + threshold
                + ") for: " + instructions);
        return probability >= threshold;
    }

    // --- targets ---

    @Override
    public boolean choose(Outcome outcome, Target target, Ability source, Game game) {
        return choose(outcome, target, source, game, null);
    }

    @Override
    public boolean choose(Outcome outcome, Target target, Ability source, Game game, Map<String, Serializable> options) {
        return chooseTargets(outcome, target, source, game, null)
                || super.choose(outcome, target, source, game, options);
    }

    @Override
    public boolean chooseTarget(Outcome outcome, Target target, Ability source, Game game) {
        return chooseTargets(outcome, target, source, game, null)
                || super.chooseTarget(outcome, target, source, game);
    }

    @Override
    public boolean chooseTarget(Outcome outcome, Cards cards, TargetCard target, Ability source, Game game) {
        return chooseTargets(outcome, target, source, game, cards)
                || super.chooseTarget(outcome, cards, target, source, game);
    }

    @Override
    public boolean choose(Outcome outcome, Cards cards, TargetCard target, Ability source, Game game) {
        return chooseTargets(outcome, target, source, game, cards)
                || super.choose(outcome, cards, target, source, game);
    }

    /**
     * Fills a target one pick at a time, each pick a question over the state as
     * it stands. Returns false when the target could not be completed, so the
     * caller can fall back.
     */
    private boolean chooseTargets(Outcome outcome, Target target, Ability source, Game game, Cards fromCards) {
        // same reason as choose(Choice): during a payment the engine picks what to tap
        if (jev() == null || payManaMode) {
            return false;
        }
        UUID abilityControllerId = target.getAffectedAbilityControllerId(getId());
        if (target.isChoiceCompleted(abilityControllerId, source, game, fromCards)) {
            return false;
        }

        while (!target.isChoiceCompleted(abilityControllerId, source, game, fromCards)) {
            Set<UUID> possible = fromCards == null
                    ? target.possibleTargets(abilityControllerId, source, game)
                    : target.possibleTargets(abilityControllerId, source, game, fromCards.copy());
            if (possible.isEmpty()) {
                break;
            }

            JevOptions<UUID> options = new JevOptions<>();
            for (UUID id : possible) {
                options.add(objectName(game, id), describeTarget(game, id), id);
            }
            boolean canStop = target.getTargets().size() >= target.getMinNumberOfTargets();
            if (canStop) {
                options.add(DONE, "Select nothing more, the current selection is enough.", null);
            }

            // when every candidate is yours and the effect is bad, this is not aiming
            // at something, it is giving something up: discards, sacrifices, costs
            boolean ownLoss = !outcome.isGood() && allMine(game, possible);
            String framing = ownLoss
                    ? "Every option below is something of YOURS that you will LOSE. Pick the one you can most "
                    + "afford to lose: keep creatures and cards you can cast soon, give up extra lands you "
                    + "already have on the battlefield and cards you cannot pay for."
                    : "This effect is " + (outcome.isGood() ? "good" : "bad") + " for whoever it hits.";

            String picked = pick(game, who()
                    + "An effect of " + sourceName(game, source) + " asks: \""
                    + JevState.rule(target.getMessage(game)) + "\". " + framing
                    + " You may select " + target.getMinNumberOfTargets() + " to " + target.getMaxNumberOfTargets()
                    + " (" + target.getTargets().size() + " selected so far). Which one do you select next?", options);

            UUID chosen = picked == null ? null : options.get(picked);
            if (chosen == null) {
                // an answer of "done", an unusable answer or a failed call
                break;
            }
            if (target.isNotTarget()) {
                target.add(chosen, game);
            } else {
                target.addTarget(chosen, source, game);
            }
        }

        return target.isChosen(game) && !target.getTargets().isEmpty();
    }

    /**
     * True when every candidate belongs to this player, which turns a "bad"
     * effect into a choice about what to give up.
     */
    private boolean allMine(Game game, Set<UUID> candidates) {
        if (candidates.isEmpty()) {
            return false;
        }
        for (UUID id : candidates) {
            if (game.getPlayer(id) != null) {
                return false; // a player option means it is aimed at somebody
            }
            Permanent permanent = game.getPermanent(id);
            if (permanent != null) {
                if (!permanent.isControlledBy(getId())) {
                    return false;
                }
                continue;
            }
            Card card = game.getCard(id);
            if (card == null || !card.isOwnedBy(getId())) {
                return false;
            }
        }
        return true;
    }

    private String describeTarget(Game game, UUID id) {
        Player player = game.getPlayer(id);
        if (player != null) {
            return "The player " + player.getName() + ", at " + player.getLife() + " life.";
        }
        Permanent permanent = game.getPermanent(id);
        if (permanent != null) {
            return String.valueOf(JevState.describe(game, permanent));
        }
        MageObject object = game.getObject(id);
        if (object == null) {
            return "Unknown object.";
        }
        if (object instanceof Card) {
            return object.getName() + ": " + JevState.rule(String.join(" ", ((Card) object).getRules(game)));
        }
        return object.getName();
    }

    private String objectName(Game game, UUID id) {
        Player player = game.getPlayer(id);
        if (player != null) {
            return player.getName();
        }
        MageObject object = game.getObject(id);
        return object == null ? id.toString() : object.getName();
    }

    private String sourceName(Game game, Ability source) {
        if (source == null) {
            return "an effect";
        }
        MageObject object = game.getObject(source.getSourceId());
        return object == null ? "an effect" : object.getName();
    }

    private String defenderName(Game game, UUID defenderId) {
        Player player = game.getPlayer(defenderId);
        if (player != null) {
            return player.getName() + " (" + player.getLife() + " life)";
        }
        Permanent permanent = game.getPermanent(defenderId);
        return permanent == null ? defenderId.toString() : permanent.getName();
    }

    // --- other dialogs ---

    @Override
    public boolean choose(Outcome outcome, Choice choice, Game game) {
        // picking a color while paying a cost is bookkeeping, and ComputerPlayer
        // already derives it from the unpaid mana: asking the model there both
        // wastes a call and can pick a color that cannot pay
        if (jev() == null || payManaMode) {
            return super.choose(outcome, choice, game);
        }
        JevOptions<String> options = new JevOptions<>();
        if (choice.isKeyChoice()) {
            for (Map.Entry<String, String> entry : choice.getKeyChoices().entrySet()) {
                options.add(entry.getValue(), entry.getValue(), entry.getKey());
            }
        } else {
            for (String value : choice.getChoices()) {
                options.add(value, value, value);
            }
        }
        if (options.isEmpty()) {
            return super.choose(outcome, choice, game);
        }

        String picked = pick(game, who() + "The game asks: \"" + JevState.rule(choice.getMessage())
                + "\". Which value serves you best here?", options);
        String value = picked == null ? null : options.get(picked);
        if (value == null) {
            return super.choose(outcome, choice, game);
        }
        if (choice.isKeyChoice()) {
            choice.setChoiceByKey(value);
        } else {
            choice.setChoice(value);
        }
        return true;
    }

    @Override
    public Mode chooseMode(Modes modes, Ability source, Game game) {
        if (jev() == null) {
            return super.chooseMode(modes, source, game);
        }
        JevOptions<Mode> options = new JevOptions<>();
        for (Mode mode : modes.getAvailableModes(source, game)) {
            if (!modes.isMayChooseSameModeMoreThanOnce() && modes.getSelectedModes().contains(mode.getId())) {
                continue;
            }
            if (!mode.getTargets().canChoose(source.getControllerId(), source, game)) {
                continue;
            }
            String text = JevState.rule(mode.getEffects().getText(mode));
            options.add(text, text, mode);
        }
        if (options.size() < 2) {
            return super.chooseMode(modes, source, game);
        }

        String picked = pick(game, who() + "You are casting " + sourceName(game, source)
                + " and must pick one of its modes. Which mode helps you most in this position?", options);
        Mode mode = picked == null ? null : options.get(picked);
        return mode == null ? super.chooseMode(modes, source, game) : mode;
    }

    @Override
    public TriggeredAbility chooseTriggeredAbility(List<TriggeredAbility> abilities, Game game) {
        if (jev() == null || abilities.size() < 2) {
            return super.chooseTriggeredAbility(abilities, game);
        }
        JevOptions<TriggeredAbility> options = new JevOptions<>();
        for (TriggeredAbility ability : abilities) {
            options.add(label(game, ability), description(game, ability), ability);
        }
        String picked = pick(game, who()
                + "Several of your triggered abilities are waiting to go on the stack. "
                + "Which one should resolve first?", options);
        TriggeredAbility chosen = picked == null ? null : options.get(picked);
        return chosen == null ? super.chooseTriggeredAbility(abilities, game) : chosen;
    }

    @Override
    public int chooseReplacementEffect(Map<String, String> effectsMap, Map<String, MageObject> objectsMap, Game game) {
        if (jev() == null || effectsMap.size() < 2) {
            return super.chooseReplacementEffect(effectsMap, objectsMap, game);
        }
        JevOptions<Integer> options = new JevOptions<>();
        List<String> keys = new ArrayList<>(effectsMap.keySet());
        for (int i = 0; i < keys.size(); i++) {
            String text = JevState.rule(effectsMap.get(keys.get(i)));
            options.add(text, text, i);
        }
        String picked = pick(game, who()
                + "Several replacement effects apply to the same event and you choose the order. "
                + "Which one should be applied first?", options);
        Integer index = picked == null ? null : options.get(picked);
        return index == null ? super.chooseReplacementEffect(effectsMap, objectsMap, game) : index;
    }

    // --- numbers ---

    @Override
    public int getAmount(int min, int max, String message, Ability source, Game game) {
        Integer amount = chooseAmount(min, max, message, source, game);
        return amount == null ? super.getAmount(min, max, message, source, game) : amount;
    }

    @Override
    public int announceX(int min, int max, String message, Game game, Ability source, boolean isManaPay) {
        if (isManaPay) {
            // paying X is arithmetic on available mana, code does that better
            return super.announceX(min, max, message, game, source, isManaPay);
        }
        Integer amount = chooseAmount(min, max, message, source, game);
        return amount == null ? super.announceX(min, max, message, game, source, isManaPay) : amount;
    }

    private Integer chooseAmount(int min, int max, String message, Ability source, Game game) {
        if (min >= max) {
            return min;
        }
        if (jev() == null) {
            return null;
        }
        // an unbounded maximum is not a real choice, keep the list short
        int realMax = Math.min(max, min + MAX_AMOUNT_OPTIONS - 1);
        JevOptions<Integer> options = new JevOptions<>();
        for (int value = min; value <= realMax; value++) {
            options.add(String.valueOf(value), "The amount is " + value + '.', value);
        }
        String picked = pick(game, who() + "An effect of " + sourceName(game, source) + " asks: \""
                + JevState.rule(message) + "\". Which amount between " + min + " and " + realMax
                + " is best for you?", options);
        return picked == null ? null : options.get(picked);
    }
}
