package org.mage.test.cards.single.otj;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class SatoruTheInfiltratorTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.s.SatoruTheInfiltrator Satoru, the Infiltrator} {U}{B}
     * Legendary Creature — Human Ninja Rogue
     * Menace
     * Whenever Satoru, the Infiltrator and/or one or more other nontoken creatures enter the battlefield under your control, if none of them were cast or no mana was spent to cast them, draw a card.
     */
    private static final String satoru = "Satoru, the Infiltrator";

    /**
     * {@link mage.cards.f.FreestriderCommando Freestrider Commando} {2}{G}
     * Creature — Centaur Mercenary
     * Freestrider Commando enters the battlefield with two +1/+1 counters on it if it wasn’t cast or no mana was spent to cast it.
     * Plot {3}{G} (You may pay {3}{G} and exile this card from your hand. Cast it as a sorcery on a later turn without paying its mana cost. Plot only as a sorcery.)
     * 3/3
     */
    private static final String commando = "Freestrider Commando";

    @Test
    public void test_RegularCast_Other() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, satoru);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, commando);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, commando);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, commando, 1);
        assertHandCount(playerA, 0); // no card draw.
    }

    @Test
    public void test_RegularCast_Other_Memnite() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, satoru);
        addCard(Zone.HAND, playerA, "Memnite"); // Is free!

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Memnite", 1);
        assertHandCount(playerA, 1);
    }

    @Test
    public void test_RegularCast_Satoru() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Underground Sea", 2);
        addCard(Zone.HAND, playerA, satoru);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, satoru);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, satoru, 1);
        assertHandCount(playerA, 0); // no card draw.
    }

    @Test
    public void test_PlotCast() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, satoru);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, commando);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plot");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, commando + " using Plot");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, commando, 1);
        assertHandCount(playerA, 1 + 1); // Drawn 1 from draw step + 1 from Satoru
    }

    @Test
    public void test_Omniscience_CastOther() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, satoru);
        addCard(Zone.BATTLEFIELD, playerA, "Omniscience");
        addCard(Zone.HAND, playerA, commando);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, commando);
        setChoice(playerA, true); // Omniscience asks for confirmation to cast to avoid missclick?

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, commando, 1);
        assertHandCount(playerA, 1); // Drawn 1 from Satoru
    }

    @Test
    public void test_Omniscience_CastSatoru() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Omniscience");
        addCard(Zone.HAND, playerA, satoru);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, satoru);
        setChoice(playerA, true); // Omniscience asks for confirmation to cast to avoid missclick?

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, satoru, 1);
        assertHandCount(playerA, 1); // Drawn 1 from Satoru
    }

    @Test
    public void test_PlotCast_WithTax() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, satoru);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, commando);
        addCard(Zone.BATTLEFIELD, playerA, "Sphere of Resistance"); // Spells cost {1} more to cast

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plot");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, commando + " using Plot");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, commando, 1);
        assertHandCount(playerA, 1); // Drawn 1 from draw step, 0 from Satoru
    }

    @Test
    public void test_Blink() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, satoru);
        addCard(Zone.BATTLEFIELD, playerA, "Savannah", 4);
        addCard(Zone.HAND, playerA, commando);
        addCard(Zone.HAND, playerA, "Ephemerate");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, commando);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ephemerate", commando);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, commando, 1);
        assertHandCount(playerA, 1); // Drawn 1 from Satoru
    }

    @Test
    public void test_MultipleOtherEnter() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, satoru);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        addCard(Zone.GRAVEYARD, playerA, commando, 3);
        addCard(Zone.HAND, playerA, "Storm of Souls"); // Return all creature cards from your graveyard to the battlefield. Each of them is a 1/1 Spirit with flying in addition to its other types. Exile Storm of Souls.

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Storm of Souls");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, commando, 3);
        assertHandCount(playerA, 1); // Drawn 1 from Satoru
    }

    @Test
    public void test_Saturo_AndAnother_Enter() {
        setStrictChooseMode(true);

        addCard(Zone.GRAVEYARD, playerA, satoru);
        addCard(Zone.GRAVEYARD, playerA, commando, 3);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        // Each player exiles all creature cards from their graveyard, then sacrifices all creatures they control,
        // then puts all cards they exiled this way onto the battlefield.
        addCard(Zone.HAND, playerA, "Living Death");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Living Death");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, commando, 3);
        assertPermanentCount(playerA, satoru, 1);
        assertHandCount(playerA, 1); // Drawn 1 from Satoru
    }

    @Test
    public void test_CopyOnStack() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, satoru);
        addCard(Zone.HAND, playerA, commando);
        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 5);
        // Copy target creature spell you control, except it isn’t legendary if the spell is legendary
        addCard(Zone.HAND, playerA, "Double Major");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, commando);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Double Major", commando);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, commando, 2);
        assertPermanentCount(playerA, satoru, 1);
        assertHandCount(playerA, 0); // Drawn 0 from Satoru, as token does not count.
    }

    @Test
    public void test_Tokens() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, satoru);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Krenko's Command");  // Create two 1/1 red Goblin creature tokens.

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Krenko's Command");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Goblin Token", 2);
        assertPermanentCount(playerA, satoru, 1);
        assertHandCount(playerA, 0); // Drawn 0 from Satoru, as token does not count.
    }

    @Test
    public void test_CopySatoruOnStack() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, satoru);
        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        // Copy target creature spell you control, except it isn’t legendary if the spell is legendary
        addCard(Zone.HAND, playerA, "Double Major");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, satoru);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Double Major", satoru);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, satoru, 2);
        assertHandCount(playerA, 1); // While Satoru does not trigger on other tokens, a copy of Satoru on the stack will trigger its own etb.
    }
}
