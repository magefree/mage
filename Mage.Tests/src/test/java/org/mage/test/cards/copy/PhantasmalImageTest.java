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
    public void testCopiedEntersBattlefieldTriggeredAbility() {
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
        Assert.assertTrue(master.getAbilities().contains(IndestructibleAbility.getInstance()));

        // But copied one should not
        Assert.assertEquals("Power different", 3, masterCopied.getPower().getValue());
        Assert.assertEquals("Toughness different", 3, masterCopied.getToughness().getValue());
        Assert.assertFalse(masterCopied.getAbilities().contains(LifelinkAbility.getInstance()));
        Assert.assertFalse(masterCopied.getAbilities().contains(IndestructibleAbility.getInstance()));
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

}
