package org.mage.test.cards.single.m21;

import mage.abilities.ActivatedAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ConspicuousSnoopTest extends CardTestPlayerBase {

    @Test
    public void testTopCardLibraryRevealed(){
        // Play with the top card of your library revealed.
        addCard(Zone.BATTLEFIELD, playerA, "Conspicuous Snoop");
        addCard(Zone.LIBRARY, playerA, "Swamp");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();

        assertTopCardRevealed(playerA, true);
    }

    @Test
    public void castGoblinSpellsFromLibrary(){
        // You may cast Goblin spells from the top of your library.
        addCard(Zone.BATTLEFIELD, playerA, "Conspicuous Snoop");
        addCard(Zone.LIBRARY, playerA, "Goblin Lackey");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Goblin Lackey");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();
        assertPermanentCount(playerA, "Goblin Lackey", 1);

    }

    @Test
    public void hasActivatedAbilities(){
        // As long as the top card of your library is a Goblin card,
        // Conspicuous Snoop has all activated abilities of that card.
        addCard(Zone.BATTLEFIELD, playerA, "Conspicuous Snoop");
        addCard(Zone.LIBRARY, playerA, "Goblin Balloon Brigade");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);


        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();
        assertAbilityCount(playerA, "Conspicuous Snoop", ActivatedAbility.class,5);

    }
}
