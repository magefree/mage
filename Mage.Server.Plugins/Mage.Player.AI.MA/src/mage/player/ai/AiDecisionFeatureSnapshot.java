package mage.player.ai;

import mage.constants.CommanderCardType;
import mage.constants.Zone;
import mage.abilities.Ability;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Compact trace-only game-state summary for explaining strategic score swings.
 */
public final class AiDecisionFeatureSnapshot {

    public final List<PlayerFeature> players;
    public final List<ThreatFeature> topThreats;
    public final String phaseIntent;
    public final int battlefieldSize;
    public final int stackSize;

    private AiDecisionFeatureSnapshot(List<PlayerFeature> players, List<ThreatFeature> topThreats,
                                      String phaseIntent, int battlefieldSize, int stackSize) {
        this.players = Collections.unmodifiableList(new ArrayList<>(players));
        this.topThreats = Collections.unmodifiableList(new ArrayList<>(topThreats));
        this.phaseIntent = phaseIntent;
        this.battlefieldSize = battlefieldSize;
        this.stackSize = stackSize;
    }

    public static AiDecisionFeatureSnapshot from(Game game, UUID activePlayerId) {
        if (game == null || activePlayerId == null) {
            return null;
        }
        List<PlayerFeature> players = new ArrayList<>();
        List<ThreatFeature> threats = new ArrayList<>();
        for (Player player : game.getPlayers().values()) {
            if (player == null) {
                continue;
            }
            PlayerFeature playerFeature = PlayerFeature.from(game, player, activePlayerId);
            players.add(playerFeature);
            threats.addAll(playerFeature.topThreats);
        }
        threats.sort(Comparator.comparingInt((ThreatFeature threat) -> threat.value).reversed());
        if (threats.size() > 3) {
            threats = new ArrayList<>(threats.subList(0, 3));
        }
        return new AiDecisionFeatureSnapshot(
                players,
                threats,
                phaseIntent(game),
                game.getBattlefield().getAllPermanents().size(),
                game.getStack().size()
        );
    }

    private static String phaseIntent(Game game) {
        if (game.getTurnStepType() == null) {
            return "unknown";
        }
        switch (game.getTurnStepType()) {
            case UNTAP:
            case UPKEEP:
                return "pre-draw";
            case DRAW:
                return "draw";
            case PRECOMBAT_MAIN:
                return "precombat-main";
            case BEGIN_COMBAT:
            case DECLARE_ATTACKERS:
            case DECLARE_BLOCKERS:
            case FIRST_COMBAT_DAMAGE:
            case COMBAT_DAMAGE:
            case END_COMBAT:
                return "combat";
            case POSTCOMBAT_MAIN:
                return "postcombat-main";
            case END_TURN:
            case CLEANUP:
                return "end-step";
            default:
                return game.getTurnStepType().toString();
        }
    }

    public static final class PlayerFeature {
        public final String playerId;
        public final String name;
        public final boolean activeDecisionPlayer;
        public final boolean inGame;
        public final boolean lost;
        public final int life;
        public final int handSize;
        public final int battlefieldSize;
        public final int creatureCount;
        public final int boardPower;
        public final int manaSources;
        public final int colorAccess;
        public final List<CommanderFeature> commanders;
        public final List<ThreatFeature> topThreats;

        private PlayerFeature(String playerId, String name, boolean activeDecisionPlayer, boolean inGame, boolean lost,
                              int life, int handSize, int battlefieldSize, int creatureCount, int boardPower,
                              int manaSources, int colorAccess, List<CommanderFeature> commanders,
                              List<ThreatFeature> topThreats) {
            this.playerId = playerId;
            this.name = name;
            this.activeDecisionPlayer = activeDecisionPlayer;
            this.inGame = inGame;
            this.lost = lost;
            this.life = life;
            this.handSize = handSize;
            this.battlefieldSize = battlefieldSize;
            this.creatureCount = creatureCount;
            this.boardPower = boardPower;
            this.manaSources = manaSources;
            this.colorAccess = colorAccess;
            this.commanders = Collections.unmodifiableList(new ArrayList<>(commanders));
            this.topThreats = Collections.unmodifiableList(new ArrayList<>(topThreats));
        }

        private static PlayerFeature from(Game game, Player player, UUID activePlayerId) {
            int battlefieldSize = 0;
            int creatureCount = 0;
            int boardPower = 0;
            int manaSources = 0;
            int colorAccess = 0;
            List<ThreatFeature> threats = new ArrayList<>();
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(player.getId())) {
                if (permanent == null) {
                    continue;
                }
                battlefieldSize++;
                if (isManaSource(permanent, game)) {
                    manaSources++;
                    colorAccess += colorAccess(permanent, game);
                }
                if (permanent.isCreature(game)) {
                    creatureCount++;
                    int power = Math.max(0, permanent.getPower().getValue());
                    int toughness = Math.max(0, permanent.getToughness().getValue());
                    boardPower += power;
                    threats.add(new ThreatFeature(
                            permanent.getId().toString(),
                            permanent.getName(),
                            player.getId().toString(),
                            player.getName(),
                            power,
                            toughness,
                            estimateThreatValue(permanent, game)
                    ));
                }
            }
            threats.sort(Comparator.comparingInt((ThreatFeature threat) -> threat.value).reversed());
            if (threats.size() > 3) {
                threats = new ArrayList<>(threats.subList(0, 3));
            }
            return new PlayerFeature(
                    player.getId().toString(),
                    player.getName(),
                    player.getId().equals(activePlayerId),
                    player.isInGame(),
                    player.hasLost(),
                    player.getLife(),
                    player.getHand().size(),
                    battlefieldSize,
                    creatureCount,
                    boardPower,
                    manaSources,
                    colorAccess,
                    commanderFeatures(game, player),
                    threats
            );
        }
    }

    public static final class CommanderFeature {
        public final String id;
        public final String name;
        public final String zone;
        public final boolean onBattlefield;

        private CommanderFeature(String id, String name, String zone, boolean onBattlefield) {
            this.id = id;
            this.name = name;
            this.zone = zone;
            this.onBattlefield = onBattlefield;
        }
    }

    public static final class ThreatFeature {
        public final String id;
        public final String name;
        public final String controllerId;
        public final String controllerName;
        public final int power;
        public final int toughness;
        public final int value;

        private ThreatFeature(String id, String name, String controllerId, String controllerName,
                              int power, int toughness, int value) {
            this.id = id;
            this.name = name;
            this.controllerId = controllerId;
            this.controllerName = controllerName;
            this.power = power;
            this.toughness = toughness;
            this.value = value;
        }
    }

    private static List<CommanderFeature> commanderFeatures(Game game, Player player) {
        List<CommanderFeature> commanders = new ArrayList<>();
        Set<UUID> commanderIds;
        try {
            commanderIds = game.getCommandersIds(player, CommanderCardType.COMMANDER_OR_OATHBREAKER, true);
        } catch (RuntimeException e) {
            return commanders;
        }
        for (UUID commanderId : commanderIds) {
            Permanent permanent = game.getPermanent(commanderId);
            Zone zone = game.getState().getZone(commanderId);
            String name = permanent == null ? "" : permanent.getName();
            if (name.isEmpty() && game.getCard(commanderId) != null) {
                name = game.getCard(commanderId).getName();
            }
            commanders.add(new CommanderFeature(
                    commanderId.toString(),
                    name,
                    zone == null ? "unknown" : zone.toString(),
                    permanent != null
            ));
        }
        return commanders;
    }

    private static boolean isManaSource(Permanent permanent, Game game) {
        if (permanent.isLand(game)) {
            return true;
        }
        try {
            for (Ability ability : permanent.getAbilities()) {
                if (ability != null && ability.isManaAbility()) {
                    return true;
                }
            }
            return false;
        } catch (RuntimeException e) {
            return false;
        }
    }

    private static int colorAccess(Permanent permanent, Game game) {
        String rules = rules(permanent);
        int colors = 0;
        colors += containsManaSymbol(rules, "w") ? 1 : 0;
        colors += containsManaSymbol(rules, "u") ? 1 : 0;
        colors += containsManaSymbol(rules, "b") ? 1 : 0;
        colors += containsManaSymbol(rules, "r") ? 1 : 0;
        colors += containsManaSymbol(rules, "g") ? 1 : 0;
        if (colors == 0 && permanent.isLand(game)) {
            return 1;
        }
        return colors;
    }

    private static boolean containsManaSymbol(String rules, String symbol) {
        return rules.contains("{" + symbol + "}")
                || rules.contains("add one mana of any color")
                || rules.contains("add one mana of any colour");
    }

    private static int estimateThreatValue(Permanent permanent, Game game) {
        int power = Math.max(0, permanent.getPower().getValue());
        int toughness = Math.max(0, permanent.getToughness().getValue());
        int value = power * 3 + toughness;
        String rules = rules(permanent);
        if (rules.contains("flying") || rules.contains("trample") || rules.contains("menace") || rules.contains("unblockable")) {
            value += 5;
        }
        if (rules.contains("double strike") || rules.contains("infect") || rules.contains("poison")) {
            value += 8;
        }
        if (rules.contains("whenever") || rules.contains("at the beginning")) {
            value += 4;
        }
        return value;
    }

    private static String rules(Permanent permanent) {
        try {
            return String.join("\n", permanent.getAbilities().getRules()).toLowerCase();
        } catch (RuntimeException e) {
            return "";
        }
    }
}
