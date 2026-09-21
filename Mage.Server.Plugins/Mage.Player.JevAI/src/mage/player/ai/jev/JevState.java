package mage.player.ai.jev;

import mage.Mana;
import mage.cards.Card;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Turns the current game into the state Jev judges: named fields it can read,
 * nothing about what to do with them. Kept small on purpose, the model has a
 * 32k context and every judgement resends the whole thing.
 */
public final class JevState {

    private static final int MAX_RULE_LENGTH = 300;

    private JevState() {
    }

    public static Map<String, Object> of(Game game, UUID playerId) {
        Map<String, Object> state = new LinkedHashMap<>();
        state.put("game", game(game));
        state.put("me", me(game, playerId));
        state.put("opponents", opponents(game, playerId));
        state.put("stack", stack(game));
        return state;
    }

    private static Map<String, Object> game(Game game) {
        Map<String, Object> info = new LinkedHashMap<>();
        info.put("turn", game.getTurnNum());
        info.put("step", String.valueOf(game.getTurnStepType()));
        Player active = game.getPlayer(game.getActivePlayerId());
        info.put("active_player", active == null ? "unknown" : active.getName());
        return info;
    }

    /**
     * The largest mana pool the player could produce right now, as a cost string
     * like {R}{R}{G}. Without it a play decision has nothing to weigh a cost against.
     */
    private static String mana(Game game, Player player) {
        Mana best = null;
        try {
            for (Mana option : player.getManaAvailable(game)) {
                if (best == null || option.count() > best.count()) {
                    best = option;
                }
            }
        } catch (RuntimeException e) {
            return "unknown";
        }
        if (best == null || best.count() == 0) {
            return "none";
        }
        return best + " (" + best.count() + " total)";
    }

    private static Map<String, Object> me(Game game, UUID playerId) {
        Player player = game.getPlayer(playerId);
        Map<String, Object> info = new LinkedHashMap<>();
        if (player == null) {
            return info;
        }
        info.put("name", player.getName());
        info.put("life", player.getLife());
        info.put("cards_in_library", player.getLibrary().size());
        // what can actually be paid right now, the main thing a play decision turns on
        info.put("mana_available", mana(game, player));
        info.put("land_drop_still_available", player.canPlayLand());
        info.put("hand", hand(game, player));
        info.put("battlefield", battlefield(game, playerId));
        info.put("graveyard", names(game, player.getGraveyard().getCards(game)));
        return info;
    }

    private static List<Object> opponents(Game game, UUID playerId) {
        List<Object> opponents = new ArrayList<>();
        for (UUID opponentId : game.getOpponents(playerId, true)) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }
            Map<String, Object> info = new LinkedHashMap<>();
            info.put("name", opponent.getName());
            info.put("life", opponent.getLife());
            info.put("cards_in_hand", opponent.getHand().size());
            info.put("cards_in_library", opponent.getLibrary().size());
            info.put("battlefield", battlefield(game, opponentId));
            info.put("graveyard", names(game, opponent.getGraveyard().getCards(game)));
            opponents.add(info);
        }
        return opponents;
    }

    private static List<Object> hand(Game game, Player player) {
        List<Object> cards = new ArrayList<>();
        for (Card card : player.getHand().getCards(game)) {
            Map<String, Object> info = new LinkedHashMap<>();
            info.put("name", card.getName());
            info.put("mana_cost", card.getManaCost().getText());
            info.put("types", String.valueOf(card.getCardType(game)));
            info.put("rules", rules(card.getRules(game)));
            cards.add(info);
        }
        return cards;
    }

    private static List<Object> battlefield(Game game, UUID controllerId) {
        List<Object> permanents = new ArrayList<>();
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(controllerId)) {
            permanents.add(describe(game, permanent));
        }
        return permanents;
    }

    /**
     * One permanent as the model sees it, also used for attacker, blocker and
     * target options.
     */
    public static Map<String, Object> describe(Game game, Permanent permanent) {
        Map<String, Object> info = new LinkedHashMap<>();
        info.put("name", permanent.getName());
        info.put("types", String.valueOf(permanent.getCardType(game)));
        if (permanent.isCreature(game)) {
            info.put("power", permanent.getPower().getValue());
            info.put("toughness", permanent.getToughness().getValue());
            info.put("damage", permanent.getDamage());
            if (permanent.hasSummoningSickness()) {
                info.put("summoning_sick", true);
            }
        }
        if (permanent.isTapped()) {
            info.put("tapped", true);
        }
        if (permanent.isAttacking()) {
            info.put("attacking", true);
        }
        if (permanent.getBlocking() > 0) {
            info.put("blocking", true);
        }
        info.put("rules", rules(permanent.getRules(game)));
        return info;
    }

    private static List<Object> stack(Game game) {
        List<Object> objects = new ArrayList<>();
        for (StackObject stackObject : game.getStack()) {
            Map<String, Object> info = new LinkedHashMap<>();
            info.put("name", stackObject.getName());
            Player controller = game.getPlayer(stackObject.getControllerId());
            info.put("controller", controller == null ? "unknown" : controller.getName());
            info.put("rules", rule(stackObject.getStackAbility().getRule()));
            objects.add(info);
        }
        return objects;
    }

    private static List<String> names(Game game, java.util.Collection<Card> cards) {
        List<String> names = new ArrayList<>();
        for (Card card : cards) {
            names.add(card.getName());
        }
        return names;
    }

    private static String rules(List<String> rules) {
        if (rules == null || rules.isEmpty()) {
            return "";
        }
        StringBuilder text = new StringBuilder();
        for (String singleRule : rules) {
            if (singleRule == null || singleRule.isEmpty()) {
                continue;
            }
            if (text.length() > 0) {
                text.append(' ');
            }
            text.append(singleRule);
            if (text.length() > MAX_RULE_LENGTH) {
                break;
            }
        }
        return rule(text.toString());
    }

    /**
     * Rules text without the client's markup, short enough to resend every call.
     */
    public static String rule(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        String result = text.replaceAll("<[^>]*>", "").trim();
        return result.length() <= MAX_RULE_LENGTH ? result : result.substring(0, MAX_RULE_LENGTH) + "...";
    }
}
