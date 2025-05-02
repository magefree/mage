package mage.player.ai;

import mage.abilities.Ability;
import mage.constants.RangeOfInfluence;
import mage.game.Game;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.LinkedList;

/**
 * AI: server side bot with game simulations (mad bot, the latest version)
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
        game.getState().setPriorityPlayerId(playerId);
        game.firePriorityEvent(playerId);
        switch (game.getTurnStepType()) {
            case UPKEEP:
            case DRAW:
                pass(game);
                return false;
            case PRECOMBAT_MAIN:
                // 09.03.2020:
                // in old version it passes opponent's pre-combat step (game.isActivePlayer(playerId) -> pass(game))
                // why?!
                printBattlefieldScore(game, "Sim PRIORITY on MAIN 1");
                if (actions.isEmpty()) {
                    calculateActions(game);
                } else {
                    // TODO: is it possible non empty actions without calculation?!
                    throw new IllegalStateException("wtf");
                }
                act(game);
                return true;
            case BEGIN_COMBAT:
                pass(game);
                return false;
            case DECLARE_ATTACKERS:
                printBattlefieldScore(game, "Sim PRIORITY on DECLARE ATTACKERS");
                if (actions.isEmpty()) {
                    calculateActions(game);
                } else {
                    // TODO: is it possible non empty actions without calculation?!
                    throw new IllegalStateException("wtf");
                }
                act(game);
                return true;
            case DECLARE_BLOCKERS:
                printBattlefieldScore(game, "Sim PRIORITY on DECLARE BLOCKERS");
                if (actions.isEmpty()) {
                    calculateActions(game);
                } else {
                    // TODO: is it possible non empty actions without calculation?!
                    throw new IllegalStateException("wtf");
                }
                act(game);
                return true;
            case FIRST_COMBAT_DAMAGE:
            case COMBAT_DAMAGE:
            case END_COMBAT:
                pass(game);
                return false;
            case POSTCOMBAT_MAIN:
                printBattlefieldScore(game, "Sim PRIORITY on MAIN 2");
                if (actions.isEmpty()) {
                    calculateActions(game);
                } else {
                    // TODO: is it possible non empty actions without calculation?!
                    throw new IllegalStateException("wtf");
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
            //logger.info("--- calculating possible actions for " + this.getName() + " on " + game.toString());
            Date startTime = new Date();
            currentScore = GameStateEvaluator2.evaluate(playerId, game).getTotalScore();
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
                        if (ability.getManaCosts().manaValue() == 0
                                && ability.getCosts().isEmpty()) {
                            if (actionCache.contains(ability.getRule() + '_' + ability.getSourceId())) {
                                doThis = false; // don't do it again
                            }
                        }
                    }
                }

                if (doThis) {
                    actions = new LinkedList<>(root.abilities);
                    combat = root.combat; // TODO: must use copy?!
                    for (Ability ability : actions) {
                        actionCache.add(ability.getRule() + '_' + ability.getSourceId());
                    }
                }
            } else {
                logger.info('[' + game.getPlayer(playerId).getName() + "][pre] Action: skip");
            }
            Date endTime = new Date();
            this.setLastThinkTime((endTime.getTime() - startTime.getTime()));

            /*
            logger.warn("Last think time: " + this.getLastThinkTime()
                    + "; actions: " + actions.size()
                    + "; hand: " + this.getHand().size()
                    + "; permanents: " + game.getBattlefield().getAllPermanents().size());
             */
        } else {
            logger.debug("Next Action exists!");
        }
    }

    @Override
    public void setAllowBadMoves(boolean allowBadMoves) {
        this.allowBadMoves = allowBadMoves;
    }
}
