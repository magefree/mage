package mage.abilities.assignment;

import mage.cards.Card;
import mage.cards.Cards;
import mage.game.Game;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Used for when you need to know how attributes are assigned among cards, such as for party count
 * Can be adapted for various card attributes
 *
 * @author TheElk801, JayDi85
 */
public abstract class RoleAssignment<T> implements Serializable {

    protected final List<T> attributes = new ArrayList<>();

    protected RoleAssignment(T... attributes) {
        for (T attribute : attributes) {
            this.attributes.add(attribute);
        }
    }

    protected abstract Set<T> makeSet(Card card, Game game);

    public int getRoleCount(Cards cards, Game game) {
        return getRoleCount(cards.getCards(game), game);
    }

    /**
     * 2+ targets shared same role, e.g. two cards with same card type - use it to filter possible targets
     */
    public boolean hasSharedRoles(Cards cards, Game game) {
        return getRoleCount(cards.getCards(game), game) < cards.size();
    }

    /**
     * Max number of cards that can get a role at the same time: each card takes up to one of its roles,
     * each role goes to up to one card (example: party count, each card type one card)
     */
    public int getRoleCount(Set<? extends Card> cards, Game game) {
        List<Set<T>> cardRoles = cards
                .stream()
                .map(card -> this.makeSet(card, game))
                .collect(Collectors.toList());
        return getMaxAssignment(cardRoles);
    }

    /**
     * Maximum bipartite matching between cards and roles (Kuhn's algorithm).
     * Package-private for unit tests.
     *
     * @param cardRoles possible roles of each card
     * @return max number of cards with own role
     */
    static <T> int getMaxAssignment(List<? extends Set<T>> cardRoles) {
        int maxRoles = (int) cardRoles
                .stream()
                .flatMap(Collection::stream)
                .distinct()
                .count();
        Map<T, Integer> roleOwners = new HashMap<>();
        for (int cardIndex = 0; cardIndex < cardRoles.size(); cardIndex++) {
            if (roleOwners.size() >= maxRoles) {
                break;
            }
            findRole(cardIndex, cardRoles, roleOwners, new HashSet<>());
        }
        return roleOwners.size();
    }

    /**
     * Try to give a role to the card, other cards can move to their free roles for it (augmenting path).
     * Each role is checked only once per search, so the search always ends.
     */
    private static <T> boolean findRole(int cardIndex, List<? extends Set<T>> cardRoles, Map<T, Integer> roleOwners, Set<T> checkedRoles) {
        for (T role : cardRoles.get(cardIndex)) {
            if (!checkedRoles.add(role)) {
                continue;
            }
            Integer owner = roleOwners.get(role);
            if (owner == null || findRole(owner, cardRoles, roleOwners, checkedRoles)) {
                roleOwners.put(role, cardIndex);
                return true;
            }
        }
        return false;
    }
}
