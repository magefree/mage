package org.mage.test.cards.abilities.other;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9
 */
public class SoulConduitTest extends CardTestPlayerBase {

    /*    
    Soul Conduit {6}
    Artifact
    {6}, {T}: Two target players exchange life totals.
    */
    private final String soulConduit = "Soul Conduit";
    
    /*
    * Reported bug issue #3414: Soul Conduit effect does not do anything to the life totals.
    */
    @Test
    public void basicTwoPlayerGame_SoulConduitEffect() {
        
        /*
        Live Fast {2}{B}
        Sorcery
        You draw two cards, lose 2 life, and get two energy counters.
        */
        String liveFast = "Live Fast";
        
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 9);
        addCard(Zone.BATTLEFIELD, playerA, soulConduit);
        addCard(Zone.HAND, playerA, liveFast);

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, liveFast); // to force losing two life
        activateAbility(1, PhaseStep.BEGIN_COMBAT, playerA, "{6}");
        addTarget(playerA, playerA);
        addTarget(playerA, playerB);
        
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();
        
        assertGraveyardCount(playerA, liveFast, 1);
        assertCounterCount(playerA, CounterType.ENERGY, 2); // from Live Fast
        assertCounterCount(playerB, CounterType.ENERGY, 0);
        assertTapped(soulConduit, true);
        assertLife(playerA, 20);
        assertLife(playerB, 18);
    }
}
