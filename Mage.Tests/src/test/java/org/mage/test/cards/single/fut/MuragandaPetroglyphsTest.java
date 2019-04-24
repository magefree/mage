package org.mage.test.cards.single.fut;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * This test is based on rulings of the card Muraganda Petroglyphs in magic Gatherer site
 * (http://gatherer.wizards.com/Pages/Card/Details.aspx?multiverseid=145110), accessed in
 * 08/01/2017.
 *
 * @author alexsandro.
 */
public class MuragandaPetroglyphsTest extends CardTestPlayerBase {


    /**
     * Muraganda Petroglyphs gives a bonus only to creatures that have no rules text at all.
     * This includes true vanilla creatures (such as Grizzly Bears), face-down creatures,
     * many tokens, and creatures that have lost their abilities (due to Ovinize, for example).
     * Any ability of any kind, whether or not the ability functions in the on the battlefield zone,
     * including things like “Cycling 2” means the creature doesn't get the bonus.
     */
    @Test
    public void trueVanillaCardsTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Muraganda Petroglyphs", 1);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Grizzly Bears", 4, 4, Filter.ComparisonScope.Any);
        assertPowerToughness(playerB, "Grizzly Bears", 4, 4, Filter.ComparisonScope.Any);

    }

    @Test
    public void faceDownCreaturesTest() {
        addCard(Zone.HAND, playerA, "Pine Walker");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Muraganda Petroglyphs", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pine Walker");
        setChoice(playerA, "Yes"); // cast it face down as 2/2 creature

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "", 1);
        assertPowerToughness(playerA, "", 4, 4);
    }

    @Test
    public void faceDownGainedAbilityTest() {
        addCard(Zone.HAND, playerA, "Pine Walker");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mass Hysteria"); // All creatures have haste.

        addCard(Zone.BATTLEFIELD, playerA, "Muraganda Petroglyphs", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pine Walker");
        setChoice(playerA, "Yes"); // cast it face down as 2/2 creature

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "", 1);
        assertPowerToughness(playerA, "", 2, 2);
    }

    @Test
    public void tokenTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Muraganda Petroglyphs", 1);
        // Put two 1/1 white Soldier creature tokens onto the battlefield.
        addCard(Zone.HAND, playerA, "Raise the Alarm"); //  Instant {1}{W}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raise the Alarm");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Soldier", 3, 3);
    }

    @Test
    public void loseAbilitiesTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Guide", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Muraganda Petroglyphs", 1);

        addCard(Zone.HAND, playerA, "Ovinize");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ovinize", "Goblin Guide");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerB, "Goblin Guide", 2, 3);
    }

    @Test
    public void CyclingAbilityTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Hundroog", 1); // Cycling {3}, 4/7
        addCard(Zone.BATTLEFIELD, playerA, "Muraganda Petroglyphs", 1);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Hundroog", 4, 7);
    }

    /**
     * Animated basic lands have mana abilities, so they won't get the bonus.
     */

    @Test
    public void animateBasicLandTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Muraganda Petroglyphs", 1);
        addCard(Zone.HAND, playerA, "Vastwood Zendikon");


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Vastwood Zendikon", "Forest");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Forest", 6, 4);

    }

    /**
     * Some Auras and Equipment grant abilities to creatures, meaning the affected creature would no longer
     * get the +2/+2 bonus. For example, Flight grants flying to the enchanted creature. Other Auras and Equipment
     * do not, meaning the affected creature would continue to get the +2/+2 bonus. For example, Dehydration states
     * something now true about the enchanted creature, but doesn't give it any abilities. Auras and Equipment that
     * grant abilities will use the words “gains” or “has,” and they'll list a keyword ability or an ability in
     * quotation marks.
     */
    @Test
    public void grantAbilitiesTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Muraganda Petroglyphs", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Runeclaw Bear", 1);
        // Enchanted creature gets +2/+0 and has trample.
        addCard(Zone.HAND, playerA, "Rancor");
        // Enchanted creature doesn't untap during itscontroller's untap step.
        addCard(Zone.HAND, playerA, "Dehydration");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA,"Rancor", "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dehydration", "Runeclaw Bear");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Grizzly Bears", 4, 2);

        assertPowerToughness(playerA, "Runeclaw Bear", 4, 4);

    }

    /**
     * Cipher grants an ability to creatures, meaning the affected creatures would no longer get the +2/+2 bonus.
     */
    @Test
    public void cipherTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Muraganda Petroglyphs", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);

        addCard(Zone.HAND, playerA, "Shadow Slice"); // {4}{B}
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shadow Slice");
        setChoice(playerA, "Grizzly Bears");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Grizzly Bears", 2, 2);
    }
}
