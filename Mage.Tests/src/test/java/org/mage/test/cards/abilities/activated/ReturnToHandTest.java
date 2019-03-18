
package org.mage.test.cards.abilities.activated;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ReturnToHandTest extends CardTestPlayerBase {

    /**
     * Tests to put a token onto the battlefield
     */
    @Test
    public void SkarrganFirebirdTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Pillarfield Ox");
        // Bloodthirst 3
        // Flying
        // {R}{R}{R}: Return Skarrgan Firebird from your graveyard to your hand. Activate this ability only if an opponent was dealt damage this turn.
        addCard(Zone.BATTLEFIELD, playerB, "Skarrgan Firebird");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);

        // As an additional cost to cast Bone Splinters, sacrifice a creature.
        // Destroy target creature.
        addCard(Zone.HAND, playerB, "Bone Splinters");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Bone Splinters", "Pillarfield Ox");
        setChoice(playerB, "Skarrgan Firebird");

        attack(2, playerB, "Silvercoat Lion");

        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "{R}{R}{R}: Return");
        setStopAt(2, PhaseStep.END_TURN);

        execute();

        assertPermanentCount(playerA, "Skarrgan Firebird", 0);
        assertGraveyardCount(playerA, "Pillarfield Ox", 1);
        assertGraveyardCount(playerB, "Bone Splinters", 1);
        assertHandCount(playerB, "Skarrgan Firebird", 1);

    }

    /**
     * Return from graveyard to hand if you play a swamp
     */
    @Test
    public void VeilbornGhoulTest1() {
        // Veilborn Ghoul can't block.
        // Whenever a Swamp enters the battlefield under your control, you may return Veilborn Ghoul from your graveyard to your hand.
        addCard(Zone.GRAVEYARD, playerA, "Veilborn Ghoul");
        addCard(Zone.HAND, playerA, "Swamp");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Swamp");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        execute();

        assertPermanentCount(playerA, "Swamp", 1);
        assertHandCount(playerA, "Veilborn Ghoul", 1);

    }

    /**
     * Return from graveyard to hand if you play a non swamp land but Urborg,
     * Tomb of Yawgmoth is in play
     */
    @Test
    public void VeilbornGhoulTest2() {
        // Veilborn Ghoul can't block.
        // Whenever a Swamp enters the battlefield under your control, you may return Veilborn Ghoul from your graveyard to your hand.
        addCard(Zone.GRAVEYARD, playerA, "Veilborn Ghoul");
        addCard(Zone.HAND, playerA, "Flood Plain");

        // Each land is a Swamp in addition to its other land types.
        addCard(Zone.BATTLEFIELD, playerA, "Urborg, Tomb of Yawgmoth", 1);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flood Plain");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        execute();

        assertPermanentCount(playerA, "Flood Plain", 1);
        assertHandCount(playerA, "Veilborn Ghoul", 1);

    }

    /**
     * Return a spell from stack to Hand
     */
    @Test
    public void BrutalExpulsionTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        // Devoid
        // Choose one or both
        // - Return target spell or creature to its owner's hand;
        // - Brutal Expulsion deals 2 damage to target creature or planeswalker. If that permanent would be put into a graveyard this turn, exile it instead.
        addCard(Zone.HAND, playerA, "Brutal Expulsion"); // {2}{U}{R}

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 4);
        addCard(Zone.HAND, playerB, "Pillarfield Ox", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Pillarfield Ox");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Brutal Expulsion", "mode=1Pillarfield Ox^mode=2Silvercoat Lion", "Pillarfield Ox");
        setModeChoice(playerA, "1");
        setModeChoice(playerA, "2");
        setStopAt(2, PhaseStep.BEGIN_COMBAT);

        execute();

        assertGraveyardCount(playerA, "Brutal Expulsion", 1);
        assertExileCount("Silvercoat Lion", 1);
        assertPermanentCount(playerB, "Pillarfield Ox", 0);
        assertHandCount(playerB, "Pillarfield Ox", 1);

    }
}
