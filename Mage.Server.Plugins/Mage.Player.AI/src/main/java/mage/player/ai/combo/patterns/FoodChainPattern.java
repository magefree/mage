package mage.player.ai.combo.patterns;

import mage.cards.Card;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.player.ai.combo.ComboDetectionResult;
import mage.player.ai.combo.ComboDetectionResult.ComboState;
import mage.player.ai.combo.ComboPattern;
import mage.players.Player;

import java.util.*;

/**
 * AI: Food Chain combo pattern.
 * Detects decks using Food Chain + creatures that can be cast from exile
 * to generate infinite mana and creature casts.
 *
 * The combo: Food Chain exiles a creature for mana to cast creatures.
 * With creatures that can be cast from exile (Misthollow Griffin, etc.),
 * you can generate infinite mana and cast your commander infinite times.
 *
 * @author duxbuse
 */
public class FoodChainPattern implements ComboPattern {

    private static final String COMBO_ID = "food-chain-combo";

    // The key combo piece
    private static final String FOOD_CHAIN = "Food Chain";

    // Creatures that can be cast from exile (enable infinite loop)
    private static final Set<String> ETERNAL_CREATURES = new HashSet<>(Arrays.asList(
            "Misthollow Griffin",
            "Eternal Scourge",
            "Squee, the Immortal"  // Can be cast from graveyard too
    ));

    // Commanders commonly used with Food Chain
    private static final Set<String> FOOD_CHAIN_COMMANDERS = new HashSet<>(Arrays.asList(
            "The First Sliver",       // Cascade infinitely
            "Korvold, Fae-Cursed King", // Draw deck
            "Prossh, Skyraider of Kher", // Infinite tokens
            "General Tazri",          // Tutor/win
            "Ukkima, Stalking Shadow" // Direct damage
    ));

    // Win conditions with infinite creature casts
    private static final Set<String> PAYOFFS = new HashSet<>(Arrays.asList(
            "Walking Ballista",       // Infinite damage
            "Goblin Cannon",          // Infinite damage
            "Altar of the Brood",     // Mill everyone
            "Impact Tremors",         // Damage on creature ETB
            "Purphoros, God of the Forge", // Damage on creature ETB
            "Aetherflux Reservoir",   // Life/damage
            "Beast Whisperer",        // Draw deck
            "Guardian Project"        // Draw deck
    ));

    // Tutors that find Food Chain
    private static final Set<String> TUTORS = new HashSet<>(Arrays.asList(
            "Demonic Tutor",
            "Vampiric Tutor",
            "Imperial Seal",
            "Grim Tutor",
            "Wishclaw Talisman",
            "Diabolic Intent",
            "Scheming Symmetry",
            "Necropotence",    // Card selection
            "Sterling Grove",  // Enchantment tutor
            "Idyllic Tutor",   // Enchantment tutor
            "Academy Rector"   // Dies to get enchantment
    ));

    private final Set<String> allComboPieces;

    public FoodChainPattern() {
        allComboPieces = new HashSet<>();
        allComboPieces.add(FOOD_CHAIN);
        allComboPieces.addAll(ETERNAL_CREATURES);
        allComboPieces.addAll(FOOD_CHAIN_COMMANDERS);
        allComboPieces.addAll(PAYOFFS);
    }

    @Override
    public String getComboId() {
        return COMBO_ID;
    }

    @Override
    public String getComboName() {
        return "Food Chain";
    }

    @Override
    public ComboDetectionResult detectCombo(Game game, UUID playerId) {
        Player player = game.getPlayer(playerId);
        if (player == null) {
            return ComboDetectionResult.notDetected(COMBO_ID);
        }

        Set<String> foundPieces = new HashSet<>();
        Set<String> missingPieces = new HashSet<>();
        Set<String> piecesInHand = new HashSet<>();
        Set<String> piecesOnBattlefield = new HashSet<>();
        Set<String> piecesInGraveyard = new HashSet<>();

        boolean hasFoodChain = false;
        boolean hasFoodChainInHand = false;
        boolean hasFoodChainOnBattlefield = false;

        boolean hasEternalCreature = false;
        boolean hasEternalInHand = false;
        boolean hasEternalOnBattlefield = false;
        boolean hasEternalInExile = false;
        String eternalCreatureName = null;

        boolean hasPayoff = false;
        boolean hasPayoffReady = false;

        boolean hasTutor = false;
        boolean hasFoodChainCommander = false;

        // Check hand
        for (Card card : player.getHand().getCards(game)) {
            String name = card.getName();
            if (name.equals(FOOD_CHAIN)) {
                hasFoodChain = true;
                hasFoodChainInHand = true;
                piecesInHand.add(name);
                foundPieces.add(name);
            } else if (ETERNAL_CREATURES.contains(name)) {
                hasEternalCreature = true;
                hasEternalInHand = true;
                eternalCreatureName = name;
                piecesInHand.add(name);
                foundPieces.add(name);
            } else if (PAYOFFS.contains(name)) {
                hasPayoff = true;
                piecesInHand.add(name);
                foundPieces.add(name);
            } else if (TUTORS.contains(name)) {
                hasTutor = true;
            }
        }

        // Check battlefield
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
            String name = permanent.getName();
            if (name.equals(FOOD_CHAIN)) {
                hasFoodChain = true;
                hasFoodChainOnBattlefield = true;
                piecesOnBattlefield.add(name);
                foundPieces.add(name);
            } else if (ETERNAL_CREATURES.contains(name)) {
                hasEternalCreature = true;
                hasEternalOnBattlefield = true;
                eternalCreatureName = name;
                piecesOnBattlefield.add(name);
                foundPieces.add(name);
            } else if (PAYOFFS.contains(name)) {
                hasPayoff = true;
                hasPayoffReady = true;
                piecesOnBattlefield.add(name);
                foundPieces.add(name);
            } else if (FOOD_CHAIN_COMMANDERS.contains(name)) {
                hasFoodChainCommander = true;
                piecesOnBattlefield.add(name);
                foundPieces.add(name);
            }
        }

        // Check exile for eternal creatures
        for (Card card : game.getExile().getAllCards(game)) {
            if (card.getOwnerId().equals(playerId)) {
                String name = card.getName();
                if (ETERNAL_CREATURES.contains(name)) {
                    hasEternalCreature = true;
                    hasEternalInExile = true;
                    eternalCreatureName = name;
                    foundPieces.add(name);
                }
            }
        }

        // Check library
        for (Card card : player.getLibrary().getCards(game)) {
            String name = card.getName();
            if (name.equals(FOOD_CHAIN)) {
                hasFoodChain = true;
                foundPieces.add(name);
            } else if (ETERNAL_CREATURES.contains(name)) {
                hasEternalCreature = true;
                foundPieces.add(name);
            } else if (PAYOFFS.contains(name)) {
                hasPayoff = true;
                foundPieces.add(name);
            } else if (TUTORS.contains(name)) {
                hasTutor = true;
            }
        }

        // Check graveyard
        for (Card card : player.getGraveyard().getCards(game)) {
            String name = card.getName();
            if (allComboPieces.contains(name)) {
                piecesInGraveyard.add(name);
                // Squee can be cast from graveyard
                if (name.equals("Squee, the Immortal")) {
                    hasEternalCreature = true;
                    foundPieces.add(name);
                }
            }
        }

        // Check command zone for Food Chain commanders
        // Note: This is a simplification; actual commander detection is more complex
        // The deck having Food Chain + eternal creature is enough to detect the pattern

        // Build missing pieces
        if (!hasFoodChain) {
            missingPieces.add("Food Chain");
        }
        if (!hasEternalCreature) {
            missingPieces.add("Misthollow Griffin or Eternal Scourge");
        }

        // Is this a Food Chain deck?
        boolean isFoodChainDeck = hasFoodChain && hasEternalCreature;

        if (!isFoodChainDeck) {
            return ComboDetectionResult.notDetected(COMBO_ID);
        }

        // Determine state
        ComboState state;
        double confidence;
        String notes;

        if (hasFoodChainOnBattlefield && (hasEternalOnBattlefield || hasEternalInHand || hasEternalInExile)) {
            // Food Chain on battlefield, have creature to combo with
            state = ComboState.EXECUTABLE;
            confidence = 1.0;
            notes = "Food Chain ready, can generate infinite mana with " + eternalCreatureName;
        } else if (hasFoodChainInHand && (hasEternalInHand || hasEternalOnBattlefield || hasEternalInExile)) {
            // Both pieces available, need to cast Food Chain
            state = ComboState.READY_IN_HAND;
            confidence = 0.9;
            notes = "Food Chain in hand, eternal creature available";
        } else if (hasFoodChain && hasEternalCreature) {
            // Pieces in deck
            state = ComboState.READY_IN_DECK;
            confidence = hasTutor ? 0.75 : 0.5;
            notes = "Food Chain combo pieces in deck" + (hasTutor ? " (have tutor)" : "");
        } else {
            state = ComboState.PARTIAL;
            confidence = 0.3;
            notes = "Partial Food Chain setup";
        }

        return ComboDetectionResult.builder(COMBO_ID)
                .state(state)
                .confidence(confidence)
                .foundPieces(foundPieces)
                .missingPieces(missingPieces)
                .piecesInHand(piecesInHand)
                .piecesOnBattlefield(piecesOnBattlefield)
                .piecesInGraveyard(piecesInGraveyard)
                .notes(notes)
                .build();
    }

    @Override
    public boolean canExecuteNow(Game game, UUID playerId) {
        ComboDetectionResult result = detectCombo(game, playerId);
        return result.getState() == ComboState.EXECUTABLE;
    }

    @Override
    public List<String> getComboSequence(Game game, UUID playerId) {
        Player player = game.getPlayer(playerId);
        if (player == null) {
            return Collections.emptyList();
        }

        List<String> sequence = new ArrayList<>();

        boolean foodChainOnField = false;
        String eternalCreature = null;
        String payoff = null;

        // Check battlefield
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
            String name = permanent.getName();
            if (name.equals(FOOD_CHAIN)) {
                foodChainOnField = true;
            } else if (ETERNAL_CREATURES.contains(name)) {
                eternalCreature = name;
            } else if (PAYOFFS.contains(name)) {
                payoff = name;
            }
        }

        // Check hand
        String foodChainInHand = null;
        String eternalInHand = null;
        String payoffInHand = null;

        for (Card card : player.getHand().getCards(game)) {
            String name = card.getName();
            if (name.equals(FOOD_CHAIN)) {
                foodChainInHand = name;
            } else if (ETERNAL_CREATURES.contains(name) && eternalInHand == null) {
                eternalInHand = name;
            } else if (PAYOFFS.contains(name) && payoffInHand == null) {
                payoffInHand = name;
            }
        }

        // Check exile
        for (Card card : game.getExile().getAllCards(game)) {
            if (card.getOwnerId().equals(playerId) && ETERNAL_CREATURES.contains(card.getName())) {
                eternalCreature = card.getName();
            }
        }

        // Build sequence
        if (!foodChainOnField && foodChainInHand != null) {
            sequence.add("Food Chain");
        }

        if (eternalCreature == null && eternalInHand != null) {
            sequence.add(eternalInHand);
        }

        // If we have Food Chain and eternal creature, start the loop
        if (foodChainOnField || foodChainInHand != null) {
            String creature = eternalCreature != null ? eternalCreature : eternalInHand;
            if (creature != null) {
                sequence.add("Activate Food Chain exiling " + creature);
                sequence.add("Cast " + creature + " from exile");
                sequence.add("Repeat for infinite mana");
            }
        }

        // Cast payoff if we have one
        if (payoffInHand != null) {
            sequence.add(payoffInHand + " (win condition)");
        }

        return sequence;
    }

    @Override
    public Set<String> getComboPieces() {
        return Collections.unmodifiableSet(allComboPieces);
    }

    @Override
    public int getPriority() {
        // High priority - generates infinite mana and creature casts
        return 90;
    }

    @Override
    public String getExpectedOutcome() {
        return "infinite mana and creature casts";
    }

    @Override
    public String toString() {
        return "FoodChainPattern";
    }
}
