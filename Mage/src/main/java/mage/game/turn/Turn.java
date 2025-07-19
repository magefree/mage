package mage.game.turn;

import mage.abilities.Ability;
import mage.constants.PhaseStep;
import mage.constants.TurnPhase;
import mage.game.Game;
import mage.game.events.GameEvent;
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
import java.util.stream.Collectors;

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

    protected Turn(final Turn turn) {
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
     * @return true if turn is skipped
     */
    public boolean play(Game game, Player activePlayer) {
        activePlayer.becomesActivePlayer();
        this.setDeclareAttackersStepStarted(false);
        if (game.isPaused() || game.checkIfGameIsOver()) {
            return false;
        }

        TurnMod skipTurnMod = game.getState().getTurnMods().useNextSkipTurn(activePlayer.getId());
        if (skipTurnMod != null) {
            game.informPlayers(String.format("%s skips their turn%s",
                    activePlayer.getLogName(),
                    skipTurnMod.getInfo()
            ));
            return true;
        }

        logStartOfTurn(game, activePlayer);
        resetCounts();

        this.activePlayerId = activePlayer.getId();
        this.currentPhase = null;

        // turn control must be called after potential turn skip due 720.1.
        checkTurnIsControlledByOtherPlayer(game, activePlayer.getId());

        game.getPlayer(activePlayer.getId()).beginTurn(game);
        GameEvent event = new GameEvent(GameEvent.EventType.BEGIN_TURN, null, null, activePlayer.getId());
        game.fireEvent(event);
        for (Phase phase : phases) {
            if (game.isPaused() || game.checkIfGameIsOver()) {
                return false;
            }
            if (isEndTurnRequested() && phase.getType() != TurnPhase.END) {
                continue;
            }
            currentPhase = phase;

            TurnMod skipPhaseMod = game.getState().getTurnMods().useNextSkipPhase(activePlayer.getId(), currentPhase.getType());
            if (skipPhaseMod != null) {
                game.informPlayers(String.format("%s skips %s phase%s",
                        activePlayer.getLogName(),
                        currentPhase.getType(),
                        skipPhaseMod.getInfo()
                ));
                continue;
            }

            game.fireEvent(new PhaseChangedEvent(activePlayer.getId(), null));
            if (!phase.play(game, activePlayer.getId())) {
                continue;
            }
            if (game.executingRollback()) {
                return false;
            }

            //20091005 - 500.4/703.4n
            game.emptyManaPools(null);
            game.saveState(false);

            //20091005 - 500.8
            while (true) {
                // TODO: make sure it work fine (without freeze) on game errors inside extra phases
                if (!playExtraPhases(game, phase.getType())) {
                    break;
                }
            }
        }
        return false;
    }

    public void resumePlay(Game game, boolean wasPaused) {
        activePlayerId = game.getActivePlayerId();
        Player activePlayer = game.getPlayer(activePlayerId);
        TurnPhase needPhaseType = game.getTurnPhaseType();
        PhaseStep needStepType = game.getTurnStepType();

        Iterator<Phase> it = phases.iterator();
        Phase nextPhase;
        do {
            nextPhase = it.next();
        } while (nextPhase.type != needPhaseType);

        // play first phase
        TurnMod skipPhaseMod = game.getState().getTurnMods().useNextSkipPhase(activePlayerId, nextPhase.getType());
        if (skipPhaseMod != null && activePlayer != null) {
            game.informPlayers(String.format("%s skips %s phase%s",
                    activePlayer.getLogName(),
                    nextPhase.getType(),
                    skipPhaseMod.getInfo()
            ));
        } else {
            if (game.isPaused() || game.checkIfGameIsOver()) {
                return;
            }
            currentPhase = nextPhase;
            game.fireEvent(new PhaseChangedEvent(activePlayerId, null));
            if (nextPhase.resumePlay(game, needStepType, wasPaused)) {
                //20091005 - 500.4/703.4n
                game.emptyManaPools(null);
                //20091005 - 500.8
                playExtraPhases(game, nextPhase.getType());
            }
        }

        // play all other phases
        while (it.hasNext()) {
            nextPhase = it.next();
            if (game.isPaused() || game.checkIfGameIsOver()) {
                return;
            }
            skipPhaseMod = game.getState().getTurnMods().useNextSkipPhase(activePlayerId, nextPhase.getType());
            if (skipPhaseMod != null && activePlayer != null) {
                game.informPlayers(String.format("%s skips %s phase%s",
                        activePlayer.getLogName(),
                        nextPhase.getType(),
                        skipPhaseMod.getInfo()
                ));
            } else {
                currentPhase = nextPhase;
                game.fireEvent(new PhaseChangedEvent(activePlayerId, null));
                if (nextPhase.play(game, activePlayerId)) {
                    //20091005 - 500.4/703.4n
                    game.emptyManaPools(null);
                    //20091005 - 500.8
                    playExtraPhases(game, nextPhase.getType());
                }
            }

            // TODO: old code, can't find any usage of turn's phase change by events/cards
            //  so it must be research and removed as outdated (maybe rollback or playExtraPhases related?)
            if (!currentPhase.equals(nextPhase)) { // phase was changed from the card
                game.fireEvent(new PhaseChangedEvent(activePlayerId, null));
                break;
            }
        }
    }

    private void checkTurnIsControlledByOtherPlayer(Game game, UUID activePlayerId) {
        // 720.1.
        // Some cards allow a player to control another player during that player’s next turn.
        // This effect applies to the next turn that the affected player actually takes.
        // The affected player is controlled during the entire turn; the effect doesn’t end until
        // the beginning of the next turn.
        //
        // 720.1b
        // If a turn is skipped, any pending player-controlling effects wait until the player who would be
        // affected actually takes a turn.

        // remove old under control
        game.getPlayers().values().forEach(player -> {
            if (player.isInGame() && !player.isGameUnderControl()) {
                Player controllingPlayer = game.getPlayer(player.getTurnControlledBy());
                if (player != controllingPlayer && controllingPlayer != null) {
                    game.informPlayers(controllingPlayer.getLogName() + " lost control over " + player.getLogName());
                }
                player.setGameUnderYourControl(game, true);
            }
        });

        // add new under control
        TurnMod newControllerMod = game.getState().getTurnMods().useNextNewController(activePlayerId);
        if (newControllerMod != null && !newControllerMod.getNewControllerId().equals(activePlayerId)) {
            // game logs added in child's call (controlPlayersTurn)
            game.getPlayer(newControllerMod.getNewControllerId()).controlPlayersTurn(game, activePlayerId, newControllerMod.getInfo());
        }
    }

    private void resetCounts() {
        for (Phase phase : phases) {
            phase.resetCount();
        }
    }

    /**
     * Play additional phases one by one
     *
     * @return false to finish
     */
    private boolean playExtraPhases(Game game, TurnPhase afterPhase) {
        while (true) {
            TurnMod extraPhaseMod = game.getState().getTurnMods().useNextExtraPhase(activePlayerId, afterPhase);
            if (extraPhaseMod == null) {
                // no more extra phases
                return false;
            }
            TurnPhase extraPhase = extraPhaseMod.getExtraPhase();
            if (extraPhase == null) {
                throw new IllegalStateException("Wrong code usage: miss data in turn mod's extra phase - " + extraPhaseMod.getInfo());
            }
            Phase phase;
            switch (extraPhase) {
                case BEGINNING:
                    phase = new BeginningPhase(true);
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
                case END:
                    phase = new EndPhase();
                    break;
                default:
                    throw new IllegalArgumentException("Unknown phase type: " + extraPhase);
            }
            PhaseStep skipAllButExtraStep = extraPhaseMod.getSkipAllButExtraStep();
            if (skipAllButExtraStep != null) {
                phase.keepOnlyStep(skipAllButExtraStep);
            }
            currentPhase = phase;
            game.fireEvent(new PhaseChangedEvent(activePlayerId, extraPhaseMod));
            Player activePlayer = game.getPlayer(activePlayerId);
            if (activePlayer != null) {
                game.informPlayers(String.format("%s starts an additional %s phase%s",
                        activePlayer.getLogName(),
                        phase.getType().toString(),
                        extraPhaseMod.getInfo()
                ));
            }
            phase.play(game, activePlayerId);

            // TODO: is it lost extra phase on multiple phases here?
            //  example:
            //  - mods contains 2 mods for same main phases
            //  - one played and afterPhase take main phase value
            //  - so it can't find a second mod
            afterPhase = extraPhase;
        }
    }

    /**
     * Used for some spells with end turn effect (e.g. Time Stop).
     */
    public void endTurn(Game game, Ability source) {
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
        // example: 0:40: TURN 1 for Human (40 - 40)

        String infoTurn = String.format("TURN %d%s for %s",
                game.getState().getTurnNum(),
                game.getState().isExtraTurn() ? " (extra)" : "",
                player.getLogName()
        );

        String infoLife = game.getPlayers().values().stream()
                .map(p -> String.valueOf(p.getLife()))
                .collect(Collectors.joining(" - "));

        game.fireStatusEvent(infoTurn + " (" + infoLife + ")", true, false);
    }
}
