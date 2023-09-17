package org.mage.test.cards.single.vow;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class BrineComberTest extends CardTestPlayerBase {

    /**
     * Brine Comber
     * {1}{W}{U}
     * Creature — Spirit
     *
     * Whenever Brine Comber enters the battlefield or becomes the target of an Aura spell, create a 1/1 white Spirit creature token with flying.
     *
     * Disturb {W}{U} (You may cast this card from your graveyard transformed for its disturb cost.)
     */
    private static final String comber = "Brine Comber";

    @Test
    public void test_Aura() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, comber);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.HAND, playerA, "Rancor"); // Aura for {G}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rancor", comber);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Spirit Token", 1);
        assertPermanentCount(playerA, "Rancor", 1);
        assertPowerToughness(playerA, comber, 1+2, 1+0);
    }

    @Test
    public void test_DisturbAura() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, comber);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.GRAVEYARD, playerA, "Lantern Bearer"); // Disturb for {2}{U}, back face being an aura.

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lanterns' Lift using Disturb", comber);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Spirit Token", 1);
        assertPermanentCount(playerA, "Lanterns' Lift", 1);
        assertPowerToughness(playerA, comber, 1+1, 1+1);
    }

    @Test
    public void test_BestowAura() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, comber);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, "Hopeful Eidolon"); // Bestow for {3}{W}.

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Hopeful Eidolon using bestow", comber);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Spirit Token", 1);
        assertPermanentCount(playerA, "Hopeful Eidolon", 1);
        assertPowerToughness(playerA, comber, 1+1, 1+1);
    }
}
