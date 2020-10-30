package org.mage.test.cards.abilities.oneshot.sacrifice;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class ProtectionSacrificeTest extends CardTestPlayerBase {

    /**
     * Checks that pro black can still be sacrificed (i.e. Geth's Verdict)
     */
    @Test
    public void gethVerdictVersusProtectionFromBlack() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.HAND, playerA, "Geth's Verdict"); // {B}{B} target player sacrifices a creature and loses a life
        addCard(Zone.BATTLEFIELD, playerB, "White Knight"); // {W}{W} pro-black

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Geth's Verdict", playerB);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "White Knight", 0);
        assertLife(playerB, 19);
    }

    /*
     * NOTE: Test is failing due to bug in code.
     * Reported bug: see issue #3332.
     * Martyr's Bond fails to force sacrifice against Shroud/protection
     */
    @Test
    public void martyrBondVersusProtectionFromWhite() {

        /*
         Martyr's Bond {4}{W}{W}
        Enchantment
        Whenever Martyr's Bond or another nonland permanent you control is put into a graveyard from the battlefield, each opponent sacrifices a permanent that shares a card type with it.
         */
        String mBond = "Martyr's Bond";
        String bGnomes = "Bottle Gnomes"; // {3} 1/3 sac: gain 3 life
        String sbDragon = "Stormbreath Dragon"; // {3}{R}{R} 4/4 Flying haste pro-white

        addCard(Zone.BATTLEFIELD, playerA, mBond);
        addCard(Zone.BATTLEFIELD, playerA, bGnomes);
        addCard(Zone.BATTLEFIELD, playerB, sbDragon);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice"); // sac gnomes to trigger bond and force dragon to be sac'd
        //setChoice(playerB, sbDragon);
        //addTarget(playerB, sbDragon);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 23);
        assertLife(playerB, 20);
        assertGraveyardCount(playerA, bGnomes, 1);
        assertPermanentCount(playerA, mBond,1);
        assertPermanentCount(playerB, sbDragon, 0);
        assertGraveyardCount(playerB, sbDragon, 1);
    }

    /*
    NOTE: Test is failing due to bug in code.
 * Reported bug: see issue #3332
 * Martyr's Bond fails to force sacrifice against Shroud/protection
 */
    @Test
    public void martyrBondVersusShroud() {

        /*
         Martyr's Bond {4}{W}{W}
        Enchantment
        Whenever Martyr's Bond or another nonland permanent you control is put into a graveyard from the battlefield, each opponent sacrifices a permanent that shares a card type with it.
         */
        String mBond = "Martyr's Bond";
        String sTribeElder = "Sakura-Tribe Elder"; // {1}{G} 1/1 Sacrifice himself: get basic tapped into play
        String sbDragon = "Stormbreath Dragon"; // {3}{R}{R} 4/4 Flying haste pro-white
        String wCloak = "Whispersilk Cloak"; // {3} Equip {2} : Equipped creature can't be blocked and has shroud.

        addCard(Zone.BATTLEFIELD, playerA, mBond);
        addCard(Zone.BATTLEFIELD, playerA, sTribeElder);
        addCard(Zone.LIBRARY, playerA, "Forest", 10);
        addCard(Zone.BATTLEFIELD, playerB, sbDragon);
        addCard(Zone.BATTLEFIELD, playerB, wCloak);
        addCard(Zone.BATTLEFIELD, playerB, "Wastes", 2);

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Equip", sbDragon); // gives Dragon shroud
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice"); // sac elder
        //setChoice(playerB, sbDragon);
        //addTarget(playerB, sbDragon);

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, sTribeElder, 1);
        assertPermanentCount(playerA, mBond,1);
        assertPermanentCount(playerB, wCloak, 1);
        assertPermanentCount(playerB, sbDragon, 0);
        assertGraveyardCount(playerB, sbDragon, 1);
    }
}
