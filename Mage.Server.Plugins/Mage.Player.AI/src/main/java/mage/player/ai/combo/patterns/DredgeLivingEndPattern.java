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
 * AI: Dredge and Living End combo pattern detection.
 * Detects decks trying to fill the graveyard with creatures and win via:
 * - Living End (cascade into mass reanimation)
 * - Dread Return (sacrifice 3 creatures to reanimate)
 * - Recurring creatures attacking (Vengevine, Bloodghast, etc.)
 *
 * Sequence: Enable graveyard fill -> Self-recurring creatures enter -> Cast payoff
 *
 * @author duxbuse
 */
public class DredgeLivingEndPattern implements ComboPattern {

    private static final String COMBO_ID = "dredge-living-end-combo";

    // Cards with Dredge mechanic - replace draws with mill + return
    private static final Set<String> DREDGE_CARDS = new HashSet<>(Arrays.asList(
            "Golgari Grave-Troll",      // Dredge 6
            "Stinkweed Imp",            // Dredge 5
            "Golgari Thug",             // Dredge 4
            "Shambling Shell",          // Dredge 3
            "Life from the Loam",       // Dredge 3
            "Dakmor Salvage",           // Dredge 2
            "Darkblast",                // Dredge 3
            "Necroplasm",               // Dredge 2
            "Greater Mossdog",          // Dredge 3
            "Moldervine Cloak"          // Dredge 2
    ));

    // Self-recurring creatures - enter battlefield from graveyard
    private static final Set<String> RECURSIVE_CREATURES = new HashSet<>(Arrays.asList(
            // Enter from graveyard when milled
            "Narcomoeba",
            "Creeping Chill",           // Not a creature but triggers on mill
            // Return on landfall
            "Bloodghast",
            // Return when creature enters from graveyard
            "Prized Amalgam",
            // Return by exiling creatures
            "Ichorid",
            "Nether Shadow",
            "Nether Traitor",
            // Return when casting creatures
            "Vengevine",
            // Return from graveyard easily
            "Gravecrawler",
            "Silversmote Ghoul",
            "Haunted Dead",
            "Skyclave Shade",
            // Bridge makes tokens
            "Bridge from Below"
    ));

    // Cycling creatures - cycle to graveyard, good targets for Living End
    private static final Set<String> CYCLING_CREATURES = new HashSet<>(Arrays.asList(
            "Street Wraith",            // Free cycle
            "Horror of the Broken Lands",
            "Architects of Will",
            "Monstrous Carabid",
            "Deadshot Minotaur",
            "Striped Riverwinder",
            "Greater Sandwurm",
            "Titanoth Rex",
            "Void Beckoner",
            "Waker of Waves",
            "Imposing Vantasaur",
            "Drannith Healer",
            "Drannith Stinger",
            "Winged Shepherd",
            "Relaxed Ranger",
            "Troll of Khazad-dum",
            "Oliphaunt"
    ));

    // Graveyard fillers - discard outlets and self-mill
    private static final Set<String> ENABLERS = new HashSet<>(Arrays.asList(
            // Discard outlets
            "Faithless Looting",
            "Cathartic Reunion",
            "Thrilling Discovery",
            "Burning Inquiry",
            "Goblin Lore",
            "Careful Study",
            "Ideas Unbound",
            "Breakthrough",
            "Frantic Search",
            "Bazaar of Baghdad",
            "Putrid Imp",
            // Self-mill
            "Stitcher's Supplier",
            "Hedron Crab",
            "Satyr Wayfinder",
            "Grisly Salvage",
            "Commune with the Gods",
            "Gather the Pack",
            "Strategic Planning",
            // Cycling enablers
            "Hollow One",               // Cheap after cycling/discarding
            "Fluctuator",               // Reduces cycling costs
            "Unpredictable Cyclone",
            "New Perspectives",
            "Astral Drift",
            "Astral Slide"
    ));

    // Cascade spells for Living End
    private static final Set<String> CASCADE_SPELLS = new HashSet<>(Arrays.asList(
            "Violent Outburst",
            "Demonic Dread",
            "Ardent Plea",
            "Shardless Agent",
            "Bloodbraid Elf",
            "Enlisted Wurm"
    ));

    // Payoff cards - win conditions
    private static final Set<String> PAYOFFS = new HashSet<>(Arrays.asList(
            // Mass reanimation
            "Living End",
            "Living Death",
            "Twilight's Call",
            "Rise of the Dark Realms",
            "Command the Dreadhorde",
            "Patriarch's Bidding",
            // Sacrifice to reanimate
            "Dread Return",
            "Footsteps of the Goryo",
            // Dredge-specific payoffs
            "Flame-Kin Zealot",         // Haste anthem when reanimated
            "Flayer of the Hatebound",  // Damage on creatures entering from graveyard
            // Good Dread Return targets
            "Griselbrand",
            "Elesh Norn, Grand Cenobite",
            "Craterhoof Behemoth",
            "Balustrade Spy",           // Mill entire deck if no basics
            "Undercity Informer"        // Mill entire deck
    ));

    private final Set<String> allComboPieces;

    public DredgeLivingEndPattern() {
        allComboPieces = new HashSet<>();
        allComboPieces.addAll(DREDGE_CARDS);
        allComboPieces.addAll(RECURSIVE_CREATURES);
        allComboPieces.addAll(CYCLING_CREATURES);
        allComboPieces.addAll(ENABLERS);
        allComboPieces.addAll(CASCADE_SPELLS);
        allComboPieces.addAll(PAYOFFS);
    }

    @Override
    public String getComboId() {
        return COMBO_ID;
    }

    @Override
    public String getComboName() {
        return "Dredge / Living End";
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

        // Track what we find
        int dredgeCardsFound = 0;
        int recursiveCreaturesFound = 0;
        int cyclingCreaturesFound = 0;
        int enablersFound = 0;
        int cascadeSpellsFound = 0;

        boolean hasLivingEnd = false;
        boolean hasDreadReturn = false;
        boolean hasPayoff = false;

        // Graveyard stats
        int creaturesInGraveyard = 0;
        int recursiveInGraveyard = 0;
        boolean hasDreadReturnTargetInGraveyard = false;

        // Hand stats
        boolean hasCascadeInHand = false;
        boolean hasDreadReturnInHand = false;
        boolean hasEnablerInHand = false;

        // Battlefield stats
        int creaturesOnBattlefield = 0;

        // Check hand
        for (Card card : player.getHand().getCards(game)) {
            String name = card.getName();
            if (DREDGE_CARDS.contains(name)) {
                dredgeCardsFound++;
                piecesInHand.add(name);
                foundPieces.add(name);
            } else if (RECURSIVE_CREATURES.contains(name)) {
                recursiveCreaturesFound++;
                piecesInHand.add(name);
                foundPieces.add(name);
            } else if (CYCLING_CREATURES.contains(name)) {
                cyclingCreaturesFound++;
                piecesInHand.add(name);
                foundPieces.add(name);
            } else if (ENABLERS.contains(name)) {
                enablersFound++;
                hasEnablerInHand = true;
                piecesInHand.add(name);
                foundPieces.add(name);
            } else if (CASCADE_SPELLS.contains(name)) {
                cascadeSpellsFound++;
                hasCascadeInHand = true;
                piecesInHand.add(name);
                foundPieces.add(name);
            } else if (name.equals("Living End")) {
                hasLivingEnd = true;
                hasPayoff = true;
                piecesInHand.add(name);
                foundPieces.add(name);
            } else if (name.equals("Dread Return")) {
                hasDreadReturn = true;
                hasDreadReturnInHand = true;
                hasPayoff = true;
                piecesInHand.add(name);
                foundPieces.add(name);
            } else if (PAYOFFS.contains(name)) {
                hasPayoff = true;
                piecesInHand.add(name);
                foundPieces.add(name);
            }
        }

        // Check graveyard
        for (Card card : player.getGraveyard().getCards(game)) {
            String name = card.getName();
            if (card.isCreature(game)) {
                creaturesInGraveyard++;
            }
            if (DREDGE_CARDS.contains(name)) {
                dredgeCardsFound++;
                piecesInGraveyard.add(name);
                foundPieces.add(name);
            } else if (RECURSIVE_CREATURES.contains(name)) {
                recursiveCreaturesFound++;
                recursiveInGraveyard++;
                piecesInGraveyard.add(name);
                foundPieces.add(name);
            } else if (CYCLING_CREATURES.contains(name)) {
                cyclingCreaturesFound++;
                piecesInGraveyard.add(name);
                foundPieces.add(name);
            } else if (name.equals("Dread Return")) {
                hasDreadReturn = true;
                hasPayoff = true;
                piecesInGraveyard.add(name);
                foundPieces.add(name);
            } else if (PAYOFFS.contains(name)) {
                hasPayoff = true;
                hasDreadReturnTargetInGraveyard = true;
                piecesInGraveyard.add(name);
                foundPieces.add(name);
            }
        }

        // Check battlefield
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
            String name = permanent.getName();
            if (permanent.isCreature(game)) {
                creaturesOnBattlefield++;
            }
            if (RECURSIVE_CREATURES.contains(name) || CYCLING_CREATURES.contains(name)) {
                piecesOnBattlefield.add(name);
                foundPieces.add(name);
            } else if (ENABLERS.contains(name)) {
                piecesOnBattlefield.add(name);
                foundPieces.add(name);
            }
        }

        // Check library
        for (Card card : player.getLibrary().getCards(game)) {
            String name = card.getName();
            if (DREDGE_CARDS.contains(name)) {
                dredgeCardsFound++;
                foundPieces.add(name);
            } else if (RECURSIVE_CREATURES.contains(name)) {
                recursiveCreaturesFound++;
                foundPieces.add(name);
            } else if (CYCLING_CREATURES.contains(name)) {
                cyclingCreaturesFound++;
                foundPieces.add(name);
            } else if (ENABLERS.contains(name)) {
                enablersFound++;
                foundPieces.add(name);
            } else if (CASCADE_SPELLS.contains(name)) {
                cascadeSpellsFound++;
                foundPieces.add(name);
            } else if (name.equals("Living End")) {
                hasLivingEnd = true;
                hasPayoff = true;
                foundPieces.add(name);
            } else if (name.equals("Dread Return")) {
                hasDreadReturn = true;
                hasPayoff = true;
                foundPieces.add(name);
            } else if (PAYOFFS.contains(name)) {
                hasPayoff = true;
                foundPieces.add(name);
            }
        }

        // Determine if this is a Dredge/Living End deck
        // Need either: Dredge cards + recursive creatures OR cycling creatures + cascade + Living End
        boolean isDredgeDeck = dredgeCardsFound >= 2 && recursiveCreaturesFound >= 3;
        boolean isLivingEndDeck = cyclingCreaturesFound >= 6 && (hasLivingEnd || cascadeSpellsFound >= 2);
        boolean isDredgeLivingEndDeck = isDredgeDeck || isLivingEndDeck;

        if (!isDredgeLivingEndDeck) {
            return ComboDetectionResult.notDetected(COMBO_ID);
        }

        // Build missing pieces
        if (!hasPayoff) {
            missingPieces.add("Living End or Dread Return");
        }
        if (dredgeCardsFound == 0 && cyclingCreaturesFound < 4) {
            missingPieces.add("Dredge cards or cycling creatures");
        }

        // Determine state
        ComboState state;
        double confidence;
        String notes;

        // Can we execute now?
        // Living End: Have cascade spell in hand and creatures in graveyard
        boolean canCastLivingEnd = hasCascadeInHand && hasLivingEnd && creaturesInGraveyard >= 4;
        // Dread Return: Have 3+ creatures on battlefield and Dread Return + target
        boolean canCastDreadReturn = creaturesOnBattlefield >= 3
                && (hasDreadReturnInHand || piecesInGraveyard.contains("Dread Return"))
                && hasDreadReturnTargetInGraveyard;

        if (canCastLivingEnd || canCastDreadReturn) {
            state = ComboState.EXECUTABLE;
            confidence = 1.0;
            if (canCastLivingEnd) {
                notes = "Can cascade into Living End with " + creaturesInGraveyard + " creatures in graveyard";
            } else {
                notes = "Can flashback Dread Return with " + creaturesOnBattlefield + " creatures";
            }
        } else if ((hasCascadeInHand && hasLivingEnd) || (hasDreadReturnInHand && creaturesOnBattlefield >= 2)) {
            state = ComboState.READY_IN_HAND;
            confidence = 0.85;
            notes = "Payoff ready, need more setup (creatures in graveyard: " + creaturesInGraveyard + ")";
        } else if (hasPayoff && (dredgeCardsFound >= 2 || cyclingCreaturesFound >= 4)) {
            state = ComboState.READY_IN_DECK;
            confidence = 0.7;
            notes = isDredgeDeck ? "Dredge deck assembled" : "Living End deck assembled";
        } else {
            state = ComboState.PARTIAL;
            confidence = 0.4;
            notes = "Partial graveyard strategy setup";
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

        // Find what we have
        String cascadeSpell = null;
        String enabler = null;
        boolean hasLivingEndInDeck = false;
        boolean hasDreadReturnAvailable = false;
        int creaturesOnField = 0;

        for (Card card : player.getHand().getCards(game)) {
            String name = card.getName();
            if (CASCADE_SPELLS.contains(name) && cascadeSpell == null) {
                cascadeSpell = name;
            } else if (ENABLERS.contains(name) && enabler == null) {
                enabler = name;
            }
        }

        for (Card card : player.getLibrary().getCards(game)) {
            if (card.getName().equals("Living End")) {
                hasLivingEndInDeck = true;
                break;
            }
        }

        for (Card card : player.getGraveyard().getCards(game)) {
            if (card.getName().equals("Dread Return")) {
                hasDreadReturnAvailable = true;
            }
        }

        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
            if (permanent.isCreature(game)) {
                creaturesOnField++;
            }
        }

        // Build sequence based on what's available
        if (enabler != null) {
            sequence.add(enabler + " (fill graveyard)");
        }

        if (cascadeSpell != null && hasLivingEndInDeck) {
            sequence.add(cascadeSpell + " (cascade into Living End)");
        } else if (hasDreadReturnAvailable && creaturesOnField >= 3) {
            sequence.add("Flashback Dread Return (sacrifice 3 creatures)");
        }

        return sequence;
    }

    @Override
    public Set<String> getComboPieces() {
        return Collections.unmodifiableSet(allComboPieces);
    }

    @Override
    public int getPriority() {
        // High priority - can win quickly with full graveyard
        return 85;
    }

    @Override
    public String getExpectedOutcome() {
        return "mass creature reanimation";
    }

    @Override
    public String toString() {
        return "DredgeLivingEndPattern";
    }
}
