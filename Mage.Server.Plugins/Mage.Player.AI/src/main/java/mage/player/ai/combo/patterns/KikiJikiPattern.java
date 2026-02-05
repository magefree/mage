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
 * AI: Kiki-Jiki / Splinter Twin combo pattern.
 * Detects decks trying to create infinite creature copies with
 * Kiki-Jiki or Splinter Twin + creatures that untap them.
 *
 * The combo: Kiki-Jiki copies a creature that untaps Kiki.
 * The copy has haste and untaps Kiki, allowing infinite copies.
 *
 * @author duxbuse
 */
public class KikiJikiPattern implements ComboPattern {

    private static final String COMBO_ID = "kiki-jiki-combo";

    // The copy makers (creatures/enchantments that make token copies)
    private static final Set<String> COPY_MAKERS = new HashSet<>(Arrays.asList(
            "Kiki-Jiki, Mirror Breaker",
            "Splinter Twin",
            "Helm of the Host",
            "Saheeli Rai",            // -2: Copy artifact or creature, combo with Felidar Guardian
            "Saheeli, Sublime Artificer" // Can copy with other pieces
    ));

    // Creatures that untap target creature/permanent (combo with Kiki/Twin)
    private static final Set<String> UNTAPPERS = new HashSet<>(Arrays.asList(
            "Zealous Conscripts",     // Untaps and gains control
            "Deceiver Exarch",        // Untaps target permanent
            "Pestermite",             // Untaps target permanent
            "Village Bell-Ringer",    // Untaps all your creatures
            "Bounding Krasis",        // Untaps target creature
            "Breaching Hippocamp",    // Untaps target creature
            "Great Oak Guardian",     // Untaps all creatures
            "Midnight Guard",         // Untaps when creature enters (needs extra piece)
            "Corridor Monitor",       // Untaps target artifact or creature
            "Hyrax Tower Scout",      // Untaps target creature
            "Intruder Alarm",         // Untaps all on creature entering
            "Combat Celebrant"        // Extra combat untaps
    ));

    // Blink effects that reset Kiki/copy makers (flicker = exile and return)
    // These work because the permanent returns untapped
    private static final Set<String> BLINKERS = new HashSet<>(Arrays.asList(
            "Felidar Guardian",       // Flickers any permanent - Saheeli combo!
            "Restoration Angel",      // Flickers non-Angel creature
            "Flickerwisp",            // Flickers any permanent (delayed return)
            "Charming Prince",        // Flickers your creature
            "Wispweaver Angel",       // Flickers your creature
            "Glasspool Mimic",        // Copies a creature
            "Spark Double",           // Copies planeswalker or creature
            "Clever Impersonator"     // Copies any nonland permanent
    ));

    // Cards that tutor for creatures
    private static final Set<String> TUTORS = new HashSet<>(Arrays.asList(
            "Imperial Recruiter",     // Tutors power 2 or less (gets Deceiver Exarch, Pestermite)
            "Recruiter of the Guard", // Tutors toughness 2 or less
            "Chord of Calling",       // Flash creature tutor
            "Eldritch Evolution",     // Creature upgrade tutor
            "Birthing Pod",           // Creature chain
            "Finale of Devastation",  // Creature tutor to battlefield
            "Green Sun's Zenith",     // Green creature tutor
            "Worldly Tutor"           // Creature to top
    ));

    // All combo enablers (untappers + blinkers)
    private final Set<String> allEnablers;
    private final Set<String> allComboPieces;

    public KikiJikiPattern() {
        allEnablers = new HashSet<>();
        allEnablers.addAll(UNTAPPERS);
        allEnablers.addAll(BLINKERS);

        allComboPieces = new HashSet<>();
        allComboPieces.addAll(COPY_MAKERS);
        allComboPieces.addAll(allEnablers);
        // Don't include generic tutors in pieces
    }

    @Override
    public String getComboId() {
        return COMBO_ID;
    }

    @Override
    public String getComboName() {
        return "Kiki-Jiki / Splinter Twin / Saheeli";
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

        boolean hasCopyMaker = false;
        boolean hasCopyMakerInHand = false;
        boolean hasCopyMakerOnBattlefield = false;
        String copyMakerOnBattlefield = null;

        boolean hasEnabler = false;       // Untapper OR blinker
        boolean hasEnablerInHand = false;
        boolean hasEnablerOnBattlefield = false;
        String enablerOnBattlefield = null;

        boolean hasTutor = false;

        // Check hand
        for (Card card : player.getHand().getCards(game)) {
            String name = card.getName();
            if (COPY_MAKERS.contains(name)) {
                hasCopyMaker = true;
                hasCopyMakerInHand = true;
                piecesInHand.add(name);
                foundPieces.add(name);
            } else if (allEnablers.contains(name)) {
                hasEnabler = true;
                hasEnablerInHand = true;
                piecesInHand.add(name);
                foundPieces.add(name);
            } else if (TUTORS.contains(name)) {
                hasTutor = true;
            }
        }

        // Check battlefield
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
            String name = permanent.getName();
            if (COPY_MAKERS.contains(name)) {
                hasCopyMaker = true;
                hasCopyMakerOnBattlefield = true;
                copyMakerOnBattlefield = name;
                piecesOnBattlefield.add(name);
                foundPieces.add(name);
            } else if (allEnablers.contains(name)) {
                hasEnabler = true;
                hasEnablerOnBattlefield = true;
                enablerOnBattlefield = name;
                piecesOnBattlefield.add(name);
                foundPieces.add(name);
            }
        }

        // Check library
        for (Card card : player.getLibrary().getCards(game)) {
            String name = card.getName();
            if (COPY_MAKERS.contains(name)) {
                hasCopyMaker = true;
                foundPieces.add(name);
            } else if (allEnablers.contains(name)) {
                hasEnabler = true;
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
            }
        }

        // Build missing pieces
        if (!hasCopyMaker) {
            missingPieces.add("Kiki-Jiki, Splinter Twin, or Saheeli Rai");
        }
        if (!hasEnabler) {
            missingPieces.add("Enabler (Felidar Guardian, Deceiver Exarch, etc.)");
        }

        // Is this a Kiki/Saheeli combo deck?
        boolean isKikiDeck = hasCopyMaker && hasEnabler;

        if (!isKikiDeck) {
            return ComboDetectionResult.notDetected(COMBO_ID);
        }

        // Determine state
        ComboState state;
        double confidence;
        String notes;

        // Check if copy maker is ready (untapped, no summoning sickness)
        // For Saheeli, check loyalty (needs at least 2 for -2 ability)
        boolean copyMakerReady = false;
        if (hasCopyMakerOnBattlefield) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
                String name = permanent.getName();
                if (COPY_MAKERS.contains(name)) {
                    if (permanent.isPlaneswalker(game)) {
                        // Saheeli needs loyalty >= 2 for -2 ability
                        if (permanent.getCounters(game).getCount("loyalty") >= 2) {
                            copyMakerReady = true;
                            break;
                        }
                    } else if (!permanent.isTapped() && !permanent.hasSummoningSickness()) {
                        copyMakerReady = true;
                        break;
                    }
                }
            }
        }

        if (copyMakerReady && hasEnablerInHand) {
            // Copy maker ready, enabler in hand - win!
            state = ComboState.EXECUTABLE;
            confidence = 1.0;
            notes = copyMakerOnBattlefield + " ready, can play enabler to combo";
        } else if (hasCopyMakerOnBattlefield && hasEnablerOnBattlefield) {
            // Both on battlefield - check if can activate
            state = ComboState.EXECUTABLE;
            confidence = 0.95;
            notes = "Both pieces on battlefield";
        } else if (hasCopyMakerInHand && hasEnablerInHand) {
            // Both in hand
            state = ComboState.READY_IN_HAND;
            confidence = 0.85;
            notes = "Both combo pieces in hand";
        } else if ((hasCopyMakerOnBattlefield && hasEnabler) || (hasEnablerOnBattlefield && hasCopyMaker)) {
            // One piece on battlefield, other in deck
            state = ComboState.READY_IN_HAND;
            confidence = 0.75;
            notes = "One piece on battlefield, other in deck/hand";
        } else if (hasCopyMaker && hasEnabler) {
            // Both in deck
            state = ComboState.READY_IN_DECK;
            confidence = hasTutor ? 0.7 : 0.5;
            notes = "Combo pieces in deck" + (hasTutor ? " (have tutor)" : "");
        } else {
            state = ComboState.PARTIAL;
            confidence = 0.3;
            notes = "Partial combo setup";
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

        // Check what's on battlefield
        String copyMakerOnField = null;
        String enablerOnField = null;
        boolean copyMakerReady = false;

        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
            String name = permanent.getName();
            if (COPY_MAKERS.contains(name)) {
                copyMakerOnField = name;
                if (permanent.isPlaneswalker(game)) {
                    // Saheeli needs 2+ loyalty
                    if (permanent.getCounters(game).getCount("loyalty") >= 2) {
                        copyMakerReady = true;
                    }
                } else if (!permanent.isTapped() && !permanent.hasSummoningSickness()) {
                    copyMakerReady = true;
                }
            } else if (allEnablers.contains(name)) {
                enablerOnField = name;
            }
        }

        // Check hand
        String copyMakerInHand = null;
        String enablerInHand = null;
        for (Card card : player.getHand().getCards(game)) {
            String name = card.getName();
            if (COPY_MAKERS.contains(name) && copyMakerInHand == null) {
                copyMakerInHand = name;
            } else if (allEnablers.contains(name) && enablerInHand == null) {
                enablerInHand = name;
            }
        }

        // Build optimal sequence
        if (copyMakerReady && enablerInHand != null) {
            // Cast enabler, then activate copy maker targeting it
            sequence.add(enablerInHand);
            sequence.add(copyMakerOnField + " (activate targeting " + enablerInHand + ")");
        } else if (copyMakerOnField != null && enablerOnField != null) {
            // Both on battlefield - activate
            sequence.add(copyMakerOnField + " (activate targeting " + enablerOnField + ")");
        } else if (copyMakerInHand != null && enablerInHand != null) {
            // Need to play both - play copy maker first, then enabler
            sequence.add(copyMakerInHand);
            sequence.add(enablerInHand);
        }

        return sequence;
    }

    @Override
    public Set<String> getComboPieces() {
        return Collections.unmodifiableSet(allComboPieces);
    }

    @Override
    public int getPriority() {
        // Very high priority - instant win combo
        return 95;
    }

    @Override
    public String getExpectedOutcome() {
        return "infinite hasty creatures";
    }

    @Override
    public String toString() {
        return "KikiJikiPattern";
    }
}
