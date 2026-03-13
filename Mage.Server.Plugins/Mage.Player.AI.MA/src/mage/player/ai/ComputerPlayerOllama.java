package mage.player.ai;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.cards.Card;
import mage.constants.RangeOfInfluence;
import mage.game.Game;
import mage.players.Player;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * AI player that delegates top-level priority and mulligan decisions to the local Ollama bridge.
 * Lower-level target/mode/combat choices still fall back to the existing XMage AI.
 */
public class ComputerPlayerOllama extends ComputerPlayerControllableProxy {

    private static final Logger logger = Logger.getLogger(ComputerPlayerOllama.class);

    private final OllamaBridgeClient bridgeClient;

    public ComputerPlayerOllama(String name, RangeOfInfluence range, int skill) {
        super(name, range, skill);
        this.bridgeClient = new OllamaBridgeClient();
    }

    public ComputerPlayerOllama(final ComputerPlayerOllama player) {
        super(player);
        this.bridgeClient = player.bridgeClient;
    }

    @Override
    public ComputerPlayerOllama copy() {
        return new ComputerPlayerOllama(this);
    }

    private boolean isUnderMe(Game game) {
        return game.isSimulation() || this.isGameUnderControl();
    }

    private Player getControllingPlayer(Game game) {
        Player player = game.getPlayer(this.getTurnControlledBy());
        this.lastControllingPlayer = player.prepareControllableProxy(this);
        return this.lastControllingPlayer;
    }

    @Override
    public boolean priority(Game game) {
        if (!isUnderMe(game)) {
            Player player = getControllingPlayer(game);
            try {
                return player.priority(game);
            } finally {
                this.passed = player.isPassed();
            }
        }

        if (!isBridgePriorityStep(game)) {
            return super.priority(game);
        }

        game.resumeTimer(getTurnControlledBy());
        try {
            return bridgePriority(game);
        } catch (Exception e) {
            logger.warn("Ollama bridge priority failed, falling back to built-in AI: " + e.getMessage());
            return super.priority(game);
        } finally {
            game.pauseTimer(getTurnControlledBy());
        }
    }

    @Override
    public boolean chooseMulligan(Game game) {
        if (!isUnderMe(game)) {
            return getControllingPlayer(game).chooseMulligan(game);
        }

        try {
            List<Card> handCards = new ArrayList<Card>(hand.getCards(game));
            OllamaBridgeClient.MulliganDecision decision = bridgeClient.decideMulligan(game, this, handCards);
            logger.info("Ollama mulligan decision for " + getName() + ": " + decision.getDecision() + " (" + decision.getSource() + ")");
            return !decision.isKeep();
        } catch (IOException e) {
            logger.warn("Ollama bridge mulligan failed, falling back to built-in AI: " + e.getMessage());
            return super.chooseMulligan(game);
        }
    }

    private boolean bridgePriority(Game game) throws IOException {
        game.getState().setPriorityPlayerId(playerId);
        game.firePriorityEvent(playerId);

        List<ActivatedAbility> playable = collectLegalActions(game);
        if (playable.isEmpty()) {
            pass(game);
            return false;
        }

        OllamaBridgeClient.BridgeDecision decision = bridgeClient.decide(game, this, playable, "");
        ActivatedAbility chosen = resolveChosenAbility(playable, decision.getActionId());
        if (chosen == null) {
            throw new IOException("Bridge returned unknown action id: " + decision.getActionId());
        }

        logger.info("Ollama selected action for " + getName() + ": "
                + getAbilityAndSourceInfo(game, chosen, true)
                + " (" + decision.getSource() + ")");

        this.activateAbility(chosen, game);
        if (chosen.isUsesStack()) {
            pass(game);
        }
        return true;
    }

    private List<ActivatedAbility> collectLegalActions(Game game) {
        List<ActivatedAbility> result = new ArrayList<ActivatedAbility>();
        List<ActivatedAbility> playable = getPlayable(game, false);
        for (ActivatedAbility ability : playable) {
            List<Ability> options = getPlayableOptions(ability, game);
            if (options.isEmpty()) {
                result.add(ability);
                continue;
            }
            for (Ability option : options) {
                if (option instanceof ActivatedAbility) {
                    result.add((ActivatedAbility) option);
                }
            }
        }
        return result;
    }

    private ActivatedAbility resolveChosenAbility(List<ActivatedAbility> playable, String actionId) {
        if (actionId == null || !actionId.startsWith("action-")) {
            return null;
        }
        try {
            int index = Integer.parseInt(actionId.substring("action-".length()));
            if (index < 0 || index >= playable.size()) {
                return null;
            }
            return playable.get(index);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private boolean isBridgePriorityStep(Game game) {
        switch (game.getTurnStepType()) {
            case PRECOMBAT_MAIN:
            case POSTCOMBAT_MAIN:
                return true;
            default:
                return false;
        }
    }
}
