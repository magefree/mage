package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class ReturnToBattlefieldEffectsTest extends CardTestPlayerBase {

    @Test
    public void testSaffiEriksdotter() {
        // Sacrifice Saffi Eriksdotter: When target creature is put into your graveyard from the battlefield this turn, return that card to the battlefield.
        addCard(Zone.BATTLEFIELD, playerA, "Saffi Eriksdotter");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice {this}: When target creature", "Silvercoat Lion");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Saffi Eriksdotter", 1);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
    }

    /**
     * Test that the creature with a +1/+1 counter returns
     */
    @Test
    public void testMarchesatheBlackRose() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        // Whenever a creature you control with a +1/+1 counter on it dies, return that card to the battlefield under your control at the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerA, "Marchesa, the Black Rose");
        // Modular 1 (This enters the battlefield with a +1/+1 counter on it. When it dies, you may put its +1/+1 counters on target artifact creature.)
        addCard(Zone.HAND, playerA, "Arcbound Worker", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Arcbound Worker");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Arcbound Worker");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertPermanentCount(playerA, "Arcbound Worker", 1);
    }

    /**
     * Test that the creature with a +1/+1 counter does not return if the card
     * was removed from graveyard meanwhile by Relic of Progenitus
     */
    @Test
    public void testMarchesatheBlackRoseAfterExile() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        // Whenever a creature you control with a +1/+1 counter on it dies, return that card to the battlefield under your control at the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerA, "Marchesa, the Black Rose");
        // Modular 1 (This enters the battlefield with a +1/+1 counter on it. When it dies, you may put its +1/+1 counters on target artifact creature.)
        addCard(Zone.HAND, playerA, "Arcbound Worker", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);

        // {T}: Target player exiles a card from their graveyard.
        // {1}, Exile Relic of Progenitus: Exile all cards from all graveyards. Draw a card.
        addCard(Zone.BATTLEFIELD, playerB, "Relic of Progenitus", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Arcbound Worker");
        castSpell(1, PhaseStep.END_COMBAT, playerB, "Lightning Bolt", "Arcbound Worker");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "{T}: Target player exiles", playerA);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertPermanentCount(playerA, "Arcbound Worker", 0);

        assertExileCount("Arcbound Worker", 1);

    }

    /**
     * With my opponent's Deathmist Raptor return-to-battlefield trigger on the
     * stack, I exiled the Deathmist Raptor with Pharika, God of Affliction.
     * However, the Deathmist Raptor returned to the battlefield from exile,
     * though it should not have because it had changed zones so was a different
     * object.
     */
    @Test
    public void testDeathmistRaptor() {
        // Deathtouch
        // Whenever a permanent you control is turned face up, you may return Deathmist Raptor from your graveyard to the battlefield face up or face down.
        // Megamorph {4}{G}
        addCard(Zone.GRAVEYARD, playerA, "Deathmist Raptor");
        addCard(Zone.HAND, playerA, "Pine Walker");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);

        addCard(Zone.BATTLEFIELD, playerB, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);
        // {B}{G}: Exile target creature card from a graveyard. It's owner puts a 1/1 black and green Snake enchantment creature token with deathtouch onto the battlefield.
        addCard(Zone.BATTLEFIELD, playerB, "Pharika, God of Affliction", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pine Walker");
        setChoice(playerA, true); // cast it face down as 2/2 creature

        activateAbility(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "{4}{G}: Turn this face-down permanent face up.");
        activateAbility(3, PhaseStep.POSTCOMBAT_MAIN, playerB, "{B}{G}: Exile target creature card from a graveyard",
                "Deathmist Raptor", "Whenever a permanent you control is turned face up");

        setStopAt(3, PhaseStep.END_TURN);

        execute();
        assertPermanentCount(playerB, "Pharika, God of Affliction", 1);
        assertPermanentCount(playerA, "Snake Token", 1);
        assertPermanentCount(playerA, "Pine Walker", 1);

        assertExileCount("Deathmist Raptor", 1);

    }

    /**
     * Reassembling Skeleton is blocked by Necroskitter, and dies. Necroskitter
     * triggers. With the trigger on the stack, activate Skeleton and return it
     * to play. Expected result: trigger can't find Skeleton since it changed
     * zones. Actual result: trigger steals control of Skeleton when it's
     * already on the battlefield.
     */
    @Test
    public void testNecroskitter1() {
        // Wither (This deals damage to creatures in the form of -1/-1 counters.)
        // Whenever a creature an opponent controls with a -1/-1 counter on it dies, you may return that card to the battlefield under your control.
        addCard(Zone.BATTLEFIELD, playerA, "Necroskitter", 1); //  1/4

        // {1}{B}: Return Reassembling Skeleton from your graveyard to the battlefield tapped.
        addCard(Zone.BATTLEFIELD, playerB, "Reassembling Skeleton");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);

        attack(2, playerB, "Reassembling Skeleton");
        block(2, playerA, "Necroskitter", "Reassembling Skeleton");

        activateAbility(2, PhaseStep.COMBAT_DAMAGE, playerB, "{1}{B}: Return", TestPlayer.NO_TARGET, "Whenever a creature");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);

        execute();

        assertLife(playerA, 20);
        assertPermanentCount(playerB, "Reassembling Skeleton", 1);

    }
}
