package mage.player.ai.combo.patterns;

import mage.cards.Card;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.player.ai.combo.ComboDetectionResult;
import mage.player.ai.combo.ComboDetectionResult.ComboState;
import mage.player.ai.combo.ComboPattern;
import mage.players.Player;
import mage.watchers.Watcher;
import mage.watchers.common.CastSpellLastTurnWatcher;

import java.util.*;

/**
 * AI: Storm combo pattern detection.
 * Detects storm decks and helps AI execute storm sequences properly:
 * Fast Mana -> Cost Reducers -> Rituals -> Cantrips -> Engine -> Payoff
 *
 * @author duxbuse
 */
public class StormComboPattern implements ComboPattern {

    private static final String COMBO_ID = "storm-combo";

    // Storm payoff cards - the cards that win with storm
    private static final Set<String> STORM_PAYOFFS = new HashSet<>(Arrays.asList(
            "Tendrils of Agony",
            "Grapeshot",
            "Brain Freeze",
            "Mind's Desire",
            "Empty the Warrens",
            "Ground Rift",
            "Temporal Fissure",
            "Hunting Pack",
            "Wing Shards",
            "Dragonstorm",
            "Ignite Memories",
            "Flusterstorm",
            "Grape Shot"  // alternate spelling
    ));

    // Mana acceleration - rituals and fast mana
    private static final Set<String> RITUALS = new HashSet<>(Arrays.asList(
            "Dark Ritual",
            "Cabal Ritual",
            "Seething Song",
            "Pyretic Ritual",
            "Desperate Ritual",
            "Rite of Flame",
            "Rain of Filth",
            "Culling the Weak",
            "Infernal Plunge",
            "Songs of the Damned",
            "Burnt Offering",
            "Sacrifice",
            "Channel",
            "High Tide"
    ));

    // Fast mana artifacts
    private static final Set<String> FAST_MANA = new HashSet<>(Arrays.asList(
            "Lion's Eye Diamond",
            "Lotus Petal",
            "Chrome Mox",
            "Mox Opal",
            "Mox Diamond",
            "Simian Spirit Guide",
            "Elvish Spirit Guide",
            "Jeweled Lotus",
            "Mana Crypt",
            "Sol Ring",
            "Mana Vault",
            "Grim Monolith"
    ));

    // Cantrips - card draw to find pieces and build storm
    private static final Set<String> CANTRIPS = new HashSet<>(Arrays.asList(
            // Blue cantrips
            "Brainstorm",
            "Ponder",
            "Preordain",
            "Gitaxian Probe",
            "Serum Visions",
            "Sleight of Hand",
            "Opt",
            "Consider",
            "Thought Scour",
            "Mental Note",
            // Red impulse draw
            "Reckless Impulse",
            "Wrenn's Resolve",
            "Light Up the Stage",
            "Expressive Iteration",
            "Jeska's Will",
            // Multi-color / colorless
            "Manamorphose",
            "Street Wraith",
            // Black draw
            "Night's Whisper",
            "Sign in Blood",
            // Looting effects
            "Faithless Looting",
            "Careful Study",
            "Ideas Unbound",
            "Frantic Search",
            // Value permanents
            "Heroes' Hangout"
    ));

    // Engine cards - enable storm turns
    private static final Set<String> ENGINE_CARDS = new HashSet<>(Arrays.asList(
            "Past in Flames",
            "Yawgmoth's Will",
            "Underworld Breach",
            "Echo of Eons",
            "Timetwister",
            "Wheel of Fortune",
            "Windfall",
            "Ad Nauseam",
            "Necropotence",
            "Peer into the Abyss"
    ));

    // Tutors and wishes - find the payoff when ready to win
    private static final Set<String> TUTORS = new HashSet<>(Arrays.asList(
            // Wishes (grab from sideboard/outside game)
            "Burning Wish",
            "Cunning Wish",
            "Death Wish",
            "Glittering Wish",
            "Living Wish",
            "Wish",
            "Fae of Wishes",
            // Instant/Sorcery tutors
            "Mystical Tutor",
            "Personal Tutor",
            "Merchant Scroll",
            "Solve the Equation",
            "Spellseeker",
            // General tutors
            "Demonic Tutor",
            "Vampiric Tutor",
            "Imperial Seal",
            "Grim Tutor",
            "Mastermind's Acquisition",
            "Beseech the Mirror",
            "Dark Petition",
            "Diabolic Tutor"
    ));

    // Cost reducers - permanents that reduce spell costs or generate mana on cast
    private static final Set<String> COST_REDUCERS = new HashSet<>(Arrays.asList(
            // Medallions
            "Ruby Medallion",
            "Sapphire Medallion",
            "Jet Medallion",
            "Pearl Medallion",
            "Emerald Medallion",
            // Creatures that reduce costs
            "Baral, Chief of Compliance",
            "Goblin Electromancer",
            "Nightscape Familiar",
            "Stormscape Familiar",
            "Thornscape Familiar",
            "Sunscape Familiar",
            // Artifacts that reduce costs
            "Helm of Awakening",
            "Cloud Key",
            "Semblance Anvil",
            "Primal Amulet",
            "Inspiring Statuary",
            // Enchantments that reduce costs
            "Artist's Talent",
            // Mana generators on cast
            "Birgi, God of Storytelling",
            "Storm-Kiln Artist",
            "Runaway Steam-Kin",
            // Copy/value engines
            "Ral, Storm Conduit",
            "Ral, Monsoon Mage",
            "Thousand-Year Storm",
            "Bonus Round",
            "Swarm Intelligence"
    ));

    // All combo pieces combined
    private final Set<String> allComboPieces;

    public StormComboPattern() {
        allComboPieces = new HashSet<>();
        allComboPieces.addAll(STORM_PAYOFFS);
        allComboPieces.addAll(RITUALS);
        allComboPieces.addAll(FAST_MANA);
        allComboPieces.addAll(CANTRIPS);
        allComboPieces.addAll(ENGINE_CARDS);
        allComboPieces.addAll(COST_REDUCERS);
        allComboPieces.addAll(TUTORS);
    }

    @Override
    public String getComboId() {
        return COMBO_ID;
    }

    @Override
    public String getComboName() {
        return "Storm Combo";
    }

    @Override
    public ComboDetectionResult detectCombo(Game game, UUID playerId) {
        Player player = game.getPlayer(playerId);
        if (player == null) {
            return ComboDetectionResult.notDetected(COMBO_ID);
        }

        Set<String> foundPieces = new HashSet<>();
        Set<String> piecesInHand = new HashSet<>();
        Set<String> piecesOnBattlefield = new HashSet<>();
        Set<String> piecesInGraveyard = new HashSet<>();

        int payoffsFound = 0;
        int ritualsFound = 0;
        int cantripsFound = 0;
        int enginesFound = 0;
        int tutorsFound = 0;

        boolean hasPayoffInHand = false;
        boolean hasTutorInHand = false;
        int ritualsInHand = 0;

        // Scan all zones
        // Hand
        for (Card card : player.getHand().getCards(game)) {
            String name = card.getName();
            if (STORM_PAYOFFS.contains(name)) {
                payoffsFound++;
                hasPayoffInHand = true;
                piecesInHand.add(name);
                foundPieces.add(name);
            } else if (TUTORS.contains(name)) {
                tutorsFound++;
                hasTutorInHand = true;
                piecesInHand.add(name);
                foundPieces.add(name);
            } else if (RITUALS.contains(name) || FAST_MANA.contains(name)) {
                ritualsFound++;
                ritualsInHand++;
                piecesInHand.add(name);
                foundPieces.add(name);
            } else if (CANTRIPS.contains(name)) {
                cantripsFound++;
                piecesInHand.add(name);
                foundPieces.add(name);
            } else if (ENGINE_CARDS.contains(name)) {
                enginesFound++;
                piecesInHand.add(name);
                foundPieces.add(name);
            }
        }

        // Track cost reducers on battlefield
        int costReducersOnBattlefield = 0;

        // Battlefield (for mana rocks, cost reducers, etc.)
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
            String name = permanent.getName();
            if (FAST_MANA.contains(name)) {
                ritualsFound++;
                piecesOnBattlefield.add(name);
                foundPieces.add(name);
            } else if (COST_REDUCERS.contains(name)) {
                costReducersOnBattlefield++;
                piecesOnBattlefield.add(name);
                foundPieces.add(name);
            }
        }

        // Library
        int costReducersInDeck = 0;
        for (Card card : player.getLibrary().getCards(game)) {
            String name = card.getName();
            if (STORM_PAYOFFS.contains(name)) {
                payoffsFound++;
                foundPieces.add(name);
            } else if (TUTORS.contains(name)) {
                tutorsFound++;
                foundPieces.add(name);
            } else if (RITUALS.contains(name) || FAST_MANA.contains(name)) {
                ritualsFound++;
                foundPieces.add(name);
            } else if (CANTRIPS.contains(name)) {
                cantripsFound++;
                foundPieces.add(name);
            } else if (ENGINE_CARDS.contains(name)) {
                enginesFound++;
                foundPieces.add(name);
            } else if (COST_REDUCERS.contains(name)) {
                costReducersInDeck++;
                foundPieces.add(name);
            }
        }

        // Graveyard (for Past in Flames)
        for (Card card : player.getGraveyard().getCards(game)) {
            String name = card.getName();
            if (allComboPieces.contains(name)) {
                piecesInGraveyard.add(name);
                foundPieces.add(name);
            }
        }

        // Determine if this is a storm deck
        // Need at least: 1 payoff (or tutor to find it), 3+ rituals/mana, some cantrips OR cost reducers
        int totalCostReducers = costReducersOnBattlefield + costReducersInDeck;
        boolean hasFinisherAccess = payoffsFound >= 1 || (tutorsFound >= 1 && payoffsFound >= 1);
        boolean isStormDeck = hasFinisherAccess && ritualsFound >= 3
                && (cantripsFound >= 2 || enginesFound >= 1 || totalCostReducers >= 2 || tutorsFound >= 2);

        if (!isStormDeck) {
            return ComboDetectionResult.notDetected(COMBO_ID);
        }

        // Calculate confidence and state
        // Cost reducers on battlefield significantly increase storm potential
        double confidence = Math.min(1.0,
                (payoffsFound * 0.3) + (ritualsFound * 0.1) + (cantripsFound * 0.05)
                + (enginesFound * 0.15) + (costReducersOnBattlefield * 0.15));

        // Check current storm count
        int currentStormCount = getStormCount(game, playerId);

        ComboState state;
        String notes;

        // Can we execute lethal storm now?
        Player opponent = getOpponent(game, playerId);
        int opponentLife = opponent != null ? opponent.getLife() : 20;

        // Cost reducers make going off easier - reduce the threshold for rituals needed
        int effectiveRitualsNeeded = costReducersOnBattlefield >= 2 ? 1 : (costReducersOnBattlefield >= 1 ? 2 : 3);

        // Can get payoff if we have it in hand OR have a tutor and payoff exists in deck
        boolean canAccessPayoff = hasPayoffInHand || (hasTutorInHand && payoffsFound > 0);

        if (canAccessPayoff && currentStormCount >= 9 && ritualsInHand >= 1) {
            // We can likely go off - 10 storm Tendrils is 20 damage
            // If we have a tutor, we can grab the payoff at high storm count
            state = ComboState.EXECUTABLE;
            if (hasTutorInHand && !hasPayoffInHand) {
                notes = "Storm count: " + currentStormCount + ", can tutor for lethal payoff";
            } else {
                notes = "Storm count: " + currentStormCount + ", can cast lethal payoff";
            }
        } else if (canAccessPayoff && ritualsInHand >= effectiveRitualsNeeded) {
            // Have pieces in hand to attempt combo (payoff or tutor to find it)
            state = ComboState.READY_IN_HAND;
            String reducerNote = costReducersOnBattlefield > 0 ?
                    " (" + costReducersOnBattlefield + " cost reducer(s) active)" : "";
            String tutorNote = (hasTutorInHand && !hasPayoffInHand) ? " (have tutor for payoff)" : "";
            notes = "Payoff and rituals in hand, storm count: " + currentStormCount + reducerNote + tutorNote;
        } else if (payoffsFound > 0 && ritualsFound >= 3) {
            // Have deck pieces, need to draw
            state = ComboState.READY_IN_DECK;
            notes = "Storm deck assembled, need to draw pieces";
        } else {
            state = ComboState.PARTIAL;
            notes = "Partial storm setup";
        }

        return ComboDetectionResult.builder(COMBO_ID)
                .state(state)
                .confidence(confidence)
                .foundPieces(foundPieces)
                .missingPieces(Collections.emptySet())
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
        List<String> fastManaInHand = new ArrayList<>();
        List<String> costReducersInHand = new ArrayList<>();
        List<String> ritualsInHand = new ArrayList<>();
        List<String> cantripsInHand = new ArrayList<>();
        String payoffInHand = null;
        String engineInHand = null;
        String tutorInHand = null;

        // Categorize cards in hand
        for (Card card : player.getHand().getCards(game)) {
            String name = card.getName();
            if (STORM_PAYOFFS.contains(name) && payoffInHand == null) {
                payoffInHand = name;
            } else if (FAST_MANA.contains(name)) {
                fastManaInHand.add(name);
            } else if (COST_REDUCERS.contains(name)) {
                costReducersInHand.add(name);
            } else if (RITUALS.contains(name)) {
                ritualsInHand.add(name);
            } else if (CANTRIPS.contains(name)) {
                cantripsInHand.add(name);
            } else if (ENGINE_CARDS.contains(name) && engineInHand == null) {
                engineInHand = name;
            } else if (TUTORS.contains(name) && tutorInHand == null) {
                tutorInHand = name;
            }
        }

        // Build sequence: Fast mana -> Cost reducers -> Rituals -> Cantrips -> Engine -> Tutor -> Payoff
        // Fast mana funds cost reducers, cost reducers make rituals/cantrips cheaper,
        // engine cards replay everything from graveyard for more mana and storm count.

        // 1. Fast mana first (Lotus Petal, LED, etc.) - provides initial mana
        sequence.addAll(fastManaInHand);

        // 2. Cost reducers (Baral, medallions, Birgi, etc.) - deploy early so rituals/cantrips cost less
        sequence.addAll(costReducersInHand);

        // 3. Rituals - generate mana and build storm count (now cheaper due to reducers)
        sequence.addAll(ritualsInHand);

        // 4. Cantrips - dig for pieces and build storm count (also cheaper)
        sequence.addAll(cantripsInHand);

        // 5. Engine card (Past in Flames, Yawgmoth's Will, etc.) - replay rituals and cantrips
        // from graveyard to generate more mana and increase storm count further
        if (engineInHand != null) {
            sequence.add(engineInHand);
        }

        // 6. Tutor for payoff if we don't have payoff in hand
        if (payoffInHand == null && tutorInHand != null) {
            sequence.add(tutorInHand + " (find storm payoff)");
        }

        // 7. Storm payoff last - cast with high storm count for lethal
        if (payoffInHand != null) {
            sequence.add(payoffInHand);
        }

        return sequence;
    }

    @Override
    public Set<String> getComboPieces() {
        return Collections.unmodifiableSet(allComboPieces);
    }

    @Override
    public int getPriority() {
        // Storm is a powerful but complex combo
        return 85;
    }

    @Override
    public String getExpectedOutcome() {
        return "lethal storm damage or tokens";
    }

    private int getStormCount(Game game, UUID playerId) {
        // Try to get storm count from watcher
        Watcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        if (watcher instanceof CastSpellLastTurnWatcher) {
            return ((CastSpellLastTurnWatcher) watcher).getAmountOfSpellsPlayerCastOnCurrentTurn(playerId);
        }
        return 0;
    }

    private Player getOpponent(Game game, UUID playerId) {
        Set<UUID> opponents = game.getOpponents(playerId);
        if (!opponents.isEmpty()) {
            return game.getPlayer(opponents.iterator().next());
        }
        return null;
    }

    @Override
    public String toString() {
        return "StormComboPattern";
    }
}
