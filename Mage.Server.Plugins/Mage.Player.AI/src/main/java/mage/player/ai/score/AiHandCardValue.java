package mage.player.ai.score;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.Card;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

/**
 * Generic hand-card value estimates for AI decisions before cards become permanents.
 */
public final class AiHandCardValue {

    private static final int MAX_KEEP_SCORE = 2500;
    private static final int MIN_KEEP_SCORE = 15;

    private AiHandCardValue() {
    }

    public static int estimateRawHandKeepScore(Game game, UUID playerId) {
        Player player = game == null ? null : game.getPlayer(playerId);
        if (player == null) {
            return 0;
        }
        int score = 0;
        for (Card card : player.getHand().getCards(game)) {
            score += estimateKeepScore(game, card, playerId);
        }
        return score;
    }

    public static int estimateHandScore(Game game, UUID playerId) {
        Player player = game == null ? null : game.getPlayer(playerId);
        if (player == null) {
            return 0;
        }
        int score = 0;
        for (Card card : player.getHand().getCards(game)) {
            score += estimateHandScore(game, card, playerId);
        }
        return score;
    }

    public static int estimateHandScore(Game game, Card card, UUID playerId) {
        return Math.max(GameStateEvaluator2.HAND_CARD_SCORE, estimateKeepScore(game, card, playerId) / 10);
    }

    public static int estimateKeepScore(Game game, Card card, UUID playerId) {
        return estimateKeepValue(game, card, playerId, false).getKeepScore();
    }

    public static CardValue estimateKeepValue(Game game, Card card, UUID playerId) {
        return estimateKeepValue(game, card, playerId, false);
    }

    public static CardValue estimateKeepValue(Game game, Card card, UUID playerId, boolean playable) {
        if (card == null) {
            return new CardValue(0, 0, 0, false, "missing card");
        }
        String rules = rules(game, card);
        if (card.isLand(game)) {
            int landScore = estimateLandKeepScore(game, playerId);
            return new CardValue(landScore, 0, 0, false, "land development");
        }

        int manaValue = Math.max(0, card.getManaValue());
        int value = 40 + manaValue * 22;
        EstimatedStats stats = EstimatedStats.printed(card);
        String detail = stats.getReason();

        if (card.isCreature(game)) {
            stats = estimateCreatureStats(game, card, playerId);
            detail = stats.getReason();
            int power = Math.max(0, stats.getPower());
            int toughness = Math.max(0, stats.getToughness());
            value += 110 + power * 34 + toughness * 26;
            if (stats.isDynamic()) {
                value += 140 + Math.min(260, (power + toughness) * 12);
            }
            int futurePotential = estimateDynamicFuturePotential(game, card, playerId, rules, stats);
            if (futurePotential > 0) {
                value += futurePotential;
                detail += ", future potential";
            }
            if (power + toughness >= 10 || rules.contains("trample")
                    || rules.contains("flying") || rules.contains("deathtouch")
                    || rules.contains("lifelink") || rules.contains("double strike")) {
                value += 90;
            }
        } else if (card.isPlaneswalker(game)) {
            value += 360;
        } else if (card.getCardType(game).contains(CardType.BATTLE)) {
            value += 260;
        } else if (card.isInstantOrSorcery(game)) {
            value += estimateSpellKeepScore(rules);
        } else if (card.getCardType(game).contains(CardType.ARTIFACT)
                || card.getCardType(game).contains(CardType.ENCHANTMENT)) {
            value += estimatePermanentSpellKeepScore(rules);
        }

        value += estimateTextRoleScore(rules);
        if (playable) {
            value += 40;
        }
        value -= estimateManaDistancePenalty(game, playerId, manaValue);
        value = clamp(value, MIN_KEEP_SCORE, MAX_KEEP_SCORE);
        return new CardValue(value, stats.getPower(), stats.getToughness(), stats.isDynamic(), detail);
    }

    public static EstimatedStats estimateCreatureStats(Game game, Card card, UUID playerId) {
        if (card == null) {
            return new EstimatedStats(0, 0, false, "missing card");
        }
        EstimatedStats structuredStats = estimateStructuredCreatureStats(game, card, playerId);
        if (structuredStats != null) {
            return structuredStats;
        }
        String rules = rules(game, card);
        int printedPower = safePower(card);
        int printedToughness = safeToughness(card);

        if (rules.contains("power and toughness are each equal to the number of cards in all graveyards")) {
            int count = countAllGraveyardCards(game, playerId);
            return new EstimatedStats(count, count, true, "cards in all graveyards");
        }
        if (rules.contains("power is equal to the number of card types among cards in all graveyards")) {
            int count = countCardTypesAmongAllGraveyards(game, playerId);
            return new EstimatedStats(count, count + toughnessPlusOne(rules, printedToughness), true,
                    "card types among cards in all graveyards");
        }
        if (rules.contains("power is equal to the number of card types among cards in your graveyard")) {
            int count = countCardTypesInGraveyard(game, playerId);
            return new EstimatedStats(count, count + toughnessPlusOne(rules, printedToughness), true,
                    "card types among cards in your graveyard");
        }
        if (rules.contains("power is equal to the number of creature cards in all graveyards")) {
            int count = countAllGraveyardCardsOfType(game, playerId, CardType.CREATURE);
            return new EstimatedStats(count, count + toughnessPlusOne(rules, printedToughness), true,
                    "creature cards in all graveyards");
        }
        if (rules.contains("power is equal to the number of creature cards in your graveyard")) {
            int count = countOwnGraveyardCardsOfType(game, playerId, CardType.CREATURE);
            return new EstimatedStats(count, count + toughnessPlusOne(rules, printedToughness), true,
                    "creature cards in your graveyard");
        }
        if (rules.contains("power is equal to the number of land cards in your graveyard")) {
            int count = countOwnGraveyardCardsOfType(game, playerId, CardType.LAND);
            return new EstimatedStats(count, count + toughnessPlusOne(rules, printedToughness), true,
                    "land cards in your graveyard");
        }
        if (rules.contains("power is equal to the number of permanent cards in your graveyard")) {
            int count = countOwnPermanentCardsInGraveyard(game, playerId);
            return new EstimatedStats(count, count + toughnessPlusOne(rules, printedToughness), true,
                    "permanent cards in your graveyard");
        }
        if (rules.contains("power is equal to the number of instant and sorcery cards in your graveyard")) {
            int count = countOwnInstantSorceryCardsInGraveyard(game, playerId);
            return new EstimatedStats(count, printedToughness, true,
                    "instant and sorcery cards in your graveyard");
        }
        if (rules.contains("power is equal to the number of cards in your hand")) {
            Player player = game == null ? null : game.getPlayer(playerId);
            int count = player == null ? 0 : player.getHand().size();
            return new EstimatedStats(count, printedToughness, true, "cards in your hand");
        }
        if (rules.contains("power and toughness are each equal to the number of creatures you control")) {
            int count = countOwnPermanents(game, playerId, permanent -> permanent.isCreature(game));
            return new EstimatedStats(count, count, true, "creatures you control");
        }
        if (rules.contains("power is equal to the number of creatures you control")) {
            int count = countOwnPermanents(game, playerId, permanent -> permanent.isCreature(game));
            return new EstimatedStats(count, printedToughness, true, "creatures you control");
        }
        if (rules.contains("power is equal to the number of lands you control")
                || rules.contains("power is equal to the amount of lands you control")) {
            int count = countOwnPermanents(game, playerId, permanent -> permanent.isLand(game));
            return new EstimatedStats(count, printedToughness, true, "lands you control");
        }
        if (rules.contains("power and toughness are each equal to the number of lands you control")) {
            int count = countOwnPermanents(game, playerId, permanent -> permanent.isLand(game));
            return new EstimatedStats(count, count, true, "lands you control");
        }
        if (rules.contains("power is equal to the number of artifacts you control")) {
            int count = countOwnPermanents(game, playerId, permanent -> permanent.isArtifact(game));
            return new EstimatedStats(count, printedToughness, true, "artifacts you control");
        }
        if (rules.contains("power is equal to the number of artifacts and/or enchantments you control")) {
            int count = countOwnPermanents(game, playerId,
                    permanent -> permanent.isArtifact(game) || permanent.isEnchantment(game));
            return new EstimatedStats(count, printedToughness, true, "artifacts and enchantments you control");
        }
        if (rules.contains("power and toughness are each equal to the number of artifacts you control")) {
            int count = countOwnPermanents(game, playerId, permanent -> permanent.isArtifact(game));
            return new EstimatedStats(count, count, true, "artifacts you control");
        }
        if (rules.contains("power and toughness are each equal to the number of enchantments you control")) {
            int count = countOwnPermanents(game, playerId, permanent -> permanent.isEnchantment(game));
            return new EstimatedStats(count, count, true, "enchantments you control");
        }

        return new EstimatedStats(printedPower, printedToughness, false, "printed stats");
    }

    private static EstimatedStats estimateStructuredCreatureStats(Game game, Card card, UUID playerId) {
        if (game == null || card == null) {
            return null;
        }
        int power = safePower(card);
        int toughness = safeToughness(card);
        boolean dynamic = false;
        String detail = null;
        UUID controllerId = playerId == null ? card.getOwnerId() : playerId;
        for (Ability originalAbility : card.getAbilities(game)) {
            Ability ability = originalAbility.copy();
            ability.setSourceId(card.getId());
            ability.setControllerId(controllerId);
            for (Effect effect : ability.getAllEffects()) {
                if (!(effect instanceof SetBasePowerToughnessSourceEffect)) {
                    continue;
                }
                SetBasePowerToughnessSourceEffect setEffect = (SetBasePowerToughnessSourceEffect) effect;
                DynamicValue powerValue = setEffect.getPowerValue();
                DynamicValue toughnessValue = setEffect.getToughnessValue();
                if (powerValue != null) {
                    power = Math.max(0, safeCalculate(powerValue, game, ability, effect));
                    dynamic = true;
                    detail = valueMessage(powerValue);
                }
                if (toughnessValue != null) {
                    toughness = Math.max(0, safeCalculate(toughnessValue, game, ability, effect));
                    dynamic = true;
                    String toughnessDetail = valueMessage(toughnessValue);
                    detail = detail == null || detail.equals(toughnessDetail)
                            ? toughnessDetail
                            : detail + " / " + toughnessDetail;
                }
            }
        }
        return dynamic ? new EstimatedStats(power, toughness, true, detail == null ? "dynamic stats" : detail) : null;
    }

    private static int estimateLandKeepScore(Game game, UUID playerId) {
        Player player = game == null ? null : game.getPlayer(playerId);
        if (player == null) {
            return 90;
        }
        int landsInPlay = 0;
        if (game != null) {
            landsInPlay = (int) game.getBattlefield()
                    .getAllActivePermanents(playerId)
                    .stream()
                    .filter(permanent -> permanent.isLand(game))
                    .count();
        }
        int landsInHand = 0;
        for (Card card : player.getHand().getCards(game)) {
            if (card.isLand(game)) {
                landsInHand++;
            }
        }
        if (landsInPlay < 3) {
            return landsInHand <= 2 ? 190 : 145;
        }
        if (landsInPlay >= 7 && landsInHand > 1) {
            return 30;
        }
        return 95;
    }

    private static int estimateSpellKeepScore(String rules) {
        int value = 80;
        if (isBoardWipeText(rules)) {
            value += 330;
        } else if (isRemovalText(rules)) {
            value += 220;
        }
        if (isCardAccessText(rules)) {
            value += 180;
        }
        if (isProtectionText(rules)) {
            value += 170;
        }
        if (isRampText(rules)) {
            value += 120;
        }
        if (isTokenText(rules)) {
            value += 90;
        }
        return value;
    }

    private static int estimatePermanentSpellKeepScore(String rules) {
        int value = 100;
        if (isEngineText(rules)) {
            value += 180;
        }
        if (isRampText(rules)) {
            value += 120;
        }
        if (isTokenText(rules)) {
            value += 90;
        }
        return value;
    }

    private static int estimateTextRoleScore(String rules) {
        int value = 0;
        if (rules.contains("graveyard")) {
            value += 80;
        }
        if (rules.contains("{x}") || rules.contains(" x ")) {
            value += 50;
        }
        if (rules.contains("you win the game") || rules.contains("extra turn")) {
            value += 260;
        }
        if (rules.contains("search your library")) {
            value += 130;
        }
        if (rules.contains("whenever") || rules.contains("at the beginning")) {
            value += 70;
        }
        return value;
    }

    private static int estimateManaDistancePenalty(Game game, UUID playerId, int manaValue) {
        if (game == null || playerId == null || manaValue <= 0) {
            return 0;
        }
        int manaSources = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
            if (permanent.isLand(game)) {
                manaSources++;
            }
        }
        int distance = manaValue - manaSources - 2;
        if (distance <= 0) {
            return 0;
        }
        return Math.min(140, distance * 25);
    }

    private static int safeCalculate(DynamicValue value, Game game, Ability ability, Effect effect) {
        try {
            return value.calculate(game, ability, effect);
        } catch (RuntimeException e) {
            return 0;
        }
    }

    private static String valueMessage(DynamicValue value) {
        try {
            return value.getMessage();
        } catch (RuntimeException e) {
            return "dynamic stats";
        }
    }

    private static int estimateDynamicFuturePotential(Game game, Card card, UUID playerId, String rules, EstimatedStats stats) {
        if (card == null || !stats.isDynamic()) {
            return 0;
        }
        int floor = dynamicFutureStatFloor(game, card, playerId, rules);
        int current = dynamicFutureCurrentStat(rules, stats);
        int missing = floor - current;
        if (missing <= 0) {
            return 0;
        }
        return Math.min(360, missing * 55);
    }

    private static int dynamicFutureCurrentStat(String rules, EstimatedStats stats) {
        if (rules.contains("toughness is equal")
                && !rules.contains("power is equal")
                && !rules.contains("power and toughness")) {
            return stats.getToughness();
        }
        return stats.getPower();
    }

    private static int dynamicFutureStatFloor(Game game, Card card, UUID playerId, String rules) {
        int manaValue = Math.max(0, card.getManaValue());
        int turn = game == null ? 1 : Math.max(1, game.getTurnNum());
        if (rules.contains("graveyard")) {
            int floor = rules.contains("all graveyards") ? 5 : 3;
            if (rules.contains("creature cards")
                    || rules.contains("instant and sorcery cards")
                    || rules.contains("land cards")
                    || rules.contains("permanent cards")
                    || rules.contains("card types")) {
                floor = Math.max(2, floor - 1);
            }
            if (manaValue >= 5) {
                floor++;
            }
            return floor;
        }
        if (rules.contains("lands you control") || rules.contains("amount of lands you control")) {
            return Math.min(7, Math.max(3, manaValue));
        }
        if (rules.contains("creatures you control")) {
            int floor = Math.min(6, Math.max(3, 2 + turn / 2));
            if (rules.contains("create ") && rules.contains(" token")) {
                floor++;
            }
            return floor;
        }
        if (rules.contains("artifacts you control")
                || rules.contains("enchantments you control")
                || rules.contains("artifacts and/or enchantments you control")) {
            return 3;
        }
        if (rules.contains("cards in your hand")) {
            Player player = game == null ? null : game.getPlayer(playerId);
            return player == null ? 3 : Math.max(3, Math.min(6, player.getHand().size()));
        }
        return 0;
    }

    private static boolean isBoardWipeText(String rules) {
        return rules.contains("destroy all")
                || rules.contains("exile all")
                || rules.contains("damage to each creature")
                || rules.contains("all creatures get -");
    }

    private static boolean isRemovalText(String rules) {
        return isBoardWipeText(rules)
                || rules.contains("destroy target")
                || rules.contains("exile target")
                || rules.contains("deals damage to target")
                || rules.contains("deal damage to target")
                || rules.contains("counter target spell")
                || rules.contains("target creature gets -")
                || rules.contains("target player sacrifices")
                || rules.contains("each opponent sacrifices")
                || rules.contains("each player sacrifices");
    }

    private static boolean isCardAccessText(String rules) {
        return rules.contains("draw a card")
                || rules.contains("draw two")
                || rules.contains("draw three")
                || rules.contains("look at the top")
                || rules.contains("impulse")
                || rules.contains("scry");
    }

    private static boolean isProtectionText(String rules) {
        return rules.contains("counter target")
                || rules.contains("hexproof")
                || rules.contains("indestructible")
                || rules.contains("protection from")
                || rules.contains("regenerate");
    }

    private static boolean isRampText(String rules) {
        return rules.contains("add {")
                || rules.contains("add one mana")
                || rules.contains("search your library") && rules.contains("land");
    }

    private static boolean isTokenText(String rules) {
        return rules.contains("create ") && rules.contains(" token");
    }

    private static boolean isEngineText(String rules) {
        return rules.contains("whenever")
                || rules.contains("at the beginning")
                || rules.contains("draw a card")
                || rules.contains("create ") && rules.contains(" token")
                || rules.contains("+1/+1 counter");
    }

    private static int countAllGraveyardCards(Game game, UUID playerId) {
        int count = 0;
        for (Player player : playersInRange(game, playerId)) {
            count += player.getGraveyard().size();
        }
        return count;
    }

    private static int countAllGraveyardCardsOfType(Game game, UUID playerId, CardType cardType) {
        int count = 0;
        for (Player player : playersInRange(game, playerId)) {
            for (Card card : player.getGraveyard().getCards(game)) {
                if (card.getCardType(game).contains(cardType)) {
                    count++;
                }
            }
        }
        return count;
    }

    private static int countOwnGraveyardCardsOfType(Game game, UUID playerId, CardType cardType) {
        Player player = game == null ? null : game.getPlayer(playerId);
        if (player == null) {
            return 0;
        }
        int count = 0;
        for (Card card : player.getGraveyard().getCards(game)) {
            if (card.getCardType(game).contains(cardType)) {
                count++;
            }
        }
        return count;
    }

    private static int countOwnPermanentCardsInGraveyard(Game game, UUID playerId) {
        Player player = game == null ? null : game.getPlayer(playerId);
        if (player == null) {
            return 0;
        }
        int count = 0;
        for (Card card : player.getGraveyard().getCards(game)) {
            if (card.getCardType(game).contains(CardType.ARTIFACT)
                    || card.getCardType(game).contains(CardType.BATTLE)
                    || card.getCardType(game).contains(CardType.CREATURE)
                    || card.getCardType(game).contains(CardType.ENCHANTMENT)
                    || card.getCardType(game).contains(CardType.LAND)
                    || card.getCardType(game).contains(CardType.PLANESWALKER)) {
                count++;
            }
        }
        return count;
    }

    private static int countOwnInstantSorceryCardsInGraveyard(Game game, UUID playerId) {
        Player player = game == null ? null : game.getPlayer(playerId);
        if (player == null) {
            return 0;
        }
        int count = 0;
        for (Card card : player.getGraveyard().getCards(game)) {
            if (card.getCardType(game).contains(CardType.INSTANT)
                    || card.getCardType(game).contains(CardType.SORCERY)) {
                count++;
            }
        }
        return count;
    }

    private static int countCardTypesAmongAllGraveyards(Game game, UUID playerId) {
        EnumSet<CardType> types = EnumSet.noneOf(CardType.class);
        for (Player player : playersInRange(game, playerId)) {
            addGraveyardCardTypes(types, player, game);
        }
        return types.size();
    }

    private static int countCardTypesInGraveyard(Game game, UUID playerId) {
        EnumSet<CardType> types = EnumSet.noneOf(CardType.class);
        Player player = game == null ? null : game.getPlayer(playerId);
        if (player != null) {
            addGraveyardCardTypes(types, player, game);
        }
        return types.size();
    }

    private static void addGraveyardCardTypes(Set<CardType> types, Player player, Game game) {
        for (Card card : player.getGraveyard().getCards(game)) {
            types.addAll(card.getCardType(game));
        }
    }

    private static int countOwnPermanents(Game game, UUID playerId, PermanentPredicate predicate) {
        if (game == null || playerId == null || predicate == null) {
            return 0;
        }
        int count = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
            if (permanent != null && predicate.matches(permanent)) {
                count++;
            }
        }
        return count;
    }

    private static List<Player> playersInRange(Game game, UUID playerId) {
        List<Player> players = new ArrayList<>();
        if (game == null) {
            return players;
        }
        if (playerId == null) {
            players.addAll(game.getPlayers().values());
            return players;
        }
        for (UUID id : game.getState().getPlayersInRange(playerId, game)) {
            Player player = game.getPlayer(id);
            if (player != null) {
                players.add(player);
            }
        }
        return players;
    }

    private static int toughnessPlusOne(String rules, int printedToughness) {
        if (rules.contains("toughness is equal to that number plus 1")) {
            return 1;
        }
        return printedToughness;
    }

    private static int safePower(Card card) {
        return card.getPower() == null ? 0 : card.getPower().getValue();
    }

    private static int safeToughness(Card card) {
        return card.getToughness() == null ? 0 : card.getToughness().getValue();
    }

    private static String rules(Game game, Card card) {
        if (card == null) {
            return "";
        }
        try {
            return String.join(" ", card.getRules(game)).toLowerCase(Locale.ROOT);
        } catch (RuntimeException e) {
            return String.join(" ", card.getRules()).toLowerCase(Locale.ROOT);
        }
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    private interface PermanentPredicate {
        boolean matches(Permanent permanent);
    }

    public static final class CardValue {
        private final int keepScore;
        private final int estimatedPower;
        private final int estimatedToughness;
        private final boolean dynamicStats;
        private final String detail;

        private CardValue(int keepScore, int estimatedPower, int estimatedToughness, boolean dynamicStats, String detail) {
            this.keepScore = keepScore;
            this.estimatedPower = estimatedPower;
            this.estimatedToughness = estimatedToughness;
            this.dynamicStats = dynamicStats;
            this.detail = detail;
        }

        public int getKeepScore() {
            return keepScore;
        }

        public int getEstimatedPower() {
            return estimatedPower;
        }

        public int getEstimatedToughness() {
            return estimatedToughness;
        }

        public boolean isDynamicStats() {
            return dynamicStats;
        }

        public String getDetail() {
            return detail;
        }
    }

    public static final class EstimatedStats {
        private final int power;
        private final int toughness;
        private final boolean dynamic;
        private final String reason;

        private EstimatedStats(int power, int toughness, boolean dynamic, String reason) {
            this.power = Math.max(0, power);
            this.toughness = Math.max(0, toughness);
            this.dynamic = dynamic;
            this.reason = reason;
        }

        private static EstimatedStats printed(Card card) {
            return new EstimatedStats(safePower(card), safeToughness(card), false, "printed stats");
        }

        public int getPower() {
            return power;
        }

        public int getToughness() {
            return toughness;
        }

        public boolean isDynamic() {
            return dynamic;
        }

        public String getReason() {
            return reason;
        }
    }
}
