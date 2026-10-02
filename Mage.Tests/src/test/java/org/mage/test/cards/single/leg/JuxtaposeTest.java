package org.mage.test.cards.single.leg;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.players.Player;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.Arrays;
import java.util.List;

public class JuxtaposeTest extends CardTestPlayerBase {

    /*
    Juxtapose
    {3}{U}
    Sorcery

    You and target player exchange control of the creature you each control with the highest mana value.
    Then exchange control of artifacts the same way. If two or more permanents a player controls are tied
    for highest mana value, their controller chooses one of them.
     */
    private static final String juxtapose = "Juxtapose";

    // creatures with mana value 2
    private static final String grizzlyBears = "Grizzly Bears"; // 2/2
    private static final String watchwolf = "Watchwolf"; // 3/3
    // creatures with mana value 3
    private static final String grayOgre = "Gray Ogre"; // 2/2
    private static final String centaurCourser = "Centaur Courser"; // 3/3
    // artifacts with mana value 2
    private static final String millstone = "Millstone";
    private static final String ankhOfMishra = "Ankh of Mishra";
    // artifacts with mana value 4
    private static final String icyManipulator = "Icy Manipulator";
    private static final String jayemdaeTome = "Jayemdae Tome";

    private void prepareTiedPermanents() {
        addCard(Zone.HAND, playerA, juxtapose);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, grizzlyBears);
        addCard(Zone.BATTLEFIELD, playerA, watchwolf);
        addCard(Zone.BATTLEFIELD, playerA, millstone);
        addCard(Zone.BATTLEFIELD, playerA, ankhOfMishra);

        addCard(Zone.BATTLEFIELD, playerB, grayOgre);
        addCard(Zone.BATTLEFIELD, playerB, centaurCourser);
        addCard(Zone.BATTLEFIELD, playerB, "Llanowar Elves"); // lower mana value, must be ignored
        addCard(Zone.BATTLEFIELD, playerB, icyManipulator);
        addCard(Zone.BATTLEFIELD, playerB, jayemdaeTome);
    }

    @Test
    public void test_TiedPermanents_ControllersChoose() {
        setStrictChooseMode(true);
        prepareTiedPermanents();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, juxtapose, playerB);
        // creatures
        setChoice(playerA, watchwolf);
        setChoice(playerB, grayOgre);
        // artifacts
        setChoice(playerA, millstone);
        setChoice(playerB, jayemdaeTome);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, grizzlyBears, 1);
        assertPermanentCount(playerA, grayOgre, 1);
        assertPermanentCount(playerB, watchwolf, 1);
        assertPermanentCount(playerB, centaurCourser, 1);
        assertPermanentCount(playerB, "Llanowar Elves", 1);

        assertPermanentCount(playerA, ankhOfMishra, 1);
        assertPermanentCount(playerA, jayemdaeTome, 1);
        assertPermanentCount(playerB, millstone, 1);
        assertPermanentCount(playerB, icyManipulator, 1);
    }

    @Test
    public void test_TiedPermanents_AIChooses() {
        // AI must be able to choose among tied permanents (it used to fail on an unsupported battlefield card target)
        setStrictChooseMode(false);
        prepareTiedPermanents();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, juxtapose, playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // AI gives away its least valuable creature
        assertPermanentCount(playerA, watchwolf, 1);
        assertPermanentCount(playerA, grayOgre, 1);
        assertPermanentCount(playerB, grizzlyBears, 1);
        assertPermanentCount(playerB, centaurCourser, 1);

        // one of the tied artifacts changes sides for each player
        Assert.assertEquals(1, countControlled(playerA, millstone, ankhOfMishra));
        Assert.assertEquals(1, countControlled(playerA, icyManipulator, jayemdaeTome));
        Assert.assertEquals(1, countControlled(playerB, millstone, ankhOfMishra));
        Assert.assertEquals(1, countControlled(playerB, icyManipulator, jayemdaeTome));
    }

    private long countControlled(Player player, String... names) {
        List<String> nameList = Arrays.asList(names);
        return currentGame.getBattlefield().getAllActivePermanents(player.getId())
                .stream()
                .filter(permanent -> nameList.contains(permanent.getName()))
                .count();
    }
}
