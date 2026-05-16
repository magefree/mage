package mage.player.ai;

import mage.MageObject;
import mage.abilities.Ability;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Writes Mad AI decision traces as JSONL for offline analysis.
 */
public final class JsonlAiDecisionTrace implements AiDecisionTrace {

    private static final Logger logger = Logger.getLogger(JsonlAiDecisionTrace.class);
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter
            .ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .withZone(ZoneOffset.UTC);

    public static final String TRACE_ENABLED_PROPERTY = "xmage.ai.trace";
    public static final String TRACE_DIR_PROPERTY = "xmage.ai.trace.dir";
    public static final String TRACE_SEARCH_NODES_PROPERTY = "xmage.ai.trace.nodes";
    public static final String TRACE_SEARCH_NODE_LIMIT_PROPERTY = "xmage.ai.trace.nodes.max";
    public static final String TRACE_STATE_IMPACT_PROPERTY = "xmage.ai.trace.impact";

    private final Path traceDir;
    private final boolean traceSearchNodes;
    private final int traceSearchNodeLimit;
    private BufferedWriter writer;
    private boolean disabled;
    private int verboseSearchNodeEvents;

    private JsonlAiDecisionTrace(Path traceDir, boolean traceSearchNodes, int traceSearchNodeLimit) {
        this.traceDir = traceDir;
        this.traceSearchNodes = traceSearchNodes;
        this.traceSearchNodeLimit = traceSearchNodeLimit;
    }

    public static AiDecisionTrace createIfEnabled() {
        if (!Boolean.getBoolean(TRACE_ENABLED_PROPERTY)) {
            return NoOpAiDecisionTrace.INSTANCE;
        }
        return new JsonlAiDecisionTrace(
                Paths.get(System.getProperty(TRACE_DIR_PROPERTY, "logs/ai-decisions")),
                Boolean.getBoolean(TRACE_SEARCH_NODES_PROPERTY),
                Integer.getInteger(TRACE_SEARCH_NODE_LIMIT_PROPERTY, 5000)
        );
    }

    @Override
    public synchronized void recordCandidate(Game game, ComputerPlayer6 player, long decisionId, Ability action,
                                             int depth, int actionNumber, ScoreSnapshot startScore,
                                             ScoreSnapshot immediateScore, ScoreSnapshot finalScore,
                                             int nodeCount, long elapsedMs, boolean searchBudgetHit,
                                             int cacheHits, int cacheMisses, int cacheSize, List<BestLineStep> bestLine,
                                             AiStrategyScore strategyScore, AiStateImpact immediateImpact, AiStateImpact finalImpact,
                                             AiDecisionFeatureSnapshot startFeatureSnapshot,
                                             AiDecisionFeatureSnapshot finalFeatureSnapshot) {
        write(game, player, decisionId, action, "candidate", depth, actionNumber, startScore, immediateScore, finalScore, nodeCount, elapsedMs, searchBudgetHit, cacheHits, cacheMisses, cacheSize, bestLine, strategyScore, immediateImpact, finalImpact, startFeatureSnapshot, finalFeatureSnapshot);
    }

    @Override
    public synchronized void recordChosen(Game game, ComputerPlayer6 player, long decisionId, Ability action,
                                          int depth, ScoreSnapshot startScore, ScoreSnapshot finalScore,
                                          int nodeCount, List<BestLineStep> bestLine, AiStrategyScore strategyScore,
                                          AiStateImpact finalImpact, AiDecisionFeatureSnapshot startFeatureSnapshot,
                                          AiDecisionFeatureSnapshot finalFeatureSnapshot) {
        write(game, player, decisionId, action, "chosen", depth, -1, startScore, null, finalScore, nodeCount, -1, false, 0, 0, 0, bestLine, strategyScore, null, finalImpact, startFeatureSnapshot, finalFeatureSnapshot);
    }

    @Override
    public synchronized void recordSearchTimeout(Game game, ComputerPlayer6 player, int depth, int maxThinkTimeSecs,
                                                 int nodeCount, int rootChildren, List<BestLineStep> bestLine) {
        if (disabled) {
            return;
        }
        try {
            ensureOpen(game, player);
            writer.write('{');
            writeField("timestamp", TIMESTAMP_FORMATTER.format(Instant.now()));
            writer.write(',');
            writeField("event", "timeout");
            writer.write(',');
            writeField("gameId", game.getId().toString());
            writer.write(',');
            writer.write("\"turn\":");
            writer.write(Integer.toString(game.getTurnNum()));
            writer.write(',');
            writeField("step", String.valueOf(game.getTurnStepType()));
            writer.write(',');
            writeField("player", player.getName());
            writer.write(',');
            writeField("aiType", player.getClass().getSimpleName());
            writer.write(',');
            writer.write("\"depth\":");
            writer.write(Integer.toString(depth));
            writer.write(',');
            writer.write("\"maxThinkTimeSecs\":");
            writer.write(Integer.toString(maxThinkTimeSecs));
            writer.write(',');
            writer.write("\"nodeCount\":");
            writer.write(Integer.toString(nodeCount));
            writer.write(',');
            writer.write("\"rootChildren\":");
            writer.write(Integer.toString(rootChildren));
            writer.write(',');
            writer.write("\"battlefieldSize\":");
            writer.write(Integer.toString(game.getBattlefield().getAllPermanents().size()));
            writer.write(',');
            writer.write("\"stackSize\":");
            writer.write(Integer.toString(game.getStack().size()));
            writer.write(',');
            writeBestLine(bestLine);
            writer.write('}');
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            disabled = true;
            logger.warn("Disabling AI decision trace after write failure", e);
            close();
        }
    }

    @Override
    public synchronized void recordSearchNode(Game game, ComputerPlayer6 player, long decisionId, SimulationNode2 node,
                                              String eventType, int searchDepth, int alpha, int beta, Integer score,
                                              String note) {
        if (disabled || !traceSearchNodes || node == null) {
            return;
        }
        if (!isStructuralSearchNode(eventType, node)) {
            if (traceSearchNodeLimit > 0 && verboseSearchNodeEvents >= traceSearchNodeLimit) {
                return;
            }
            verboseSearchNodeEvents++;
        }
        try {
            Game nodeGame = node.getGame() == null ? game : node.getGame();
            ensureOpen(game, player);
            writer.write('{');
            writeField("timestamp", TIMESTAMP_FORMATTER.format(Instant.now()));
            writer.write(',');
            writeField("event", "searchNode");
            writer.write(',');
            writeField("nodeEvent", eventType);
            writer.write(',');
            writeField("gameId", game.getId().toString());
            writer.write(',');
            writer.write("\"decisionId\":");
            writer.write(Long.toString(decisionId));
            writer.write(',');
            writer.write("\"nodeId\":");
            writer.write(Integer.toString(node.getNodeNumber()));
            writer.write(',');
            writer.write("\"parentNodeId\":");
            writer.write(node.getParent() == null ? "null" : Integer.toString(node.getParent().getNodeNumber()));
            writer.write(',');
            writer.write("\"turn\":");
            writer.write(Integer.toString(game.getTurnNum()));
            writer.write(',');
            writeField("step", String.valueOf(game.getTurnStepType()));
            writer.write(',');
            writeField("player", player.getName());
            writer.write(',');
            writeField("aiType", player.getClass().getSimpleName());
            writer.write(',');
            writer.write("\"depth\":");
            writer.write(Integer.toString(node.getDepth()));
            writer.write(',');
            writer.write("\"searchDepth\":");
            writer.write(Integer.toString(searchDepth));
            writer.write(',');
            writer.write("\"alpha\":");
            writer.write(Integer.toString(alpha));
            writer.write(',');
            writer.write("\"beta\":");
            writer.write(Integer.toString(beta));
            if (score != null) {
                writer.write(',');
                writer.write("\"score\":");
                writer.write(Integer.toString(score));
            }
            writer.write(',');
            writeField("nodeKind", getNodeKind(node));
            writer.write(',');
            writeField("action", getNodeAction(game, player, node));
            writer.write(',');
            writeField("note", note);
            writer.write(',');
            writer.write("\"nodeCount\":");
            writer.write(Integer.toString(SimulationNode2.getCount()));
            writer.write(',');
            writer.write("\"childCount\":");
            writer.write(Integer.toString(node.getChildren() == null ? 0 : node.getChildren().size()));
            writer.write(',');
            writer.write("\"battlefieldSize\":");
            writer.write(Integer.toString(nodeGame.getBattlefield().getAllPermanents().size()));
            writer.write(',');
            writer.write("\"stackSize\":");
            writer.write(Integer.toString(nodeGame.getStack().size()));
            writer.write('}');
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            disabled = true;
            logger.warn("Disabling AI decision trace after write failure", e);
            close();
        }
    }

    @Override
    public synchronized void recordCombatSelection(Game game, ComputerPlayer6 player, CombatSelection selection) {
        if (disabled || selection == null) {
            return;
        }
        try {
            ensureOpen(game, player);
            writer.write('{');
            writeField("timestamp", TIMESTAMP_FORMATTER.format(Instant.now()));
            writer.write(',');
            writeField("event", "combat");
            writer.write(',');
            writeField("combatEvent", selection.combatEvent);
            writer.write(',');
            writeField("gameId", game.getId().toString());
            writer.write(',');
            writer.write("\"turn\":");
            writer.write(Integer.toString(game.getTurnNum()));
            writer.write(',');
            writeField("step", String.valueOf(game.getTurnStepType()));
            writer.write(',');
            writeField("player", player.getName());
            writer.write(',');
            writeField("aiType", player.getClass().getSimpleName());
            writer.write(',');
            writeField("reason", selection.reason);
            writer.write(',');
            writeField("defenderId", selection.defenderId);
            writer.write(',');
            writeField("defenderName", selection.defenderName);
            writer.write(',');
            writer.write("\"availableAttackers\":");
            writer.write(Integer.toString(selection.availableAttackers));
            writer.write(',');
            writer.write("\"availableBlockers\":");
            writer.write(Integer.toString(selection.availableBlockers));
            writer.write(',');
            writer.write("\"selectedAttackers\":");
            writer.write(Integer.toString(selection.attackers.size()));
            writer.write(',');
            writer.write("\"selectedBlockers\":");
            writer.write(Integer.toString(countSelectedBlockers(selection.blockAssignments)));
            writer.write(',');
            writer.write("\"attackers\":");
            writeCombatants(selection.attackers);
            writer.write(',');
            writer.write("\"blockAssignments\":");
            writeCombatAssignments(selection.blockAssignments);
            writer.write('}');
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            disabled = true;
            logger.warn("Disabling AI decision trace after write failure", e);
            close();
        }
    }

    private boolean isStructuralSearchNode(String eventType, SimulationNode2 node) {
        if ("decision-start".equals(eventType) || "root".equals(eventType)) {
            return true;
        }
        return "selected".equals(eventType)
                && node.getParent() != null
                && node.getParent().getParent() == null;
    }

    private void write(Game game, ComputerPlayer6 player, long decisionId, Ability action, String eventType,
                       int depth, int actionNumber, ScoreSnapshot startScore,
                       ScoreSnapshot immediateScore, ScoreSnapshot finalScore,
                       int nodeCount, long elapsedMs, boolean searchBudgetHit,
                       int cacheHits, int cacheMisses, int cacheSize, List<BestLineStep> bestLine,
                       AiStrategyScore strategyScore, AiStateImpact immediateImpact, AiStateImpact finalImpact,
                       AiDecisionFeatureSnapshot startFeatureSnapshot,
                       AiDecisionFeatureSnapshot finalFeatureSnapshot) {
        if (disabled) {
            return;
        }
        try {
            ensureOpen(game, player);
            writer.write('{');
            writeField("timestamp", TIMESTAMP_FORMATTER.format(Instant.now()));
            writer.write(',');
            writeField("event", eventType);
            writer.write(',');
            writeField("gameId", game.getId().toString());
            writer.write(',');
            writer.write("\"decisionId\":");
            writer.write(Long.toString(decisionId));
            writer.write(',');
            writer.write("\"turn\":");
            writer.write(Integer.toString(game.getTurnNum()));
            writer.write(',');
            writeField("step", String.valueOf(game.getTurnStepType()));
            writer.write(',');
            writeField("player", player.getName());
            writer.write(',');
            writeField("aiType", player.getClass().getSimpleName());
            writer.write(',');
            writer.write("\"depth\":");
            writer.write(Integer.toString(depth));
            if (actionNumber >= 0) {
                writer.write(',');
                writer.write("\"actionNumber\":");
                writer.write(Integer.toString(actionNumber));
            }
            writer.write(',');
            writeField("action", player.getAbilityAndSourceInfo(game, action, true));
            writer.write(',');
            writeField("actionRule", action.getRule());
            writer.write(',');
            writeField("actionText", action.toString());
            writer.write(',');
            writeField("sourceId", String.valueOf(action.getSourceId()));
            writer.write(',');
            MageObject sourceObject = action.getSourceObject(game);
            writeField("sourceName", sourceObject == null ? "" : sourceObject.getName());
            writer.write(',');
            writer.write("\"usesStack\":");
            writer.write(Boolean.toString(action.isUsesStack()));
            writer.write(',');
            writer.write("\"manaAbility\":");
            writer.write(Boolean.toString(action.isManaAbility()));
            writer.write(',');
            writeTargets(action);
            writer.write(',');
            writer.write("\"startScore\":");
            writer.write(Integer.toString(startScore.total));
            writer.write(',');
            writer.write("\"finalScore\":");
            writer.write(Integer.toString(finalScore.total));
            writer.write(',');
            writer.write("\"scoreDelta\":");
            writer.write(Long.toString((long) finalScore.total - startScore.total));
            writeStrategyScore(strategyScore);
            writeStateImpact("immediateImpact", immediateImpact);
            writeStateImpact("finalImpact", finalImpact);
            writeFeatureSnapshot("startFeatureSnapshot", startFeatureSnapshot);
            writeFeatureSnapshot("finalFeatureSnapshot", finalFeatureSnapshot);
            writer.write(',');
            writeScoreSnapshot("start", startScore);
            if (immediateScore != null) {
                writer.write(',');
                writeScoreSnapshot("immediate", immediateScore);
                writer.write(',');
                writer.write("\"immediateScoreDelta\":");
                writer.write(Integer.toString(immediateScore.total - startScore.total));
            }
            writer.write(',');
            writeScoreSnapshot("final", finalScore);
            writer.write(',');
            writeBestLine(bestLine);
            writer.write(',');
            writer.write("\"nodeCount\":");
            writer.write(Integer.toString(nodeCount));
            if (elapsedMs >= 0) {
                writer.write(',');
                writer.write("\"elapsedMs\":");
                writer.write(Long.toString(elapsedMs));
            }
            writer.write(',');
            writer.write("\"searchBudgetHit\":");
            writer.write(Boolean.toString(searchBudgetHit));
            writer.write(',');
            writer.write("\"cacheHits\":");
            writer.write(Integer.toString(cacheHits));
            writer.write(',');
            writer.write("\"cacheMisses\":");
            writer.write(Integer.toString(cacheMisses));
            writer.write(',');
            writer.write("\"cacheSize\":");
            writer.write(Integer.toString(cacheSize));
            writer.write('}');
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            disabled = true;
            logger.warn("Disabling AI decision trace after write failure", e);
            close();
        }
    }

    private void writeStateImpact(String name, AiStateImpact impact) throws IOException {
        if (impact == null || !impact.hasImpact()) {
            return;
        }
        writer.write(',');
        writer.write('"');
        writer.write(name);
        writer.write("\":{");
        boolean first = true;
        first = writeNumberField("playerLifeDelta", impact.getPlayerLifeDelta(), first);
        first = writeNumberField("opponentsLifeDelta", impact.getOpponentsLifeDelta(), first);
        first = writeNumberField("ownEntered", impact.getOwnEntered(), first);
        first = writeNumberField("opponentEntered", impact.getOpponentEntered(), first);
        first = writeNumberField("ownLeft", impact.getOwnLeft(), first);
        first = writeNumberField("opponentLeft", impact.getOpponentLeft(), first);
        first = writeNumberField("ownCreaturesLeft", impact.getOwnCreaturesLeft(), first);
        first = writeNumberField("opponentCreaturesLeft", impact.getOpponentCreaturesLeft(), first);
        first = writeNumberField("ownPowerToughnessChanged", impact.getOwnPowerToughnessChanged(), first);
        first = writeNumberField("opponentPowerToughnessChanged", impact.getOpponentPowerToughnessChanged(), first);
        first = writeNumberField("ownAbilitiesChanged", impact.getOwnAbilitiesChanged(), first);
        first = writeNumberField("opponentAbilitiesChanged", impact.getOpponentAbilitiesChanged(), first);
        first = writeNumberField("ownCountersChanged", impact.getOwnCountersChanged(), first);
        first = writeNumberField("opponentCountersChanged", impact.getOpponentCountersChanged(), first);
        first = writeNumberField("ownDamageChanged", impact.getOwnDamageChanged(), first);
        first = writeNumberField("opponentDamageChanged", impact.getOpponentDamageChanged(), first);
        first = writeNumberField("ownTappedChanged", impact.getOwnTappedChanged(), first);
        first = writeNumberField("opponentTappedChanged", impact.getOpponentTappedChanged(), first);
        first = writeNumberField("ownCombatChanged", impact.getOwnCombatChanged(), first);
        first = writeNumberField("opponentCombatChanged", impact.getOpponentCombatChanged(), first);
        if (!impact.getExamples().isEmpty()) {
            if (!first) {
                writer.write(',');
            }
            writer.write("\"examples\":[");
            boolean firstExample = true;
            for (String example : impact.getExamples()) {
                if (!firstExample) {
                    writer.write(',');
                }
                writer.write('"');
                writer.write(escape(example));
                writer.write('"');
                firstExample = false;
            }
            writer.write(']');
        }
        writer.write('}');
    }

    private boolean writeNumberField(String name, int value, boolean first) throws IOException {
        if (value == 0) {
            return first;
        }
        if (!first) {
            writer.write(',');
        }
        writer.write('"');
        writer.write(name);
        writer.write("\":");
        writer.write(Integer.toString(value));
        return false;
    }

    private void writeFeatureSnapshot(String name, AiDecisionFeatureSnapshot snapshot) throws IOException {
        if (snapshot == null) {
            return;
        }
        writer.write(',');
        writer.write('"');
        writer.write(name);
        writer.write("\":{");
        writer.write("\"phaseIntent\":\"");
        writer.write(escape(snapshot.phaseIntent));
        writer.write("\",\"battlefieldSize\":");
        writer.write(Integer.toString(snapshot.battlefieldSize));
        writer.write(",\"stackSize\":");
        writer.write(Integer.toString(snapshot.stackSize));
        writer.write(",\"players\":[");
        boolean firstPlayer = true;
        for (AiDecisionFeatureSnapshot.PlayerFeature playerFeature : snapshot.players) {
            if (!firstPlayer) {
                writer.write(',');
            }
            writePlayerFeature(playerFeature);
            firstPlayer = false;
        }
        writer.write("],\"topThreats\":[");
        boolean firstThreat = true;
        for (AiDecisionFeatureSnapshot.ThreatFeature threat : snapshot.topThreats) {
            if (!firstThreat) {
                writer.write(',');
            }
            writeThreatFeature(threat);
            firstThreat = false;
        }
        writer.write("]}");
    }

    private void writePlayerFeature(AiDecisionFeatureSnapshot.PlayerFeature playerFeature) throws IOException {
        writer.write('{');
        writeField("playerId", playerFeature.playerId);
        writer.write(',');
        writeField("name", playerFeature.name);
        writer.write(',');
        writer.write("\"activeDecisionPlayer\":");
        writer.write(Boolean.toString(playerFeature.activeDecisionPlayer));
        writer.write(",\"inGame\":");
        writer.write(Boolean.toString(playerFeature.inGame));
        writer.write(",\"lost\":");
        writer.write(Boolean.toString(playerFeature.lost));
        writer.write(",\"life\":");
        writer.write(Integer.toString(playerFeature.life));
        writer.write(",\"handSize\":");
        writer.write(Integer.toString(playerFeature.handSize));
        writer.write(",\"battlefieldSize\":");
        writer.write(Integer.toString(playerFeature.battlefieldSize));
        writer.write(",\"creatureCount\":");
        writer.write(Integer.toString(playerFeature.creatureCount));
        writer.write(",\"boardPower\":");
        writer.write(Integer.toString(playerFeature.boardPower));
        writer.write(",\"manaSources\":");
        writer.write(Integer.toString(playerFeature.manaSources));
        writer.write(",\"colorAccess\":");
        writer.write(Integer.toString(playerFeature.colorAccess));
        writer.write(",\"commanders\":[");
        boolean firstCommander = true;
        for (AiDecisionFeatureSnapshot.CommanderFeature commander : playerFeature.commanders) {
            if (!firstCommander) {
                writer.write(',');
            }
            writer.write('{');
            writeField("id", commander.id);
            writer.write(',');
            writeField("name", commander.name);
            writer.write(',');
            writeField("zone", commander.zone);
            writer.write(",\"onBattlefield\":");
            writer.write(Boolean.toString(commander.onBattlefield));
            writer.write('}');
            firstCommander = false;
        }
        writer.write("],\"topThreats\":[");
        boolean firstThreat = true;
        for (AiDecisionFeatureSnapshot.ThreatFeature threat : playerFeature.topThreats) {
            if (!firstThreat) {
                writer.write(',');
            }
            writeThreatFeature(threat);
            firstThreat = false;
        }
        writer.write("]}");
    }

    private void writeThreatFeature(AiDecisionFeatureSnapshot.ThreatFeature threat) throws IOException {
        writer.write('{');
        writeField("id", threat.id);
        writer.write(',');
        writeField("name", threat.name);
        writer.write(',');
        writeField("controllerId", threat.controllerId);
        writer.write(',');
        writeField("controllerName", threat.controllerName);
        writer.write(",\"power\":");
        writer.write(Integer.toString(threat.power));
        writer.write(",\"toughness\":");
        writer.write(Integer.toString(threat.toughness));
        writer.write(",\"value\":");
        writer.write(Integer.toString(threat.value));
        writer.write('}');
    }

    private void writeStrategyScore(AiStrategyScore strategyScore) throws IOException {
        if (strategyScore == null || !strategyScore.hasStrategySignal()) {
            return;
        }
        writer.write(',');
        writer.write("\"strategyBaseScore\":");
        writer.write(Integer.toString(strategyScore.getBaseScore()));
        writer.write(',');
        writer.write("\"strategyRawModifier\":");
        writer.write(Integer.toString(strategyScore.getRawModifier()));
        writer.write(',');
        writer.write("\"strategyAppliedModifier\":");
        writer.write(Integer.toString(strategyScore.getAppliedModifier()));
        writer.write(',');
        writer.write("\"strategyAdjustedScore\":");
        writer.write(Integer.toString(strategyScore.getAdjustedScore()));
        writer.write(',');
        writer.write("\"strategyContributions\":[");
        boolean first = true;
        for (AiStrategyScore.Contribution contribution : strategyScore.getContributions()) {
            if (!first) {
                writer.write(',');
            }
            writer.write("{\"label\":\"");
            writer.write(escape(contribution.getLabel()));
            writer.write("\",\"value\":");
            writer.write(Integer.toString(contribution.getValue()));
            writer.write(",\"detail\":\"");
            writer.write(escape(contribution.getDetail()));
            writer.write("\"}");
            first = false;
        }
        writer.write(']');
    }

    private void writeScoreSnapshot(String prefix, ScoreSnapshot score) throws IOException {
        writer.write('"');
        writer.write(prefix);
        writer.write("PlayerLifeScore\":");
        writer.write(Integer.toString(score.playerLife));
        writer.write(',');
        writer.write('"');
        writer.write(prefix);
        writer.write("PlayerHandScore\":");
        writer.write(Integer.toString(score.playerHand));
        writer.write(',');
        writer.write('"');
        writer.write(prefix);
        writer.write("PlayerPermanentsScore\":");
        writer.write(Integer.toString(score.playerPermanents));
        writer.write(',');
        writer.write('"');
        writer.write(prefix);
        writer.write("OpponentLifeScore\":");
        writer.write(Integer.toString(score.opponentLife));
        writer.write(',');
        writer.write('"');
        writer.write(prefix);
        writer.write("OpponentHandScore\":");
        writer.write(Integer.toString(score.opponentHand));
        writer.write(',');
        writer.write('"');
        writer.write(prefix);
        writer.write("OpponentPermanentsScore\":");
        writer.write(Integer.toString(score.opponentPermanents));
    }

    private void writeBestLine(List<BestLineStep> bestLine) throws IOException {
        writer.write("\"bestLine\":[");
        if (bestLine != null) {
            boolean first = true;
            for (BestLineStep step : bestLine) {
                if (!first) {
                    writer.write(',');
                }
                writer.write("{\"depth\":");
                writer.write(Integer.toString(step.depth));
                writer.write(",\"action\":\"");
                writer.write(escape(step.action));
                writer.write("\"}");
                first = false;
            }
        }
        writer.write(']');
    }

    private void writeTargets(Ability action) throws IOException {
        writer.write("\"targetIds\":[");
        boolean first = true;
        for (Target target : action.getAllSelectedTargets()) {
            for (Object targetId : target.getTargets()) {
                if (!first) {
                    writer.write(',');
                }
                writer.write('"');
                writer.write(escape(String.valueOf(targetId)));
                writer.write('"');
                first = false;
            }
        }
        writer.write(']');
    }

    private int countSelectedBlockers(List<CombatAssignment> blockAssignments) {
        int count = 0;
        for (CombatAssignment assignment : blockAssignments) {
            count += assignment.blockers.size();
        }
        return count;
    }

    private void writeCombatants(List<Combatant> combatants) throws IOException {
        writer.write('[');
        boolean first = true;
        for (Combatant combatant : combatants) {
            if (!first) {
                writer.write(',');
            }
            writeCombatant(combatant);
            first = false;
        }
        writer.write(']');
    }

    private void writeCombatAssignments(List<CombatAssignment> assignments) throws IOException {
        writer.write('[');
        boolean first = true;
        for (CombatAssignment assignment : assignments) {
            if (!first) {
                writer.write(',');
            }
            writer.write('{');
            writer.write("\"attacker\":");
            writeCombatant(assignment.attacker);
            writer.write(',');
            writer.write("\"blockers\":");
            writeCombatants(assignment.blockers);
            writer.write('}');
            first = false;
        }
        writer.write(']');
    }

    private void writeCombatant(Combatant combatant) throws IOException {
        writer.write('{');
        writeField("id", combatant.id);
        writer.write(',');
        writeField("name", combatant.name);
        writer.write(',');
        writeField("controllerId", combatant.controllerId);
        writer.write(',');
        writeField("controllerName", combatant.controllerName);
        writer.write(',');
        writeField("defenderId", combatant.defenderId);
        writer.write(',');
        writeField("defenderName", combatant.defenderName);
        writer.write(',');
        writer.write("\"power\":");
        writer.write(Integer.toString(combatant.power));
        writer.write(',');
        writer.write("\"toughness\":");
        writer.write(Integer.toString(combatant.toughness));
        writer.write(',');
        writer.write("\"combatDamage\":");
        writer.write(Integer.toString(combatant.combatDamage));
        writer.write(',');
        writer.write("\"value\":");
        writer.write(Integer.toString(combatant.value));
        writer.write(',');
        writeStringArrayField("abilities", combatant.abilities);
        writer.write('}');
    }

    private void writeStringArrayField(String name, List<String> values) throws IOException {
        writer.write('"');
        writer.write(name);
        writer.write("\":[");
        boolean first = true;
        for (String value : values) {
            if (!first) {
                writer.write(',');
            }
            writer.write('"');
            writer.write(escape(value));
            writer.write('"');
            first = false;
        }
        writer.write(']');
    }

    private String getNodeKind(SimulationNode2 node) {
        if (node.getAbilities() != null && !node.getAbilities().isEmpty()) {
            return "priority";
        }
        if (!node.getTargets().isEmpty()) {
            return "target";
        }
        if (!node.getChoices().isEmpty()) {
            return "choice";
        }
        return "state";
    }

    private String getNodeAction(Game displayGame, ComputerPlayer6 player, SimulationNode2 node) {
        if (node.getAbilities() != null && !node.getAbilities().isEmpty()) {
            return player.getAbilityAndSourceInfo(
                    node.getGame() == null ? displayGame : node.getGame(),
                    node.getAbilities().get(0),
                    true
            );
        }
        if (!node.getTargets().isEmpty()) {
            return node.getTargets().stream()
                    .map(id -> {
                        Player targetPlayer = displayGame.getPlayer(id);
                        if (targetPlayer != null) {
                            return targetPlayer.getName();
                        }
                        MageObject object = displayGame.getObject(id);
                        return object == null ? String.valueOf(id) : object.getIdName();
                    })
                    .collect(java.util.stream.Collectors.joining(", "));
        }
        if (!node.getChoices().isEmpty()) {
            return String.join(", ", node.getChoices());
        }
        return "";
    }

    private void ensureOpen(Game game, Player player) throws IOException {
        if (writer != null) {
            return;
        }
        Files.createDirectories(traceDir);
        String fileName = sanitize(game.getId().toString() + "-" + player.getName()) + ".jsonl";
        writer = Files.newBufferedWriter(
                traceDir.resolve(fileName),
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        );
    }

    private void writeField(String name, String value) throws IOException {
        writer.write('"');
        writer.write(name);
        writer.write("\":\"");
        writer.write(escape(value == null ? "" : value));
        writer.write('"');
    }

    private String escape(String value) {
        StringBuilder sb = new StringBuilder(value.length() + 16);
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            switch (c) {
                case '"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                default:
                    if (c < 0x20) {
                        sb.append(String.format("\\u%04x", (int) c));
                    } else {
                        sb.append(c);
                    }
                    break;
            }
        }
        return sb.toString();
    }

    private String sanitize(String value) {
        return value.replaceAll("[^a-zA-Z0-9._-]", "_");
    }

    @Override
    public synchronized void close() {
        if (writer == null) {
            return;
        }
        try {
            writer.close();
        } catch (IOException e) {
            logger.warn("Failed to close AI decision trace", e);
        } finally {
            writer = null;
        }
    }
}
