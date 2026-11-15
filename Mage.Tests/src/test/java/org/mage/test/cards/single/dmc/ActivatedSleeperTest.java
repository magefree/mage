package org.mage.test.cards.single.dmc;

import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 707.9b
 * Some copy effects modify a characteristic or another copiable value as part of the copying process.
 * That characteristic or value becomes part of the copiable values of the copy.
 *
 * @author JayDi85
 */
public class ActivatedSleeperTest extends CardTestPlayerBase {

    /**
     * Activated Sleeper {2}{B}
     * Creature — Phyrexian Shapeshifter
     * Flash
     * You may have this creature enter as a copy of any creature card in a graveyard that was put there from the
     * battlefield this turn, except it's a Phyrexian in addition to its other types.
     */
    private static final String sleeper = "Activated Sleeper";

    @Test
    public void test_CopyIsPhyrexian() {
        addCard(Zone.HAND, playerA, sleeper);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}, 3 damage
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Elvish Visionary"); // 1/1 elf, ETB: draw a card

        // kill
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Elvish Visionary");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // copy
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sleeper);
        setChoice(playerA, true); // use copy
        setChoice(playerA, "Elvish Visionary");
        // Activated Sleeper ruling:
        // Treat Activated Sleeper as though it were the chosen card entering the battlefield, except it's also a
        // Phyrexian. Any "As [this creature] enters the battlefield," "[This creature] enters the battlefield with,"
        // and "When [this creature] enters the battlefield" abilities of the chosen card will work.
        // (2022-09-09)

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Elvish Visionary", 1);
        assertPowerToughness(playerA, "Elvish Visionary", 1, 1);
        assertSubtype("Elvish Visionary", SubType.ELF);
        assertSubtype("Elvish Visionary", SubType.PHYREXIAN);
        assertHandCount(playerA, 1); // draw by copied ETB
    }

    @Test
    public void test_TokenCopyOfCopyIsPhyrexian() {
        addCard(Zone.HAND, playerA, sleeper);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}, 3 damage
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        //
        // Create a token that's a copy of target creature you control.
        addCard(Zone.HAND, playerA, "Cackling Counterpart"); // {1}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // 2/2 bear

        // kill
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // copy
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}", 3); // keep islands for the token
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sleeper);
        setChoice(playerA, true); // use copy
        setChoice(playerA, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // token copy of the copy
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cackling Counterpart", "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 2);
        assertTokenCount(playerA, "Grizzly Bears", 1);
        Permanent token = currentGame.getBattlefield().getAllActivePermanents().stream()
                .filter(permanent -> permanent instanceof PermanentToken)
                .findFirst()
                .orElse(null);
        Assert.assertNotNull("must have token copy", token);
        Assert.assertTrue("token copy must keep phyrexian", token.hasSubtype(SubType.PHYREXIAN, currentGame));
    }
}
