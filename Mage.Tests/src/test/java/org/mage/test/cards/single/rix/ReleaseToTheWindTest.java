package org.mage.test.cards.single.rix;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class ReleaseToTheWindTest extends CardTestPlayerBase {

    @Test
    public void test_Exile_PermanentCard() {
        // Exile target nonland permanent. For as long as that card remains exiled, its owner may cast it without paying its mana cost.
        addCard(Zone.HAND, playerA, "Release to the Wind"); // {2}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1);

        // exile
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Release to the Wind", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkExileCount("after exile", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Grizzly Bears", 1);
        checkPlayableAbility("after exile - non owner can't play 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Grizzly Bears", false);

        // owner can play
        checkPlayableAbility("after exile - non owner can't play 2", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Grizzly Bears", false);
        checkPlayableAbility("after exile - owner can play", 2, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Grizzly Bears", true);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Grizzly Bears", 1);
    }

    @Test
    public void test_Exile_ModalDoubleFacedCard() {
        // Exile target nonland permanent. For as long as that card remains exiled, its owner may cast it without paying its mana cost.
        addCard(Zone.HAND, playerA, "Release to the Wind"); // {2}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        //
        // Akoum Warrior {5}{R} - creature
        // Akoum Teeth - land
        addCard(Zone.HAND, playerA, "Akoum Warrior");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);

        // prepare mdf
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}", 6);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkPermanentCount("prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 1);

        // exile mdf creature
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Release to the Wind", "Akoum Warrior");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkExileCount("after exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior", 1);

        // you can cast mdf, but can't play a land
        checkPlayableAbility("after exile - can play mdf creature", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Akoum Warrior", true);
        checkPlayableAbility("after exile - can't play mdf land", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Akoum Teeth", false);

        // cast mdf again for free
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Akoum Warrior");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Akoum Warrior", 1);
    }

    @Test
    public void test_Exile_Adventure() {
        setStrictChooseMode(true);
        skipInitShuffling();

        // Exile target nonland permanent. For as long as that card remains exiled, its owner may cast it without paying its mana cost.
        addCard(Zone.HAND, playerA, "Release to the Wind"); // {2}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        // Bramble Familiar {1}{G}
        // Creature — Elemental Raccoon
        // 2/2
        // {T}: Add {G}.
        // {1}{G}, {T}, Discard a card: Return Bramble Familiar to its owner's hand.
        // Fetch Quest {5}{G}{G}
        // Sorcery — Adventure
        // Mill seven cards. Then put a creature, enchantment, or land card from among the milled cards onto the battlefield.
        addCard(Zone.HAND, playerA, "Bramble Familiar");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.LIBRARY, playerA, "Relentless Rats", 7);

        // cast Bramble Familiar
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bramble Familiar");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkPermanentCount("prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bramble Familiar", 1);

        // exile adventure creature
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Release to the Wind", "Bramble Familiar");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkExileCount("after exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bramble Familiar", 1);

        // you can cast both sides
        checkPlayableAbility("after exile - can play creature", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Bramble Familiar", true);
        checkPlayableAbility("after exile - can play adventure side", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Fetch Quest", true);

        // cast adventure side for free
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fetch Quest");
        setChoice(playerA, "Relentless Rats");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, "Bramble Familiar", 1); // On an adventure
        assertPermanentCount(playerA, "Relentless Rats", 1);
        assertGraveyardCount(playerA, "Relentless Rats", 6);
    }

    @Test
    public void test_Exile_MisthollowGriffin_PlayWithMana() {
        setStrictChooseMode(true);
        skipInitShuffling();

        // Exile target nonland permanent. For as long as that card remains exiled, its owner may cast it without paying its mana cost.
        addCard(Zone.HAND, playerA, "Release to the Wind"); // {2}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3 + 4);
        // Misthollow Griffin {2}{U}{U}
        // Creature — Griffin
        // Flying
        // You may cast Misthollow Griffin from exile.
        // 3/3
        addCard(Zone.BATTLEFIELD, playerA, "Misthollow Griffin");

        // exile Griffin
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Release to the Wind", "Misthollow Griffin");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkExileCount("after exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Misthollow Griffin", 1);

        // cast using Misthollow Griffin alternative cost (so paying mana.)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Misthollow Griffin");
        setChoice(playerA, "Misthollow Griffin"); // Choose the alternative cast.

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Misthollow Griffin", 1);
        assertTappedCount("Island", true, 3 + 4);
    }

    @Test
    public void test_Exile_MisthollowGriffin_PlayWithoutPayingManacost() {
        setStrictChooseMode(true);
        skipInitShuffling();

        // Exile target nonland permanent. For as long as that card remains exiled, its owner may cast it without paying its mana cost.
        addCard(Zone.HAND, playerA, "Release to the Wind"); // {2}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3 + 4);
        // Misthollow Griffin {2}{U}{U}
        // Creature — Griffin
        // Flying
        // You may cast Misthollow Griffin from exile.
        // 3/3
        addCard(Zone.BATTLEFIELD, playerA, "Misthollow Griffin");

        // exile Griffin
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Release to the Wind", "Misthollow Griffin");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkExileCount("after exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Misthollow Griffin", 1);

        // cast using Misthollow Griffin alternative cost (so not paying mana.)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Misthollow Griffin");
        setChoice(playerA, "Release to the Wind"); // Choose the alternative cast.

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Misthollow Griffin", 1);
        assertTappedCount("Island", true, 3);
    }
}
