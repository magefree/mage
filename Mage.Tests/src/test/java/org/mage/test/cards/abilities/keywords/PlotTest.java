/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class PlotTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.d.DjinnOfFoolsFall Djinn of Fool's Fall} {4}{U}
     * Creature — Djinn
     * Flying
     * Plot {3}{U}
     * 4/3
     */
    private static final String djinn = "Djinn of Fool's Fall";

    @Test
    public void TestSimplePlot() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, djinn);

        checkPlayableAbility("plot can't be used during upkeep", 1, PhaseStep.UPKEEP, playerA, "Plot", false);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plot");
        checkExileCount("plot is in exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, djinn, 1);

        checkPlayableAbility("Can not be cast on same turn", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast " + djinn + " using Plot", false);
        checkPlayableAbility("Can not be cast on opponent turn", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + djinn + " using Plot", false);
        checkPlayableAbility("Can not be cast on non-main phase", 3, PhaseStep.UPKEEP, playerA, "Cast " + djinn + " using Plot", false);
        checkPlayableAbility("Can be cast on main phase", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast " + djinn + " using Plot", true);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, djinn + " using Plot");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, djinn, 1);
        assertTappedCount("Island", true, 0);
    }

    /**
     * {@link mage.cards.l.LonghornSharpshooter Longhorn Sharpshooter} {2}{R}
     * Creature — Minotaur Rogue
     * Reach
     * When Longhorn Sharpshooter becomes plotted, it deals 2 damage to any target.
     * Plot {3}{R} (You may pay {3}{R} and exile this card from your hand. Cast it as a sorcery on a later turn without paying its mana cost. Plot only as a sorcery.)
     * 3/3
     */
    private static final String sharpshooter = "Longhorn Sharpshooter";

    @Test
    public void TestSharpshooterTrigger() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, sharpshooter);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plot");
        addTarget(playerA, playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, sharpshooter, 1);
        assertLife(playerB, 20 - 2);
    }

    /**
     * {@link mage.cards.k.KellanJoinsUp Kellan Joins Up} {G}{W}{U}
     * Legendary Enchantment
     * <p>
     * When Kellan Joins Up enters the battlefield, you may exile a nonland card with mana value 3 or less from your hand. If you do, it becomes plotted. (You may cast it as a sorcery on a later turn without paying its mana cost.)
     * <p>
     * Whenever a legendary creature enters the battlefield under your control, put a +1/+1 counter on each creature you control.
     */
    private static final String kellanJoinsUp = "Kellan Joins Up";

    @Test
    public void TestKellanJoinsUpTriggerSharpshooter() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.HAND, playerA, kellanJoinsUp);
        addCard(Zone.HAND, playerA, sharpshooter);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, kellanJoinsUp);
        addTarget(playerA, sharpshooter); // choose sharpshooter to exile & plot
        addTarget(playerA, playerB); // sharpshooter does trigger and deals 2

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, sharpshooter + " using Plot");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, sharpshooter, 1);
        assertLife(playerB, 20 - 2);
    }

    @Test
    public void TestPlottingInstant() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.HAND, playerA, kellanJoinsUp);
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, kellanJoinsUp);
        addTarget(playerA, "Lightning Bolt"); // choose Bolt to exile & plot

        checkPlayableAbility("Can not be cast on non-main phase", 3, PhaseStep.UPKEEP, playerA, "Cast Lightning Bolt using Plot", false);
        checkPlayableAbility("Can be cast on main phase", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Lightning Bolt using Plot", true);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt using Plot", playerB);

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertLife(playerB, 20 - 3);
    }

    @Test
    public void TestPlottingAdventure_CastRegularSide() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.HAND, playerA, kellanJoinsUp);
        addCard(Zone.HAND, playerA, "Bramble Familiar");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, kellanJoinsUp);
        addTarget(playerA, "Bramble Familiar");

        checkPlayableAbility("Can not be cast on non-main phase", 3, PhaseStep.UPKEEP, playerA, "Cast Bramble Familiar using Plot", false);
        checkPlayableAbility("Can not be cast on non-main phase", 3, PhaseStep.UPKEEP, playerA, "Cast Fetch Quest using Plot", false);
        checkPlayableAbility("Can be cast on main phase", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Bramble Familiar using Plot", true);
        checkPlayableAbility("Can be cast on main phase", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Fetch Quest using Plot", true);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Bramble Familiar using Plot");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Bramble Familiar", 1);
    }

    @Test
    public void TestPlottingAdventure_CastAdventureSide() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.HAND, playerA, kellanJoinsUp);
        addCard(Zone.HAND, playerA, "Bramble Familiar");
        addCard(Zone.LIBRARY, playerA, "Plateau", 2); // The card. Add 2 since one will be drawn t3

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, kellanJoinsUp);
        addTarget(playerA, "Bramble Familiar");

        checkPlayableAbility("Can not be cast on non-main phase", 3, PhaseStep.UPKEEP, playerA, "Cast Bramble Familiar using Plot", false);
        checkPlayableAbility("Can not be cast on non-main phase", 3, PhaseStep.UPKEEP, playerA, "Cast Fetch Quest using Plot", false);
        checkPlayableAbility("Can be cast on main phase", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Bramble Familiar using Plot", true);
        checkPlayableAbility("Can be cast on main phase", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Fetch Quest using Plot", true);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Fetch Quest using Plot");
        setChoice(playerA, "Plateau"); // choice for Fetch Quest

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, "Bramble Familiar", 1); // The card is back in exile, but as an adventuring card, not a plotted one.
        assertPermanentCount(playerA, "Plateau", 1);
    }

    @Test
    public void TestPlottingSplit_CastLeft() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.HAND, playerA, kellanJoinsUp);
        addCard(Zone.HAND, playerA, "Wear // Tear");
        addCard(Zone.BATTLEFIELD, playerB, "Memnite");
        addCard(Zone.BATTLEFIELD, playerB, "Glorious Anthem");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, kellanJoinsUp);
        addTarget(playerA, "Wear // Tear");

        checkExileCount("assert the full card is in exile", 1, PhaseStep.BEGIN_COMBAT, playerA, "Wear // Tear", 1);

        checkPlayableAbility("Can not be cast on non-main phase", 3, PhaseStep.UPKEEP, playerA, "Cast Wear using Plot", false);
        checkPlayableAbility("Can not be cast on non-main phase", 3, PhaseStep.UPKEEP, playerA, "Cast Tear using Plot", false);
        checkPlayableAbility("Can be cast on main phase", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Wear using Plot", true);
        checkPlayableAbility("Can be cast on main phase", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Tear using Plot", true);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Wear using Plot", "Memnite");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Memnite", 0);
        assertPermanentCount(playerB, "Glorious Anthem", 1);
        assertGraveyardCount(playerA, "Wear // Tear", 1);
    }

    @Test
    public void TestPlottingSplit_CastRight() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.HAND, playerA, kellanJoinsUp);
        addCard(Zone.HAND, playerA, "Wear // Tear");
        addCard(Zone.BATTLEFIELD, playerB, "Memnite");
        addCard(Zone.BATTLEFIELD, playerB, "Glorious Anthem");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, kellanJoinsUp);
        addTarget(playerA, "Wear // Tear");

        checkExileCount("assert the full card is in exile", 1, PhaseStep.BEGIN_COMBAT, playerA, "Wear // Tear", 1);

        checkPlayableAbility("Can not be cast on non-main phase", 3, PhaseStep.UPKEEP, playerA, "Cast Wear using Plot", false);
        checkPlayableAbility("Can not be cast on non-main phase", 3, PhaseStep.UPKEEP, playerA, "Cast Tear using Plot", false);
        checkPlayableAbility("Can be cast on main phase", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Wear using Plot", true);
        checkPlayableAbility("Can be cast on main phase", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Tear using Plot", true);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Tear using Plot", "Glorious Anthem");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Memnite", 1);
        assertPermanentCount(playerB, "Glorious Anthem", 0);
        assertGraveyardCount(playerA, "Wear // Tear", 1);
    }

    @Test
    public void TestPlottingMDFC() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.HAND, playerA, kellanJoinsUp);
        addCard(Zone.HAND, playerA, "Tangled Florahedron // Tangled Vale");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, kellanJoinsUp);
        addTarget(playerA, "Tangled Florahedron");

        checkExileCount("assert the card is in exile", 1, PhaseStep.BEGIN_COMBAT, playerA, "Tangled Florahedron", 1);

        checkPlayableAbility("Can not be cast on non-main phase", 3, PhaseStep.UPKEEP, playerA, "Cast Tangled Florahedron using Plot", false);
        checkPlayableAbility("Can not cast lands", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Tangled Vale using Plot", false);
        checkPlayableAbility("Can be cast on main phase", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Tangled Florahedron using Plot", true);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Tangled Florahedron using Plot");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Tangled Florahedron", 1);
    }
}
