package org.mage.test.cards.single.brc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.permanent.Permanent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class RootpathPurifierTest extends CardTestPlayerBase {

    private static final String purifier = "Rootpath Purifier";
    private static final String growth = "Rampant Growth";
    private static final String lay = "Lay of the Land";
    private static final String tree = "Tree of Tales";
    private static final String murder = "Murder";

    private void checkSupertypes() {
        Assertions.assertTrue(
                playerA.getLibrary()
                        .getCards(currentGame)
                        .stream()
                        .filter(card -> card.isLand(currentGame))
                        .allMatch(card -> card.isBasic(currentGame)),
                "All lands in library should be basic"
        );
        for (Permanent permanent : currentGame.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND, playerA.getId(), currentGame
        )) {
            Assertions.assertTrue(permanent.isBasic(currentGame), permanent.getName() + " should be basic");
        }
    }

    @Test
    public void testChangeSupertypeInLibrary() {
        addCard(Zone.BATTLEFIELD, playerA, purifier);
        addCard(Zone.LIBRARY, playerA, tree);


        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, purifier, 1);
        checkSupertypes();
    }

    @Test
    public void testSearchToBattlefield() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, purifier);
        addCard(Zone.HAND, playerA, growth);
        addCard(Zone.LIBRARY, playerA, tree);

        addTarget(playerA, tree);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, growth);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, tree, 1);
        assertPermanentCount(playerA, purifier, 1);
        checkSupertypes();
    }

    @Test
    public void testSearchToHand() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, purifier);
        addCard(Zone.HAND, playerA, lay);
        addCard(Zone.LIBRARY, playerA, tree);

        addTarget(playerA, tree);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lay);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, tree, 1);
        assertPermanentCount(playerA, purifier, 1);
        checkSupertypes();
        Assertions.assertTrue(
                playerA.getHand()
                        .getCards(currentGame)
                        .stream()
                        .filter(card -> tree.equals(card.getName()))
                        .noneMatch(card -> card.isBasic(currentGame)),
                tree + " should not be basic in hand"
        );
    }

    @Test
    public void testRemove() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, purifier);
        addCard(Zone.HAND, playerA, murder);
        addCard(Zone.LIBRARY, playerA, tree);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, murder, purifier);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, purifier, 0);
        assertGraveyardCount(playerA, purifier, 1);
        assertGraveyardCount(playerA, murder, 1);
        Assertions.assertTrue(
                playerA.getLibrary()
                        .getCards(currentGame)
                        .stream()
                        .filter(card -> tree.equals(card.getName()))
                        .noneMatch(card -> card.isBasic(currentGame)),
                tree + " should not be basic"
        );
    }
}
