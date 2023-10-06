package org.mage.test.cards.replacement.entersBattlefield;

import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class PrimalClayTest extends CardTestPlayerBase {

    private static final String clay = "Primal Clay";
    // As Primal Clay enters the battlefield, it becomes your choice of a 3/3 artifact creature, a 2/2 artifact creature
    // with flying, or a 1/6 Wall artifact creature with defender in addition to its other types.
    private static final String plasma = "Primal Plasma";
    // As Primal Plasma enters the battlefield, it becomes your choice of a 3/3 creature, a 2/2 creature with flying,
    // or a 1/6 creature with defender.
    private static final String sentry = "Molten Sentry";
    // As Molten Sentry enters the battlefield, flip a coin. If the coin comes up heads, Molten Sentry enters the battlefield
    // as a 5/2 creature with haste. If it comes up tails, Molten Sentry enters the battlefield as a 2/5 creature with defender.
    private static final String aquamorph = "Aquamorph Entity";
    // As Aquamorph Entity enters the battlefield or is turned face up, it becomes your choice of 5/1 or 1/5.
    private static final String tunnel = "Tunnel";
    // Destroy target Wall. It can't be regenerated.
    private static final String clone = "Clone";
    // You may have Clone enter the battlefield as a copy of any creature on the battlefield.
    private static final String cryptoplasm = "Cryptoplasm";
    // At the beginning of your upkeep, you may have Cryptoplasm become a copy of another target creature, except it has this ability.
    private static final String cloudshift = "Cloudshift";
    // Exile target creature you control, then return that card to the battlefield under your control.

    @Test
    public void testClayPTSet() {
        addCard(Zone.HAND, playerA, clay);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, clay);
        setChoice(playerA, "a 3/3 artifact creature");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, clay, 3, 3);
    }

    @Test
    public void testClayAbilityGained() {
        addCard(Zone.HAND, playerA, clay);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, clay);
        setChoice(playerA, "a 2/2 artifact creature with flying");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, clay, 2, 2);
        assertAbility(playerA, clay, FlyingAbility.getInstance(), true);
    }

    @Test
    @Ignore("current workaround implementation doesn't account for this")
    public void testClaySubtypeGained() {
        addCard(Zone.HAND, playerA, clay);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, clay);
        setChoice(playerA, "a 1/6 Wall artifact creature with defender");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, clay, 1, 6);
        assertAbility(playerA, clay, DefenderAbility.getInstance(), true);
        assertSubtype(clay, SubType.WALL);
    }

    @Test
    public void testClayCopyPTOnBattlefield() {
        addCard(Zone.HAND, playerA, clay);
        addCard(Zone.HAND, playerA, cryptoplasm);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 3);
        addCard(Zone.HAND, playerB, "The Battle of Bywater", 1);
        // Destroy all creatures with power 3 or greater. Then create a Food token for each creature you control.

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, clay);
        setChoice(playerA, "a 3/3 artifact creature");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, cryptoplasm);

        // cryptoplasm trigger at next upkeep
        setChoice(playerA, true); // whether to copy
        addTarget(playerA, clay); // what to copy

        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, "The Battle of Bywater");
        // since cryptoplasm now a 3/3, both are destroyed

        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, clay, 1);
        assertGraveyardCount(playerA, cryptoplasm, 1);
    }

    @Ignore("Chosen characteristics of Primal Clay should be copiable values")
    @Test
    public void testClayCopySubtypeOnBattlefield() {
        addCard(Zone.HAND, playerA, clay);
        addCard(Zone.HAND, playerA, cryptoplasm);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        addCard(Zone.HAND, playerB, tunnel, 2);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, clay);
        setChoice(playerA, "a 1/6 Wall artifact creature with defender");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, cryptoplasm);

        // cryptoplasm trigger at next upkeep
        setChoice(playerA, true); // whether to copy
        addTarget(playerA, clay); // what to copy

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerB, tunnel, clay);
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerB, tunnel, clay);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, clay, 1);
        assertGraveyardCount(playerA, cryptoplasm, 1);
    }

    @Test
    public void testPlasmaClone() {
        addCard(Zone.HAND, playerA, plasma);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerB, clone);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, plasma);
        setChoice(playerA, "a 1/6 creature with defender");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, clone);
        setChoice(playerB, true); // whether to copy
        setChoice(playerB, plasma); // what to copy
        setChoice(playerB, "a 2/2 creature with flying"); // new choice as ETB

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, plasma, 1, 6);
        assertPowerToughness(playerB, plasma, 2, 2);
        assertAbility(playerB, plasma, FlyingAbility.getInstance(), true);
        //assertAbility(playerB, plasma, DefenderAbility.getInstance(), true); TODO: this is a copiable value
    }

    @Test
    public void testMoltenSentryClone() {
        addCard(Zone.HAND, playerA, sentry);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerB, clone);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);

        setFlipCoinResult(playerA, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sentry);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, clone);
        setChoice(playerB, true); // whether to copy
        setFlipCoinResult(playerB, false);
        setChoice(playerB, sentry); // what to copy

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, sentry, 5, 2);
        assertAbility(playerA, sentry, HasteAbility.getInstance(), true);
        assertPowerToughness(playerB, sentry, 2, 5);
        assertAbility(playerB, sentry, DefenderAbility.getInstance(), true);
    }

    @Test
    public void testAquamorphEntityETB() {
        addCard(Zone.HAND, playerA, aquamorph);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, aquamorph);
        setChoice(playerA, "a 5/1 creature");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, aquamorph, 5, 1);
    }

    @Test
    public void testAquamorphEntityUnmorph() {
        addCard(Zone.HAND, playerA, aquamorph);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        addCard(Zone.HAND, playerA, "Island");
        addCard(Zone.HAND, playerA, "Savage Swipe", 1);
        // Target creature you control gets +2/+2 until end of turn if its power is 2. Then it fights target creature you don’t control.
        addCard(Zone.BATTLEFIELD, playerB, "Siege Mastodon", 1); // 3/5 creature for fighting

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, aquamorph+" using Morph");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Savage Swipe");
        addTarget(playerA, ""); // morph
        addTarget(playerA, "Siege Mastodon");
        // 2/2 becomes 4/4, fights 3/5, neither dies
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Island");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{U}: Turn"); // unmorph
        setChoice(playerA, "a 1/5 creature");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, aquamorph, 1 + 2, 5 + 2);
        assertDamageReceived(playerA, aquamorph, 3);
        assertDamageReceived(playerB, "Siege Mastodon", 4);
    }

    @Test
    public void testClayFlicker() {
        addCard(Zone.HAND, playerA, clay);
        addCard(Zone.HAND, playerA, cloudshift);
        addCard(Zone.BATTLEFIELD, playerA, "Waterkin Shaman");
        // 2/1; Whenever a creature with flying enters the battlefield under your control, Waterkin Shaman gets +1/+1 until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, clay);
        setChoice(playerA, "a 2/2 artifact creature with flying");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, cloudshift, clay);
        setChoice(playerA, "a 3/3 artifact creature");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, clay, 3, 3);
        assertPowerToughness(playerA, "Waterkin Shaman", 2 + 1, 1 + 1);
        assertAbility(playerA, clay, FlyingAbility.getInstance(), false);
    }

    @Test
    public void testClayFlickerWall() {
        addCard(Zone.HAND, playerA, clay);
        addCard(Zone.HAND, playerA, cloudshift);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, clay);
        setChoice(playerA, "a 1/6 artifact creature with defender");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, cloudshift, clay);
        setChoice(playerA, "a 3/3 artifact creature");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, clay, 3, 3);
        assertNotSubtype(clay, SubType.WALL);
    }

}
