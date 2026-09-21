package mage.player.ai;

import mage.MageObject;
import mage.abilities.Ability;
import mage.constants.RangeOfInfluence;
import mage.game.Game;
import mage.player.ai.jev.JevAnswer;
import mage.player.ai.jev.JevClient;
import mage.player.ai.jev.JevOptions;
import mage.player.ai.jev.JevQuestion;
import mage.player.ai.jev.JevState;
import mage.player.ai.score.GameStateEvaluator2;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The stock XMage bot with Jev on both ends of its search.
 * <p>
 * The search is what it is good at: it plays out lines and scores the resulting
 * board. What it cannot do is judge which lines deserve the time, or pick
 * between lines whose scores are close but whose plans differ. So:
 * <ol>
 * <li><b>Before the search</b>, when the root has more candidate plays than the
 * search can explore well, Jev answers one yes/no per play - is this worth
 * simulating? - and the rest are dropped. Fewer branches means the same time
 * budget goes deeper on the plays that matter.</li>
 * <li><b>After the search</b>, the best lines come back with real scores. Jev
 * picks among the top ones, seeing each line's plays and what the search thinks
 * it is worth.</li>
 * </ol>
 * Everything else - targeting, combat, mana - stays with {@link ComputerPlayer7},
 * which resolves it by simulation. Without a key, or when a call fails, this
 * plays exactly like the stock bot.
 */
public class JevHybridPlayer extends ComputerPlayer7 {

    private static final Logger logger = Logger.getLogger(JevHybridPlayer.class);

    /**
     * Only prune when the root has more plays than this: below it the search
     * covers everything anyway and a call would buy nothing.
     */
    private static final int PRUNE_ABOVE = 5;

    /**
     * Never prune below this many plays, whatever the model says.
     */
    private static final int KEEP_AT_LEAST = 3;

    /**
     * How many scored lines to offer at the end.
     */
    private static final int LINES_OFFERED = 4;

    private static final double WORTH_SIMULATING = 0.5;

    /**
     * Pruning is OFF by default because it measured worse: over 10 games each
     * against the stock bot, pruning scored 3-6-1 and not pruning 3-1-2 (rest of
     * that series below). Judging a play in isolation, before the search has said
     * what it leads to, throws away lines the search would have ranked properly.
     * Turn it back on with -Djev.hybrid.prune=true to re-measure.
     */
    private static final boolean PRUNE_ENABLED = "true".equalsIgnoreCase(System.getProperty("jev.hybrid.prune"));

    /**
     * A line is only offered when its score is within this much of the best one.
     * Further back than that and the search is not being blind, it is being right:
     * that gap is material or life it has actually counted.
     */
    private static final int OFFER_WITHIN = 50;
    private static final double OFFER_WITHIN_FRACTION = 0.02;

    private static volatile boolean clientLoaded;
    private static volatile JevClient client;

    /**
     * Set while the root's plays are being collected: the search calls optimize()
     * for every node it visits, and only the root's call is worth a question.
     */
    // volatile: the search runs addActions() on a pool thread, which is where
    // optimize() is called from
    private transient volatile boolean pruneNextOptimize;

    /**
     * Score of every line the search tried from the root, filled while it runs.
     * The engine's alpha-beta keeps only the winning child on the node itself, so
     * the alternatives have to be caught on the way past. Written from the search
     * thread, read after it joins.
     */
    private final transient Map<SimulationNode2, Integer> rootLines = new ConcurrentHashMap<>();
    private transient volatile SimulationNode2 collectingFor;

    public JevHybridPlayer(String name, RangeOfInfluence range, int skill) {
        super(name, range, skill);
    }

    public JevHybridPlayer(final JevHybridPlayer player) {
        super(player);
    }

    @Override
    public JevHybridPlayer copy() {
        return new JevHybridPlayer(this);
    }

    private static JevClient jev() {
        if (!clientLoaded) {
            synchronized (JevHybridPlayer.class) {
                if (!clientLoaded) {
                    client = JevClient.fromEnvironment();
                    clientLoaded = true;
                }
            }
        }
        return client;
    }

    // --- 1. before the search: which plays are worth simulating ---

    @Override
    protected void optimize(Game game, List<Ability> allActions) {
        super.optimize(game, allActions); // the bot's own ordering and cleanups
        if (!pruneNextOptimize) {
            return;
        }
        pruneNextOptimize = false; // deeper nodes keep the full list

        JevClient jevClient = jev();
        if (jevClient == null || !PRUNE_ENABLED || allActions.size() <= PRUNE_ABOVE) {
            return;
        }

        // one independent yes/no per play, answered in a single call
        Map<String, JevQuestion> questions = new LinkedHashMap<>();
        Map<String, Ability> abilityByQuestion = new LinkedHashMap<>();
        int index = 0;
        for (Ability ability : allActions) {
            String id = "play_" + index++;
            questions.put(id, JevQuestion.noul(
                    "You are playing Magic: The Gathering as " + getName() + ", described in `me`. "
                            + "Your search engine can only play out a few lines properly this turn, so it needs "
                            + "the candidates narrowed down. The candidate is: " + describe(game, ability) + ". "
                            + "Is this play worth spending search time on, as opposed to obviously pointless or "
                            + "clearly worse than the other things you can do right now?"));
            abilityByQuestion.put(id, ability);
        }

        Map<String, JevAnswer> answers = jevClient.ask(JevState.of(game, getId()), questions);
        if (answers.isEmpty()) {
            return; // the bot searches everything, as before
        }

        List<Ability> dropped = new ArrayList<>();
        for (Map.Entry<String, JevAnswer> entry : answers.entrySet()) {
            if (entry.getValue().getNoul() < WORTH_SIMULATING) {
                Ability ability = abilityByQuestion.get(entry.getKey());
                if (ability != null) {
                    dropped.add(ability);
                }
            }
        }
        // the engine's own order is by promise, so give up the least promising drops first
        int mayDrop = allActions.size() - KEEP_AT_LEAST;
        if (mayDrop <= 0 || dropped.isEmpty()) {
            return;
        }

        int removed = 0;
        for (Iterator<Ability> it = allActions.iterator(); it.hasNext() && removed < mayDrop; ) {
            Ability ability = it.next();
            if (contains(dropped, ability)) {
                it.remove();
                removed++;
            }
        }
        if (removed > 0) {
            logger.info("Jev (" + getName() + ") pruned " + removed + " of "
                    + (allActions.size() + removed) + " candidate plays before searching");
        }
    }

    private static boolean contains(List<Ability> abilities, Ability wanted) {
        for (Ability ability : abilities) {
            if (ability == wanted) {
                return true;
            }
        }
        return false;
    }

    /**
     * The first recursion level is one candidate line each - the root's own plays -
     * and the returned value is what the search thinks that line is worth. The
     * engine puts step nodes between the root and these, so depth identifies them
     * rather than parentage.
     */
    @Override
    protected int addActions(SimulationNode2 node, int depth, int alpha, int beta) {
        int score = super.addActions(node, depth, alpha, beta);
        if (collectingFor != null
                && depth == maxDepth - 1
                && node != null
                && node.getAbilities() != null
                && !node.getAbilities().isEmpty()
                && score != Integer.MIN_VALUE
                && score != Integer.MAX_VALUE) {
            rootLines.put(node, score);
        }
        return score;
    }

    // --- 2. after the search: which scored line to follow ---

    @Override
    protected void calculateActions(Game game) {
        JevClient jevClient = jev();
        if (jevClient == null) {
            super.calculateActions(game);
            return;
        }
        if (getNextAction(game)) {
            return; // a line is already queued up
        }

        currentScore = GameStateEvaluator2.evaluate(getId(), game).getTotalScore();
        Game sim = createSimulation(game);
        SimulationNode2.resetCount();
        root = new SimulationNode2(null, sim, maxDepth, getId());

        pruneNextOptimize = true; // the next optimize() call is the root's
        rootLines.clear();
        collectingFor = root;
        addActionsTimed();
        collectingFor = null;
        pruneNextOptimize = false;

        if (root == null || root.children == null || root.children.isEmpty()) {
            logger.info("AI player can't find next action: " + getName());
            return;
        }

        SimulationNode2 searchPick = root.children.get(0);
        SimulationNode2 chosen = chooseLine(game, searchPick, candidateLines());
        root = chosen == null ? searchPick : chosen;

        // keep the stock guard against repeating a free action forever
        boolean doThis = true;
        if (root.abilities.size() == 1) {
            for (Ability ability : root.abilities) {
                if (ability.getManaCosts().manaValue() == 0
                        && ability.getCosts().isEmpty()
                        && actionCache.contains(ability.getRule() + '_' + ability.getSourceId())) {
                    doThis = false;
                }
            }
        }
        if (doThis) {
            actions = new LinkedList<>(root.abilities);
            combat = root.combat;
            for (Ability ability : actions) {
                actionCache.add(ability.getRule() + '_' + ability.getSourceId());
            }
        }
    }

    /**
     * The lines the search tried from the root, best score first.
     */
    private List<SimulationNode2> candidateLines() {
        List<SimulationNode2> lines = new ArrayList<>(rootLines.keySet());
        Collections.sort(lines, new Comparator<SimulationNode2>() {
            @Override
            public int compare(SimulationNode2 left, SimulationNode2 right) {
                return rootLines.get(right).compareTo(rootLines.get(left));
            }
        });
        return lines;
    }

    /**
     * Asks Jev to pick among the best scored lines. Returns null to leave the
     * search's own pick alone.
     */
    private SimulationNode2 chooseLine(Game game, SimulationNode2 searchPick, List<SimulationNode2> lines) {
        if (lines.size() < 2) {
            logger.debug("Jev (" + getName() + ") search left " + lines.size()
                    + " comparable lines, keeping its pick");
            return null;
        }
        List<SimulationNode2> candidates = lines.subList(0, Math.min(LINES_OFFERED, lines.size()));

        JevOptions<SimulationNode2> options = new JevOptions<>();
        int bestScore = rootLines.get(candidates.get(0));
        int margin = Math.max(OFFER_WITHIN, (int) Math.abs(bestScore * OFFER_WITHIN_FRACTION));
        for (SimulationNode2 node : candidates) {
            String plays = describeLine(game, node);
            if (plays.isEmpty()) {
                continue;
            }
            int score = rootLines.get(node);
            if (score < bestScore - margin) {
                continue; // the search has counted a real difference here
            }
            int gain = score - currentScore;
            options.add(plays, plays + " The search scores the board after this line at " + score
                    + " (" + (gain >= 0 ? "+" : "") + gain + " against doing nothing, "
                    + (score - bestScore) + " against the best-scored line).", node);
        }
        if (options.size() < 2) {
            return null;
        }

        String picked = jev().choose(JevState.of(game, getId()),
                "You are playing Magic: The Gathering as " + getName() + ", described in `me`. "
                        + "Your search engine played out these lines and scored the board each one leads to. "
                        + "The score counts material and life, so it is good at close combat maths and blind to "
                        + "plans: it cannot see that a line hands the opponent the game next turn, wastes a card "
                        + "that wins later, or walks into what their open mana and cards suggest. "
                        + "These lines score within " + margin + " points of each other, so the score cannot "
                        + "separate them: pick the one whose plan is better. Which line do you play?",
                options.criteria());

        SimulationNode2 chosen = options.get(picked);
        if (chosen == null) {
            logger.info("Jev (" + getName() + ") gave no usable line, keeping the search's pick");
        } else if (chosen == searchPick) {
            logger.info("Jev (" + getName() + ") agreed with the search: '" + picked + "' (score " + bestScore + ')');
        } else {
            logger.info("Jev (" + getName() + ") overrode the search: took '" + picked
                    + "' (score " + rootLines.get(chosen) + ") over score " + bestScore);
        }
        return chosen;
    }

    private String describeLine(Game game, SimulationNode2 node) {
        StringBuilder text = new StringBuilder();
        for (Ability ability : node.getAbilities()) {
            if (text.length() > 0) {
                text.append(", then ");
            }
            text.append(describe(game, ability));
        }
        return text.toString();
    }

    private String describe(Game game, Ability ability) {
        MageObject source = game.getObject(ability.getSourceId());
        String name = source == null ? "" : source.getName();
        String rule = JevState.rule(ability.getRule());
        if (name == null || name.isEmpty()) {
            return rule.isEmpty() ? String.valueOf(ability) : rule;
        }
        return rule.isEmpty() ? name : name + " - " + rule;
    }

    // --- mulligan stays a judgement, the search cannot see a shuffle ---

    @Override
    public boolean chooseMulligan(Game game) {
        JevClient jevClient = jev();
        if (jevClient == null) {
            return super.chooseMulligan(game);
        }
        double probability = jevClient.noul(JevState.of(game, getId()),
                "You are playing Magic: The Gathering as " + getName() + ". Judge the opening hand in `me.hand`: "
                        + "it has " + getHand().size() + " cards. A hand with no lands, or with almost nothing but "
                        + "lands, cannot function; a hand one card smaller that works beats a full one that does not. "
                        + "Should this hand be mulliganed away?", -1.0);
        if (probability < 0) {
            return super.chooseMulligan(game);
        }
        logger.debug("Jev (" + getName() + ") mulligan probability " + probability);
        return probability >= 0.5;
    }
}
