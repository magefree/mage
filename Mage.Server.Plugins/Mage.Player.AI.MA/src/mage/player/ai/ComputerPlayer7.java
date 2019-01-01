
package mage.player.ai;

import java.util.LinkedList;
import mage.abilities.Ability;
import mage.constants.RangeOfInfluence;
import mage.game.Game;
import org.apache.log4j.Logger;

/**
 *
 * @author ayratn
 */
public class ComputerPlayer7 extends ComputerPlayer6 {

    private static final Logger logger = Logger.getLogger(ComputerPlayer7.class);

    private boolean allowBadMoves;

    public ComputerPlayer7(String name, RangeOfInfluence range, int skill) {
        super(name, range, skill);
    }

    public ComputerPlayer7(final ComputerPlayer7 player) {
        super(player);
        this.allowBadMoves = player.allowBadMoves;
    }

    @Override
    public ComputerPlayer7 copy() {
        return new ComputerPlayer7(this);
    }

    @Override
    public boolean priority(Game game) {
        game.resumeTimer(getTurnControlledBy());
        boolean result = priorityPlay(game);
        game.pauseTimer(getTurnControlledBy());
        return result;
    }

    private boolean priorityPlay(Game game) {
        if (lastLoggedTurn != game.getTurnNum()) {
            lastLoggedTurn = game.getTurnNum();
            logger.info("======================= Turn: " + game.getTurnNum() + " [" + game.getPlayer(game.getActivePlayerId()).getName() + "] =========================================");
        }
        logState(game);
        logger.debug("Priority -- Step: " + (game.getTurn().getStepType() + "                       ").substring(0, 25) + " ActivePlayer-" + game.getPlayer(game.getActivePlayerId()).getName() + " PriorityPlayer-" + name);
        game.getState().setPriorityPlayerId(playerId);
        game.firePriorityEvent(playerId);
        switch (game.getTurn().getStepType()) {
            case UPKEEP:
            case DRAW:
                pass(game);
                return false;
            case PRECOMBAT_MAIN:
                if (game.isActivePlayer(playerId)) {
                    printOutState(game);
                    if (actions.isEmpty()) {
                        logger.info("Sim Calculate pre combat actions ----------------------------------------------------- ");
                        calculateActions(game);
                    }
                    act(game);
                    return true;
                } else {
                    pass(game);
                }
                return false;
            case BEGIN_COMBAT:
                pass(game);
                return false;
            case DECLARE_ATTACKERS:
                printOutState(game);
                if (actions.isEmpty()) {
                    logger.info("Sim Calculate declare attackers actions ----------------------------------------------------- ");
                    calculateActions(game);
                }
                act(game);
                return true;
            case DECLARE_BLOCKERS:
                printOutState(game);
                if (actions.isEmpty()) {
                    calculateActions(game);
                }
                act(game);
                return true;
            case FIRST_COMBAT_DAMAGE:
            case COMBAT_DAMAGE:
            case END_COMBAT:
                pass(game);
                return false;
            case POSTCOMBAT_MAIN:
                printOutState(game);
                if (actions.isEmpty()) {
                    calculateActions(game);
                }
                act(game);
                return true;
            case END_TURN:
            case CLEANUP:
                actionCache.clear();
                pass(game);
                return false;
        }
        return false;
    }

    protected void calculateActions(Game game) {
        if (!getNextAction(game)) {
            currentScore = GameStateEvaluator2.evaluate(playerId, game);
            Game sim = createSimulation(game);
            SimulationNode2.resetCount();
            root = new SimulationNode2(null, sim, maxDepth, playerId);
            addActionsTimed(); // TODO: root can be null again after addActionsTimed O_o need to research (it's a CPU AI problem?)
            if (root != null && root.children != null && !root.children.isEmpty()) {
                logger.trace("After add actions timed: root.children.size = " + root.children.size());
                root = root.children.get(0);
                // prevent repeating always the same action with no cost
                boolean doThis = true;
                if (root.abilities.size() == 1) {
                    for (Ability ability : root.abilities) {
                        if (ability.getManaCosts().convertedManaCost() == 0
                                && ability.getCosts().isEmpty()) {
                            if (actionCache.contains(ability.getRule() + '_' + ability.getSourceId())) {
                                doThis = false; // don't do it again
                            }
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
            } else {
                logger.info('[' + game.getPlayer(playerId).getName() + "][pre] Action: skip");
            }
        } else {
            logger.debug("Next Action exists!");
        }
    }

    @Override
    public void setAllowBadMoves(boolean allowBadMoves) {
        this.allowBadMoves = allowBadMoves;
    }
}
