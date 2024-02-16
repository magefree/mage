package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AbilityOwnershipTest extends CardTestPlayerBase {

    @Test
    public void testOwned() {
        // When Soul Snuffers enters the battlefield, put a -1/-1 counter on each creature.
        addCard(Zone.GRAVEYARD, playerB, "Soul Snuffers");
        //
        // Exploit (When this creature enters the battlefield, you may sacrifice a creature.)
        // When Minister of Pain exploits a creature, creatures your opponents control get -1/-1 until end of turn.
        addCard(Zone.GRAVEYARD, playerB, "Minister of Pain");

        // Put all creature cards from all graveyards onto the battlefield under your control.
        addCard(Zone.HAND, playerA, "Rise of the Dark Realms");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 9);
        //
        // Whenever you put one or more -1/-1 counters on a creature, each opponent loses 1 life and you gain 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Obelisk Spider");

        setLife(playerA, 20);
        setLife(playerB, 20);

        // return two creatures from graveyard:
        // - Soul Snuffers must gives 1 counter to each creature (total: 3x from my creatures)
        // - Minister of Pain must gives 0 counter to opponent's creature (total: 0x, cause opponents don't have creatures and we don't use exploit)
        // - SO Obelisk Spider must triggers 3x times
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rise of the Dark Realms");
        setChoice(playerA, "When "); // two triggers
        // * Ability: Soul Snuffers - EntersBattlefieldTriggeredAbility: When {this} enters the battlefield, put a -1/-1 counter on each creature.
        // * Ability: Minister of Pain - ExploitAbility: Exploit <i>(When this creature enters the battlefield, you may sacrifice a creature.)</i>
        setChoice(playerA, false); // no exploit
        // 3x life triggers
        setChoice(playerA, "Whenever you put one or more");
        setChoice(playerA, "Whenever you put one or more");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        // Obelisk Spider Triggers 3x (Soul Snuffers puts 3 counters on 3 creatures)
        assertLife(playerA, 20 + 3);
        assertLife(playerB, 20 - 3);
    }

    @Test
    public void testToGraveyard() {
        addCard(Zone.GRAVEYARD, playerB, "Soul Snuffers");
        addCard(Zone.GRAVEYARD, playerB, "Minister of Pain");
        // Whenever you put one or more -1/-1 counters on a creature, each opponent loses 1 life and you gain 1 life.
        addCard(Zone.BATTLEFIELD, playerB, "Obelisk Spider");

        addCard(Zone.HAND, playerA, "Rise of the Dark Realms");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 9);

        setLife(playerA, 20);
        setLife(playerB, 20);

        // return two creatures from graveyard:
        // - Soul Snuffers must gives 1 counter to each creature (total: 2x from my creatures)
        // - Minister of Pain must gives 0 counter to opponent's creature (total: 0x, cause opponents don't have creatures and we don't use exploit)
        // - SO Obelisk Spider must triggers 2x times, BUT it's controlled by opponent, so no triggers at all
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rise of the Dark Realms");
        setChoice(playerA, "When "); // two triggers
        setChoice(playerA, false); // no exploit

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }
}
