package mage.game.turn;

import mage.abilities.Ability;
import mage.constants.PhaseStep;
import mage.constants.TurnPhase;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.PhaseChangedEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.util.ThreadLocalStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class Turn implements Serializable {

    private static final ThreadLocalStringBuilder threadLocalBuilder = new ThreadLocalStringBuilder(50);

    private Phase currentPhase;
    private UUID activePlayerId;
    private final List<Phase> phases = new ArrayList<>();
    private boolean declareAttackersStepStarted = false;
    private boolean endTurn; // indicates that an end turn effect has resolved.

    public Turn() {
        endTurn = false;
        phases.add(new BeginningPhase());
        phases.add(new PreCombatMainPhase());
        phases.add(new CombatPhase());
        phases.add(new PostCombatMainPhase());
        phases.add(new EndPhase());
    }

    public Turn(final Turn turn) {
        if (turn.currentPhase != null) {
            this.currentPhase = turn.currentPhase.copy();
        }
        this.activePlayerId = turn.activePlayerId;
        for (Phase phase : turn.phases) {
            this.phases.add(phase.copy());
        }
        this.declareAttackersStepStarted = turn.declareAttackersStepStarted;
        this.endTurn = turn.endTurn;

    }

    public TurnPhase getPhaseType() {
        if (currentPhase != null) {
            return currentPhase.getType();
        }
        return null;
    }

    public Phase getPhase() {
        return currentPhase;
    }

    public Phase getPhase(TurnPhase turnPhase) {
        for (Phase phase : phases) {
            if (phase.getType() == turnPhase) {
                return phase;
            }
        }
        return null;
    }

    public void setPhase(Phase phase) {
        this.currentPhase = phase;
    }

    public Step getStep() {
        if (currentPhase != null) {
            return currentPhase.getStep();
        }
        return null;
    }

    /**
     * @param game
     * @param activePlayer
     * @return true if turn is skipped
     */
    public boolean play(Game game, Player activePlayer) {
        // uncomment this to trace triggered abilities and/or continous effects 
        // TraceUtil.traceTriggeredAbilities(game);
        // game.getState().getContinuousEffects().traceContinuousEffects(game);
        activePlayer.becomesActivePlayer();
        this.setDeclareAttackersStepStarted(false);
        if (game.isPaused() || game.checkIfGameIsOver()) {
            return false;
        }


        if (game.getState().getTurnMods().skipTurn(activePlayer.getId())) {
            game.informPlayers(activePlayer.getLogName() + " skips their turn.");
            return true;
        }
        logStartOfTurn(game, activePlayer);

        checkTurnIsControlledByOtherPlayer(game, activePlayer.getId());

        this.activePlayerId = activePlayer.getId();
        resetCounts();
        game.getPlayer(activePlayer.getId()).beginTurn(game);
        for (Phase phase : phases) {
            if (game.isPaused() || game.checkIfGameIsOver()) {
                return false;
            }
            if (isEndTurnRequested() && phase.getType() != TurnPhase.END) {
                continue;
            }
            currentPhase = phase;
            game.fireEvent(new PhaseChangedEvent(activePlayer.getId(), null));
            if (game.getState().getTurnMods().skipPhase(
                    activePlayer.getId(), currentPhase.getType()
            ) || !phase.play(game, activePlayer.getId())) {
                continue;
            }
            if (game.executingRollback()) {
                return false;
            }
            //20091005 - 500.4/703.4n
            game.emptyManaPools(null);
            game.saveState(false);

            //20091005 - 500.8
            while (playExtraPhases(game, phase.getType())) ;
        }
        return false;
    }

    public void resumePlay(Game game, boolean wasPaused) {
        activePlayerId = game.getActivePlayerId();
        UUID priorityPlayerId = game.getPriorityPlayerId();
        TurnPhase phaseType = game.getTurnPhaseType();
        PhaseStep stepType = game.getTurnStepType();

        Iterator<Phase> it = phases.iterator();
        Phase phase;
        do {
            phase = it.next();
            currentPhase = phase;
        } while (phase.type != phaseType);
        if (phase.resumePlay(game, stepType, wasPaused)) {
            //20091005 - 500.4/703.4n
            game.emptyManaPools(null);
            //game.saveState();
            //20091005 - 500.8
            playExtraPhases(game, phase.getType());
        }
        while (it.hasNext()) {
            phase = it.next();
            if (game.isPaused() || game.checkIfGameIsOver()) {
                return;
            }
            currentPhase = phase;
            if (!game.getState().getTurnMods().skipPhase(activePlayerId, currentPhase.getType())) {
                if (phase.play(game, activePlayerId)) {
                    //20091005 - 500.4/703.4n
                    game.emptyManaPools(null);
                    //game.saveState();
                    //20091005 - 500.8
                    playExtraPhases(game, phase.getType());
                }
            }
            if (!currentPhase.equals(phase)) { // phase was changed from the card
                game.fireEvent(new PhaseChangedEvent(activePlayerId, null));
                break;
            }
        }
    }

    private void checkTurnIsControlledByOtherPlayer(Game game, UUID activePlayerId) {
        UUID newControllerId = game.getState().getTurnMods().controlsTurn(activePlayerId);
        if (newControllerId != null && !newControllerId.equals(activePlayerId)) {
            game.getPlayer(newControllerId).controlPlayersTurn(game, activePlayerId);
        }
    }

    private void resetCounts() {
        for (Phase phase : phases) {
            phase.resetCount();
        }
    }

    private boolean playExtraPhases(Game game, TurnPhase afterPhase) {
        while (true) {
            TurnMod extraPhaseTurnMod = game.getState().getTurnMods().extraPhase(activePlayerId, afterPhase);
            if (extraPhaseTurnMod == null) {
                return false;
            }
            TurnPhase extraPhase = extraPhaseTurnMod.getExtraPhase();
            if (extraPhase == null) {
                return false;
            }
            Phase phase;
            switch (extraPhase) {
                case BEGINNING:
                    phase = new BeginningPhase();
                    break;
                case PRECOMBAT_MAIN:
                    phase = new PreCombatMainPhase();
                    break;
                case COMBAT:
                    phase = new CombatPhase();
                    break;
                case POSTCOMBAT_MAIN:
                    phase = new PostCombatMainPhase();
                    break;
                default:
                    phase = new EndPhase();
            }
            currentPhase = phase;
            game.fireEvent(new PhaseChangedEvent(activePlayerId, extraPhaseTurnMod));
            Player activePlayer = game.getPlayer(activePlayerId);
            if (activePlayer != null && !game.isSimulation()) {
                game.informPlayers(activePlayer.getLogName() + " starts an additional " + phase.getType().toString() + " phase");
            }
            phase.play(game, activePlayerId);
            afterPhase = extraPhase;
        }
    }

    /*protected void playExtraTurns(Game game) {
     while (game.getState().getTurnMods().extraTurn(activePlayerId)) {
     this.play(game, activePlayerId);
     }
     }*/

    /**
     * Used for some spells with end turn effect (e.g. Time Stop).
     *
     * @param game
     * @param activePlayerId
     * @param source
     */
    public void endTurn(Game game, UUID activePlayerId, Ability source) {
        // Ending the turn this way (Time Stop) means the following things happen in order:

        setEndTurnRequested(true);

        // 1) All spells and abilities on the stack are exiled. This includes (e.g.) Time Stop, though it will continue to resolve.
        // It also includes spells and abilities that can't be countered.
        while (!game.hasEnded() && !game.getStack().isEmpty()) {
            StackObject stackObject = game.getStack().peekFirst();
            if (stackObject instanceof Spell) {
                ((Spell) stackObject).moveToExile(null, "", source, game);
            } else {
                game.getStack().remove(stackObject, game); // stack ability
            }
        }
        // 2) All attacking and blocking creatures are removed from combat.
        for (UUID attackerId : game.getCombat().getAttackers()) {
            Permanent permanent = game.getPermanent(attackerId);
            if (permanent != null) {
                permanent.removeFromCombat(game);
            }
            game.getCombat().removeAttacker(attackerId, game);
        }
        for (UUID blockerId : game.getCombat().getBlockers()) {
            Permanent permanent = game.getPermanent(blockerId);
            if (permanent != null) {
                permanent.removeFromCombat(game);
            }
        }
        // 3) State-based actions are checked. No player gets priority, and no triggered abilities are put onto the stack.
        // seems like trigger events have to be removed: http://tabakrules.tumblr.com/post/122350751009/days-undoing-has-been-officially-spoiled-on
        game.getState().clearTriggeredAbilities();
        game.checkStateAndTriggered(); // triggered effects don't go to stack because check of endTurnRequested

        // 4) The current phase and/or step ends.
        // The game skips straight to the cleanup step. The cleanup step happens in its entirety.
        // this is caused by the endTurnRequest state
    }

    public boolean isDeclareAttackersStepStarted() {
        return declareAttackersStepStarted;
    }

    public void setDeclareAttackersStepStarted(boolean declareAttackersStepStarted) {
        this.declareAttackersStepStarted = declareAttackersStepStarted;
    }

    public void setEndTurnRequested(boolean endTurn) {
        this.endTurn = endTurn;
    }

    public boolean isEndTurnRequested() {
        return endTurn;
    }

    public Turn copy() {
        return new Turn(this);
    }

    public String getValue(int turnNum) {
        StringBuilder sb = threadLocalBuilder.get();
        sb.append('[').append(turnNum)
                .append(':').append(currentPhase.getType())
                .append(':').append(currentPhase.getStep().getType())
                .append(']');

        return sb.toString();
    }

    private void logStartOfTurn(Game game, Player player) {
        StringBuilder sb = new StringBuilder(game.getState().isExtraTurn() ? "Extra turn" : "Turn ");
        sb.append(game.getState().getTurnNum()).append(' ');
        sb.append(player.getLogName());
        sb.append(" (");
        int delimiter = game.getPlayers().size() - 1;
        for (Player gamePlayer : game.getPlayers().values()) {
            sb.append(gamePlayer.getLife());
            int poison = gamePlayer.getCounters().getCount(CounterType.POISON);
            if (poison > 0) {
                sb.append("[P:").append(poison).append(']');
            }
            if (delimiter > 0) {
                sb.append(" - ");
                delimiter--;
            }
        }
        sb.append(')');
        game.fireStatusEvent(sb.toString(), true, false);
    }
}
