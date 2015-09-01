package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class CryptoplasmTest extends CardTestPlayerBase {

    @Test
    public void testTransform() {
        addCard(Zone.BATTLEFIELD, playerA, "Cryptoplasm", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Craw Wurm", 1);

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Craw Wurm", 2);
    }

    /**
     * I have a Cryptoplasm in play, currently copying a Sigiled Paladin, and I
     * enchant it with a Followed Footsteps. Next turn the aura triggers (the
     * Crypto is still copying the same creature) and places a token on the
     * battlefield, except the token is an untransformed Cryptoplasm, when it
     * should be a Sigiled Paladin with Cryptoplasm's ability (as per rule
     * 706.3), since that's what the enchanted creature currently is.
     *
     * 6/1/2011 If another creature becomes a copy of Cryptoplasm, it will
     * become a copy of whatever Cryptoplasm is currently copying (if anything),
     * plus it will have Cryptoplasm's triggered ability.
     */
    @Test
    public void testFollowedFootsteps() {
        // First strike
        // Exalted (Whenever a creature you control attacks alone, that creature gets +1/+1 until end of turn.)
        addCard(Zone.BATTLEFIELD, playerA, "Sigiled Paladin", 1); //  {W}{W}

        addCard(Zone.BATTLEFIELD, playerB, "Island", 5);
        // Enchant creature
        // At the beginning of your upkeep, put a token that's a copy of enchanted creature onto the battlefield.
        addCard(Zone.HAND, playerB, "Followed Footsteps", 1); // {3}{U}{U}
        // At the beginning of your upkeep, you may have Cryptoplasm become a copy of another target creature. If you do, Cryptoplasm gains this ability.
        addCard(Zone.BATTLEFIELD, playerB, "Cryptoplasm", 1); // {1}{U}{U}

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Followed Footsteps");
        addTarget(playerB, "Sigiled Paladin[only copy]");

        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Followed Footsteps", 1);
        assertPermanentCount(playerB, "Cryptoplasm", 0);
        assertPermanentCount(playerB, "Sigiled Paladin", 2);
    }
}
