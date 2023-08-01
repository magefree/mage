package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author notgreat
 */
public class BackupTest extends CardTestPlayerBase {

    @Test
    public void ConclaveSledgeCaptainTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        addCard(Zone.HAND, playerA, "Conclave Sledge-Captain");
        addCard(Zone.BATTLEFIELD, playerA, "Raging Goblin"); //1/1
        addCard(Zone.BATTLEFIELD, playerB, "Memnite"); //1/1
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Conclave Sledge-Captain");
        addTarget(playerA, "Raging Goblin");
        addTarget(playerA, "Raging Goblin");
        addTarget(playerA, "Raging Goblin");//Now a 4/4

        attack(1, playerA, "Raging Goblin");
        block(1,playerB,"Memnite","Raging Goblin");

        setStrictChooseMode(false); //auto-stack triggers
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Raging Goblin", 1);
        assertPowerToughness(playerA, "Raging Goblin", 13, 13);//dealt 3 damage, so +9 counters
        assertPermanentCount(playerA, "Conclave Sledge-Captain", 1);
        assertPowerToughness(playerA, "Conclave Sledge-Captain", 4, 4);
    }
    @Test
    public void ConclaveSledgeCaptainSelfTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        addCard(Zone.HAND, playerA, "Conclave Sledge-Captain");
        addCard(Zone.BATTLEFIELD, playerA, "Fervor");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Conclave Sledge-Captain");
        //Auto-targets self 3x, now a 7/7
        attack(1, playerA, "Conclave Sledge-Captain");

        setStrictChooseMode(false); //auto-stack triggers
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertPermanentCount(playerA, "Conclave Sledge-Captain", 1);
        assertPowerToughness(playerA, "Conclave Sledge-Captain", 14, 14);
    }
    @Test
    public void ConclaveSledgeCaptainSplitTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        addCard(Zone.HAND, playerA, "Conclave Sledge-Captain");
        addCard(Zone.BATTLEFIELD, playerA, "Raging Goblin"); //1/1
        addCard(Zone.BATTLEFIELD, playerA, "Raging Cougar"); //2/2
        addCard(Zone.BATTLEFIELD, playerA, "Raging Minotaur"); //3/3

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Conclave Sledge-Captain");
        addTarget(playerA, "Raging Goblin");
        addTarget(playerA, "Raging Cougar");
        addTarget(playerA, "Raging Minotaur");

        attack(1, playerA, "Raging Goblin");
        attack(1, playerA, "Raging Cougar");
        attack(1, playerA, "Raging Minotaur");

        setStrictChooseMode(false); //auto-stack triggers
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Raging Goblin", 4, 4);
        assertPowerToughness(playerA, "Raging Cougar", 6, 6);
        assertPowerToughness(playerA, "Raging Minotaur", 8, 8);
        assertPowerToughness(playerA, "Conclave Sledge-Captain", 4, 4);
    }
    @Test
    public void MirrorShieldHopliteStrictTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Mirror-Shield Hoplite");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.HAND, playerA, "Consuming Aetherborn");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Consuming Aetherborn");
        addTarget(playerA, "Consuming Aetherborn");
        setChoice(playerA,true);
        addTarget(playerA, "Mirror-Shield Hoplite");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertPowerToughness(playerA, "Mirror-Shield Hoplite", 3, 3);
        assertPowerToughness(playerA, "Consuming Aetherborn", 3, 3);
    }
    @Test
    public void MirrorShieldHopliteTriggeredTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Mirror-Shield Hoplite");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.HAND, playerA, "Enduring Bondwarden");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, "Murder");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Enduring Bondwarden");
        addTarget(playerA, "Mirror-Shield Hoplite");
        setChoice(playerA,false); //2 +1/+1 counters
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder", "Mirror-Shield Hoplite");
        //Auto-targets Bondwarden 2x

        setStrictChooseMode(false); //auto-stack triggers
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertPowerToughness(playerA, "Enduring Bondwarden", 4, 5);
    }
    @Test
    public void MirrorShieldHopliteSourceTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Mirror-Shield Hoplite");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.HAND, playerA, "Enduring Bondwarden");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, "Murder");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Enduring Bondwarden");
        addTarget(playerA, "Mirror-Shield Hoplite");
        setChoice(playerA,true);
        addTarget(playerA, "Enduring Bondwarden");
        //The source of the copy is the same as the source of the original backup ability.
        //So the Bondwarden shouldn't gain the ability again
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder", "Enduring Bondwarden");
        addTarget(playerA, "Mirror-Shield Hoplite");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertPowerToughness(playerA, "Mirror-Shield Hoplite", 4, 4);
    }
}
