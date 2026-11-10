package mage.abilities.assignment;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Unit tests for the roles assignment algorithm (maximum bipartite matching between cards and roles),
 * used by party count, "a card of each card type" targets, etc
 *
 * @author JayDi85
 */
public class RoleAssignmentTest {

    private static Set<String> roles(String... roles) {
        return new LinkedHashSet<>(Arrays.asList(roles));
    }

    /**
     * Check the result for every order of cards
     */
    private static void assertAssignment(int needCount, List<Set<String>> cardRoles) {
        List<List<Set<String>>> orders = new ArrayList<>();
        permutations(new ArrayList<>(cardRoles), 0, orders);
        for (List<Set<String>> order : orders) {
            Assert.assertEquals("wrong roles count for cards order " + order, needCount, RoleAssignment.getMaxAssignment(order));
        }
    }

    private static void permutations(List<Set<String>> list, int index, List<List<Set<String>>> res) {
        if (index == list.size()) {
            res.add(new ArrayList<>(list));
            return;
        }
        for (int i = index; i < list.size(); i++) {
            Collections.swap(list, index, i);
            permutations(list, index + 1, res);
            Collections.swap(list, index, i);
        }
    }

    /**
     * Reference result: try all possible assignments
     */
    private static int bruteForce(List<Set<String>> cardRoles, int cardIndex, Set<String> usedRoles) {
        if (cardIndex == cardRoles.size()) {
            return 0;
        }
        // card without role
        int best = bruteForce(cardRoles, cardIndex + 1, usedRoles);
        // card with one of the free roles
        for (String role : cardRoles.get(cardIndex)) {
            if (usedRoles.add(role)) {
                best = Math.max(best, 1 + bruteForce(cardRoles, cardIndex + 1, usedRoles));
                usedRoles.remove(role);
            }
        }
        return best;
    }

    @Test
    public void test_NoCards() {
        assertAssignment(0, Arrays.asList());
    }

    @Test
    public void test_CardsWithoutRoles() {
        assertAssignment(0, Arrays.asList(roles()));
        assertAssignment(0, Arrays.asList(roles(), roles()));
        assertAssignment(1, Arrays.asList(roles(), roles("A", "B")));
        assertAssignment(1, Arrays.asList(roles(), roles("A"), roles("A")));
    }

    @Test
    public void test_DifferentRoles() {
        assertAssignment(1, Arrays.asList(roles("A")));
        assertAssignment(2, Arrays.asList(roles("A"), roles("B")));
        assertAssignment(4, Arrays.asList(roles("A"), roles("B"), roles("C"), roles("D")));
    }

    @Test
    public void test_SameRole() {
        assertAssignment(1, Arrays.asList(roles("A"), roles("A")));
        assertAssignment(1, Arrays.asList(roles("A"), roles("A"), roles("A")));
    }

    @Test
    public void test_ManyCardsWithSameTwoRoles() {
        // prod case: party with three Cleric Wizards and a Rogue Warrior
        assertAssignment(2, Arrays.asList(roles("A", "B"), roles("A", "B"), roles("A", "B")));
        assertAssignment(3, Arrays.asList(roles("A", "B"), roles("A", "B"), roles("A", "B"), roles("C", "D")));
        assertAssignment(3, Arrays.asList(roles("A", "B"), roles("A", "B"), roles("A", "B"), roles("A"), roles("C", "D")));
        assertAssignment(4, Arrays.asList(roles("A", "B"), roles("A", "B"), roles("A", "B"), roles("C"), roles("C", "D")));
    }

    @Test
    public void test_NeedMoveOtherCards() {
        // a card can get a role only if another card moves to its other role
        assertAssignment(2, Arrays.asList(roles("A", "B"), roles("A")));
        assertAssignment(3, Arrays.asList(roles("A", "B"), roles("B", "C"), roles("C")));
        // long chain of moves
        assertAssignment(4, Arrays.asList(roles("A", "B"), roles("B", "C"), roles("C", "D"), roles("D")));
        assertAssignment(4, Arrays.asList(roles("A", "B", "C", "D"), roles("A"), roles("B"), roles("C")));
    }

    @Test
    public void test_NotAllCardsCanGetRole() {
        assertAssignment(2, Arrays.asList(roles("A"), roles("A"), roles("A", "B")));
        assertAssignment(3, Arrays.asList(roles("A", "B", "C"), roles("A", "B"), roles("A", "B"), roles("A")));
        assertAssignment(4, Arrays.asList(roles("A", "B"), roles("A", "B"), roles("A", "B"), roles("C", "D"), roles("C", "D"), roles("C", "D")));
    }

    @Test(timeout = 5000)
    public void test_ManyCards() {
        // party of many changelings
        List<Set<String>> cardRoles = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            cardRoles.add(roles("A", "B", "C", "D"));
        }
        Assert.assertEquals(4, RoleAssignment.getMaxAssignment(cardRoles));

        // many cards with same two roles and other roles
        cardRoles.clear();
        for (int i = 0; i < 100; i++) {
            cardRoles.add(roles("A", "B"));
        }
        cardRoles.add(roles("C", "D"));
        Assert.assertEquals(3, RoleAssignment.getMaxAssignment(cardRoles));

        // many cards with own roles (e.g. a card of each card type in a big library)
        cardRoles.clear();
        for (int i = 0; i < 100; i++) {
            cardRoles.add(roles("R" + i, "R" + (i + 1)));
        }
        Assert.assertEquals(100, RoleAssignment.getMaxAssignment(cardRoles));
    }

    @Test
    public void test_RandomCards() {
        // compare with all possible assignments
        Random random = new Random(20261018);
        String[] allRoles = {"A", "B", "C", "D", "E"};
        for (int testNumber = 0; testNumber < 20000; testNumber++) {
            int cardsAmount = random.nextInt(8);
            int rolesAmount = 1 + random.nextInt(allRoles.length);
            List<Set<String>> cardRoles = new ArrayList<>();
            for (int i = 0; i < cardsAmount; i++) {
                Set<String> cardRole = new LinkedHashSet<>();
                for (int r = 0; r < rolesAmount; r++) {
                    if (random.nextInt(3) == 0) {
                        cardRole.add(allRoles[r]);
                    }
                }
                cardRoles.add(cardRole);
            }
            int needCount = bruteForce(cardRoles, 0, new HashSet<>());
            Assert.assertEquals("wrong roles count for cards " + cardRoles, needCount, RoleAssignment.getMaxAssignment(cardRoles));
            Collections.shuffle(cardRoles, random);
            Assert.assertEquals("wrong roles count for cards " + cardRoles, needCount, RoleAssignment.getMaxAssignment(cardRoles));
        }
    }
}
