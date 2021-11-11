
package org.mage.test.cards.abilities.equipped;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class SwordOfTheMeekTest extends CardTestPlayerBase {

    /**
     * Played a game vs. the AI when I noticed the following:
     *
     * Had Master of Etherium and Myrsmith in play, Sword of the Meek in the
     * graveyard. I cast an artifact spell, Myrsmith triggers. I pay {1} to get
     * a Myr token which enters the battlefield as a 2/2 (due to Master of
     * Etherium), but Sword of the Meek triggers regardless and I can have it
     * enter the battlefield attached to the token. Also, Myrsmith's trigger
     * wasn't optional, it didn't ask whether I wanted to pay or not.
     */
    @Test
    public void testEquipAlive() {
        // Master of Etherium's power and toughness are each equal to the number of artifacts you control.
        // Other artifact creatures you control get +1/+1.
        addCard(Zone.BATTLEFIELD, playerA, "Master of Etherium", 1); // Creature 1/1

        // Whenever you cast an artifact spell, you may pay . If you do, put a 1/1 colorless Myr artifact creature token onto the battlefield.
        addCard(Zone.BATTLEFIELD, playerA, "Myrsmith", 1); // Creature 1/1

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Chromatic Star");

        // Equipped creature gets +1/+2.
        // Equip {2}
        // Whenever a 1/1 creature enters the battlefield under your control, you may return Sword of the Meek from your graveyard to the battlefield, then attach it to that creature.
        addCard(Zone.GRAVEYARD, playerA, "Sword of the Meek");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chromatic Star");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Chromatic Star", 1);

        assertPermanentCount(playerA, "Myr Token", 1);
        assertPowerToughness(playerA, "Myr Token", 2, 2);
        assertPermanentCount(playerA, "Sword of the Meek", 0);
        assertPowerToughness(playerA, "Master of Etherium", 3, 3);

        Permanent myr = getPermanent("Myr Token", playerA.getId());
        Assert.assertTrue("Myr may not have any attachments", myr.getAttachments().isEmpty());
    }

}
