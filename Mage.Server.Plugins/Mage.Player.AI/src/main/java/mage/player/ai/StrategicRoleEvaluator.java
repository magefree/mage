package mage.player.ai;

import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

/**
 * Determines the strategic role (beatdown or control) for the AI player
 * based on the classic "Who's the Beatdown?" concept by Mike Flores.
 *
 * The beatdown player should:
 * - Attack aggressively
 * - Trade life for board presence
 * - Avoid trading creatures unnecessarily
 *
 * The control player should:
 * - Prioritize removal
 * - Trade creatures to stabilize
 * - Preserve life total
 *
 * This evaluator dynamically adjusts based on:
 * - Deck archetype (from full analysis for self, revealed-only for opponent)
 * - Game momentum (recent life/board changes)
 * - Clock awareness (turns until lethal)
 * - Pressure states (must attack/defend)
 *
 * @author AI improvements
 */
public final class StrategicRoleEvaluator {

    // Role constants
    public static final int ROLE_BEATDOWN = 1;    // Attack aggressively
    public static final int ROLE_CONTROL = -1;    // Defend and stabilize
    public static final int ROLE_FLEXIBLE = 0;    // Adapt to situation

    // Threshold for "close" deck matchups where play/draw matters significantly
    private static final int CLOSE_MATCHUP_THRESHOLD = 15;

    // Bonus for going first in close matchups (tempo advantage)
    private static final int PLAY_ADVANTAGE_BONUS = 20;

    // Threshold for determining beatdown vs control role
    private static final int ROLE_THRESHOLD = 15;

    // Track previous turn's life totals for momentum calculation
    private static final Map<String, Integer> previousLifeTotals = new WeakHashMap<>();

    // Track previous turn's board values for momentum calculation
    private static final Map<String, Integer> previousBoardValues = new WeakHashMap<>();

    private StrategicRoleEvaluator() {
        // Utility class
    }

    /**
     * Determines the strategic role for the player in the current game.
     * Uses full deck analysis for self but only revealed information for opponent.
     *
     * @param player   The AI player
     * @param opponent The opponent player
     * @param game     The current game state
     * @return ROLE_BEATDOWN, ROLE_CONTROL, or ROLE_FLEXIBLE
     */
    public static int determineRole(Player player, Player opponent, Game game) {
        if (player == null || opponent == null || game == null) {
            return ROLE_FLEXIBLE;
        }

        // Analyze our deck fully (we know our own cards)
        int myAggroScore = DeckArchetypeAnalyzer.analyzeAggroScore(player, game);

        // Analyze opponent based only on revealed information
        // This simulates realistic game knowledge
        int oppAggroScore = DeckArchetypeAnalyzer.analyzeOpponentFromRevealedInfo(opponent, game);

        // Base role from deck comparison
        // Higher aggro score = more likely to be beatdown
        int deckDifference = myAggroScore - oppAggroScore;

        // CRITICAL: Play/Draw advantage in close matchups
        // When decks are similar, whoever went first should be the beatdown
        // because they have natural tempo advantage (attack first each turn cycle)
        boolean isCloseMatchup = Math.abs(deckDifference) < CLOSE_MATCHUP_THRESHOLD;
        if (isCloseMatchup) {
            boolean wentFirst = didPlayerGoFirst(player, game);
            if (wentFirst) {
                deckDifference += PLAY_ADVANTAGE_BONUS;  // Push toward beatdown
            } else {
                deckDifference -= PLAY_ADVANTAGE_BONUS;  // Push toward control
            }
        }

        // Adjust for game state
        int lifeDifference = player.getLife() - opponent.getLife();
        int boardAdvantage = calculateBoardAdvantage(player, opponent, game);
        int turnNumber = game.getTurnNum();

        // Life modifier: being behind on life pushes toward aggro (need to race)
        // being ahead pushes toward control (protect the lead)
        int lifeModifier = 0;
        if (lifeDifference < -10) {
            lifeModifier = 10; // Desperate, need to race
        } else if (lifeDifference < -5) {
            lifeModifier = 5;
        } else if (lifeDifference > 10) {
            lifeModifier = -10; // Comfortable lead, play defensive
        } else if (lifeDifference > 5) {
            lifeModifier = -5;
        }

        // Board modifier: being behind on board pushes toward control (need to stabilize)
        int boardModifier = 0;
        if (boardAdvantage < -20) {
            boardModifier = -10; // Behind on board, need to stabilize
        } else if (boardAdvantage < -10) {
            boardModifier = -5;
        } else if (boardAdvantage > 20) {
            boardModifier = 10; // Ahead on board, press the advantage
        } else if (boardAdvantage > 10) {
            boardModifier = 5;
        }

        // Late game favors control decks (their bombs come online)
        int turnModifier = 0;
        if (turnNumber > 8) {
            turnModifier = -15; // Very late game
        } else if (turnNumber > 6) {
            turnModifier = -10; // Late game
        } else if (turnNumber < 4) {
            turnModifier = 5; // Early game favors aggro
        }

        // === DYNAMIC ADJUSTMENTS ===

        // Momentum modifier: track how the game is trending
        int momentumModifier = calculateMomentum(player, opponent, game);

        // Clock modifier: how many turns until someone wins/loses
        int clockModifier = calculateClockModifier(player, opponent, game);

        // Pressure modifier: must attack/defend based on board state
        int pressureModifier = calculatePressureModifier(player, opponent, game);

        // Calculate final role score
        int roleScore = deckDifference + lifeModifier + boardModifier + turnModifier
                + momentumModifier + clockModifier + pressureModifier;

        if (roleScore > ROLE_THRESHOLD) {
            return ROLE_BEATDOWN;
        }
        if (roleScore < -ROLE_THRESHOLD) {
            return ROLE_CONTROL;
        }
        return ROLE_FLEXIBLE;
    }

    /**
     * Calculates momentum based on how the game state is changing.
     * Positive momentum (we're gaining) = more aggressive
     * Negative momentum (we're losing) = more defensive
     */
    private static int calculateMomentum(Player player, Player opponent, Game game) {
        String playerKey = player.getId().toString() + "_" + game.getId().toString();
        String opponentKey = opponent.getId().toString() + "_" + game.getId().toString();

        int currentPlayerLife = player.getLife();
        int currentOpponentLife = opponent.getLife();
        int currentPlayerBoard = calculateBoardValue(player, game);
        int currentOpponentBoard = calculateBoardValue(opponent, game);

        // Get previous values (default to current if first check)
        int prevPlayerLife = previousLifeTotals.getOrDefault(playerKey, currentPlayerLife);
        int prevOpponentLife = previousLifeTotals.getOrDefault(opponentKey, currentOpponentLife);
        int prevPlayerBoard = previousBoardValues.getOrDefault(playerKey, currentPlayerBoard);
        int prevOpponentBoard = previousBoardValues.getOrDefault(opponentKey, currentOpponentBoard);

        // Update cached values for next turn
        previousLifeTotals.put(playerKey, currentPlayerLife);
        previousLifeTotals.put(opponentKey, currentOpponentLife);
        previousBoardValues.put(playerKey, currentPlayerBoard);
        previousBoardValues.put(opponentKey, currentOpponentBoard);

        // Calculate life momentum (positive = we're dealing more damage than taking)
        int lifeMomentum = (prevOpponentLife - currentOpponentLife) - (prevPlayerLife - currentPlayerLife);

        // Calculate board momentum (positive = our board is growing faster)
        int boardMomentum = (currentPlayerBoard - prevPlayerBoard) - (currentOpponentBoard - prevOpponentBoard);

        int momentumModifier = 0;

        // Life momentum: dealing damage faster than taking = push aggressive
        if (lifeMomentum > 5) {
            momentumModifier += 8; // Strong positive momentum
        } else if (lifeMomentum > 2) {
            momentumModifier += 4;
        } else if (lifeMomentum < -5) {
            momentumModifier -= 8; // Strong negative momentum
        } else if (lifeMomentum < -2) {
            momentumModifier -= 4;
        }

        // Board momentum: growing board = push aggressive
        if (boardMomentum > 30) {
            momentumModifier += 5;
        } else if (boardMomentum < -30) {
            momentumModifier -= 5;
        }

        return momentumModifier;
    }

    /**
     * Calculates how close we are to winning or losing (the "clock").
     * If we can win soon, push aggressive. If opponent threatens lethal, push defensive.
     */
    private static int calculateClockModifier(Player player, Player opponent, Game game) {
        // Calculate damage potential per turn for both players
        int ourDamagePerTurn = calculateDamagePerTurn(player, opponent, game);
        int theirDamagePerTurn = calculateDamagePerTurn(opponent, player, game);

        // Calculate turns to win/lose
        int turnsToWin = theirDamagePerTurn > 0 ? (opponent.getLife() / ourDamagePerTurn) : 99;
        int turnsToLose = ourDamagePerTurn > 0 ? (player.getLife() / theirDamagePerTurn) : 99;

        int clockModifier = 0;

        // If we can win in 2-3 turns, push very aggressive
        if (turnsToWin <= 2) {
            clockModifier += 15;
        } else if (turnsToWin <= 3) {
            clockModifier += 10;
        } else if (turnsToWin <= 4) {
            clockModifier += 5;
        }

        // If we might lose in 2-3 turns, must evaluate racing vs blocking
        if (turnsToLose <= 2) {
            // Can we win faster? Race. Otherwise, must defend.
            if (turnsToWin < turnsToLose) {
                clockModifier += 10; // Race!
            } else {
                clockModifier -= 15; // Must defend
            }
        } else if (turnsToLose <= 3) {
            if (turnsToWin < turnsToLose) {
                clockModifier += 5;
            } else {
                clockModifier -= 8;
            }
        }

        return clockModifier;
    }

    /**
     * Calculates pressure based on board state.
     * Evasive creatures or overwhelming board = pressure to attack
     * Opponent has lethal on board = pressure to block
     */
    private static int calculatePressureModifier(Player player, Player opponent, Game game) {
        int pressureModifier = 0;

        // Count our evasive damage (flying, trample with big creatures)
        int evasiveDamage = 0;
        for (Permanent perm : game.getBattlefield().getAllActivePermanents(player.getId())) {
            if (perm.isCreature(game) && canAttack(perm, game)) {
                boolean hasEvasion = perm.getAbilities(game).containsKey(FlyingAbility.getInstance().getId());
                boolean hasTrample = perm.getAbilities(game).containsKey(TrampleAbility.getInstance().getId());

                if (hasEvasion) {
                    evasiveDamage += perm.getPower().getValue();
                } else if (hasTrample && perm.getPower().getValue() >= 4) {
                    evasiveDamage += perm.getPower().getValue() / 2; // Trample gets some through
                }
            }
        }

        // High evasive damage = pressure to attack
        if (evasiveDamage >= 6) {
            pressureModifier += 10;
        } else if (evasiveDamage >= 4) {
            pressureModifier += 5;
        }

        // Count opponent's evasive damage
        int oppEvasiveDamage = 0;
        for (Permanent perm : game.getBattlefield().getAllActivePermanents(opponent.getId())) {
            if (perm.isCreature(game) && canAttack(perm, game)) {
                boolean hasEvasion = perm.getAbilities(game).containsKey(FlyingAbility.getInstance().getId());
                if (hasEvasion) {
                    oppEvasiveDamage += perm.getPower().getValue();
                }
            }
        }

        // High opponent evasive damage = pressure to race or find answers
        if (oppEvasiveDamage >= player.getLife() / 2) {
            // They threaten lethal in 2 turns with evasion - must race
            pressureModifier += 5;
        }

        return pressureModifier;
    }

    /**
     * Estimates damage per turn we can deal to opponent.
     */
    private static int calculateDamagePerTurn(Player attacker, Player defender, Game game) {
        int damage = 0;

        for (Permanent perm : game.getBattlefield().getAllActivePermanents(attacker.getId())) {
            if (perm.isCreature(game) && canAttack(perm, game)) {
                // Assume evasive creatures deal full damage
                boolean hasEvasion = perm.getAbilities(game).containsKey(FlyingAbility.getInstance().getId());
                if (hasEvasion) {
                    damage += perm.getPower().getValue();
                } else {
                    // Ground creatures might be blocked - estimate 50% gets through on average
                    damage += perm.getPower().getValue() / 2;
                }
            }
        }

        return Math.max(damage, 1); // At least 1 to avoid division by zero
    }

    /**
     * Check if a creature can attack (not summoning sick, not tapped).
     */
    private static boolean canAttack(Permanent creature, Game game) {
        return !creature.isTapped()
                && !creature.hasSummoningSickness()
                && creature.canAttack(null, game);
    }

    /**
     * Calculate total board value for a player.
     */
    private static int calculateBoardValue(Player player, Game game) {
        int value = 0;
        for (Permanent perm : game.getBattlefield().getAllActivePermanents(player.getId())) {
            value += evaluatePermanentValue(perm, game);
        }
        return value;
    }

    /**
     * Returns a numeric role score (not just the category).
     * Useful for fine-grained adjustments to weights.
     *
     * @param player   The AI player
     * @param opponent The opponent player
     * @param game     The current game state
     * @return A role score where positive = beatdown, negative = control
     */
    public static int getRoleScore(Player player, Player opponent, Game game) {
        if (player == null || opponent == null || game == null) {
            return 0;
        }

        // Use full deck analysis for self, revealed-only for opponent
        int myAggroScore = DeckArchetypeAnalyzer.analyzeAggroScore(player, game);
        int oppAggroScore = DeckArchetypeAnalyzer.analyzeOpponentFromRevealedInfo(opponent, game);

        int deckDifference = myAggroScore - oppAggroScore;

        boolean isCloseMatchup = Math.abs(deckDifference) < CLOSE_MATCHUP_THRESHOLD;
        if (isCloseMatchup) {
            boolean wentFirst = didPlayerGoFirst(player, game);
            if (wentFirst) {
                deckDifference += PLAY_ADVANTAGE_BONUS;
            } else {
                deckDifference -= PLAY_ADVANTAGE_BONUS;
            }
        }

        int lifeDifference = player.getLife() - opponent.getLife();
        int boardAdvantage = calculateBoardAdvantage(player, opponent, game);
        int turnNumber = game.getTurnNum();

        int lifeModifier = lifeDifference / 2; // Scale down
        int boardModifier = boardAdvantage / 10; // Scale down
        int turnModifier = (turnNumber > 6) ? -10 : 0;

        // Include dynamic modifiers
        int momentumModifier = calculateMomentum(player, opponent, game) / 2;
        int clockModifier = calculateClockModifier(player, opponent, game) / 2;
        int pressureModifier = calculatePressureModifier(player, opponent, game) / 2;

        return deckDifference + lifeModifier + boardModifier + turnModifier
                + momentumModifier + clockModifier + pressureModifier;
    }

    /**
     * Clears cached momentum data. Call at the start of a new game.
     */
    public static void clearCache() {
        previousLifeTotals.clear();
        previousBoardValues.clear();
    }

    /**
     * Checks if this player was the starting player (went first).
     */
    private static boolean didPlayerGoFirst(Player player, Game game) {
        UUID startingPlayerId = game.getStartingPlayerId();
        return player.getId().equals(startingPlayerId);
    }

    /**
     * Calculates the board advantage based on permanent values.
     * Returns positive if player is ahead, negative if behind.
     */
    private static int calculateBoardAdvantage(Player player, Player opponent, Game game) {
        int playerBoardValue = 0;
        int opponentBoardValue = 0;

        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(player.getId())) {
            playerBoardValue += evaluatePermanentValue(permanent, game);
        }

        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(opponent.getId())) {
            opponentBoardValue += evaluatePermanentValue(permanent, game);
        }

        return playerBoardValue - opponentBoardValue;
    }

    /**
     * Simple evaluation of a permanent's board presence value.
     */
    private static int evaluatePermanentValue(Permanent permanent, Game game) {
        int value = 0;

        if (permanent.isCreature(game)) {
            // Creature value based on power and toughness
            value += permanent.getPower().getValue() * 10;
            value += permanent.getToughness().getValue() * 5;
        } else if (permanent.isPlaneswalker(game)) {
            // Planeswalkers are high value
            value += 50;
        } else if (permanent.isArtifact(game) || permanent.isEnchantment(game)) {
            // Non-creature, non-planeswalker permanents
            value += 15;
        } else if (!permanent.isLand(game)) {
            // Other permanents
            value += 10;
        }
        // Lands don't count for board advantage in combat evaluation

        return value;
    }

    /**
     * Returns a human-readable description of the role.
     */
    public static String getRoleName(int role) {
        switch (role) {
            case ROLE_BEATDOWN:
                return "Beatdown";
            case ROLE_CONTROL:
                return "Control";
            default:
                return "Flexible";
        }
    }
}
