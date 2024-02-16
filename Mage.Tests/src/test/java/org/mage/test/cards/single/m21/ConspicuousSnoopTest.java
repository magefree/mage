package org.mage.test.cards.single.m21;

import mage.abilities.ActivatedAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ConspicuousSnoopTest extends CardTestPlayerBase {

    @Test
    public void test_TopCardLibraryRevealed() {
        // Play with the top card of your library revealed.
        addCard(Zone.BATTLEFIELD, playerA, "Conspicuous Snoop");
        addCard(Zone.LIBRARY, playerA, "Swamp");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertTopCardRevealed(playerA, true);
    }

    @Test
    public void test_castGoblinSpellsFromLibrary() {
        skipInitShuffling();
        removeAllCardsFromLibrary(playerA);

        // You may cast Goblin spells from the top of your library.
        addCard(Zone.BATTLEFIELD, playerA, "Conspicuous Snoop");

        addCard(Zone.LIBRARY, playerA, "Atog", 1); // second from top
        addCard(Zone.LIBRARY, playerA, "Goblin Lackey", 1); // first from top
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2 * 2);

        // Whenever Goblin Lackey deals damage to a player, you may put a Goblin permanent card from your hand onto the battlefield.
        checkPlayableAbility("can play goblin", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Goblin Lackey", true);
        checkPlayableAbility("can't play non goblin before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Atog", false);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Goblin Lackey");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("can't play non goblin after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Atog", false);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Goblin Lackey", 1);
    }

    @Test
    public void test_hasActivatedAbilities() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        // Play with the top card of your library revealed.
        // You may cast Goblin spells from the top of your library.
        // As long as the top card of your library is a Goblin card, Conspicuous Snoop has all activated abilities of that card.
        addCard(Zone.BATTLEFIELD, playerA, "Conspicuous Snoop");
        // {R}: Goblin Balloon Brigade gains flying until end of turn.        
        addCard(Zone.LIBRARY, playerA, "Goblin Balloon Brigade");
        skipInitShuffling();

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);

        setStrictChooseMode(true);
        execute();

        assertAbilityCount(playerA, "Conspicuous Snoop", ActivatedAbility.class, 3); // (2 X casts + gains flying )
    }
}
