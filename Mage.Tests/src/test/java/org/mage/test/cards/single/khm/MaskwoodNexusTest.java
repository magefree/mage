package org.mage.test.cards.single.khm;

import mage.abilities.keyword.HexproofAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.m.MaskwoodNexus Maskwood Nexus}
 * {4}
 * Artifact
 * Creatures you control are every creature type. The same is true for creature spells you control and creature cards you own that aren't on the battlefield.
 * {3}, {T}: Create a 2/2 blue Shapeshifter creature token with changeling.
 *
 * @author grimreap142
 */

public class MaskwoodNexusTest extends CardTestPlayerBase {

    private static final String maskwood = "Maskwood Nexus";
    private static final String magda = "Magda, Brazen Outlaw";
    private static final String pMonk = "Pinnacle Monk";
    private static final String jadeOrb = "Jade Orb of Dragonkind";

    @Test
    public void magdaSearch() {
        addCard(Zone.BATTLEFIELD, playerA, magda);
        addCard(Zone.LIBRARY, playerA, "Sol Ring");
        addCard(Zone.BATTLEFIELD, playerA, maskwood);
        addCard(Zone.LIBRARY, playerA, pMonk);
        // create 5 treasures
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Treasure Vault", 1);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}{X}, {T}, Sacrifice");
        setChoice(playerA, "X=5");

        // activate Magda and select Pinnacle Monk
        setChoice(playerA, "Treasure Token", 5);
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Sacrifice five Treasures:");
        addTarget(playerA, pMonk);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, pMonk, 1);
        assertPermanentCount(playerA, magda, 1);

    }

    @Test
    public void maskwoodAndJadeOrbCounterTest() {
        addCard(Zone.HAND, playerA, pMonk);
        addCard(Zone.BATTLEFIELD, playerA, maskwood, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, jadeOrb);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, pMonk);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertTapped(jadeOrb, true);
        assertCounterCount(pMonk, CounterType.P1P1, 1);
        assertAbility(playerA, pMonk, HexproofAbility.getInstance(), true);
    }

}
