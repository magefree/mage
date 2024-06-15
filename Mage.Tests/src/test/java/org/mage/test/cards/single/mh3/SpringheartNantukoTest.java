package org.mage.test.cards.single.mh3;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class SpringheartNantukoTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.SpringheartNantuko Springheart Nantuko} {1}{G}
     * Bestow {1}{G}
     * Enchanted creature gets +1/+1
     * Landfall — Whenever a land enters the battlefield under your control, you may pay {1}{G} if Springheart Nantuko is attached to a creature you control. If you do, create a token that's a copy of that creature. If you didn't create a token this way, create a 1/1 green Insect creature token.
     */
    private static final String nantuko = "Springheart Nantuko";

    @Test
    public void test_NotBestowed() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, nantuko);
        addCard(Zone.HAND, playerA, "Plains");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plains");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, 3);
        assertPermanentCount(playerA, "Insect Token", 1);
    }

    @Test
    public void test_Bestow_Dontpay() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, nantuko);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, "Plains");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, nantuko + " using bestow", "Grizzly Bears", true);
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plains");
        setChoice(playerA, false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Grizzly Bears", 3, 3);
        assertPermanentCount(playerA, "Insect Token", 1);
    }

    @Test
    public void test_Bestow_Pay() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, nantuko);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, "Forest");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, nantuko + " using bestow", "Grizzly Bears", true);
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest");
        setChoice(playerA, true); // choose yes to "you may pay {1}{G}"

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 2); // 1 + 1 token
        assertPermanentCount(playerA, "Insect Token", 0);
        assertTappedCount("Forest", true, 4);
    }
}
