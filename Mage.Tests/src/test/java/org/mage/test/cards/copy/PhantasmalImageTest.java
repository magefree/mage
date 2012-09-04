package org.mage.test.cards.copy;

import junit.framework.Assert;
import mage.Constants;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.game.permanent.Permanent;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 *
 * Card: You may have {this} enter the battlefield as a copy of any creature on the battlefield, except it's an Illusion in addition to its other types and it gains "When this creature becomes the target of a spell or ability, sacrifice it."
 */
public class PhantasmalImageTest extends CardTestPlayerBase {

    @Test
    public void testCopyCreature() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Constants.Zone.HAND, playerA, "Phantasmal Image");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Craw Wurm");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Phantasmal Image");

        setStopAt(2, Constants.PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Craw Wurm", 1);
        assertPermanentCount(playerB, "Craw Wurm", 1);
    }

    /**
     * Tests that copy effect will copy EntersBattlefieldTriggeredAbility and it will be applied.
     */
    @Test
    public void testCopyEntersBattlefieldTriggeredAbility() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Constants.Zone.HAND, playerA, "Phantasmal Image");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Howling Banshee");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Phantasmal Image");

        setStopAt(2, Constants.PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Howling Banshee", 1);
        assertPermanentCount(playerB, "Howling Banshee", 1);

        assertLife(playerA, 17);
        assertLife(playerB, 17);
    }

    /**
     * Tests that copy won't have level up counters and will have zero level.
     */
    @Test
    public void testCopyCreatureWithLevelUpAbility() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Transcendent Master");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains", 12);

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Constants.Zone.HAND, playerB, "Phantasmal Image");


        for (int i = 0; i < 12; i++) {
            activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Level up {1}");
        }

        castSpell(2, Constants.PhaseStep.PRECOMBAT_MAIN, playerB, "Phantasmal Image");

        setStopAt(2, Constants.PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Transcendent Master", 1);
        assertPermanentCount(playerB, "Transcendent Master", 1);

        Permanent master = getPermanent("Transcendent Master", playerA.getId());
        Permanent masterCopied = getPermanent("Transcendent Master", playerB.getId());

        // Original master should be upgraded to 3rd level
        Assert.assertEquals("Power different", 9, master.getPower().getValue());
        Assert.assertEquals("Toughness different", 9, master.getToughness().getValue());
        Assert.assertTrue(master.getAbilities().contains(LifelinkAbility.getInstance()));
        Assert.assertTrue(master.getAbilities().contains(new IndestructibleAbility()));

        // But copied one should not
        Assert.assertEquals("Power different", 3, masterCopied.getPower().getValue());
        Assert.assertEquals("Toughness different", 3, masterCopied.getToughness().getValue());
        Assert.assertFalse(masterCopied.getAbilities().contains(LifelinkAbility.getInstance()));
        Assert.assertFalse(masterCopied.getAbilities().contains(new IndestructibleAbility()));
    }

    /**
     * Tests copying creature with BecomesTargetTriggeredAbility
     */
    @Test
    public void testCopyBecomesTargetTriggeredAbility() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Constants.Zone.HAND, playerA, "Phantasmal Image", 2);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Illusionary Servant");

        setChoice(playerA, "Illusionary Servant");
        setChoice(playerA, "Illusionary Servant-M12");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Phantasmal Image");
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Phantasmal Image");

        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, 0);
        assertPermanentCount(playerA, "Illusionary Servant", 3);
    }

    /**
     * Tests copying already transformed creature
     * Makes sure it still has "When this creature becomes the target of a spell or ability, sacrifice it"
     */
    @Test
    public void testCopyAlreadyTransformed() {
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Forest", 2);
        addCard(Constants.Zone.HAND, playerB, "Phantasmal Image");
        addCard(Constants.Zone.HAND, playerB, "Titanic Growth");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Huntmaster of the Fells");

        castSpell(2, Constants.PhaseStep.PRECOMBAT_MAIN, playerB, "Phantasmal Image");
        castSpell(2, Constants.PhaseStep.POSTCOMBAT_MAIN, playerB, "Titanic Growth", "Ravager of the Fells-M12");

        setStopAt(2, Constants.PhaseStep.END_TURN);
        execute();

        // check opponent's creature wasn't chosen as a target for Titanic Growth
        assertPowerToughness(playerA, "Ravager of the Fells", 4, 4);
        // check playerA's creature was sacrificed
        assertPermanentCount(playerB, "Ravager of the Fells", 0);
        assertGraveyardCount(playerB, "Phantasmal Image", 1);
    }

    /**
     * Tests that copy of Geralf's Messenger also enters tapped
     * Geralf's Messenger: Geralf's Messenger enters the battlefield tapped
     */
    @Test
    public void testCopyEntersTapped() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Constants.Zone.HAND, playerA, "Phantasmal Image");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Geralf's Messenger");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Phantasmal Image");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        Permanent copy = getPermanent("Geralf's Messenger", playerA.getId());
        Assert.assertNotNull(copy);
        Assert.assertTrue("Should be tapped", copy.isTapped());

        // Tests: When Geralf's Messenger enters the battlefield, target opponent loses 2 life.
        assertLife(playerB, 18);
    }


    /**
     * Tests that copy effect will copy AsEntersBattlefieldAbility and will choose another color.
     * As there is no permanent of the second color, copy of Lurebound Scarecrow will be sacrificed.
     */
    @Test
    public void testCopyAsEntersBattlefieldAbility() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Llanowar Elves");
        addCard(Constants.Zone.HAND, playerA, "Phantasmal Image");
        addCard(Constants.Zone.HAND, playerA, "Lurebound Scarecrow");

        setChoice(playerA, "Green");
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Lurebound Scarecrow");
        setChoice(playerA, "Red");
        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Phantasmal Image");

        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Lurebound Scarecrow", 1);
    }

    /**
     * Tests that copy effect will copy AsEntersBattlefieldAbility and will choose another color.
     * Both Lurebound Scarecrow cards should stay on battlefield.
     */
    @Test
    public void testCopyAsEntersBattlefieldAbility2() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Llanowar Elves");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Raging Goblin");
        addCard(Constants.Zone.HAND, playerA, "Phantasmal Image");
        addCard(Constants.Zone.HAND, playerA, "Lurebound Scarecrow");

        setChoice(playerA, "Green");
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Lurebound Scarecrow");
        setChoice(playerA, "Red");
        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Phantasmal Image");

        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Lurebound Scarecrow", 2);
    }

    @Test
    public void testCopiedFlyingWorks() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Constants.Zone.HAND, playerA, "Phantasmal Image");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Fervor");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Elite Vanguard");

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Azure Drake");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Llanowar Elves");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Phantasmal Image");
        attack(1, playerA, "Azure Drake");
        block(1, playerB, "Llanowar Elves", "Azure Drake");

        attack(2, playerB, "Azure Drake");
        block(2, playerA, "Elite Vanguard", "Azure Drake");

        setStopAt(2, Constants.PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 18);
        assertLife(playerA, 18);
    }

}
