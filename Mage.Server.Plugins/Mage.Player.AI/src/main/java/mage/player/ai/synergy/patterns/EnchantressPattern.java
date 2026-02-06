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
 * AI: Detects enchantress synergies.
 * Cards that draw when enchantments are cast or enter the battlefield.
 * Protect the draw engines - they enable sustained card advantage.
 *
 * Enablers (Draw Engines):
 * - On Cast Triggers: Argothian Enchantress, Mesa Enchantress, etc.
 * - On ETB Triggers (Constellation): Eidolon of Blossoms, Setessan Champion
 * - Cost Reducers: Herald of the Pantheon
 *
 * Payoffs: Any enchantment in deck - more enchantments = more draws.
 *
 * @author duxbuse
 */
public class EnchantressPattern implements SynergyPattern {

    private static final String SYNERGY_ID = "enchantress";
    private static final String SYNERGY_NAME = "Enchantress";

    // Score bonuses
    private static final int BONUS_ENCHANTRESS_ONLY = 100;
    private static final int BONUS_ENCHANTRESS_WITH_5_ENCHANTMENTS = 400;
    private static final int BONUS_ENCHANTRESS_WITH_10_ENCHANTMENTS = 600;
    private static final int BONUS_PER_ADDITIONAL_ENCHANTRESS = 300;
    private static final int BONUS_COST_REDUCER = 200;

    // Minimum enchantments needed for synergy
    private static final int MIN_ENCHANTMENTS = 5;

    private final Set<String> enchantressCards;
    private final Set<String> costReducers;
    private final Set<String> allEnablers;

    public EnchantressPattern() {
        // Enchantress cards (draw on enchantment cast/ETB)
        enchantressCards = new HashSet<>();

        // On cast triggers
        enchantressCards.add("Argothian Enchantress");
        enchantressCards.add("Mesa Enchantress");
        enchantressCards.add("Verduran Enchantress");
        enchantressCards.add("Satyr Enchanter");
        enchantressCards.add("Enchantress's Presence");
        enchantressCards.add("Sythis, Harvest's Hand");
        enchantressCards.add("Femeref Enchantress");

        // Constellation (ETB triggers)
        enchantressCards.add("Eidolon of Blossoms");
        enchantressCards.add("Setessan Champion");
        enchantressCards.add("Constellation Orrery");
        enchantressCards.add("Doomwake Giant");
        enchantressCards.add("Archon of Sun's Grace");
        enchantressCards.add("Nessian Wanderer");

        // Cost reducers
        costReducers = new HashSet<>();
        costReducers.add("Herald of the Pantheon");
        costReducers.add("Jukai Naturalist");
        costReducers.add("Starfield Mystic");

        // Combine all enablers
        allEnablers = new HashSet<>();
        allEnablers.addAll(enchantressCards);
        allEnablers.addAll(costReducers);
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
        return SynergyType.ENCHANTRESS;
    }

    @Override
    public Set<String> getEnablers() {
        return Collections.unmodifiableSet(allEnablers);
    }

    @Override
    public Set<String> getPayoffs() {
        // Payoffs are dynamic - any enchantment
        return Collections.emptySet();
    }

    @Override
    public Set<String> getAllPieces() {
        return allEnablers;
    }

    @Override
    public SynergyDetectionResult detectSynergy(Game game, UUID playerId) {
        Player player = game.getPlayer(playerId);
        if (player == null) {
            return SynergyDetectionResult.notDetected(SYNERGY_ID, SynergyType.ENCHANTRESS);
        }

        Set<String> enchantressesFound = new HashSet<>();
        Set<String> enchantressesOnBattlefield = new HashSet<>();
        Set<String> reducersOnBattlefield = new HashSet<>();
        int enchantmentCountTotal = 0;
        int enchantmentCountOnBattlefield = 0;

        // Check battlefield
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
            String name = permanent.getName();

            if (enchantressCards.contains(name)) {
                enchantressesFound.add(name);
                enchantressesOnBattlefield.add(name);
            }
            if (costReducers.contains(name)) {
                enchantressesFound.add(name);
                reducersOnBattlefield.add(name);
            }

            // Count enchantments
            if (permanent.isEnchantment(game)) {
                enchantmentCountTotal++;
                enchantmentCountOnBattlefield++;
            }
        }

        // Check hand
        for (Card card : player.getHand().getCards(game)) {
            String name = card.getName();
            if (allEnablers.contains(name)) {
                enchantressesFound.add(name);
            }
            if (card.isEnchantment(game)) {
                enchantmentCountTotal++;
            }
        }

        // Check library for enchantresses and count enchantments
        for (Card card : player.getLibrary().getCards(game)) {
            String name = card.getName();
            if (allEnablers.contains(name)) {
                enchantressesFound.add(name);
            }
            if (card.isEnchantment(game)) {
                enchantmentCountTotal++;
            }
        }

        // Check graveyard
        for (Card card : player.getGraveyard().getCards(game)) {
            String name = card.getName();
            if (allEnablers.contains(name)) {
                enchantressesFound.add(name);
            }
            if (card.isEnchantment(game)) {
                enchantmentCountTotal++;
            }
        }

        if (enchantressesFound.isEmpty()) {
            return SynergyDetectionResult.notDetected(SYNERGY_ID, SynergyType.ENCHANTRESS);
        }

        // Determine state and calculate bonus
        SynergyState state;
        int scoreBonus = 0;
        StringBuilder notes = new StringBuilder();

        boolean hasEnchantressInPlay = !enchantressesOnBattlefield.isEmpty();
        boolean hasEnoughEnchantments = enchantmentCountTotal >= MIN_ENCHANTMENTS;

        if (hasEnchantressInPlay && hasEnoughEnchantments) {
            state = SynergyState.ACTIVE;

            // Base bonus based on enchantment count
            if (enchantmentCountTotal >= 10) {
                scoreBonus = BONUS_ENCHANTRESS_WITH_10_ENCHANTMENTS;
            } else {
                scoreBonus = BONUS_ENCHANTRESS_WITH_5_ENCHANTMENTS;
            }

            // Bonus for multiple enchantresses
            int enchantressCount = enchantressesOnBattlefield.size();
            if (enchantressCount > 1) {
                scoreBonus += (enchantressCount - 1) * BONUS_PER_ADDITIONAL_ENCHANTRESS;
            }

            // Bonus for cost reducer
            if (!reducersOnBattlefield.isEmpty()) {
                scoreBonus += BONUS_COST_REDUCER;
            }

            notes.append(String.format("Enchantresses: %d, Enchantments: %d",
                    enchantressCount, enchantmentCountTotal));
        } else if (hasEnchantressInPlay) {
            state = SynergyState.READY;
            scoreBonus = BONUS_ENCHANTRESS_ONLY + 100;
            notes.append("Enchantress in play, building enchantment count");
        } else if (hasEnoughEnchantments) {
            state = SynergyState.PARTIAL;
            scoreBonus = BONUS_ENCHANTRESS_ONLY;
            notes.append("Have enchantments, need enchantress");
        } else {
            state = SynergyState.ENABLER_ONLY;
            scoreBonus = BONUS_ENCHANTRESS_ONLY;
        }

        // Payoffs are dynamically all enchantments
        Set<String> payoffsFound = new HashSet<>();
        payoffsFound.add(enchantmentCountTotal + " enchantments in deck");

        Set<String> payoffsOnBattlefield = new HashSet<>();
        if (enchantmentCountOnBattlefield > 0) {
            payoffsOnBattlefield.add(enchantmentCountOnBattlefield + " enchantments");
        }

        return SynergyDetectionResult.builder(SYNERGY_ID, SynergyType.ENCHANTRESS)
                .state(state)
                .enablersFound(enchantressesFound)
                .payoffsFound(payoffsFound)
                .enablersOnBattlefield(enchantressesOnBattlefield)
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
     * Check if a card is an enchantress (draws on enchantment).
     */
    public boolean isEnchantress(String cardName) {
        return enchantressCards.contains(cardName);
    }

    /**
     * Check if a card is a cost reducer.
     */
    public boolean isCostReducer(String cardName) {
        return costReducers.contains(cardName);
    }
}
