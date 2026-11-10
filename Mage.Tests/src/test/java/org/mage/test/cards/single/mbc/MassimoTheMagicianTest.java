package org.mage.test.cards.single.mbc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author alexchet
 */
public class MassimoTheMagicianTest extends CardTestPlayerBase {

    /*
    Massimo, the Magician
    {U}{R}{W}
    Legendary Creature - Cat Wizard
    Whenever Massimo or another creature you control enters, exile up to one target instant or sorcery card
    with mana value 1 from your graveyard. If a card is exiled this way, that creature gains "Whenever this
    creature deals combat damage to a player, copy the exiled card. You may cast the copy without paying its
    mana cost."
    3/2
    */
    private static final String massimo = "Massimo, the Magician";
    private static final String bolt = "Lightning Bolt"; // {R} instant, 3 damage to any target
    private static final String bears = "Grizzly Bears"; // 2/2

    @Test
    public void testMassimoEnters() {
        addCard(Zone.HAND, playerA, massimo);
        addCard(Zone.GRAVEYARD, playerA, bolt);
        addCard(Zone.BATTLEFIELD, playerA, "Plateau"); // R/W
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, massimo);
        addTarget(playerA, bolt);

        attack(3, playerA, massimo);
        setChoice(playerA, true); // cast the copy
        addTarget(playerA, playerB);

        setStopAt(3, PhaseStep.END_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertExileCount(playerA, bolt, 1);
        assertLife(playerB, 20 - 3 - 3);
        assertPermanentCount(playerA, massimo, 1);
    }

    @Test
    public void testAnotherCreatureEntersGainsAbility() {
        addCard(Zone.BATTLEFIELD, playerA, massimo);
        addCard(Zone.HAND, playerA, bears);
        addCard(Zone.GRAVEYARD, playerA, bolt);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bears);
        addTarget(playerA, bolt);

        // only the bears got the ability; Massimo connecting does nothing extra
        attack(3, playerA, massimo);
        attack(3, playerA, bears);
        setChoice(playerA, true); // cast the copy
        addTarget(playerA, playerB);

        setStopAt(3, PhaseStep.END_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertExileCount(playerA, bolt, 1);
        assertLife(playerB, 20 - 3 - 2 - 3);
    }

    @Test
    public void testDeclineToCast() {
        addCard(Zone.HAND, playerA, massimo);
        addCard(Zone.GRAVEYARD, playerA, bolt);
        addCard(Zone.BATTLEFIELD, playerA, "Plateau");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, massimo);
        addTarget(playerA, bolt);

        attack(3, playerA, massimo);
        setChoice(playerA, false); // don't cast the copy

        setStopAt(3, PhaseStep.END_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertExileCount(playerA, bolt, 1);
        assertLife(playerB, 20 - 3);
    }

    @Test
    public void testOnlyManaValueOneIsTargetable() {
        addCard(Zone.BATTLEFIELD, playerA, massimo);
        addCard(Zone.HAND, playerA, bears);
        addCard(Zone.GRAVEYARD, playerA, "Divination"); // {2}{U} sorcery
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bears);
        addTarget(playerA, TestPlayer.TARGET_SKIP); // no legal targets, "up to one" so nothing is exiled

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, "Divination", 1);
        assertExileCount(playerA, "Divination", 0);
    }
}
