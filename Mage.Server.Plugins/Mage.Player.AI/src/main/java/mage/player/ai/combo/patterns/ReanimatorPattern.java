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
 * AI: Reanimator combo pattern.
 * Detects decks trying to cheat creatures into play from the graveyard via:
 * - Enablers: Entomb, Buried Alive, Faithless Looting, Faithful Mending,
 *   Psychic Frog, or evoke creatures (Grief, Solitude)
 * - Reanimation: Reanimate, Animate Dead, Goryo's Vengeance, Unearth,
 *   Emperor of Bones, Persist, etc.
 * - Targets: Griselbrand, Atraxa, Archon of Cruelty, evoke elementals
 *
 * @author duxbuse
 */
public class ReanimatorPattern implements ComboPattern {

    private static final String COMBO_ID = "reanimator-combo";

    // Cards that put creatures in graveyard from library/hand
    private static final Set<String> ENABLERS = new HashSet<>(Arrays.asList(
            // Direct to graveyard from library
            "Entomb",
            "Buried Alive",
            "Unmarked Grave",
            // Looting/discard effects
            "Faithless Looting",
            "Faithful Mending",
            "Careful Study",
            "Putrid Imp",
            "Oona's Prowler",
            "Psychic Frog",
            // Evoke creatures (cast, die, reanimate)
            "Grief",
            "Solitude",
            "Fury",
            "Subtlety",
            "Endurance"
    ));

    // Reanimation spells
    private static final Set<String> REANIMATION_SPELLS = new HashSet<>(Arrays.asList(
            // Classic reanimation
            "Reanimate",
            "Animate Dead",
            "Dance of the Dead",
            "Necromancy",
            "Exhume",
            "Life // Death",
            "Unburial Rites",
            "Persist",
            "Victimize",
            // Instant speed / conditional
            "Goryo's Vengeance",
            "Shallow Grave",
            "Makeshift Mannequin",
            "Corpse Dance",
            // Cheap / low CMC reanimation
            "Unearth",
            "Claim // Fame",
            // Creature-based reanimation
            "Emperor of Bones",
            "Apprentice Necromancer",
            "Doomed Necromancer"
    ));

    // High-value reanimation targets
    private static final Set<String> PRIME_TARGETS = new HashSet<>(Arrays.asList(
            // S-tier targets
            "Griselbrand",
            "Jin-Gitaxias, Core Augur",
            "Razaketh, the Foulblooded",
            "Atraxa, Grand Unifier",
            // A-tier targets
            "Vilis, Broker of Blood",
            "Archon of Cruelty",
            "Iona, Shield of Emeria",
            "Elesh Norn, Grand Cenobite",
            "Serra's Emissary",
            // Evoke elementals (for Scam-style decks)
            "Grief",
            "Solitude",
            "Fury",
            // Control/lock pieces
            "Sire of Insanity",
            "Sheoldred, Whispering One",
            "Blazing Archon",
            // Value creatures
            "Tidespout Tyrant",
            "Inkwell Leviathan",
            "Empyrial Archangel",
            "Angel of Despair",
            "Ashen Rider",
            "Grave Titan",
            "Massacre Wurm",
            "Craterhoof Behemoth",
            "Worldspine Wurm",
            // Modern/Pioneer targets
            "Archon of Emeria",
            "Chancellor of the Annex"
    ));

    private final Set<String> allComboPieces;

    public ReanimatorPattern() {
        allComboPieces = new HashSet<>();
        allComboPieces.addAll(ENABLERS);
        allComboPieces.addAll(REANIMATION_SPELLS);
        allComboPieces.addAll(PRIME_TARGETS);
    }

    @Override
    public String getComboId() {
        return COMBO_ID;
    }

    @Override
    public String getComboName() {
        return "Reanimator";
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

        boolean hasEnabler = false;
        boolean hasEnablerInHand = false;
        boolean hasReanimateSpell = false;
        boolean hasReanimateInHand = false;
        boolean hasTarget = false;
        boolean hasTargetInGraveyard = false;
        String bestTargetInGraveyard = null;
        int bestTargetValue = 0;

        // Check hand
        for (Card card : player.getHand().getCards(game)) {
            String name = card.getName();
            if (ENABLERS.contains(name)) {
                hasEnabler = true;
                hasEnablerInHand = true;
                piecesInHand.add(name);
                foundPieces.add(name);
            } else if (REANIMATION_SPELLS.contains(name)) {
                hasReanimateSpell = true;
                hasReanimateInHand = true;
                piecesInHand.add(name);
                foundPieces.add(name);
            } else if (PRIME_TARGETS.contains(name)) {
                hasTarget = true;
                piecesInHand.add(name);
                foundPieces.add(name);
            }
        }

        // Check graveyard for targets
        for (Card card : player.getGraveyard().getCards(game)) {
            String name = card.getName();
            if (PRIME_TARGETS.contains(name)) {
                hasTarget = true;
                hasTargetInGraveyard = true;
                piecesInGraveyard.add(name);
                foundPieces.add(name);
                // Track the best target
                int value = getTargetValue(name);
                if (value > bestTargetValue) {
                    bestTargetValue = value;
                    bestTargetInGraveyard = name;
                }
            }
            if (ENABLERS.contains(name) || REANIMATION_SPELLS.contains(name)) {
                piecesInGraveyard.add(name);
            }
        }

        // Check library
        for (Card card : player.getLibrary().getCards(game)) {
            String name = card.getName();
            if (ENABLERS.contains(name)) {
                hasEnabler = true;
                foundPieces.add(name);
            } else if (REANIMATION_SPELLS.contains(name)) {
                hasReanimateSpell = true;
                foundPieces.add(name);
            } else if (PRIME_TARGETS.contains(name)) {
                hasTarget = true;
                foundPieces.add(name);
            }
        }

        // Check battlefield (targets already reanimated)
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
            String name = permanent.getName();
            if (PRIME_TARGETS.contains(name)) {
                piecesOnBattlefield.add(name);
                foundPieces.add(name);
            }
        }

        // Build missing pieces
        if (!hasEnabler && !hasTargetInGraveyard) {
            missingPieces.add("Entomb (or creature in graveyard)");
        }
        if (!hasReanimateSpell) {
            missingPieces.add("Reanimate effect");
        }
        if (!hasTarget) {
            missingPieces.add("Reanimation target");
        }

        // Is this a reanimator deck?
        boolean isReanimatorDeck = (hasEnabler || hasTargetInGraveyard) && hasReanimateSpell && hasTarget;

        if (!isReanimatorDeck) {
            return ComboDetectionResult.notDetected(COMBO_ID);
        }

        // Determine state
        ComboState state;
        double confidence;
        String notes;

        if (hasTargetInGraveyard && hasReanimateInHand) {
            // Target in graveyard, reanimate in hand - execute!
            state = ComboState.EXECUTABLE;
            confidence = 1.0;
            notes = "Can reanimate " + bestTargetInGraveyard;
        } else if (hasEnablerInHand && hasReanimateInHand && hasTarget) {
            // Have both enabler and reanimate, can set up combo
            state = ComboState.READY_IN_HAND;
            confidence = 0.9;
            notes = "Enabler and reanimate in hand";
        } else if (hasEnabler && hasReanimateSpell && hasTarget) {
            // All pieces in deck
            state = ComboState.READY_IN_DECK;
            confidence = 0.7;
            notes = "Reanimator pieces in deck";
        } else {
            state = ComboState.PARTIAL;
            confidence = 0.4;
            notes = "Partial reanimator setup";
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
        String enablerInHand = null;
        String reanimateInHand = null;
        String targetInGraveyard = null;

        // Find pieces
        for (Card card : player.getHand().getCards(game)) {
            String name = card.getName();
            if (ENABLERS.contains(name) && enablerInHand == null) {
                enablerInHand = name;
            } else if (REANIMATION_SPELLS.contains(name) && reanimateInHand == null) {
                reanimateInHand = name;
            }
        }

        // Find best target in graveyard
        int bestValue = 0;
        for (Card card : player.getGraveyard().getCards(game)) {
            String name = card.getName();
            if (PRIME_TARGETS.contains(name)) {
                int value = getTargetValue(name);
                if (value > bestValue) {
                    bestValue = value;
                    targetInGraveyard = name;
                }
            }
        }

        // Build sequence
        if (targetInGraveyard != null && reanimateInHand != null) {
            // Can reanimate directly
            sequence.add(reanimateInHand);
        } else if (enablerInHand != null && reanimateInHand != null) {
            // Need to entomb first
            sequence.add(enablerInHand);
            sequence.add(reanimateInHand);
        }

        return sequence;
    }

    @Override
    public Set<String> getComboPieces() {
        return Collections.unmodifiableSet(allComboPieces);
    }

    @Override
    public int getPriority() {
        // High priority - puts massive threats into play early
        return 85;
    }

    @Override
    public String getExpectedOutcome() {
        return "massive creature advantage";
    }

    /**
     * Get the relative value of a reanimation target.
     * Higher value = better target.
     */
    private int getTargetValue(String cardName) {
        switch (cardName) {
            case "Griselbrand":
                return 100;  // Draw 7 repeatedly
            case "Jin-Gitaxias, Core Augur":
                return 95;   // Draw 7, opponent discards
            case "Razaketh, the Foulblooded":
                return 90;   // Tutor repeatedly
            case "Atraxa, Grand Unifier":
                return 88;   // Draw 10+
            case "Vilis, Broker of Blood":
                return 85;   // Draw engine
            case "Archon of Cruelty":
                return 80;   // Value engine
            case "Iona, Shield of Emeria":
            case "Serra's Emissary":
                return 75;   // Lock out color/card type
            case "Elesh Norn, Grand Cenobite":
                return 70;   // Board control
            case "Grief":
                return 68;   // Thoughtseize on a stick (Scam)
            case "Sire of Insanity":
                return 65;   // Discard lock
            case "Solitude":
                return 62;   // Removal + body
            case "Sheoldred, Whispering One":
                return 60;
            case "Fury":
                return 55;   // Board wipe potential
            default:
                return 50;
        }
    }

    @Override
    public String toString() {
        return "ReanimatorPattern";
    }
}
