package org.mage.test.cards.single.gtc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class DiluvianPrimordialTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.d.DiluvianPrimordial Diluvian Primordial} {5}{U}{U}
     * Creature — Avatar
     * Flying
     * When Diluvian Primordial enters the battlefield, for each opponent, you may cast up to one target instant or sorcery card from that player’s graveyard without paying its mana cost. If a spell cast this way would be put into a graveyard, exile it instead.
     * 5/5
     */
    private static final String primordial = "Diluvian Primordial";

    // Bug: NPE on casting Valakut Awakening
    @Test
    public void test_MDFC() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, primordial);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        addCard(Zone.GRAVEYARD, playerB, "Valakut Awakening"); // MDFC Instant / Land

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, primordial);
        addTarget(playerA, "Valakut Awakening");
        setChoice(playerA, true); // Yes to "You may"
        setChoice(playerA, TestPlayer.CHOICE_SKIP); // No choice for Awakening's effect

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerB, "Valakut Awakening", 1);
    }

    @Test
    public void test_Split() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, primordial);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        addCard(Zone.GRAVEYARD, playerB, "Fire // Ice");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, primordial);
        addTarget(playerA, "Fire // Ice");
        setChoice(playerA, true); // Yes to "You may"
        setChoice(playerA, "Cast Ice"); // Choose what part of the card to cast
        addTarget(playerA, primordial);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerB, "Fire // Ice", 1);
        assertTapped(primordial, true);
    }

    @Test
    public void test_Adventure() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, primordial);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        addCard(Zone.GRAVEYARD, playerB, "Twice Upon a Time"); // Adventure with Sorcery on main face

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, primordial);
        addTarget(playerA, "Twice Upon a Time");
        setChoice(playerA, true); // Yes to "You may"
        setChoice(playerA, "Cast Unlikely Meeting"); // Cast Adventure side
        addTarget(playerA, TestPlayer.TARGET_SKIP); // not searching for a Doctor

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerB, "Twice Upon a Time", 1);
    }
}
