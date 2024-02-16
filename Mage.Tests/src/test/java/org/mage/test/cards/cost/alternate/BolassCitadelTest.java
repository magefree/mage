package org.mage.test.cards.cost.alternate;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class BolassCitadelTest extends CardTestPlayerBase {

    @Test
    public void testCastEagerCadet() {
        /*
         * Eager Cadet
         * Creature — Human Soldier
         * 1/1
         */
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Bolas's Citadel");
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Eager Cadet");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Eager Cadet");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Eager Cadet", 1);
        assertGraveyardCount(playerA, 0);
        assertLife(playerA, 19);
    }

    @Test
    public void testCastAdventure() {
        /*
         * Curious Pair {1}{G}
         * Creature — Human Peasant
         * 1/3
         * ----
         * Treats to Share {G}
         * Sorcery — Adventure
         * Create a Food token.
         */
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Bolas's Citadel");
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Curious Pair");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treats to Share");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, "Food Token", 1);
        assertExileCount(playerA, "Curious Pair", 1);
        assertGraveyardCount(playerA, 0);
        assertLife(playerA, 19);
    }

    @Test
    public void testArtifactCast() {
        removeAllCardsFromLibrary(playerA);

        addCard(Zone.BATTLEFIELD, playerA, "Bolas's Citadel");
        addCard(Zone.LIBRARY, playerA, "Fellwar Stone");

        // cast from top library for 2 life
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fellwar Stone");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Fellwar Stone", 1);
        assertLife(playerA, 20 - 2);
    }

    @Test
    public void testCardWithCycling() {
        // bug: can't cast cards with cycling, see https://github.com/magefree/mage/issues/6215
        // bug active only in GUI (HumanPlayer)
        removeAllCardsFromLibrary(playerA);

        // You may play the top card of your library. If you cast a spell this way, pay life equal to its converted mana cost rather than pay its mana cost.
        addCard(Zone.BATTLEFIELD, playerA, "Bolas's Citadel");
        // Cycling {2} ({2}, Discard this card: Draw a card.)
        addCard(Zone.LIBRARY, playerA, "Archfiend of Ifnir"); // {3}{B}{B}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Archfiend of Ifnir");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Archfiend of Ifnir", 1);
        assertLife(playerA, 20 - 5);
    }

    @Test
    public void testCardWithAdventure() {
        removeAllCardsFromLibrary(playerA);

        // You may play the top card of your library. If you cast a spell this way, pay life equal to its converted mana cost rather than pay its mana cost.
        addCard(Zone.BATTLEFIELD, playerA, "Bolas's Citadel");
        // Adventure spell: Chop Down {2}{W}  - Destroy target creature with power 4 or greater.
        addCard(Zone.LIBRARY, playerA, "Giant Killer"); // {W}
        addCard(Zone.BATTLEFIELD, playerA, "Ferocious Zheng"); // 4/4
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        // cast adventure
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chop Down", "Ferocious Zheng");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("must kill", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Ferocious Zheng", 0);
        checkExileCount("on adventure", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Giant Killer", 1);

        // cast normal
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Giant Killer");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Giant Killer", 1);
        assertGraveyardCount(playerA, "Ferocious Zheng", 1);
        assertLife(playerA, 20 - 3);
    }

    @Test
    public void testOpponentCantUseMyBolas() {
        // https://github.com/magefree/mage/issues/6741
        removeAllCardsFromLibrary(playerA);
        removeAllCardsFromLibrary(playerB);

        // You may play the top card of your library. If you cast a spell this way, pay life equal to its converted mana cost rather than pay its mana cost.
        addCard(Zone.BATTLEFIELD, playerA, "Bolas's Citadel");
        //
        addCard(Zone.LIBRARY, playerA, "Balduvian Bears", 2); // {1}{G}
        addCard(Zone.LIBRARY, playerB, "Grizzly Bears", 2); // {1}{G}

        // 1 turn
        checkPlayableAbility("A can use bolas on 1", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Balduvian Bears", true);
        checkPlayableAbility("B cant use bolas on 1", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Grizzly Bears", false);

        // 2 turn
        checkPlayableAbility("A can't use bolas on 2", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Balduvian Bears", false);
        checkPlayableAbility("B can't use bolas on 2", 2, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Grizzly Bears", false);

        // 3 turn
        checkPlayableAbility("A can use bolas on 3", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Balduvian Bears", true);
        checkPlayableAbility("B cant use bolas on 3", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Grizzly Bears", false);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();
    }
}
