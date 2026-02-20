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
 * AI: Detects spellslinger synergies.
 * Creatures that trigger on instant/sorcery casts. Protect the payoffs.
 *
 * Enablers (Spell Payoffs):
 * - Damage Payoffs: Guttersnipe, Thermo-Alchemist, Firebrand Archer
 * - Token Payoffs: Young Pyromancer, Monastery Mentor, Talrand, Murmuring Mystic
 * - Copy/Value Payoffs: Thousand-Year Storm, Swarm Intelligence
 *
 * Payoffs: High density of instants/sorceries in deck.
 *
 * @author duxbuse
 */
public class SpellslingerPattern implements SynergyPattern {

    private static final String SYNERGY_ID = "spellslinger";
    private static final String SYNERGY_NAME = "Spellslinger";

    // Score bonuses
    private static final int BONUS_PAYOFF_ONLY = 100;
    private static final int BONUS_PAYOFF_WITH_15_SPELLS = 400;
    private static final int BONUS_PAYOFF_WITH_25_SPELLS = 600;
    private static final int BONUS_PER_ADDITIONAL_PAYOFF = 300;
    private static final int BONUS_SPELL_COPY = 300;

    // Minimum instants/sorceries needed for synergy
    private static final int MIN_SPELLS = 15;

    private final Set<String> damagePayoffs;
    private final Set<String> tokenPayoffs;
    private final Set<String> copyPayoffs;
    private final Set<String> allPayoffs;

    public SpellslingerPattern() {
        // Damage payoffs (deal damage on cast)
        damagePayoffs = new HashSet<>();
        damagePayoffs.add("Guttersnipe");
        damagePayoffs.add("Thermo-Alchemist");
        damagePayoffs.add("Firebrand Archer");
        damagePayoffs.add("Electrostatic Field");
        damagePayoffs.add("Kessig Flamebreather");
        damagePayoffs.add("Orcish Cannonade"); // Not a payoff but included for completeness
        damagePayoffs.add("Aria of Flame");
        damagePayoffs.add("Sentinel Tower");
        damagePayoffs.add("Sphinx-Bone Wand");

        // Token payoffs (create tokens on cast)
        tokenPayoffs = new HashSet<>();
        tokenPayoffs.add("Young Pyromancer");
        tokenPayoffs.add("Monastery Mentor");
        tokenPayoffs.add("Talrand, Sky Summoner");
        tokenPayoffs.add("Murmuring Mystic");
        tokenPayoffs.add("Docent of Perfection");
        tokenPayoffs.add("Saheeli, Sublime Artificer");
        tokenPayoffs.add("Poppet Stitcher");
        tokenPayoffs.add("Metallurgic Summonings");
        tokenPayoffs.add("Shark Typhoon");

        // Copy/value payoffs
        copyPayoffs = new HashSet<>();
        copyPayoffs.add("Thousand-Year Storm");
        copyPayoffs.add("Swarm Intelligence");
        copyPayoffs.add("Archmage Emeritus");
        copyPayoffs.add("Storm-Kiln Artist");
        copyPayoffs.add("Birgi, God of Storytelling");
        copyPayoffs.add("Runaway Steam-Kin");
        copyPayoffs.add("Baral, Chief of Compliance");
        copyPayoffs.add("Goblin Electromancer");
        copyPayoffs.add("Curious Homunculus");
        copyPayoffs.add("Primal Amulet");

        // Prowess creatures are also spellslinger payoffs
        tokenPayoffs.add("Monastery Swiftspear");
        tokenPayoffs.add("Soul-Scar Mage");
        tokenPayoffs.add("Stormchaser Mage");
        tokenPayoffs.add("Sprite Dragon");

        // Combine all payoffs
        allPayoffs = new HashSet<>();
        allPayoffs.addAll(damagePayoffs);
        allPayoffs.addAll(tokenPayoffs);
        allPayoffs.addAll(copyPayoffs);
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
        return SynergyType.SPELLSLINGER;
    }

    @Override
    public Set<String> getEnablers() {
        return Collections.unmodifiableSet(allPayoffs);
    }

    @Override
    public Set<String> getPayoffs() {
        // Payoffs are instants/sorceries - dynamic
        return Collections.emptySet();
    }

    @Override
    public Set<String> getAllPieces() {
        return allPayoffs;
    }

    @Override
    public SynergyDetectionResult detectSynergy(Game game, UUID playerId) {
        Player player = game.getPlayer(playerId);
        if (player == null) {
            return SynergyDetectionResult.notDetected(SYNERGY_ID, SynergyType.SPELLSLINGER);
        }

        Set<String> payoffsFound = new HashSet<>();
        Set<String> payoffsOnBattlefield = new HashSet<>();
        Set<String> copyEffectsOnBattlefield = new HashSet<>();
        int spellCountTotal = 0;
        int spellCountInHand = 0;

        // Check battlefield
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
            String name = permanent.getName();

            if (allPayoffs.contains(name)) {
                payoffsFound.add(name);
                payoffsOnBattlefield.add(name);

                if (copyPayoffs.contains(name)) {
                    copyEffectsOnBattlefield.add(name);
                }
            }
        }

        // Check hand
        for (Card card : player.getHand().getCards(game)) {
            String name = card.getName();
            if (allPayoffs.contains(name)) {
                payoffsFound.add(name);
            }
            // Count instants/sorceries
            if (card.isInstant(game) || card.isSorcery(game)) {
                spellCountTotal++;
                spellCountInHand++;
            }
        }

        // Check library for payoffs and count spells
        for (Card card : player.getLibrary().getCards(game)) {
            String name = card.getName();
            if (allPayoffs.contains(name)) {
                payoffsFound.add(name);
            }
            if (card.isInstant(game) || card.isSorcery(game)) {
                spellCountTotal++;
            }
        }

        // Check graveyard (spells in graveyard count for spell density)
        for (Card card : player.getGraveyard().getCards(game)) {
            String name = card.getName();
            if (allPayoffs.contains(name)) {
                payoffsFound.add(name);
            }
            if (card.isInstant(game) || card.isSorcery(game)) {
                spellCountTotal++;
            }
        }

        if (payoffsFound.isEmpty()) {
            return SynergyDetectionResult.notDetected(SYNERGY_ID, SynergyType.SPELLSLINGER);
        }

        // Determine state and calculate bonus
        SynergyState state;
        int scoreBonus = 0;
        StringBuilder notes = new StringBuilder();

        boolean hasPayoffInPlay = !payoffsOnBattlefield.isEmpty();
        boolean hasEnoughSpells = spellCountTotal >= MIN_SPELLS;

        if (hasPayoffInPlay && hasEnoughSpells) {
            state = SynergyState.ACTIVE;

            // Base bonus based on spell count
            if (spellCountTotal >= 25) {
                scoreBonus = BONUS_PAYOFF_WITH_25_SPELLS;
            } else {
                scoreBonus = BONUS_PAYOFF_WITH_15_SPELLS;
            }

            // Bonus for multiple payoffs
            int payoffCount = payoffsOnBattlefield.size();
            if (payoffCount > 1) {
                scoreBonus += (payoffCount - 1) * BONUS_PER_ADDITIONAL_PAYOFF;
            }

            // Bonus for spell copy effects
            if (!copyEffectsOnBattlefield.isEmpty()) {
                scoreBonus += BONUS_SPELL_COPY;
            }

            notes.append(String.format("Payoffs: %d, Instants/Sorceries: %d, In hand: %d",
                    payoffCount, spellCountTotal, spellCountInHand));
        } else if (hasPayoffInPlay) {
            state = SynergyState.READY;
            scoreBonus = BONUS_PAYOFF_ONLY + 100;
            notes.append("Payoff in play, building spell count");
        } else if (hasEnoughSpells) {
            state = SynergyState.PARTIAL;
            scoreBonus = BONUS_PAYOFF_ONLY;
            notes.append("Have spells, need payoff");
        } else {
            state = SynergyState.ENABLER_ONLY;
            scoreBonus = BONUS_PAYOFF_ONLY;
        }

        // Spells are dynamic payoffs
        Set<String> spellsAsPayoffs = new HashSet<>();
        spellsAsPayoffs.add(spellCountTotal + " instants/sorceries in deck");

        Set<String> spellsOnBattlefieldPlaceholder = new HashSet<>();
        if (spellCountInHand > 0) {
            spellsOnBattlefieldPlaceholder.add(spellCountInHand + " spells in hand");
        }

        return SynergyDetectionResult.builder(SYNERGY_ID, SynergyType.SPELLSLINGER)
                .state(state)
                .enablersFound(payoffsFound)
                .payoffsFound(spellsAsPayoffs)
                .enablersOnBattlefield(payoffsOnBattlefield)
                .payoffsOnBattlefield(spellsOnBattlefieldPlaceholder)
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
     * Check if a card is a damage payoff.
     */
    public boolean isDamagePayoff(String cardName) {
        return damagePayoffs.contains(cardName);
    }

    /**
     * Check if a card is a token payoff.
     */
    public boolean isTokenPayoff(String cardName) {
        return tokenPayoffs.contains(cardName);
    }

    /**
     * Check if a card provides spell copying.
     */
    public boolean isCopyPayoff(String cardName) {
        return copyPayoffs.contains(cardName);
    }
}
