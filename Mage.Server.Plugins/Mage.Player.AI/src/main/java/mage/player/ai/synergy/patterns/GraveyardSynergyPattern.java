package mage.player.ai.synergy.patterns;

import mage.cards.Card;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.player.ai.synergy.SynergyDetectionResult;
import mage.player.ai.synergy.SynergyDetectionResult.SynergyState;
import mage.player.ai.synergy.SynergyPattern;
import mage.player.ai.synergy.SynergyType;
import mage.players.Player;

import java.util.*;

/**
 * AI: Detects graveyard synergies.
 * Cards that benefit from full graveyards or enable graveyard strategies.
 *
 * Enablers (Self-Mill/Graveyard Fillers):
 * - Self-Mill: Hedron Crab, Stitcher's Supplier, Satyr Wayfinder
 * - Dredge: Golgari Grave-Troll, Life from the Loam, Stinkweed Imp
 *
 * Payoffs (Graveyard Matters):
 * - Power/Toughness: Tarmogoyf, Boneyard Wurm
 * - Reanimation: Reanimate, Animate Dead, Exhume
 * - Delve: Treasure Cruise, Dig Through Time
 *
 * @author duxbuse
 */
public class GraveyardSynergyPattern implements SynergyPattern {

    private static final String SYNERGY_ID = "graveyard-synergy";
    private static final String SYNERGY_NAME = "Graveyard Synergy";

    // Score bonuses
    private static final int BONUS_ENABLER_ONLY = 100;
    private static final int BONUS_ENABLER_WITH_PAYOFF = 400;
    private static final int BONUS_REANIMATION_PACKAGE = 500;
    private static final int BONUS_GRAVEYARD_ACTIVE = 300;
    private static final int BONUS_PER_ADDITIONAL_SYNERGY = 150;

    // Minimum graveyard cards needed for synergy
    private static final int MIN_GRAVEYARD_CARDS = 5;

    private final Set<String> selfMillers;
    private final Set<String> dredgeCards;
    private final Set<String> reanimationSpells;
    private final Set<String> graveyardPayoffs;
    private final Set<String> reanimationTargets;
    private final Set<String> allEnablers;

    public GraveyardSynergyPattern() {
        // Self-mill creatures
        selfMillers = new HashSet<>();
        selfMillers.add("Hedron Crab");
        selfMillers.add("Stitcher's Supplier");
        selfMillers.add("Satyr Wayfinder");
        selfMillers.add("Glowspore Shaman");
        selfMillers.add("Underrealm Lich");
        selfMillers.add("Mesmeric Orb");
        selfMillers.add("Altar of Dementia");
        selfMillers.add("Millikin");
        selfMillers.add("Hermit Druid");
        selfMillers.add("Balustrade Spy");
        selfMillers.add("Undercity Informer");
        selfMillers.add("Sidisi, Brood Tyrant");
        selfMillers.add("The Mimeoplasm");
        selfMillers.add("Grisly Salvage");
        selfMillers.add("Mulch");

        // Dredge cards
        dredgeCards = new HashSet<>();
        dredgeCards.add("Golgari Grave-Troll");
        dredgeCards.add("Life from the Loam");
        dredgeCards.add("Stinkweed Imp");
        dredgeCards.add("Golgari Thug");
        dredgeCards.add("Dakmor Salvage");
        dredgeCards.add("Darkblast");
        dredgeCards.add("Shambling Shell");

        // Reanimation spells
        reanimationSpells = new HashSet<>();
        reanimationSpells.add("Reanimate");
        reanimationSpells.add("Animate Dead");
        reanimationSpells.add("Exhume");
        reanimationSpells.add("Dance of the Dead");
        reanimationSpells.add("Necromancy");
        reanimationSpells.add("Life // Death");
        reanimationSpells.add("Unburial Rites");
        reanimationSpells.add("Victimize");
        reanimationSpells.add("Dread Return");
        reanimationSpells.add("Living Death");
        reanimationSpells.add("Karmic Guide");
        reanimationSpells.add("Reveillark");
        reanimationSpells.add("Persist");

        // Graveyard payoffs
        graveyardPayoffs = new HashSet<>();

        // Power/Toughness based on graveyard
        graveyardPayoffs.add("Tarmogoyf");
        graveyardPayoffs.add("Boneyard Wurm");
        graveyardPayoffs.add("Splinterfright");
        graveyardPayoffs.add("Ghoultree");
        graveyardPayoffs.add("Nighthowler");
        graveyardPayoffs.add("Lhurgoyf");
        graveyardPayoffs.add("Mortivore");

        // Delve cards
        graveyardPayoffs.add("Treasure Cruise");
        graveyardPayoffs.add("Dig Through Time");
        graveyardPayoffs.add("Tasigur, the Golden Fang");
        graveyardPayoffs.add("Gurmag Angler");
        graveyardPayoffs.add("Murderous Cut");
        graveyardPayoffs.add("Tombstalker");
        graveyardPayoffs.add("Hooting Mandrills");

        // Threshold cards
        graveyardPayoffs.add("Werebear");
        graveyardPayoffs.add("Nimble Mongoose");
        graveyardPayoffs.add("Mystic Enforcer");

        // Escape cards
        graveyardPayoffs.add("Uro, Titan of Nature's Wrath");
        graveyardPayoffs.add("Kroxa, Titan of Death's Hunger");
        graveyardPayoffs.add("Ox of Agonas");

        // Flashback value
        graveyardPayoffs.add("Snapcaster Mage");
        graveyardPayoffs.add("Past in Flames");
        graveyardPayoffs.add("Mission Briefing");

        // Other graveyard matters
        graveyardPayoffs.add("Kess, Dissident Mage");
        graveyardPayoffs.add("Muldrotha, the Gravetide");
        graveyardPayoffs.add("Karador, Ghost Chieftain");
        graveyardPayoffs.add("Laboratory Maniac");
        graveyardPayoffs.add("Thassa's Oracle");

        // Big reanimation targets
        reanimationTargets = new HashSet<>();
        reanimationTargets.add("Griselbrand");
        reanimationTargets.add("Razaketh, the Foulblooded");
        reanimationTargets.add("Jin-Gitaxias, Core Augur");
        reanimationTargets.add("Sheoldred, Whispering One");
        reanimationTargets.add("Elesh Norn, Grand Cenobite");
        reanimationTargets.add("Iona, Shield of Emeria");
        reanimationTargets.add("Tidespout Tyrant");
        reanimationTargets.add("Sphinx of the Steel Wind");
        reanimationTargets.add("Ashen Rider");
        reanimationTargets.add("Archon of Cruelty");

        // Combine all enablers
        allEnablers = new HashSet<>();
        allEnablers.addAll(selfMillers);
        allEnablers.addAll(dredgeCards);
        allEnablers.addAll(reanimationSpells);
    }

    @Override
    public String getSynergyId() {
        return SYNERGY_ID;
    }

    @Override
    public String getName() {
        return SYNERGY_NAME;
    }

    @Override
    public SynergyType getType() {
        return SynergyType.GRAVEYARD_ENABLER;
    }

    @Override
    public Set<String> getEnablers() {
        return Collections.unmodifiableSet(allEnablers);
    }

    @Override
    public Set<String> getPayoffs() {
        Set<String> allPayoffs = new HashSet<>(graveyardPayoffs);
        allPayoffs.addAll(reanimationTargets);
        return Collections.unmodifiableSet(allPayoffs);
    }

    @Override
    public Set<String> getAllPieces() {
        Set<String> all = new HashSet<>(allEnablers);
        all.addAll(graveyardPayoffs);
        all.addAll(reanimationTargets);
        return all;
    }

    @Override
    public SynergyDetectionResult detectSynergy(Game game, UUID playerId) {
        Player player = game.getPlayer(playerId);
        if (player == null) {
            return SynergyDetectionResult.notDetected(SYNERGY_ID, SynergyType.GRAVEYARD_ENABLER);
        }

        Set<String> enablersFound = new HashSet<>();
        Set<String> payoffsFound = new HashSet<>();
        Set<String> enablersOnBattlefield = new HashSet<>();
        Set<String> payoffsOnBattlefield = new HashSet<>();
        boolean hasReanimationSpell = false;
        boolean hasReanimationTarget = false;
        int graveyardSize = player.getGraveyard().size();
        int creaturesInGraveyard = 0;

        // Count creatures in graveyard
        for (Card card : player.getGraveyard().getCards(game)) {
            if (card.isCreature(game)) {
                creaturesInGraveyard++;
            }
        }

        // Check battlefield
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
            String name = permanent.getName();

            if (selfMillers.contains(name) || dredgeCards.contains(name)) {
                enablersFound.add(name);
                enablersOnBattlefield.add(name);
            }
            if (graveyardPayoffs.contains(name)) {
                payoffsFound.add(name);
                payoffsOnBattlefield.add(name);
            }
        }

        // Check hand
        for (Card card : player.getHand().getCards(game)) {
            String name = card.getName();
            if (allEnablers.contains(name)) {
                enablersFound.add(name);
            }
            if (reanimationSpells.contains(name)) {
                hasReanimationSpell = true;
            }
            if (graveyardPayoffs.contains(name)) {
                payoffsFound.add(name);
            }
        }

        // Check library
        for (Card card : player.getLibrary().getCards(game)) {
            String name = card.getName();
            if (allEnablers.contains(name)) {
                enablersFound.add(name);
            }
            if (graveyardPayoffs.contains(name)) {
                payoffsFound.add(name);
            }
            if (reanimationTargets.contains(name)) {
                hasReanimationTarget = true;
            }
        }

        // Check graveyard for reanimation targets and payoffs
        for (Card card : player.getGraveyard().getCards(game)) {
            String name = card.getName();
            if (allEnablers.contains(name)) {
                enablersFound.add(name);
            }
            if (graveyardPayoffs.contains(name)) {
                payoffsFound.add(name);
            }
            if (reanimationTargets.contains(name)) {
                payoffsFound.add(name);
                hasReanimationTarget = true;
                // Reanimation target in graveyard is on "battlefield" for detection purposes
                payoffsOnBattlefield.add(name);
            }
        }

        if (enablersFound.isEmpty() && payoffsFound.isEmpty()) {
            return SynergyDetectionResult.notDetected(SYNERGY_ID, SynergyType.GRAVEYARD_ENABLER);
        }

        // Determine state and calculate bonus
        SynergyState state;
        int scoreBonus = 0;
        StringBuilder notes = new StringBuilder();

        boolean hasEnablerInPlay = !enablersOnBattlefield.isEmpty();
        boolean hasGraveyardBuilt = graveyardSize >= MIN_GRAVEYARD_CARDS;
        boolean hasReanimationPackage = hasReanimationSpell && hasReanimationTarget;

        if (hasReanimationPackage && creaturesInGraveyard > 0) {
            // Reanimation package active
            state = SynergyState.ACTIVE;
            scoreBonus = BONUS_REANIMATION_PACKAGE;

            if (hasEnablerInPlay) {
                scoreBonus += BONUS_GRAVEYARD_ACTIVE;
            }

            notes.append(String.format("Reanimation ready! Targets in GY: %d", creaturesInGraveyard));
        } else if (hasEnablerInPlay && (hasGraveyardBuilt || !payoffsFound.isEmpty())) {
            state = SynergyState.ACTIVE;
            scoreBonus = BONUS_ENABLER_WITH_PAYOFF;

            // Bonus for filled graveyard
            if (hasGraveyardBuilt) {
                scoreBonus += BONUS_GRAVEYARD_ACTIVE;
            }

            // Bonus for multiple synergy pieces
            int pieceCount = enablersOnBattlefield.size() + payoffsOnBattlefield.size();
            if (pieceCount > 2) {
                scoreBonus += (pieceCount - 2) * BONUS_PER_ADDITIONAL_SYNERGY;
            }

            notes.append(String.format("Mill active, GY size: %d, Creatures: %d",
                    graveyardSize, creaturesInGraveyard));
        } else if (hasEnablerInPlay) {
            state = SynergyState.READY;
            scoreBonus = BONUS_ENABLER_ONLY + 100;
            notes.append("Mill enabler in play, building graveyard");
        } else if (!enablersFound.isEmpty() && !payoffsFound.isEmpty()) {
            state = SynergyState.PARTIAL;
            scoreBonus = BONUS_ENABLER_ONLY + 50;
            notes.append("Have pieces, need to deploy");
        } else if (!enablersFound.isEmpty()) {
            state = SynergyState.ENABLER_ONLY;
            scoreBonus = BONUS_ENABLER_ONLY;
        } else {
            state = SynergyState.PAYOFF_ONLY;
        }

        // Add graveyard info to payoffs
        Set<String> payoffsWithGY = new HashSet<>(payoffsFound);
        if (graveyardSize > 0) {
            payoffsWithGY.add(graveyardSize + " cards in graveyard");
        }

        return SynergyDetectionResult.builder(SYNERGY_ID, SynergyType.GRAVEYARD_ENABLER)
                .state(state)
                .enablersFound(enablersFound)
                .payoffsFound(payoffsWithGY)
                .enablersOnBattlefield(enablersOnBattlefield)
                .payoffsOnBattlefield(payoffsOnBattlefield)
                .scoreBonus(scoreBonus)
                .notes(notes.toString())
                .build();
    }

    @Override
    public boolean isActive(Game game, UUID playerId) {
        SynergyDetectionResult result = detectSynergy(game, playerId);
        return result.getState() == SynergyState.ACTIVE;
    }

    @Override
    public int calculateSynergyBonus(Game game, UUID playerId) {
        SynergyDetectionResult result = detectSynergy(game, playerId);
        return result.getScoreBonus();
    }

    /**
     * Check if a card is a self-miller.
     */
    public boolean isSelfMiller(String cardName) {
        return selfMillers.contains(cardName);
    }

    /**
     * Check if a card has dredge.
     */
    public boolean isDredgeCard(String cardName) {
        return dredgeCards.contains(cardName);
    }

    /**
     * Check if a card is a reanimation spell.
     */
    public boolean isReanimationSpell(String cardName) {
        return reanimationSpells.contains(cardName);
    }

    /**
     * Check if a card is a good reanimation target.
     */
    public boolean isReanimationTarget(String cardName) {
        return reanimationTargets.contains(cardName);
    }
}
